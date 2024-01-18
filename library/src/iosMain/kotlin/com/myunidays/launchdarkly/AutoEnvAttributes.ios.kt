package com.myunidays.launchdarkly

actual fun AutoEnvAttributes.toNative(): Any = when(this) {
    AutoEnvAttributes.Enabled -> cocoapods.LaunchDarkly.AutoEnvAttributesEnabled
    AutoEnvAttributes.Disabled -> cocoapods.LaunchDarkly.AutoEnvAttributesDisabled
}
