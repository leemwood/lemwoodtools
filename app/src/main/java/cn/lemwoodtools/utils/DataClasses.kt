package cn.lemwoodtools.utils

data class Release(
    val tag_name: String,
    val name: String,
    val body: String,
    val published_at: String,
    val html_url: String
)

data class Announcement(
    val title: String,
    val content: String,
    val date: String,
    val priority: String = "normal"
)

data class GitHubContent(
    val name: String,
    val content: String,
    val encoding: String
)