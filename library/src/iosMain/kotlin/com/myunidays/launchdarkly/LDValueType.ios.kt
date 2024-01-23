package com.myunidays.launchdarkly

fun cocoapods.LaunchDarkly.LDValueType.toValueType(): LDValueType = when (this) {
    cocoapods.LaunchDarkly.LDValueTypeNull -> LDValueType.Null
    cocoapods.LaunchDarkly.LDValueTypeNumber -> LDValueType.Number
    cocoapods.LaunchDarkly.LDValueTypeBool -> LDValueType.Boolean
    cocoapods.LaunchDarkly.LDValueTypeArray -> LDValueType.Array
    cocoapods.LaunchDarkly.LDValueTypeObject -> LDValueType.Object
    cocoapods.LaunchDarkly.LDValueTypeString -> LDValueType.String
    else -> LDValueType.Null
}
