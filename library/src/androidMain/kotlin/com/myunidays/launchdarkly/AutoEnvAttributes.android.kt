package com.myunidays.launchdarkly

actual fun AutoEnvAttributes.toNative(): Any = when (this) {
    AutoEnvAttributes.Enabled -> com.launchdarkly.sdk.android.LDConfig.Builder.AutoEnvAttributes.Enabled
    AutoEnvAttributes.Disabled -> com.launchdarkly.sdk.android.LDConfig.Builder.AutoEnvAttributes.Disabled
}
