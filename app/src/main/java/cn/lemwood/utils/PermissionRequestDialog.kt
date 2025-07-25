package cn.lemwood.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import cn.lemwood.tools.R

@Composable
fun PermissionRequestDialog(
    onDismiss: () -> Unit,
    onPermissionsResult: (Map<String, Boolean>) -> Unit,
    settingsManager: SettingsManager
) {
    val context = LocalContext.current
    val permissionManager = remember { PermissionManager() }
    var dontShowAgain by remember { mutableStateOf(false) }
    
    // 初始化权限管理器
    LaunchedEffect(Unit) {
        permissionManager.initialize(context)
    }
    
    // 权限状态
    val permissionState by permissionManager.permissionState.collectAsState()
    
    // 单个权限请求启动器
    val singlePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // 这里会在权限结果返回时调用
    }
    
    // 多个权限请求启动器
    val multiplePermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // 更新权限状态
        permissions.forEach { (permission, isGranted) ->
            permissionManager.updatePermissionStatus(permission, isGranted)
        }
        onPermissionsResult(permissions)
    }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // 标题
                Text(
                    text = stringResource(R.string.permission_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // 描述
                Text(
                    text = stringResource(R.string.permission_dialog_message),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // 权限列表
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(permissionManager.getAllPermissionsList()) { permission ->
                        PermissionItem(
                            permission = permission,
                            isGranted = permissionState.permissions[permission.permission] == true,
                            onRequestPermission = { 
                                if (permission.permission == Manifest.permission.POST_NOTIFICATIONS) {
                                    singlePermissionLauncher.launch(permission.permission)
                                } else {
                                    multiplePermissionsLauncher.launch(arrayOf(permission.permission))
                                }
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // "下次启动不再弹出"选项
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = dontShowAgain,
                        onCheckedChange = { dontShowAgain = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = stringResource(R.string.permission_dont_show_again),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = stringResource(R.string.permission_dont_show_again_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 按钮
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            if (dontShowAgain) {
                                settingsManager.setShowPermissionDialog(false)
                            }
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.permission_skip))
                    }
                    
                    Button(
                        onClick = {
                            // 请求所有未授权的权限
                            val unGrantedPermissions = permissionManager.getAllPermissions()
                                .filter { permission ->
                                    ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
                                }
                            
                            if (unGrantedPermissions.isNotEmpty()) {
                                multiplePermissionsLauncher.launch(unGrantedPermissions.toTypedArray())
                            }
                            
                            if (dontShowAgain) {
                                settingsManager.setShowPermissionDialog(false)
                            }
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.permission_continue))
                    }
                }
            }
        }
    }
}

@Composable
fun PermissionItem(
    permission: Permission,
    isGranted: Boolean,
    onRequestPermission: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isGranted) 
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = getPermissionDisplayName(permission.permission),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    
                    if (!permission.isRequired) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.permission_optional),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                
                Text(
                    text = getPermissionDescription(permission.permission),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            if (isGranted) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.permission_granted),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Button(
                    onClick = onRequestPermission,
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = stringResource(R.string.permission_grant),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun getPermissionDisplayName(permission: String): String {
    return when (permission) {
        Manifest.permission.POST_NOTIFICATIONS -> stringResource(R.string.permission_notifications)
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_AUDIO -> stringResource(R.string.permission_storage)
        Manifest.permission.CAMERA -> stringResource(R.string.permission_camera)
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION -> stringResource(R.string.permission_location)
        else -> permission
    }
}

@Composable
private fun getPermissionDescription(permission: String): String {
    return when (permission) {
        Manifest.permission.POST_NOTIFICATIONS -> stringResource(R.string.permission_notifications_desc)
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_AUDIO -> stringResource(R.string.permission_storage_desc)
        Manifest.permission.CAMERA -> stringResource(R.string.permission_camera_desc)
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION -> stringResource(R.string.permission_location_desc)
        else -> ""
    }
}