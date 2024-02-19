package com.myunidays.launchdarkly

import android.app.Application
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Suppress("TooManyFunctions")
actual class LDClient actual constructor(
    appContext: Any?,
    config: LDConfig,
    context: LDContext,
    onReady: () -> Unit
) {

    internal actual val json: Json = Json {
        ignoreUnknownKeys = true
    }

    private val android =
        com.launchdarkly.sdk.android.LDClient.init(
            appContext as Application,
            config.android,
            context.android
        ).get()

    init {
        onReady()
    }

    actual val allFlags: Map<String, LDValue>
        get() = android
            .allFlags()
            .map { it.key to LDValue(it.value) }
            .toMap()

    actual fun boolVariation(
        key: String,
        defaultValue: Boolean
    ): Boolean = android.boolVariation(key, defaultValue)

    actual fun intVariation(
        key: String,
        defaultValue: Int
    ): Int = android.intVariation(key, defaultValue)

    actual fun doubleVariation(
        key: String,
        defaultValue: Double
    ): Double = android.doubleVariation(key, defaultValue)

    actual fun stringVariation(
        key: String,
        defaultValue: String
    ): String = android.stringVariation(key, defaultValue)

    actual fun boolVariationDetail(
        key: String,
        defaultValue: Boolean
    ): EvaluationDetailInterface<Boolean> = EvaluationDetail(android.boolVariationDetail(key, defaultValue))

    actual fun intVariationDetail(
        key: String,
        defaultValue: Int
    ): EvaluationDetailInterface<Int> = EvaluationDetail(android.intVariationDetail(key, defaultValue))

    actual fun doubleVariationDetail(
        key: String,
        defaultValue: Double
    ): EvaluationDetailInterface<Double> = EvaluationDetail(android.doubleVariationDetail(key, defaultValue))

    actual fun stringVariationDetail(
        key: String,
        defaultValue: String
    ): EvaluationDetailInterface<String> = EvaluationDetail(android.stringVariationDetail(key, defaultValue))

    actual fun close() {
        android.close()
    }

    actual fun jsonValueVariation(
        key: String,
        defaultValue: LDValue
    ): LDValue = LDValue(android.jsonValueVariation(key, defaultValue.android))

    actual fun <T> jsonValueVariation(
        key: String,
        deserializer: KSerializer<T>
    ): T? =
        android.jsonValueVariation(key, com.launchdarkly.sdk.LDValue.ofNull())
            .takeUnless { it.isNull }
            ?.let {
                json.decodeFromString(
                    deserializer,
                    it.toJsonString()
                )
            }

    actual fun <T> jsonListValueVariation(
        key: String,
        deserializer: KSerializer<T>
    ): List<T> =
        android.jsonValueVariation(key, com.launchdarkly.sdk.LDValue.ofNull())
            .takeUnless { it.isNull }
            ?.let {
                json.decodeFromString(
                    ListSerializer(deserializer),
                    it.toJsonString()
                )
            }
            ?: emptyList()

    actual fun identify(context: LDContext) {
        android.identify(context.android)
    }
}
