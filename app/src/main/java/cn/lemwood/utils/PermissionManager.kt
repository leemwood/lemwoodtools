package cn.lemwood.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 权限管理器
 * 负责管理应用所需的各种权限
 */
object PermissionManager {
    
    data class Permission(
        val name: String,
        val permission: String,
        val nameResId: Int,
        val descriptionResId: Int,
        val isRequired: Boolean = true,
        val isGranted: Boolean = false
    )
    
    data class PermissionState(
        val permissions: List<Permission> = emptyList(),
        val allRequiredGranted: Boolean = false,
        val isInitialized: Boolean = false
    )
    
    private val _permissionState = MutableStateFlow(PermissionState())
    val permissionState: StateFlow<PermissionState> = _permissionState.asStateFlow()
    
    private val requiredPermissions = listOf(
        Permission(
            name = "notifications",
            permission = Manifest.permission.POST_NOTIFICATIONS,
            nameResId = cn.lemwood.R.string.permission_notifications,
            descriptionResId = cn.lemwood.R.string.permission_notifications_desc,
            isRequired = false // 可选权限，用户可以选择是否开启
        ),
        Permission(
            name = "storage",
            permission = Manifest.permission.WRITE_EXTERNAL_STORAGE,
            nameResId = cn.lemwood.R.string.permission_storage,
            descriptionResId = cn.lemwood.R.string.permission_storage_desc,
            isRequired = false // Android 10+ 不再需要此权限
        ),
        Permission(
            name = "camera",
            permission = Manifest.permission.CAMERA,
            nameResId = cn.lemwood.R.string.permission_camera,
            descriptionResId = cn.lemwood.R.string.permission_camera_desc,
            isRequired = false // 仅在使用二维码扫描时需要
        ),
        Permission(
            name = "location",
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            nameResId = cn.lemwood.R.string.permission_location,
            descriptionResId = cn.lemwood.R.string.permission_location_desc,
            isRequired = false // 仅在使用天气功能时需要
        )
    )
    
    /**
     * 初始化权限管理器
     */
    fun initialize(context: Context) {
        val updatedPermissions = requiredPermissions.map { permission ->
            permission.copy(
                isGranted = ContextCompat.checkSelfPermission(
                    context,
                    permission.permission
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
        
        val allRequiredGranted = updatedPermissions
            .filter { it.isRequired }
            .all { it.isGranted }
        
        _permissionState.value = PermissionState(
            permissions = updatedPermissions,
            allRequiredGranted = allRequiredGranted,
            isInitialized = true
        )
    }
    
    /**
     * 更新权限状态
     */
    fun updatePermissionStatus(context: Context, permission: String, isGranted: Boolean) {
        val updatedPermissions = _permissionState.value.permissions.map { perm ->
            if (perm.permission == permission) {
                perm.copy(isGranted = isGranted)
            } else {
                perm
            }
        }
        
        val allRequiredGranted = updatedPermissions
            .filter { it.isRequired }
            .all { it.isGranted }
        
        _permissionState.value = _permissionState.value.copy(
            permissions = updatedPermissions,
            allRequiredGranted = allRequiredGranted
        )
    }
    
    /**
     * 获取所有权限列表
     */
    fun getAllPermissions(): List<String> {
        return requiredPermissions.map { it.permission }
    }
    
    /**
     * 获取必需权限列表
     */
    fun getRequiredPermissions(): List<String> {
        return requiredPermissions
            .filter { it.isRequired }
            .map { it.permission }
    }
    
    /**
     * 检查特定权限是否已授予
     */
    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
    
    /**
     * 检查是否所有必需权限都已授予
     */
    fun areAllRequiredPermissionsGranted(): Boolean {
        return _permissionState.value.allRequiredGranted
    }
    
    /**
     * 重置权限状态
     */
    fun reset() {
        _permissionState.value = PermissionState()
    }
}