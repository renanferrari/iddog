package com.renanferrari.iddog.user.actions

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.renanferrari.iddog.common.CoroutineScopeRule
import com.renanferrari.iddog.common.TestingData.INVALID_EMAIL
import com.renanferrari.iddog.common.TestingData.USER
import com.renanferrari.iddog.common.TestingData.VALID_EMAIL
import com.renanferrari.iddog.common.TestingData.makeErrorResponse
import com.renanferrari.iddog.common.TestingData.makeSuccessResponse
import com.renanferrari.iddog.user.model.UserApi
import com.renanferrari.iddog.user.model.UserApi.SignUpResponse
import com.renanferrari.iddog.user.actions.SignUp.HttpError
import com.renanferrari.iddog.user.actions.SignUp.InvalidEmailError
import com.renanferrari.iddog.user.actions.SignUp.InvalidResponseError
import com.renanferrari.iddog.user.model.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpTest {

  @get:Rule var coroutineScope = CoroutineScopeRule()

  private val api: UserApi = mock()
  private val repository: UserRepository = mock()
  private lateinit var signUp: SignUp

  @Before fun before() {
    signUp = SignUp(api, repository)
  }

  @Test fun `When email is invalid should not call api and should return error`() =
      coroutineScope.runBlockingTest {
        val result = signUp.execute(INVALID_EMAIL)

        assertThat(result.exceptionOrNull()).isInstanceOf(InvalidEmailError::class.java)

        verifyZeroInteractions(api)
      }

  @Test fun `When response is failure should not call repository and should return error`() =
      coroutineScope.runBlockingTest {
        whenever(api.signUp(any())).thenReturn(makeErrorResponse(401))

        val result = signUp.execute(VALID_EMAIL)

        assertThat(result.exceptionOrNull()).isInstanceOf(HttpError::class.java)

        verify(api).signUp(any())
        verifyZeroInteractions(repository)
      }

  @Test fun `When response has null body should call repository and return error`() =
      coroutineScope.runBlockingTest {
        whenever(api.signUp(any())).thenReturn(makeSuccessResponse())

        val result = signUp.execute(VALID_EMAIL)

        assertThat(result.exceptionOrNull()).isInstanceOf(InvalidResponseError::class.java)

        verify(api).signUp(any())
        verify(repository).setUser(isNull())
      }

  @Test fun `When response is success should call repository and return user`() =
      coroutineScope.runBlockingTest {
        whenever(api.signUp(any())).thenReturn(makeSuccessResponse(SignUpResponse(USER)))

        val result = signUp.execute(VALID_EMAIL)

        assertThat(result.getOrNull()).isEqualTo(USER)

        verify(api).signUp(any())
        verify(repository).setUser(any())
      }
}