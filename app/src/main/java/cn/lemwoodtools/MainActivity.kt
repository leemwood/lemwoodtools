package cn.lemwoodtools

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.lemwoodtools.databinding.ActivityMainBinding
import cn.lemwoodtools.utils.GitHubApiClient
import cn.lemwoodtools.utils.AnnouncementManager
import cn.lemwoodtools.utils.VersionChecker

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var gitHubApiClient: GitHubApiClient
    private lateinit var announcementManager: AnnouncementManager
    private lateinit var versionChecker: VersionChecker
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initializeComponents()
        setupWebView()
        setupSwipeRefresh()
        loadVueApp()
        checkForUpdates()
        loadAnnouncements()
    }
    
    private fun initializeComponents() {
        webView = binding.webView
        swipeRefreshLayout = binding.swipeRefreshLayout
        gitHubApiClient = GitHubApiClient()
        announcementManager = AnnouncementManager(gitHubApiClient)
        versionChecker = VersionChecker(gitHubApiClient, this)
    }    
    private fun setupWebView() {
        webView.webViewClient = WebViewClient()
        
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowUniversalAccessFromFileURLs = true
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        
        // Add JavaScript interface for native communication
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
    }
    
    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            webView.reload()
            swipeRefreshLayout.isRefreshing = false
        }
    }
    
    private fun loadVueApp() {
        webView.loadUrl("file:///android_asset/vue/index.html")
    }
    
    private fun checkForUpdates() {
        versionChecker.checkForUpdates { hasUpdate, latestVersion ->
            if (hasUpdate) {
                // Show update dialog or notification
                runOnUiThread {
                    // Implementation for update notification
                }
            }
        }
    }
    
    private fun loadAnnouncements() {
        announcementManager.getAnnouncements { announcements ->
            runOnUiThread {
                // Pass announcements to Vue app
                val announcementsJson = announcements.joinToString(",") { 
                    "{\"title\":\"${it.title}\",\"content\":\"${it.content}\",\"date\":\"${it.date}\"}" 
                }
                webView.evaluateJavascript(
                    "window.updateAnnouncements([$announcementsJson])", 
                    null
                )
            }
        }
    }
    
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}