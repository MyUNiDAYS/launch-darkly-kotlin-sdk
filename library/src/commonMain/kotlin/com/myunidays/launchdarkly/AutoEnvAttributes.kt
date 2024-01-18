package com.myunidays.launchdarkly

enum class AutoEnvAttributes {
    Enabled,
    Disabled
}

expect fun AutoEnvAttributes.toNative(): Any
