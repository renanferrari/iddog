package com.renanferrari.iddog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.renanferrari.iddog.common.CoroutineScopeRule
import com.renanferrari.iddog.common.Utils.INVALID_EMAIL
import com.renanferrari.iddog.common.Utils.USER
import com.renanferrari.iddog.common.Utils.VALID_EMAIL
import com.renanferrari.iddog.common.Utils.makeSuccessResponse
import com.renanferrari.iddog.common.captureValues
import com.renanferrari.iddog.common.getValueForTest
import com.renanferrari.iddog.common.infrastructure.IDDogApi
import com.renanferrari.iddog.common.infrastructure.IDDogApi.SignUpResponse
import com.renanferrari.iddog.user.actions.GetUser
import com.renanferrari.iddog.user.actions.SignUp
import com.renanferrari.iddog.user.actions.SignUp.InvalidEmailError
import com.renanferrari.iddog.user.model.UserRepository
import com.renanferrari.iddog.user.ui.SignUpViewModel
import com.renanferrari.iddog.user.ui.SignUpViewModel.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpViewModelTest {

  @get:Rule var coroutineScope = CoroutineScopeRule()
  @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

  private val api: IDDogApi = mock()
  private val userRepository: UserRepository = mock()
  private val signUp = SignUp(api, userRepository)
  private val getUser = GetUser(userRepository)
  private lateinit var signUpViewModel: SignUpViewModel

  @Before fun before() {
    signUpViewModel = SignUpViewModel(getUser, signUp, coroutineScope.testDispatcherProvider)
  }

  @Test fun `When user is logged in, should return user state`() =
      coroutineScope.runBlockingTest {
        whenever(userRepository.getUser()).thenReturn(USER)

        assertThat(signUpViewModel.state.getValueForTest()).isEqualTo(State(USER))
      }

  @Test fun `When user is not logged in, should return empty state`() =
      coroutineScope.runBlockingTest {
        whenever(userRepository.getUser()).thenReturn(null)

        assertThat(signUpViewModel.state.getValueForTest()).isEqualTo(State.empty())
      }

  @Test fun `When an invalid email is set, should return loading state and then error state`() =
      coroutineScope.runBlockingTest {
        whenever(userRepository.getUser()).thenReturn(null)

        signUpViewModel.state.captureValues {
          signUpViewModel.setEmail(INVALID_EMAIL)
          assertThat(values).isEqualTo(
              listOf(
                  State.empty(),
                  State.loading(),
                  State.error(InvalidEmailError(INVALID_EMAIL).message)
              )
          )
        }

        verifyZeroInteractions(api)
      }

  @Test fun `When a valid email is set, should return loading state and then user state`() =
      coroutineScope.runBlockingTest {
        whenever(userRepository.getUser()).thenReturn(null)
        whenever(api.signUp(any())).thenReturn(makeSuccessResponse(SignUpResponse(USER)))

        signUpViewModel.state.captureValues {
          signUpViewModel.setEmail(VALID_EMAIL)
          assertThat(values).isEqualTo(
              listOf(
                  State.empty(),
                  State.loading(),
                  State(USER)
              )
          )
        }
      }
}
