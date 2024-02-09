package com.myunidays.launchdarkly

actual class LDContext internal constructor(val android: com.launchdarkly.sdk.LDContext) {
    actual constructor(key: String, kind: String) : this(key, ContextKind(kind))
    actual constructor(key: String, kind: ContextKind) :
        this(com.launchdarkly.sdk.LDContext.create(kind.android, key))

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
