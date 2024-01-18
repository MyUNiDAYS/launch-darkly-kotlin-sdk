package com.myunidays.launchdarkly

actual class EvaluationDetail<T> internal constructor(
    private val ios: EvaluationDetailInterface<T>
): EvaluationDetailInterface<T> {
    override val value: T?
        get() = ios.value
    override val variationIndex: Int
        get() = ios.variationIndex
    override val reason: EvaluationReason?
        get() = ios.reason

}
