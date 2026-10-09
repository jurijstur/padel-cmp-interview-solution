package io.bifri.interview.cmp.padel

internal fun String.toBuildConfigString(): String = "\"${replace("\\", "\\\\").replace("\"", "\\\"")}\""
