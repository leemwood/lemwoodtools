package cn.lemwood.data

/**
 * 工具仓库接口
 * 定义了工具数据访问的标准接口
 */
interface IToolsRepository {
    /**
     * 获取所有工具列表
     */
    fun getAllTools(): List<ToolItem>
    
    /**
     * 根据查询条件搜索工具
     * @param query 搜索关键词
     * @return 匹配的工具列表
     */
    fun searchTools(query: String): List<ToolItem>
    
    /**
     * 按分类获取工具
     * @return 分类到工具列表的映射
     */
    fun getToolsByCategory(): Map<String, List<ToolItem>>
    
    /**
     * 根据ID获取工具
     * @param id 工具ID
     * @return 工具项，如果不存在则返回null
     */
    fun getToolById(id: String): ToolItem?
    
    /**
     * 获取所有分类
     * @return 分类列表
     */
    fun getCategories(): List<String>
}