package cn.lemwood.utils

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import cn.lemwood.R

object InitializationManager {
    
    data class InitializationStep(
        val nameResId: Int,
        val descriptionResId: Int,
        var isCompleted: Boolean = false,
        var isError: Boolean = false
    )
    
    data class InitializationState(
        val currentStep: Int = 0,
        val totalSteps: Int = 0,
        val steps: List<InitializationStep> = emptyList(),
        val isCompleted: Boolean = false,
        val hasError: Boolean = false,
        val showPermissionDialog: Boolean = false
    )
    
    private val _initializationState = MutableStateFlow(InitializationState())
    val initializationState: StateFlow<InitializationState> = _initializationState.asStateFlow()
    
    private val initSteps = listOf(
        InitializationStep(R.string.init_step_language, R.string.init_step_language_desc),
        InitializationStep(R.string.init_step_permissions, R.string.init_step_permissions_desc),
        InitializationStep(R.string.init_step_tools, R.string.init_step_tools_desc),
        InitializationStep(R.string.init_step_cache, R.string.init_step_cache_desc)
    )
    
    suspend fun startInitialization(context: Context) {
        try {
            // 重置状态
            _initializationState.value = InitializationState(
                totalSteps = initSteps.size,
                steps = initSteps.map { it.copy() }
            )
            
            // 执行前两个初始化步骤
            for (index in 0..1) {
                _initializationState.value = _initializationState.value.copy(
                    currentStep = index
                )
                
                // 执行具体的初始化逻辑
                when (index) {
                    0 -> initializeLanguage(context)
                    1 -> {
                        initializePermissions(context)
                        // 权限步骤会显示对话框，暂停在这里
                        return
                    }
                }
                
                // 标记当前步骤完成
                val updatedSteps = _initializationState.value.steps.toMutableList()
                updatedSteps[index] = updatedSteps[index].copy(isCompleted = true)
                
                _initializationState.value = _initializationState.value.copy(
                    steps = updatedSteps
                )
                
                // 模拟处理时间
                delay(800)
            }
            
        } catch (e: Exception) {
            // 处理错误
            val currentStep = _initializationState.value.currentStep
            val updatedSteps = _initializationState.value.steps.toMutableList()
            if (currentStep < updatedSteps.size) {
                updatedSteps[currentStep] = updatedSteps[currentStep].copy(isError = true)
            }
            
            _initializationState.value = _initializationState.value.copy(
                steps = updatedSteps,
                hasError = true
            )
        }
    }
    
    private suspend fun initializeLanguage(context: Context) {
        // 初始化语言设置
        delay(500)
        // 这里可以添加具体的语言初始化逻辑
    }
    
    private suspend fun initializePermissions(context: Context) {
        // 初始化权限管理
        delay(600)
        PermissionManager.initialize(context)
        
        // 初始化通知渠道
        NotificationHelper.initNotificationChannel(context)
        
        // 显示权限请求对话框
        _initializationState.value = _initializationState.value.copy(
            showPermissionDialog = true
        )
        
        // 等待权限处理完成
        // 这里会暂停，直到权限对话框被处理
    }
    
    /**
     * 权限处理完成后调用
     */
    fun onPermissionsHandled() {
        _initializationState.value = _initializationState.value.copy(
            showPermissionDialog = false
        )
    }
    
    /**
     * 继续初始化流程
     */
    suspend fun continueInitialization(context: Context) {
        val currentStep = _initializationState.value.currentStep
        
        // 标记当前步骤完成
        val updatedSteps = _initializationState.value.steps.toMutableList()
        if (currentStep < updatedSteps.size) {
            updatedSteps[currentStep] = updatedSteps[currentStep].copy(isCompleted = true)
        }
        
        _initializationState.value = _initializationState.value.copy(
            steps = updatedSteps
        )
        
        // 继续执行剩余步骤
        for (index in (currentStep + 1) until initSteps.size) {
            _initializationState.value = _initializationState.value.copy(
                currentStep = index
            )
            
            when (index) {
                2 -> initializeTools(context)
                3 -> initializeCache(context)
            }
            
            val steps = _initializationState.value.steps.toMutableList()
            steps[index] = steps[index].copy(isCompleted = true)
            
            _initializationState.value = _initializationState.value.copy(
                steps = steps
            )
            
            delay(800)
        }
        
        // 标记初始化完成
        _initializationState.value = _initializationState.value.copy(
            isCompleted = true
        )
    }
    
    private suspend fun initializeTools(context: Context) {
        // 初始化工具库
        delay(700)
        // 这里可以添加工具库的预加载逻辑
    }
    
    private suspend fun initializeCache(context: Context) {
        // 初始化缓存系统
        delay(500)
        // 这里可以添加缓存系统的初始化逻辑
    }
    
    fun reset() {
        _initializationState.value = InitializationState()
    }
}