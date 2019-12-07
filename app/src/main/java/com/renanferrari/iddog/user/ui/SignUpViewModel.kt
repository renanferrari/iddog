package com.renanferrari.iddog.user.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.renanferrari.iddog.common.utils.DefaultDispatcherProvider
import com.renanferrari.iddog.common.utils.DispatcherProvider
import com.renanferrari.iddog.user.actions.GetUser
import com.renanferrari.iddog.user.actions.SignUp
import com.renanferrari.iddog.user.model.User
import kotlinx.coroutines.launch

class SignUpViewModel(
  private val getUser: GetUser,
  private val signUp: SignUp,
  private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

  private val _state = MutableLiveData<State>()
  val state: LiveData<State> = liveData(dispatcherProvider.io()) {
    emit(State(getUser.execute()))
    emitSource(_state)
  }

  fun setEmail(email: String) {
    viewModelScope.launch(dispatcherProvider.io()) {
      _state.postValue(State.loading())
      val result = signUp.execute(email)
      if (result.isSuccess) {
        _state.postValue(State(result.getOrNull()))
      } else {
        _state.postValue(State.error(result.exceptionOrNull()?.message))
      }
    }
  }

  data class State(
    val user: User?,
    val loading: Boolean = false,
    val error: String? = null
  ) {
    companion object {
      fun empty() = State(null)
      fun loading() = State(null, true)
      fun error(error: String?) = State(null, false, error)
    }
  }
}