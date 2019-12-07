package com.renanferrari.iddog.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.renanferrari.iddog.common.DefaultDispatcherProvider
import com.renanferrari.iddog.common.DispatcherProvider
import com.renanferrari.iddog.feed.action.GetDogsByBreed
import com.renanferrari.iddog.feed.model.Dog
import com.renanferrari.iddog.feed.model.Dog.Breed
import kotlinx.coroutines.launch

class FeedViewModel(
  private val getDogsByBreed: GetDogsByBreed,
  private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

  private val _state = MutableLiveData<State>()
  val state: LiveData<State> = liveData(dispatcherProvider.io()) {
    emit(State.loading())
    emitSource(_state)
  }

  fun setBreed(breed: Breed) {
    viewModelScope.launch(dispatcherProvider.io()) {
      val result = getDogsByBreed.execute(breed)
      if (result.isSuccess) {
        _state.postValue(State(result.getOrNull() ?: emptyList()))
      } else {
        _state.postValue(State.error(result.exceptionOrNull()?.message))
      }
    }
  }

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
