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
        val actualPackageName = this::class.java.`package`?.name?.substringBefore(".data")
        
        assertEquals(expectedPackageName, actualPackageName)
    }
    
    @Test
    fun classLoading_worksCorrectly() {
        // 测试关键类是否能正确加载
        try {
            Class.forName("cn.lemwood.data.Tool")
            Class.forName("cn.lemwood.data.ToolsRepository")
        } catch (e: ClassNotFoundException) {
            fail("关键类加载失败: ${e.message}")
        }
    }
    
    @Test
    fun dataClasses_instantiation() {
        // 测试数据类是否能正确实例化
        try {
            val tool = cn.lemwood.data.Tool(
                id = 1,
                name = "测试",
                description = "测试描述",
                category = "测试分类",
                icon = "test_icon"
            )
            assertNotNull(tool)
            
            val repository = cn.lemwood.data.ToolsRepository()
            assertNotNull(repository)
            
        } catch (e: Exception) {
            fail("数据类实例化失败: ${e.message}")
        }
    }
    
    @Test
    fun repository_initialization() {
        // 测试仓库初始化是否正常
        try {
            val repository = cn.lemwood.data.ToolsRepository()
            val tools = repository.getAllTools()
            
            assertNotNull("工具列表不应为null", tools)
            assertTrue("工具列表不应为空", tools.isNotEmpty())
            
        } catch (e: Exception) {
            fail("仓库初始化失败: ${e.message}")
        }
    }
}