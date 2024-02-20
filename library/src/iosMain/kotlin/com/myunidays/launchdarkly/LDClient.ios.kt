package com.myunidays.launchdarkly

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Suppress("TooManyFunctions")
actual class LDClient actual constructor(
    appContext: Any?,
    config: LDConfig,
    context: LDContext,
    onReady: () -> Unit
) {
    private var ios: cocoapods.LaunchDarkly.LDClient? = null

    internal actual val json: Json = Json {
        ignoreUnknownKeys = true
    }

    actual val allFlags: Map<String, LDValue>
        get() = ios
            ?.allFlags()
            ?.map { it.key as String to LDValue(it.value as cocoapods.LaunchDarkly.LDValue) }
            ?.toMap()
            ?: emptyMap()
    init {
        cocoapods.LaunchDarkly.LDClient.startWithConfiguration(config.ios, context.ios) {
            ios = cocoapods.LaunchDarkly.LDClient.get()
            onReady()
        }
    }

    actual fun boolVariation(
        key: String,
        defaultValue: Boolean
    ): Boolean = ios?.boolVariationForKey(key, defaultValue) ?: defaultValue

    actual fun intVariation(
        key: String,
        defaultValue: Int
    ): Int =
        (ios?.integerVariationForKey(key, defaultValue.toLong()) ?: defaultValue.toLong()).toInt()

    actual fun doubleVariation(
        key: String,
        defaultValue: Double
    ): Double = ios?.doubleVariationForKey(key, defaultValue) ?: defaultValue

    actual fun stringVariation(
        key: String,
        defaultValue: String
    ): String = ios?.stringVariationForKey(key, defaultValue) ?: defaultValue

    actual fun boolVariationDetail(
        key: String,
        defaultValue: Boolean
    ): EvaluationDetailInterface<Boolean> =
        BoolEvaluationDetail(ios!!.boolVariationDetailForKey(key, defaultValue))

    actual fun intVariationDetail(
        key: String,
        defaultValue: Int
    ): EvaluationDetailInterface<Int> =
        IntegerEvaluationDetail(
            ios!!.integerVariationDetailForKey(
                key,
                defaultValue.toLong()
            )
        )

    actual fun doubleVariationDetail(
        key: String,
        defaultValue: Double
    ): EvaluationDetailInterface<Double> = DoubleEvaluationDetail(ios!!.doubleVariationDetailForKey(key, defaultValue))

    actual fun stringVariationDetail(
        key: String,
        defaultValue: String
    ): EvaluationDetailInterface<String> = StringEvaluationDetail(ios!!.stringVariationDetailForKey(key, defaultValue))

    actual fun close() {
        ios?.close()
    }

    actual fun jsonValueVariation(
        key: String,
        defaultValue: LDValue
    ): LDValue = LDValue(ios!!.jsonVariationForKey(key, defaultValue.ios))

    actual fun <T> jsonValueVariation(
        key: String,
        deserializer: KSerializer<T>
    ): T? =
        ios?.jsonVariationForKey(key, cocoapods.LaunchDarkly.LDValue.ofNull())
            .takeUnless { it?.getType() == cocoapods.LaunchDarkly.LDValueTypeNull }
            ?.let { remoteValue ->
                json.decodeFromString(
                    deserializer,
                    JsonObject(
                        remoteValue.dictValue()
                            .mapNotNull {
                                runCatching {
                                    it.key as String to it.value as cocoapods.LaunchDarkly.LDValue
                                }.getOrNull()
                            }.associate { it.first to JsonPrimitive(it.second.stringValue()) }
                    ).toString()
                )
            }

    actual fun <T> jsonListValueVariation(
        key: String,
        deserializer: KSerializer<T>
    ): List<T> =
        ios?.jsonVariationForKey(key, cocoapods.LaunchDarkly.LDValue.ofNull())
            .takeUnless { it?.getType() == cocoapods.LaunchDarkly.LDValueTypeNull }
            ?.let { remoteValue ->
                json.decodeFromString(
                    ListSerializer(deserializer),
                    JsonArray(
                        remoteValue.arrayValue()
                            .filterIsInstance<cocoapods.LaunchDarkly.LDValue>()
                            .map { singleRemoteValue ->
                                JsonObject(
                                    singleRemoteValue.dictValue()
                                        .mapNotNull {
                                            runCatching {
                                                it.key as String to
                                                    it.value as cocoapods.LaunchDarkly.LDValue
                                            }
                                                .getOrNull()
                                        }
                                        .associate {
                                            it.first to JsonPrimitive(it.second.stringValue())
                                        }
                                )
                            }
                    ).toString()
                )
            }
            ?: emptyList()

    actual fun identify(context: LDContext) {
        ios?.identifyWithContext(context.ios)
    }
}
