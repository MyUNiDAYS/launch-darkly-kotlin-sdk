package com.myunidays.launchdarkly

import cocoapods.LaunchDarkly.AutoEnvAttributesEnabled

// need to include autoEnvAttributes
actual class LDConfig actual constructor(mobileKey: String) {
    val ios = cocoapods.LaunchDarkly.LDConfig(mobileKey, AutoEnvAttributesEnabled)
}