package com.myunidays.launchdarkly

actual class LDConfig actual constructor(mobileKey: String, autoEnvAttributes: AutoEnvAttributes) {
    val ios = cocoapods.LaunchDarkly.LDConfig(mobileKey,
        autoEnvAttributes.toNative() as cocoapods.LaunchDarkly.AutoEnvAttributes
    )
}
