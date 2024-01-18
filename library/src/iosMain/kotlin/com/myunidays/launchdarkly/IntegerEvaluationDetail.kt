package com.myunidays.launchdarkly

class IntegerEvaluationDetail internal constructor(private val ios: cocoapods.LaunchDarkly.LDIntegerEvaluationDetail) :
    EvaluationDetailInterface<Int> {
    override val value: Int
        get() = ios.value().toInt()
    override val variationIndex: Int
        get() = ios.variationIndex().toInt()
    override val reason: EvaluationReason?
        get() = ios.reason()?.let { EvaluationReason(it) }
}
