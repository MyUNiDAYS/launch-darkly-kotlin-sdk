package com.myunidays.launchdarkly

actual class LDContext actual constructor(key: String) {
    val ios = cocoapods.LaunchDarkly.LDContextBuilder(key).build().success()!!
}
