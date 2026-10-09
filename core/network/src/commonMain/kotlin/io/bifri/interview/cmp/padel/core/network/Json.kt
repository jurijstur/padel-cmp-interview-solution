package io.bifri.interview.cmp.padel.core.network

import com.ionspin.kotlin.bignum.serialization.kotlinx.bigdecimal.bigDecimalHumanReadableSerializerModule
import com.ionspin.kotlin.bignum.serialization.kotlinx.biginteger.bigIntegerhumanReadableSerializerModule
import io.ktor.serialization.kotlinx.json.DefaultJson
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

internal fun defaultJson() = Json(DefaultJson) {
    explicitNulls = false
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {
        include(bigIntegerhumanReadableSerializerModule)
        include(bigDecimalHumanReadableSerializerModule)
    }
}
