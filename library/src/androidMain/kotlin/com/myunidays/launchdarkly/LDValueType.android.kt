package com.myunidays.launchdarkly

fun com.launchdarkly.sdk.LDValueType.toValueType(): LDValueType = when (this) {
    com.launchdarkly.sdk.LDValueType.NULL -> LDValueType.Null
    com.launchdarkly.sdk.LDValueType.BOOLEAN -> LDValueType.Boolean
    com.launchdarkly.sdk.LDValueType.NUMBER -> LDValueType.Number
    com.launchdarkly.sdk.LDValueType.STRING -> LDValueType.String
    com.launchdarkly.sdk.LDValueType.ARRAY -> LDValueType.Array
    com.launchdarkly.sdk.LDValueType.OBJECT -> LDValueType.Object
}
