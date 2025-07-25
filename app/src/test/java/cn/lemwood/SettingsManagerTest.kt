package cn.lemwood

import android.content.Context
import android.content.SharedPreferences
import cn.lemwood.utils.SettingsManager
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyBoolean

class SettingsManagerTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        whenever(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences)
        whenever(mockSharedPreferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.putBoolean(anyString(), anyBoolean())).thenReturn(mockEditor)
    }

    @Test
    fun `test notifications disabled by default`() {
        // 模拟SharedPreferences返回默认值false
        whenever(mockSharedPreferences.getBoolean(SettingsManager.KEY_NOTIFICATIONS, false)).thenReturn(false)

        // 初始化SettingsManager
        SettingsManager.initialize(mockContext)

        // 验证通知默认是关闭的
        val isEnabled = SettingsManager.isNotificationsEnabled()
        assertFalse("Notifications should be disabled by default", isEnabled)
    }

    @Test
    fun `test setNotificationsEnabled saves correctly`() {
        // 初始化SettingsManager
        SettingsManager.initialize(mockContext)

        // 设置通知为启用
        SettingsManager.setNotificationsEnabled(true)

        // 验证调用了正确的方法
        verify(mockEditor).putBoolean(SettingsManager.KEY_NOTIFICATIONS, true)
        verify(mockEditor).apply()
    }
}