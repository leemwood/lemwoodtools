package cn.lemwoodtools.utils

import okhttp3.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class GitHubApiClient {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val baseUrl = "https://api.github.com/repos/ning-g-mo/lemwoodtools"
    
    fun getLatestRelease(callback: (Release?) -> Unit) {
        val request = Request.Builder()
            .url("$baseUrl/releases/latest")
            .build()
            
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }
            
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        val releaseJson = it.body?.string()
                        val release = gson.fromJson(releaseJson, Release::class.java)
                        callback(release)
                    } else {
                        callback(null)
                    }
                }
            }
        })
    }
    
    fun getAnnouncements(callback: (List<Announcement>) -> Unit) {
        // Get announcements from a specific file in the repository
        val request = Request.Builder()
            .url("$baseUrl/contents/announcements.json")
            .build()
            
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(emptyList())
            }
            
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        try {
                            val contentJson = it.body?.string()
                            val contentResponse = gson.fromJson(contentJson, GitHubContent::class.java)
                            val decodedContent = String(android.util.Base64.decode(contentResponse.content.replace("\n", ""), android.util.Base64.DEFAULT))
                            val listType = object : TypeToken<List<Announcement>>() {}.type
                            val announcements = gson.fromJson<List<Announcement>>(decodedContent, listType)
                            callback(announcements)
                        } catch (e: Exception) {
                            callback(emptyList())
                        }
                    } else {
                        callback(emptyList())
                    }
                }
            }
        })
    }
}