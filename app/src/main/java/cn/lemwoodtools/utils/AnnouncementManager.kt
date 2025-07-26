package cn.lemwoodtools.utils

class AnnouncementManager(private val gitHubApiClient: GitHubApiClient) {
    
    fun getAnnouncements(callback: (List<Announcement>) -> Unit) {
        gitHubApiClient.getAnnouncements { announcements ->
            // Sort announcements by priority and date
            val sortedAnnouncements = announcements.sortedWith(
                compareByDescending<Announcement> { 
                    when (it.priority) {
                        "high" -> 3
                        "medium" -> 2
                        "normal" -> 1
                        else -> 0
                    }
                }.thenByDescending { it.date }
            )
            callback(sortedAnnouncements)
        }
    }
}