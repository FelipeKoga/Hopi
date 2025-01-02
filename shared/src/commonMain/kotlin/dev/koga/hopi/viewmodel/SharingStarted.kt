package dev.koga.hopi.viewmodel

import kotlinx.coroutines.flow.SharingStarted

val SharingStarted.Companion.WhileViewSubscribed
    get() = WhileSubscribed(stopTimeoutMillis = 5_000)