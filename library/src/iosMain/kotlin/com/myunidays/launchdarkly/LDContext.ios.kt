package com.myunidays.launchdarkly

actual class LDContext internal constructor(val ios: cocoapods.LaunchDarkly.LDContext) {
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
        cocoapods.LaunchDarkly.LDContextBuilder(key).let { contextBuilder ->
            contextBuilder.kindWithKind(kind.kind)
            privateAttributes.forEach {
                contextBuilder.addPrivateAttributeWithReference(cocoapods.LaunchDarkly.Reference(it))
            }
            values.forEach { (k, v) ->
                contextBuilder.trySetValueWithName(k, v.ios)
            }
            contextBuilder.build().success()!!
        }
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
