package com.renanferrari.iddog.di

import androidx.preference.PreferenceManager
import com.renanferrari.iddog.BuildConfig.DEBUG
import com.renanferrari.iddog.feed.action.GetDogsByBreed
import com.renanferrari.iddog.feed.model.DogsApi
import com.renanferrari.iddog.feed.ui.FeedViewModel
import com.renanferrari.iddog.user.actions.GetUser
import com.renanferrari.iddog.user.actions.SignUp
import com.renanferrari.iddog.user.infrastructure.SharedPreferencesUserRepository
import com.renanferrari.iddog.user.infrastructure.TokenInterceptor
import com.renanferrari.iddog.user.model.UserApi
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
  viewModel { FeedViewModel(get()) }
}

val appModule = androidModule + viewModelModule

val domainModule = module {
  single { SignUp(get(), get()) }
  single { GetUser(get()) }
  single { GetDogsByBreed(get()) }
}

val apiModule = module {
  single { TokenInterceptor(get()) }
  single { HttpLoggingInterceptor().apply { level = if (DEBUG) Level.BODY else Level.NONE } }
  single {
    OkHttpClient.Builder()
        .addInterceptor(get<TokenInterceptor>())
        .addInterceptor(get<HttpLoggingInterceptor>())
        .build()
  }
  single {
    Retrofit.Builder()
        .baseUrl("https://api-iddog.idwall.co/")
        .client(get())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }
  single { get<Retrofit>().create(UserApi::class.java) }
  single { get<Retrofit>().create(DogsApi::class.java) }
}

val repositoriesModule = module {
  single<UserRepository> { SharedPreferencesUserRepository(get()) }
}

val infrastructureModule = apiModule + repositoriesModule