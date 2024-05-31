package com.myunidays.launchdarkly

class EvaluationReason internal constructor(val ios: Map<Any?, *>) {

    /**
     * Determines if the evaluation is part of an experiment.
     * It checks if the 'inExperiment' key in the `ios` map is a cocoapods.LaunchDarkly.LDValue
     * and then tries to extract a Boolean value from it.
     */
    actual val isInExperiment: Boolean
        get() = ios["inExperiment"]?.let { inExperiment ->
            // Check if the value associated with 'inExperiment' is of type LDValue
            when (inExperiment) {
                is cocoapods.LaunchDarkly.LDValue -> extractBoolean(inExperiment)
                else -> false
            }
        } ?: false

    /**
     * Helper method to extract a Boolean value from an LDValue instance.
     * If the value inside LDValue is not a Boolean, it returns false.
     *
     * @param value An instance of cocoapods.LaunchDarkly.LDValue
     * @return The Boolean value contained in the LDValue, or false if it's not a Boolean
     */
    private fun extractBoolean(value: cocoapods.LaunchDarkly.LDValue): Boolean {
        return (LDValue(value).value() as? Boolean) ?: false
    }
}