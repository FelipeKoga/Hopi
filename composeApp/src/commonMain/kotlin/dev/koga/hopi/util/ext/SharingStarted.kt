package dev.koga.hopi.util.ext

import kotlinx.coroutines.flow.SharingStarted

val SharingStarted.Companion.WhileViewSubscribed
    get() = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)