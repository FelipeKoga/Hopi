package dev.koga.hopi.analytics

expect interface AnalyticsLogger {
    fun log(event: String)
}