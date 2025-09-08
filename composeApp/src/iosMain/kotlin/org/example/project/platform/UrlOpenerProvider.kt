package org.example.project.platform

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual object UrlOpenerProvider {
    actual fun create(): UrlOpener = IosUrlOpener()
}

class IosUrlOpener : UrlOpener {
    override suspend fun openUrl(url: String): Result<Unit> {
        return try {
            val nsUrl = NSURL.URLWithString(url)
            if (nsUrl != null && UIApplication.sharedApplication.canOpenURL(nsUrl)) {
                UIApplication.sharedApplication.openURL(nsUrl)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Cannot open URL: $url"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun openMapsWithAddress(address: String): Result<Unit> {
        return try {
            // First try Apple Maps
            val appleMapsUrl = "http://maps.apple.com/?q=${address.replace(" ", "+")}"
            val appleMapsNsUrl = NSURL.URLWithString(appleMapsUrl)
            
            if (appleMapsNsUrl != null && UIApplication.sharedApplication.canOpenURL(appleMapsNsUrl)) {
                UIApplication.sharedApplication.openURL(appleMapsNsUrl)
                Result.success(Unit)
            } else {
                // Fallback to Google Maps
                val googleMapsUrl = "comgooglemaps://?q=${address.replace(" ", "+")}"
                val googleMapsNsUrl = NSURL.URLWithString(googleMapsUrl)
                
                if (googleMapsNsUrl != null && UIApplication.sharedApplication.canOpenURL(googleMapsNsUrl)) {
                    UIApplication.sharedApplication.openURL(googleMapsNsUrl)
                    Result.success(Unit)
                } else {
                    // Final fallback to web browser with Google Maps
                    val webMapsUrl = "https://www.google.com/maps/search/?api=1&query=${address.replace(" ", "+")}"
                    openUrl(webMapsUrl)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
