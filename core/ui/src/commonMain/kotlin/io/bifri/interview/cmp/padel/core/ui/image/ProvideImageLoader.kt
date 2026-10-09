package io.bifri.interview.cmp.padel.core.ui.image

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import okio.Path

private const val ImageDiskCacheMaxBytes = 50L * 1024L * 1024L
internal const val ImageDiskCacheDir = "coil_image_cache"

expect fun PlatformContext.imageCacheDir(): Path

@Composable
fun ProvideAppImageLoader() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.imageCacheDir())
                    .maxSizeBytes(ImageDiskCacheMaxBytes)
                    .build()
            }
            .build()
    }
}
