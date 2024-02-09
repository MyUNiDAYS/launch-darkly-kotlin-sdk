package com.myunidays.launchdarkly

actual class ContextKind actual constructor(kind: String) {
    val android = com.launchdarkly.sdk.ContextKind.of(kind)
}
