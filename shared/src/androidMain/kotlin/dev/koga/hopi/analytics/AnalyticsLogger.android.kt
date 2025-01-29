package dev.koga.hopi.analytics

actual interface AnalyticsLogger {
    actual fun log(event: String)
}

class AndroidAnalyticsLogger : AnalyticsLogger {
    override fun log(event: String) {
        println("Log from Android: $event")
    }
}