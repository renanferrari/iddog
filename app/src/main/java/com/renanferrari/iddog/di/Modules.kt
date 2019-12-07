package com.renanferrari.iddog.di

import androidx.preference.PreferenceManager
import com.renanferrari.iddog.BuildConfig.DEBUG
import com.renanferrari.iddog.common.infrastructure.IDDogApi
import com.renanferrari.iddog.user.infrastructure.SharedPreferencesUserRepository
import com.renanferrari.iddog.user.infrastructure.TokenAuthenticator
import com.renanferrari.iddog.user.actions.GetUser
import com.renanferrari.iddog.user.actions.SignUp
import com.renanferrari.iddog.user.model.UserRepository
import com.renanferrari.iddog.user.ui.SignUpViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val androidModule = module {
  single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
}

val viewModelModule = module {
  viewModel { SignUpViewModel(get(), get()) }
}

val appModule = androidModule + viewModelModule

val domainModule = module {
  single { SignUp(get(), get()) }
  single { GetUser(get()) }
}

val apiModule = module {
  single { TokenAuthenticator(get()) }
  single { HttpLoggingInterceptor().apply { level = if (DEBUG) Level.BODY else Level.NONE } }
  single {
    OkHttpClient.Builder()
//      .authenticator(get())
        .addInterceptor(get<HttpLoggingInterceptor>())
        .build()
  }
  single {
    Retrofit.Builder()
        .baseUrl(IDDogApi.BASE_URL)
        .client(get())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IDDogApi::class.java)
  }
}

val repositoriesModule = module {
  single<UserRepository> { SharedPreferencesUserRepository(get()) }
}

val infrastructureModule = apiModule + repositoriesModule