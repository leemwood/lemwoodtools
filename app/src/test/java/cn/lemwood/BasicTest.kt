package cn.lemwood

import org.junit.Test
import org.junit.Assert.*

/**
 * 基础功能测试
 * 确保测试环境正常工作
 */
class BasicTest {
    
    @Test
    fun kotlin_basics_work() {
        // 测试基础Kotlin功能
        val list = listOf(1, 2, 3, 4, 5)
        assertEquals(5, list.size)
        assertTrue(list.contains(3))
        
        val map = mapOf("key1" to "value1", "key2" to "value2")
        assertEquals("value1", map["key1"])
        assertTrue(map.containsKey("key2"))
    }
    
    @Test
    fun string_operations_work() {
        val text = "LemwoodTools"
        assertEquals("lemwoodtools", text.lowercase())
        assertEquals("LEMWOODTOOLS", text.uppercase())
        assertTrue(text.startsWith("Lemwood"))
        assertTrue(text.endsWith("Tools"))
    }
    
    @Test
    fun null_safety_works() {
        val nullable: String? = null
        val nonNull: String? = "test"
        
        assertNull(nullable)
        assertNotNull(nonNull)
        assertEquals("test", nonNull)
    }
    
    @Test
    fun collections_work() {
        val mutableList = mutableListOf<String>()
        assertTrue(mutableList.isEmpty())
        
        mutableList.add("item1")
        mutableList.add("item2")
        
        assertEquals(2, mutableList.size)
        assertTrue(mutableList.contains("item1"))
    }
}