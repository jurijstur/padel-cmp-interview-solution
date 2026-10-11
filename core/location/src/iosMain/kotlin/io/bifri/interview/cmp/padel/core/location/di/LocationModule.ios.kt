package io.bifri.interview.cmp.padel.core.location.di

import io.bifri.interview.cmp.padel.core.location.IosLocationService
import io.bifri.interview.cmp.padel.core.location.LocationService
import org.koin.core.module.Module

internal actual fun Module.registerLocationService() {
    single<LocationService> { IosLocationService() }
}
