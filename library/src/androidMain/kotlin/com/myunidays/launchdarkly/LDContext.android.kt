package com.myunidays.launchdarkly

actual class LDContext actual constructor(key: String) {
    val android = com.launchdarkly.sdk.LDContext.create(key)
}