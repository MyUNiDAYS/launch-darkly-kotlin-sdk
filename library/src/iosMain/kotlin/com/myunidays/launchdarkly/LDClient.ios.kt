package com.myunidays.launchdarkly

import cocoapods.LaunchDarkly.LDValueTypeArray
import cocoapods.LaunchDarkly.LDValueTypeBool
import cocoapods.LaunchDarkly.LDValueTypeNull
import cocoapods.LaunchDarkly.LDValueTypeNumber
import cocoapods.LaunchDarkly.LDValueTypeObject
import cocoapods.LaunchDarkly.LDValueTypeString
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

    actual fun jsonValueVariationDetail(
        key: String,
        defaultValue: LDValue
    ): EvaluationDetailInterface<LDValue> = ios!!.jsonVariationDetailForKey(key, defaultValue.ios).let {
        JsonValueEvaluationDetail(it, LDValue(it.value()))
    }

    actual fun <T> jsonValueVariation(
        key: String,
        deserializer: KSerializer<T>
    ): T? =
        this.jsonValueVariationDetail(key, deserializer)?.value

    actual fun <T> jsonValueVariationDetail(
        key: String,
        deserializer: KSerializer<T>
    ): EvaluationDetailInterface<T?> =
        ios!!.jsonVariationDetailForKey(key, cocoapods.LaunchDarkly.LDValue.ofNull())
            .let { jsonEvaluationDetail ->
                jsonEvaluationDetail.value().takeUnless { it.getType() == cocoapods.LaunchDarkly.LDValueTypeNull }
                    ?.let { remoteValue ->
                        json.decodeFromString(
                            deserializer,
                            (remoteValue.dictValue() as Map<String, cocoapods.LaunchDarkly.LDValue>)
                                .convertToJsonObject()
                                .toString()
                        )
                    }.let { value ->
                        JsonValueEvaluationDetail(jsonEvaluationDetail, value)
                    }
            }

    actual fun <T> jsonListValueVariation(
        key: String,
        deserializer: KSerializer<T>
    ): List<T> = this.jsonListValueVariationDetail(key, deserializer).value ?: emptyList()

    // Define a generic function that returns an EvaluationDetailInterface for a list of type T
    // The function takes a key and a deserializer for type T as parameters
    actual fun <T> jsonListValueVariationDetail(
        key: String,
        deserializer: KSerializer<T>
    ): EvaluationDetailInterface<List<T>> =

        // Call the iOS-specific jsonVariationDetailForKey method with the provided key
        // and a default null value, then process the result using 'let'
        ios!!.jsonVariationDetailForKey(key, cocoapods.LaunchDarkly.LDValue.ofNull())
            .let { jsonEvaluationDetail ->

                // Extract the value from the jsonEvaluationDetail if its type is not Null
                jsonEvaluationDetail.value().takeUnless { it.getType() == cocoapods.LaunchDarkly.LDValueTypeNull }

                    // If the value is not null, process the remote value
                    ?.let { remoteValue ->

                        // Decode the remote value into a list of type T using the provided deserializer
                        json.decodeFromString(
                            ListSerializer(deserializer),
                            // Convert the remote value to a JSON string
                            JsonArray(
                                // Filter the remote value array to instances of LDValue
                                remoteValue.arrayValue()
                                    .filterIsInstance<cocoapods.LaunchDarkly.LDValue>()
                                    // Map each single remote value to a JsonObject
                                    .map { singleRemoteValue ->

                                        (singleRemoteValue as Map<String, cocoapods.LaunchDarkly.LDValue>)
                                            .convertToJsonObject()
                                    }
                            ).toString()
                        ).let { value ->
                            // Construct a JsonValueEvaluationDetail object from the decoded value
                            JsonValueEvaluationDetail(jsonEvaluationDetail, value)
                        }
                    } // Return an empty list if the value is null
                    ?: JsonValueEvaluationDetail(jsonEvaluationDetail, emptyList())
            }

    actual fun identify(context: LDContext) {
        ios?.identifyWithContext(context.ios)
    }
}

/**
 * Converts a LaunchDarkly dictionary to a JsonObject
 */
fun Map<String, cocoapods.LaunchDarkly.LDValue>.convertToJsonObject(): JsonObject {

    return JsonObject(
        this
            .map {
                it.key to
                    when (it.value.getType()) {
                        LDValueTypeNull -> JsonPrimitive(null)
                        LDValueTypeBool -> JsonPrimitive(it.value.boolValue())
                        LDValueTypeNumber -> JsonPrimitive(it.value.doubleValue())
                        LDValueTypeString -> JsonPrimitive(it.value.stringValue())
                        LDValueTypeArray -> {
                            JsonArray(
                                (
                                    it.value
                                        .arrayValue() as List<cocoapods.LaunchDarkly.LDValue>
                                    )
                                    .map { item ->
                                        JsonPrimitive(item.stringValue())
                                    }
                            )
                        }

                        LDValueTypeObject -> JsonObject(
                            (it.value.dictValue() as Map<String, cocoapods.LaunchDarkly.LDValue>)
                                .convertToJsonObject()
                        )

                        else -> {
                            JsonPrimitive(it.value.stringValue())
                        }
                    }
            }.toMap()
    )
}
