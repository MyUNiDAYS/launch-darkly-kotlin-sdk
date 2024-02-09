package com.myunidays.launchdarkly

expect class LDContext(key: String, kind: String = "user") {

    @Suppress("UnusedPrivateProperty")
    constructor(key: String, kind: ContextKind = ContextKind())
    companion object {
        fun createMulti(contexts: List<LDContext>): LDContext
    }
}
