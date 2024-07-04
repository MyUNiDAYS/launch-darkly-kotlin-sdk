package com.myunidays.launchdarkly

class JsonValueEvaluationDetail<T> internal constructor(
    private val ios: cocoapods.LaunchDarkly.LDJSONEvaluationDetail,
    override val value: T
) :
    EvaluationDetailInterface<T> {
    override val variationIndex: Int
        get() = ios.variationIndex().toInt()
    override val reason: EvaluationReason?
        get() = ios.reason()?.let { EvaluationReason(it) }
}
