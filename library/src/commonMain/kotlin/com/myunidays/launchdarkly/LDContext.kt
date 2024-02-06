package com.myunidays.launchdarkly

expect class LDContext(key: String) {
    companion object {
        fun createMulti(vararg contexts: LDContext): LDContext
    }
}
