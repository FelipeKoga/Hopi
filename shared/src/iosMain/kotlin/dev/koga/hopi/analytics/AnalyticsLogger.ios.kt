package dev.koga.hopi.analytics

actual interface AnalyticsLogger {
    actual fun log(event: String)
}