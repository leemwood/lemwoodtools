package cn.lemwood.data

import org.junit.Test
import org.junit.Assert.*

/**
 * 工具仓库的单元测试
 */
class ToolsRepositoryTest {
    
    private val repository = ToolsRepository()
    
    @Test
    fun getAllTools_returnsNonEmptyList() {
        val tools = repository.getAllTools()
        
        assertNotNull(tools)
        assertTrue(tools.isNotEmpty())
    }
    
    @Test
    fun getAllTools_containsExpectedCategories() {
        val tools = repository.getAllTools()
        val categories = tools.map { it.category }.distinct()
        
        assertTrue(categories.contains("系统工具"))
        assertTrue(categories.contains("网络工具"))
        assertTrue(categories.contains("文本工具"))
        assertTrue(categories.contains("计算工具"))
    }
    
    @Test
    fun getToolsByCategory_returnsCorrectTools() {
        val systemTools = repository.getToolsByCategory("系统工具")
        
        assertNotNull(systemTools)
        assertTrue(systemTools.all { it.category == "系统工具" })
    }
    
    @Test
    fun getToolsByCategory_emptyCategory_returnsEmptyList() {
        val tools = repository.getToolsByCategory("不存在的分类")
        
        assertNotNull(tools)
        assertTrue(tools.isEmpty())
    }
    
    @Test
    fun searchTools_findsCorrectResults() {
        val results = repository.searchTools("二维码")
        
        assertNotNull(results)
        assertTrue(results.any { it.name.contains("二维码") || it.description.contains("二维码") })
    }
    
    @Test
    fun searchTools_emptyQuery_returnsAllTools() {
        val allTools = repository.getAllTools()
        val searchResults = repository.searchTools("")
        
        assertEquals(allTools.size, searchResults.size)
    }
    
    @Test
    fun searchTools_noResults_returnsEmptyList() {
        val results = repository.searchTools("不存在的工具名称xyz123")
        
        assertNotNull(results)
        assertTrue(results.isEmpty())
    }
    
    @Test
    fun allTools_haveValidProperties() {
        val tools = repository.getAllTools()
        
        tools.forEach { tool ->
            assertTrue("Tool ID should be positive", tool.id > 0)
            assertTrue("Tool name should not be empty", tool.name.isNotEmpty())
            assertTrue("Tool description should not be empty", tool.description.isNotEmpty())
            assertTrue("Tool category should not be empty", tool.category.isNotEmpty())
            assertTrue("Tool icon should not be empty", tool.icon.isNotEmpty())
        }
    }
}