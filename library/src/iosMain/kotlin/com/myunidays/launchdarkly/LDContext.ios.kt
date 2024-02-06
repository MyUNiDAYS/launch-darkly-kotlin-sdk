package com.myunidays.launchdarkly

actual class LDContext internal constructor(val ios: cocoapods.LaunchDarkly.LDContext) {
    actual constructor(key: String) : this(cocoapods.LaunchDarkly.LDContextBuilder(key).build().success()!!)

    actual companion object {
        actual fun createMulti(vararg contexts: LDContext): LDContext {
            val contextBuilder = cocoapods.LaunchDarkly.LDMultiContextBuilder()
            contexts.forEach { context ->
                contextBuilder.addContextWithContext(context.ios)
            }
            return LDContext(contextBuilder.build().success()!!)
        }
    }
}
