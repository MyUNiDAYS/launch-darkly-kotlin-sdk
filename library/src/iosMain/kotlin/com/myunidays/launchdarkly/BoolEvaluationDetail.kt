package com.myunidays.launchdarkly

class BoolEvaluationDetail internal constructor(private val ios: cocoapods.LaunchDarkly.LDBoolEvaluationDetail) :
    EvaluationDetailInterface<Boolean> {
    override val value: Boolean
        get() = ios.value()
    override val variationIndex: Int
        get() = ios.variationIndex().toInt()
    override val reason: EvaluationReason?
        get() = ios.reason()?.let { EvaluationReason(it) }
}
