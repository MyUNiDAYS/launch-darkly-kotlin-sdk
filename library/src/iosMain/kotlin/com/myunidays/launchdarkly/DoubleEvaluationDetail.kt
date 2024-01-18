package com.myunidays.launchdarkly

class DoubleEvaluationDetail internal constructor(private val ios: cocoapods.LaunchDarkly.LDDoubleEvaluationDetail):
    EvaluationDetailInterface<Double> {
    override val value: Double
        get() = ios.value()
    override val variationIndex: Int
        get() = ios.variationIndex().toInt()
    override val reason: EvaluationReason?
        get() = ios.reason()?.let { EvaluationReason(it) }
}
