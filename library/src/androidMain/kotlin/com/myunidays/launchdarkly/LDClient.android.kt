package com.myunidays.launchdarkly

import android.app.Application

actual class LDClient actual constructor(appContext: Any?, config: LDConfig, context: LDContext) {

    private val android =
        com.launchdarkly.sdk.android.LDClient.init(
            appContext as Application,
            config.android,
            context.android
        ).get()

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

}
