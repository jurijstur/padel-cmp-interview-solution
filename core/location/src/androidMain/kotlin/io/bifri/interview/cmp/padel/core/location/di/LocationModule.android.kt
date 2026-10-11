package io.bifri.interview.cmp.padel.core.location.di

import io.bifri.interview.cmp.padel.core.location.AndroidLocationService
import io.bifri.interview.cmp.padel.core.location.LocationService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

internal actual fun Module.registerLocationService() {
    single<LocationService> { AndroidLocationService(context = androidContext()) }
}
