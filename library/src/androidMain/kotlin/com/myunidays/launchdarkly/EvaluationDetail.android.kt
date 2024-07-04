package com.myunidays.launchdarkly

actual class EvaluationDetail<T> internal constructor(
    val android: com.launchdarkly.sdk.EvaluationDetail<T>,
) : EvaluationDetailInterface<T> {

    companion object {

        fun <T> fromValues(
            value: T?,
            variationIndex: Int,
            reason: com.launchdarkly.sdk.EvaluationReason?
        ): EvaluationDetail<T> {
            val sdkDetail = createEvaluationDetail(value, variationIndex, reason)
            return EvaluationDetail(sdkDetail)
        }

        private fun <T> createEvaluationDetail(
            value: T?,
            variationIndex: Int,
            reason: com.launchdarkly.sdk.EvaluationReason?
        ): com.launchdarkly.sdk.EvaluationDetail<T> {
            // Use reflection to access the private constructor
            val constructor = com.launchdarkly.sdk.EvaluationDetail::class.constructors.first()
            return constructor.call(value, variationIndex, reason) as com.launchdarkly.sdk.EvaluationDetail<T>
        }
    }

    override val value: T?
        get() = android.value
    override val variationIndex: Int
        get() = android.variationIndex
    override val reason: EvaluationReason?
        get() = android.reason?.let { EvaluationReason(it) }
}
