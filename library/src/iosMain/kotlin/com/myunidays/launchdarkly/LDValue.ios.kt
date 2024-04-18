package com.myunidays.launchdarkly

actual class LDValue internal constructor(val ios: cocoapods.LaunchDarkly.LDValue) {
    actual constructor(value: String) : this(cocoapods.LaunchDarkly.LDValue.ofString(value))
    actual fun stringValue(): String? = ios.stringValue()
    actual fun type(): LDValueType = ios.getType().toValueType()

    actual fun value(): Any? = when (type()) {
        LDValueType.Null -> null
        LDValueType.Boolean -> ios.boolValue()
        LDValueType.Number -> ios.doubleValue()
        LDValueType.String -> ios.stringValue()
        LDValueType.Array -> ios.arrayValue()
        LDValueType.Object -> ios.dictValue()
    }

    actual companion object {
        actual val Empty: LDValue = LDValue(cocoapods.LaunchDarkly.LDValue.ofNull())
    }
}
