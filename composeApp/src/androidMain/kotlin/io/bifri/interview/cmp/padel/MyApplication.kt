package io.bifri.interview.cmp.padel

import android.app.Application
import io.bifri.interview.cmp.padel.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@MyApplication) }
    }
}
