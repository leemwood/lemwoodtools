package cn.lemwood.data

import org.junit.Test
import org.junit.Assert.*

/**
 * ToolsRepository的单元测试
 * 测试工具仓库的各种功能
 */
class ToolsRepositoryTest {

    @Test
    fun `getAllTools should return non-empty list`() {
        val tools = ToolsRepository.getAllTools()
        assertTrue("工具列表不应为空", tools.isNotEmpty())
    }

    @Test
    fun `searchTools with empty query should return all tools`() {
        val allTools = ToolsRepository.getAllTools()
        val searchResult = ToolsRepository.searchTools("")
        assertEquals("空查询应返回所有工具", allTools.size, searchResult.size)
    }

    @Test
    fun `searchTools with blank query should return all tools`() {
        val allTools = ToolsRepository.getAllTools()
        val searchResult = ToolsRepository.searchTools("   ")
        assertEquals("空白查询应返回所有工具", allTools.size, searchResult.size)
    }

    @Test
    fun `searchTools should find tools by title`() {
        val searchResult = ToolsRepository.searchTools("计算器")
        assertTrue("应该能通过标题找到工具", searchResult.isNotEmpty())
        assertTrue("搜索结果应包含计算器", 
            searchResult.any { it.title.contains("计算器") })
    }

    @Test
    fun `searchTools should find tools by description`() {
        val searchResult = ToolsRepository.searchTools("科学计算")
        assertTrue("应该能通过描述找到工具", searchResult.isNotEmpty())
    }

    @Test
    fun `searchTools should find tools by category`() {
        val searchResult = ToolsRepository.searchTools("数学")
        assertTrue("应该能通过分类找到工具", searchResult.isNotEmpty())
    }

    @Test
    fun `searchTools should find tools by keywords`() {
        val searchResult = ToolsRepository.searchTools("加减乘除")
        assertTrue("应该能通过关键词找到工具", searchResult.isNotEmpty())
    }

    @Test
    fun `searchTools should be case insensitive`() {
        val lowerResult = ToolsRepository.searchTools("计算器")
        val upperResult = ToolsRepository.searchTools("计算器")
        assertEquals("搜索应该不区分大小写", lowerResult.size, upperResult.size)
    }

    @Test
    fun `getToolsByCategory should group tools correctly`() {
        val categorizedTools = ToolsRepository.getToolsByCategory()
        assertTrue("应该有分类", categorizedTools.isNotEmpty())
        
        // 验证每个分类都有工具
        categorizedTools.forEach { (category, tools) ->
            assertTrue("分类 $category 应该有工具", tools.isNotEmpty())
            tools.forEach { tool ->
                assertEquals("工具的分类应该匹配", category, tool.category)
            }
        }
    }

    @Test
    fun `getToolById should return correct tool`() {
        val allTools = ToolsRepository.getAllTools()
        val firstTool = allTools.first()
        
        val foundTool = ToolsRepository.getToolById(firstTool.id)
        assertNotNull("应该能找到工具", foundTool)
        assertEquals("返回的工具应该匹配", firstTool.id, foundTool?.id)
    }

    @Test
    fun `getToolById should return null for non-existent id`() {
        val foundTool = ToolsRepository.getToolById("non_existent_id")
        assertNull("不存在的ID应该返回null", foundTool)
    }

    @Test
    fun `getCategories should return sorted unique categories`() {
        val categories = ToolsRepository.getCategories()
        assertTrue("应该有分类", categories.isNotEmpty())
        
        // 验证唯一性
        val uniqueCategories = categories.distinct()
        assertEquals("分类应该是唯一的", categories.size, uniqueCategories.size)
        
        // 验证排序
        val sortedCategories = categories.sorted()
        assertEquals("分类应该是排序的", sortedCategories, categories)
    }

    @Test
    fun `all tools should have valid properties`() {
        val tools = ToolsRepository.getAllTools()
        
        tools.forEach { tool ->
            assertTrue("工具ID不应为空", tool.id.isNotBlank())
            assertTrue("工具标题不应为空", tool.title.isNotBlank())
            assertTrue("工具描述不应为空", tool.description.isNotBlank())
            assertTrue("工具路由不应为空", tool.route.isNotBlank())
            assertTrue("工具分类不应为空", tool.category.isNotBlank())
        }
    }

    @Test
    fun `all tool ids should be unique`() {
        val tools = ToolsRepository.getAllTools()
        val ids = tools.map { it.id }
        val uniqueIds = ids.distinct()
        
        assertEquals("所有工具ID应该是唯一的", ids.size, uniqueIds.size)
    }
}