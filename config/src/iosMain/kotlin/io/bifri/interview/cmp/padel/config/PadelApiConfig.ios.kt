package io.bifri.interview.cmp.padel.config

import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import platform.Foundation.dictionaryWithContentsOfURL

internal actual fun padelApiConfig(): PadelApiConfig {
    val url = NSBundle.mainBundle.URLForResource("PadelApi", withExtension = "plist")
    val secrets = url?.let { NSDictionary.dictionaryWithContentsOfURL(it) }
    return PadelApiConfig(
        apiToken = (secrets?.get("ApiToken") as? String).orEmpty(),
    )
}
