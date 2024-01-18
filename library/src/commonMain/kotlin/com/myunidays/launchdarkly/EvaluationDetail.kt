package com.myunidays.launchdarkly

interface EvaluationDetailInterface<T> {
    val value: T?
    val variationIndex: Int
    val reason: EvaluationReason?
}

expect class EvaluationDetail<T> : EvaluationDetailInterface<T>
