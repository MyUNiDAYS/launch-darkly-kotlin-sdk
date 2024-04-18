package com.myunidays.launchdarkly

actual class LDContext internal constructor(val android: com.launchdarkly.sdk.LDContext) {
    actual constructor(
        key: String,
        kind: String,
        values: Map<String, LDValue>,
        privateAttributes: List<String>
    ) : this(key, ContextKind(kind), values, privateAttributes)

    actual constructor(
        key: String,
        kind: ContextKind,
        values: Map<String, LDValue>,
        privateAttributes: List<String>
    ) : this(
        com.launchdarkly.sdk.LDContext.builder(key).let { builder ->
            builder
                .kind(kind.android)
                .privateAttributes(*privateAttributes.toTypedArray())
            values.forEach { (k, v) ->
                builder.trySet(k, v.android)
            }
            builder.build()
        }
    )

    actual companion object {
        @Suppress("SpreadOperator")
        actual fun createMulti(contexts: List<LDContext>): LDContext =
            LDContext(
                com.launchdarkly.sdk.LDContext.createMulti(
                    *contexts.map { it.android }.toTypedArray()
                )
            )
    }
}
