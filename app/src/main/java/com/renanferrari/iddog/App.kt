package com.renanferrari.iddog

import android.app.Application
import com.renanferrari.iddog.BuildConfig.DEBUG
import com.renanferrari.iddog.di.appModule
import com.renanferrari.iddog.di.domainModule
import com.renanferrari.iddog.di.infrastructureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger(if (DEBUG) Level.DEBUG else Level.INFO)
      androidContext(this@App)
      modules(appModule + domainModule + infrastructureModule)
    }
  }
}