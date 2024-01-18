package com.myunidays.launchdarkly

actual class LDClient actual constructor(appContext: Any?, config: LDConfig, context: LDContext) {
    private lateinit var ios: cocoapods.LaunchDarkly.LDClient

    actual val allFlags: Map<String, LDValue>
        get() = ios
            .allFlags()
            ?.map { it.key as String to LDValue(it.value as cocoapods.LaunchDarkly.LDValue) }
            ?.toMap()
            ?: emptyMap()
    init {
        cocoapods.LaunchDarkly.LDClient.startWithConfiguration(config.ios, context.ios) {
            ios = cocoapods.LaunchDarkly.LDClient.get()!!
        }
    }

    actual fun boolVariation(
        key: String,
        defaultValue: Boolean
    ): Boolean = ios.boolVariationForKey(key, defaultValue)

    actual fun intVariation(
        key: String,
        defaultValue: Int
    ): Int = ios.integerVariationForKey(key, defaultValue.toLong()).toInt()

    actual fun doubleVariation(
        key: String,
        defaultValue: Double
    ): Double = ios.doubleVariationForKey(key, defaultValue)

    actual fun stringVariation(
        key: String,
        defaultValue: String
    ): String = ios.stringVariationForKey(key, defaultValue)

    actual fun boolVariationDetail(
        key: String,
        defaultValue: Boolean
    ): EvaluationDetailInterface<Boolean> = BoolEvaluationDetail(ios.boolVariationDetailForKey(key, defaultValue))

    actual fun intVariationDetail(
        key: String,
        defaultValue: Int
    ): EvaluationDetailInterface<Int> = IntegerEvaluationDetail(ios.integerVariationDetailForKey(key, defaultValue.toLong()))

    actual fun doubleVariationDetail(
        key: String,
        defaultValue: Double
    ): EvaluationDetailInterface<Double> = DoubleEvaluationDetail(ios.doubleVariationDetailForKey(key, defaultValue))

    actual fun stringVariationDetail(
        key: String,
        defaultValue: String
    ): EvaluationDetailInterface<String> = StringEvaluationDetail(ios.stringVariationDetailForKey(key, defaultValue))

    actual fun close() {
        ios.close()
    }
}
