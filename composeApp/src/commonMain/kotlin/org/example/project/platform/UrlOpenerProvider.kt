package org.example.project.platform

expect object UrlOpenerProvider {
    fun create(): UrlOpener
}
