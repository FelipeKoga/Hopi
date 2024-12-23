package dev.koga.mmofinder

class JsPlatform: Platform {
    override val name: String = "Web with JS"
}

actual fun getPlatform(): Platform = JsPlatform()