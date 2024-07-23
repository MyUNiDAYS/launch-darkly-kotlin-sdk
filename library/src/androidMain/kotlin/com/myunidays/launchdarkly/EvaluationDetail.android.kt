package com.myunidays.launchdarkly

actual class EvaluationDetail<T> internal constructor(
    val android: com.launchdarkly.sdk.EvaluationDetail<T>,
) : EvaluationDetailInterface<T> {

    companion object {

        fun <T> fromValues(
            value: T?,
            variationIndex: Int,
            reason: com.launchdarkly.sdk.EvaluationReason?
        ): EvaluationDetail<T> =
            EvaluationDetail(createEvaluationDetail(value, variationIndex, reason))

        private fun <T> createEvaluationDetail(
            value: T?,
            variationIndex: Int,
            reason: com.launchdarkly.sdk.EvaluationReason?
        ): com.launchdarkly.sdk.EvaluationDetail<T> =
            com.launchdarkly.sdk.EvaluationDetail.fromValue(value, variationIndex, reason)
    }

    override val value: T?
        get() = android.value
    override val variationIndex: Int
        get() = android.variationIndex
    override val reason: EvaluationReason?
        get() = android.reason?.let { EvaluationReason(it) }
}
