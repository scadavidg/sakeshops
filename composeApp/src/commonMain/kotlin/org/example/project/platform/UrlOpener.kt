package org.example.project.platform

interface UrlOpener {
    /**
     * Opens the default browser with the given URL
     */
    suspend fun openUrl(url: String): Result<Unit>

    /**
     * Opens the default maps app with the given address for search
     */
    suspend fun openMapsWithAddress(address: String): Result<Unit>
}
