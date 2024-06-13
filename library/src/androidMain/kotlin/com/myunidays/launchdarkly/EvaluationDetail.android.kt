package com.myunidays.launchdarkly

actual class EvaluationDetail<T> internal constructor(
    val android: com.launchdarkly.sdk.EvaluationDetail<T>
) : EvaluationDetailInterface<T> {
    override val value: T?
        get() = android.value
    override val variationIndex: Int
        get() = android.variationIndex
    override val reason: EvaluationReason?
        get() = android.reason?.let { EvaluationReason(it) }
}
