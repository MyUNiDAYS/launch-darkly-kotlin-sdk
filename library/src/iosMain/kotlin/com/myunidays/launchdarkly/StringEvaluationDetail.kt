package com.myunidays.launchdarkly

class StringEvaluationDetail internal constructor(private val ios: cocoapods.LaunchDarkly.LDStringEvaluationDetail):
    EvaluationDetailInterface<String> {
    override val value: String?
        get() = ios.value()
    override val variationIndex: Int
        get() = ios.variationIndex().toInt()
    override val reason: EvaluationReason?
        get() = ios.reason()?.let { EvaluationReason(it) }
}