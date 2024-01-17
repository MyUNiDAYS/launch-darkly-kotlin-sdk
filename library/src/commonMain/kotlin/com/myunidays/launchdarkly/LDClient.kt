package com.myunidays.launchdarkly

//expect class LDClient(config: LDConfig, context: LDContext) {
expect class LDClient {
    fun boolVariation(key: String, defaultValue: Boolean): Boolean
    fun boolVariationDetail(key: String, defaultValue: Boolean): EvaluationDetailInterface<Boolean>
    fun intVariation(key: String, defaultValue: Int): Int
    fun intVariationDetail(key: String, defaultValue: Int): EvaluationDetailInterface<Int>
    fun doubleVariation(key: String, defaultValue: Double): Double
    fun doubleVariationDetail(key: String, defaultValue: Double): EvaluationDetailInterface<Double>
    fun stringVariation(key: String, defaultValue: String): String
    fun stringVariationDetail(key: String, defaultValue: String): EvaluationDetailInterface<String>

//
//    public LDValue jsonValueVariation(@NonNull String key, LDValue defaultValue) {
//        return (LDValue)this.variationDetailInternal(key, LDValue.normalize(defaultValue), false, false).getValue();
//    }
//
//    public EvaluationDetail<LDValue> jsonValueVariationDetail(@NonNull String key, LDValue defaultValue) {
//        return this.variationDetailInternal(key, LDValue.normalize(defaultValue), false, true);
//    }
}
