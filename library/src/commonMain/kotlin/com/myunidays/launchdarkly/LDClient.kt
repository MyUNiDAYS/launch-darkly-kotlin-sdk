package com.myunidays.launchdarkly

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

@Suppress("TooManyFunctions")
expect class LDClient(
    appContext: Any? = null,
    config: LDConfig,
    context: LDContext,
    onReady: () -> Unit
) {
    internal val json: Json

    val allFlags: Map<String, LDValue>
    fun boolVariation(key: String, defaultValue: Boolean): Boolean
    fun boolVariationDetail(key: String, defaultValue: Boolean): EvaluationDetailInterface<Boolean>
    fun intVariation(key: String, defaultValue: Int): Int
    fun intVariationDetail(key: String, defaultValue: Int): EvaluationDetailInterface<Int>
    fun doubleVariation(key: String, defaultValue: Double): Double
    fun doubleVariationDetail(key: String, defaultValue: Double): EvaluationDetailInterface<Double>
    fun stringVariation(key: String, defaultValue: String): String
    fun stringVariationDetail(key: String, defaultValue: String): EvaluationDetailInterface<String>
    fun jsonValueVariation(key: String, defaultValue: LDValue): LDValue
    fun <T> jsonValueVariation(key: String, deserializer: KSerializer<T>): T?
    fun <T> jsonListValueVariation(key: String, deserializer: KSerializer<T>): List<T>
    fun close()
    fun identify(context: LDContext)
}
