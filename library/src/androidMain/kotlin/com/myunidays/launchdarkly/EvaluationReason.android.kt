package com.myunidays.launchdarkly

actual class EvaluationReason internal constructor(val reason: com.launchdarkly.sdk.EvaluationReason) {
    actual val isInExperiment: Boolean
        get() = reason.isInExperiment
}
