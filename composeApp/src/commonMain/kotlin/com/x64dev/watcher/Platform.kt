package com.x64dev.watcher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform