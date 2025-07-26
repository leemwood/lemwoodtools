package cn.lemwoodtools.utils

import android.content.Context

class VersionChecker(
    private val gitHubApiClient: GitHubApiClient,
    private val context: Context
) {
    
    fun checkForUpdates(callback: (Boolean, String?) -> Unit) {
        val currentVersion = getCurrentVersion()
        
        gitHubApiClient.getLatestRelease { release ->
            if (release != null) {
                val latestVersion = release.tag_name.removePrefix("v")
                val hasUpdate = compareVersions(currentVersion, latestVersion) < 0
                callback(hasUpdate, latestVersion)
            } else {
                callback(false, null)
            }
        }
    }
    
    private fun getCurrentVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "1.0.0"
        }
    }
    
    private fun compareVersions(version1: String, version2: String): Int {
        val parts1 = version1.split(".").map { it.toIntOrNull() ?: 0 }
        val parts2 = version2.split(".").map { it.toIntOrNull() ?: 0 }
        
        val maxLength = maxOf(parts1.size, parts2.size)
        
        for (i in 0 until maxLength) {
            val part1 = parts1.getOrNull(i) ?: 0
            val part2 = parts2.getOrNull(i) ?: 0
            
            when {
                part1 < part2 -> return -1
                part1 > part2 -> return 1
            }
        }
        
        return 0
    }
}