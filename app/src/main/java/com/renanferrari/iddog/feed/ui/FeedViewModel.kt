package com.renanferrari.iddog.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.renanferrari.iddog.common.DefaultDispatcherProvider
import com.renanferrari.iddog.common.DispatcherProvider
import com.renanferrari.iddog.common.Result.Failure
import com.renanferrari.iddog.common.Result.Success
import com.renanferrari.iddog.feed.action.GetDogsByBreed
import com.renanferrari.iddog.feed.model.Dog
import com.renanferrari.iddog.feed.model.Dog.Breed
import com.renanferrari.iddog.user.actions.SignOut
import kotlinx.coroutines.launch

class FeedViewModel(
  private val getDogsByBreed: GetDogsByBreed,
  private val signOut: SignOut,
  private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

  private val _state = MutableLiveData<State>()
  val state: LiveData<State> = liveData(dispatcherProvider.io()) {
    emit(State.loading())
    emitSource(_state)
  }

  fun setBreed(breed: Breed) {
    viewModelScope.launch(dispatcherProvider.io()) {
      when (val result = getDogsByBreed.execute(breed)) {
        is Success -> _state.postValue(State(result.value))
        is Failure -> _state.postValue(State.error(result.cause.message.orEmpty()))
      }
    }
  }

  fun signOut() = signOut.execute()

  data class State(
    val dogs: List<Dog>,
    val loading: Boolean = false,
    val error: String? = null
  ) {
    companion object {
      fun empty() = State(emptyList())
      fun loading() = State(emptyList(), true)
      fun error(error: String?) = State(emptyList(), false, error)
    }
  }
}
