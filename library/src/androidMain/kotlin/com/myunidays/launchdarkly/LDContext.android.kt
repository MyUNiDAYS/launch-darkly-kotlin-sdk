package com.myunidays.launchdarkly

actual class LDContext internal constructor(val android: com.launchdarkly.sdk.LDContext) {
    actual constructor(key: String) : this(com.launchdarkly.sdk.LDContext.create(key))

    actual companion object {
        @Suppress("SpreadOperator")
        actual fun createMulti(vararg contexts: LDContext): LDContext =
            LDContext(com.launchdarkly.sdk.LDContext.createMulti(*contexts.map { it.android }.toTypedArray()))
    }
}
