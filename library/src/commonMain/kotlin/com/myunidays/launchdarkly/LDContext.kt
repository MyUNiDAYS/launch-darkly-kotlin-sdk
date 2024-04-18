package com.myunidays.launchdarkly

expect class LDContext(
    key: String,
    kind: String,
    values: Map<String, LDValue>,
    privateAttributes: List<String>
) {

    @Suppress("UnusedPrivateProperty")
    constructor(
        key: String,
        kind: ContextKind = ContextKind(),
        values: Map<String, LDValue> = emptyMap(),
        privateAttributes: List<String> = emptyList()
    )

    companion object {
        fun createMulti(contexts: List<LDContext>): LDContext
    }
}
