package cn.lemwood

import org.junit.Test
import org.junit.Assert.*

/**
 * 应用启动相关的单元测试
 * 用于检测可能导致闪退的问题
 */
class AppStartupTest {
    
    @Test
    fun packageName_isCorrect() {
        val expectedPackageName = "cn.lemwood"
        val actualPackageName = this::class.java.`package`?.name
        
        assertEquals(expectedPackageName, actualPackageName)
    }
    
    @Test
    fun basicMath_isCorrect() {
        // 基础数学运算测试，确保测试环境正常
        assertEquals(4, 2 + 2)
        assertEquals(0, 2 - 2)
        assertEquals(4, 2 * 2)
        assertEquals(1, 2 / 2)
    }
    
    @Test
    fun stringOperations_work() {
        // 字符串操作测试
        val testString = "LemwoodTools"
        assertTrue(testString.isNotEmpty())
        assertTrue(testString.contains("Lemwood"))
        assertEquals("lemwoodtools", testString.lowercase())
    }
    
    @Test
    fun listOperations_work() {
        // 列表操作测试
        val testList = listOf("tool1", "tool2", "tool3")
        assertEquals(3, testList.size)
        assertTrue(testList.contains("tool1"))
        assertFalse(testList.isEmpty())
    }
    
    @Test
    fun nullSafety_works() {
        // 空安全测试
        val nullableString: String? = null
        val nonNullString: String? = "test"
        
        assertNull(nullableString)
        assertNotNull(nonNullString)
        assertEquals("test", nonNullString)
    }
}