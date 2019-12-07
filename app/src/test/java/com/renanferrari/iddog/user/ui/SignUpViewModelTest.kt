package com.renanferrari.iddog.user.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.renanferrari.iddog.common.CoroutineScopeRule
import com.renanferrari.iddog.common.TestingData.INVALID_EMAIL
import com.renanferrari.iddog.common.TestingData.USER
import com.renanferrari.iddog.common.TestingData.VALID_EMAIL
import com.renanferrari.iddog.common.TestingData.makeSuccessResponse
import com.renanferrari.iddog.common.captureValues
import com.renanferrari.iddog.common.getValueForTest
import com.renanferrari.iddog.user.model.UserApi
import com.renanferrari.iddog.user.model.UserApi.SignUpResponse
import com.renanferrari.iddog.user.actions.GetUser
import com.renanferrari.iddog.user.actions.SignUp
import com.renanferrari.iddog.user.actions.SignUp.InvalidEmailError
import com.renanferrari.iddog.user.model.UserRepository
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

  private val api: UserApi = mock()
  private val repository: UserRepository = mock()
  private val signUp = SignUp(api, repository)
  private val getUser = GetUser(repository)
  private lateinit var signUpViewModel: SignUpViewModel

  @Before fun before() {
    signUpViewModel = SignUpViewModel(getUser, signUp, coroutineScope.testDispatcherProvider)
  }

  @Test fun `When user is logged in, should return user state`() =
      coroutineScope.runBlockingTest {
        whenever(repository.getUser()).thenReturn(USER)

        assertThat(signUpViewModel.state.getValueForTest()).isEqualTo(State(USER))
      }

  @Test fun `When user is not logged in, should return empty state`() =
      coroutineScope.runBlockingTest {
        whenever(repository.getUser()).thenReturn(null)

        assertThat(signUpViewModel.state.getValueForTest()).isEqualTo(State.empty())
      }

  @Test fun `When an invalid email is set, should return loading state and then error state`() =
      coroutineScope.runBlockingTest {
        whenever(repository.getUser()).thenReturn(null)

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
        whenever(repository.getUser()).thenReturn(null)
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
