package com.myunidays.launchdarkly

actual class LDValue internal constructor(val android: com.launchdarkly.sdk.LDValue) {
    actual constructor(value: String) : this(com.launchdarkly.sdk.LDValue.of(value))

    actual fun stringValue(): String? = android.stringValue()
    actual fun type(): LDValueType = android.type.toValueType()
    actual fun value(): Any? = when (type()) {
        LDValueType.Null -> null
        LDValueType.Boolean -> android.booleanValue()
        LDValueType.Number -> when {
            android.isInt -> android.intValue()
            else -> android.doubleValue()
        }
        LDValueType.String -> android.stringValue()
        LDValueType.Array -> android.values()
        LDValueType.Object -> android.keys().map { key ->
            key to android.get(key)
        }
    }

    actual companion object {
        actual val Empty: LDValue = LDValue(com.launchdarkly.sdk.LDValue.ofNull())
    }
}
