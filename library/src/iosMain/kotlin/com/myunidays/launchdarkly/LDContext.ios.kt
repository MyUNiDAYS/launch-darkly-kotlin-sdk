package com.myunidays.launchdarkly

actual class LDContext internal constructor(val ios: cocoapods.LaunchDarkly.LDContext) {
    actual constructor(key: String, kind: String) : this(key, ContextKind(kind))
    actual constructor(key: String, kind: ContextKind) :
        this(
            cocoapods.LaunchDarkly.LDContextBuilder(key)
                .apply { kindWithKind(kind.kind) }
                .build()
                .success()!!
        )

    actual companion object {
        actual fun createMulti(contexts: List<LDContext>): LDContext {
            val contextBuilder = cocoapods.LaunchDarkly.LDMultiContextBuilder()
            contexts.forEach { context ->
                contextBuilder.addContextWithContext(context.ios)
            }
            return LDContext(contextBuilder.build().success()!!)
        }
    }
}
