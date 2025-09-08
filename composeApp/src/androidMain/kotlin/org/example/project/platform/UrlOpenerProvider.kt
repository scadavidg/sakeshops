package org.example.project.platform

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.example.project.MainActivity

actual object UrlOpenerProvider {
    actual fun create(): UrlOpener = AndroidUrlOpener()
}

class AndroidUrlOpener : UrlOpener {
    private val context: Context
        get() = MainActivity.instance

    override suspend fun openUrl(url: String): Result<Unit> {
        return try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun openMapsWithAddress(address: String): Result<Unit> {
        return try {
            val encodedAddress = Uri.encode(address)
            val mapsUri = "geo:0,0?q=$encodedAddress"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUri))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            // Check if there's an app that can handle the maps intent
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                Result.success(Unit)
            } else {
                // Fallback to web browser with Google Maps
                val webMapsUrl = "https://www.google.com/maps/search/?api=1&query=$encodedAddress"
                openUrl(webMapsUrl)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
