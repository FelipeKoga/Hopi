package dev.koga.mmofinder

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform