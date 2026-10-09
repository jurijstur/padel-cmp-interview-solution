package io.bifri.interview.cmp.padel.core.ui.image

import coil3.PlatformContext
import okio.Path
import okio.Path.Companion.toPath

actual fun PlatformContext.imageCacheDir(): Path =
    cacheDir.resolve(ImageDiskCacheDir).absolutePath.toPath()
