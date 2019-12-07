package com.renanferrari.iddog.feed.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.renanferrari.iddog.common.CoroutineScopeRule
import com.renanferrari.iddog.common.TestingData.HUSKY_CATEGORY
import com.renanferrari.iddog.common.TestingData.HUSKY_DOG_LIST
import com.renanferrari.iddog.common.TestingData.HUSKY_URL_LIST
import com.renanferrari.iddog.common.TestingData.makeErrorResponse
import com.renanferrari.iddog.common.TestingData.makeSuccessResponse
import com.renanferrari.iddog.common.captureValues
import com.renanferrari.iddog.common.getValueForTest
import com.renanferrari.iddog.feed.action.GetDogsByBreed
import com.renanferrari.iddog.common.HttpError
import com.renanferrari.iddog.feed.model.Dog.Breed.HUSKY
import com.renanferrari.iddog.feed.model.DogsApi
import com.renanferrari.iddog.feed.model.DogsApi.FeedResponse
import com.renanferrari.iddog.feed.ui.FeedViewModel.State
import com.renanferrari.iddog.user.actions.SignOut
import com.renanferrari.iddog.user.model.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FeedViewModelTest {

  @get:Rule var coroutineScope = CoroutineScopeRule()
  @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

  private val api: DogsApi = mock()
  private val repository: UserRepository = mock()
  private val getDogsByBreed = GetDogsByBreed(api)
  private val signOut = SignOut(repository)
  private lateinit var feedViewModel: FeedViewModel

  @Before fun before() {
    feedViewModel = FeedViewModel(getDogsByBreed, signOut, coroutineScope.testDispatcherProvider)
  }

  @Test fun `Initial state must be loading`() =
      coroutineScope.runBlockingTest {
        assertThat(feedViewModel.state.getValueForTest()).isEqualTo(State.loading())
      }

  @Test fun `When api response is null, should return empty state`() =
      coroutineScope.runBlockingTest {
        whenever(api.feed(any())).thenReturn(makeSuccessResponse(null))

        feedViewModel.state.captureValues {
          feedViewModel.setBreed(HUSKY)
          assertThat(values).isEqualTo(
              listOf(
                  State.loading(),
                  State.empty()
              )
          )
        }
      }

  @Test fun `When api response is error, should return error state`() =
      coroutineScope.runBlockingTest {
        whenever(api.feed(any())).thenReturn(makeErrorResponse(401))

        feedViewModel.state.captureValues {
          feedViewModel.setBreed(HUSKY)
          assertThat(values).isEqualTo(
              listOf(
                  State.loading(),
                  State.error(HttpError(401).message)
              )
          )
        }
      }

  @Test fun `When api response is success, should return content state`() =
      coroutineScope.runBlockingTest {
        whenever(api.feed(HUSKY_CATEGORY)).thenReturn(
            makeSuccessResponse(FeedResponse(HUSKY_CATEGORY, HUSKY_URL_LIST))
        )

        feedViewModel.state.captureValues {
          feedViewModel.setBreed(HUSKY)
          assertThat(values).isEqualTo(
              listOf(
                  State.loading(),
                  State(HUSKY_DOG_LIST)
              )
          )
        }
      }
}
