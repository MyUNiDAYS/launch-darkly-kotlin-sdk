package com.myunidays.launchdarkly

import com.launchdarkly.sdk.android.LDConfig

// need to include autoEnvAttributes
actual class LDConfig actual constructor(mobileKey: String) {
    val android = com.launchdarkly.sdk.android.LDConfig
        .Builder(LDConfig.Builder.AutoEnvAttributes.Enabled)
        .mobileKey(mobileKey)
        .build()
}