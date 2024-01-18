package com.myunidays.launchdarkly

actual class LDConfig actual constructor(mobileKey: String, autoEnvAttributes: AutoEnvAttributes) {
    val android = com.launchdarkly.sdk.android.LDConfig
        .Builder(autoEnvAttributes.toNative() as com.launchdarkly.sdk.android.LDConfig.Builder.AutoEnvAttributes?)
        .mobileKey(mobileKey)
        .build()
}
