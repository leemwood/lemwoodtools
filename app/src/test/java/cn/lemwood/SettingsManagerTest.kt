package cn.lemwood

import android.content.Context
import android.content.SharedPreferences
import cn.lemwood.utils.SettingsManager
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

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
        `when`(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putBoolean(anyString(), anyBoolean())).thenReturn(mockEditor)
    }

    @Test
    fun testNotificationsDefaultToFalse() {
        // 模拟SharedPreferences返回默认值false
        `when`(mockSharedPreferences.getBoolean("notifications", false)).thenReturn(false)
        
        // 初始化SettingsManager
        SettingsManager.initialize(mockContext)
        
        // 验证通知功能默认为关闭
        assertFalse("通知功能应该默认关闭", SettingsManager.isNotificationsEnabled())
    }

    @Test
    fun testSetNotificationsEnabled() {
        // 初始化SettingsManager
        SettingsManager.initialize(mockContext)
        
        // 设置通知为启用
        SettingsManager.setNotificationsEnabled(true)
        
        // 验证设置被保存
        verify(mockEditor).putBoolean("notifications", true)
        verify(mockEditor).apply()
    }
}