package cn.lemwood.data

import org.junit.Test
import org.junit.Assert.*

/**
 * 工具数据模型的单元测试
 */
class ToolTest {
    
    @Test
    fun tool_creation_isCorrect() {
        val tool = Tool(
            id = 1,
            name = "测试工具",
            description = "这是一个测试工具",
            category = "测试",
            icon = "test_icon"
        )
        
        assertEquals(1, tool.id)
        assertEquals("测试工具", tool.name)
        assertEquals("这是一个测试工具", tool.description)
        assertEquals("测试", tool.category)
        assertEquals("test_icon", tool.icon)
    }
    
    @Test
    fun tool_properties_notNull() {
        val tool = Tool(
            id = 1,
            name = "测试工具",
            description = "这是一个测试工具",
            category = "测试",
            icon = "test_icon"
        )
        
        assertNotNull(tool.name)
        assertNotNull(tool.description)
        assertNotNull(tool.category)
        assertNotNull(tool.icon)
        assertTrue(tool.id > 0)
    }
    
    @Test
    fun tool_name_notEmpty() {
        val tool = Tool(
            id = 1,
            name = "测试工具",
            description = "这是一个测试工具",
            category = "测试",
            icon = "test_icon"
        )
        
        assertTrue(tool.name.isNotEmpty())
        assertTrue(tool.description.isNotEmpty())
        assertTrue(tool.category.isNotEmpty())
        assertTrue(tool.icon.isNotEmpty())
    }
}