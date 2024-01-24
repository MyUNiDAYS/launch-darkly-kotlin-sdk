package com.myunidays.launchdarkly

expect class LDValue {
    fun stringValue(): String?

    fun value(): Any?

    fun type(): LDValueType

    companion object {
        val Empty: LDValue
    }
}
