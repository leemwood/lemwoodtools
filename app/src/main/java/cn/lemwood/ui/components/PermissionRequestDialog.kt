package cn.lemwood.ui.components

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.lemwood.R
import cn.lemwood.utils.PermissionManager

@Composable
fun PermissionRequestDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onPermissionsHandled: () -> Unit
) {
    val context = LocalContext.current
    val permissionState by PermissionManager.permissionState.collectAsState()
    
    // 通知权限请求器
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        PermissionManager.updatePermissionStatus(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
            isGranted
        )
    }
    
    // 多权限请求器
    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.forEach { (permission, isGranted) ->
            PermissionManager.updatePermissionStatus(context, permission, isGranted)
        }
    }
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Security,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "权限设置",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Column {
                    Text(
                        "为了提供更好的使用体验，应用需要以下权限。您可以选择性授予权限：",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(permissionState.permissions) { permission ->
                            PermissionItem(
                                permission = permission,
                                onRequestPermission = { perm ->
                                    when (perm.permission) {
                                        Manifest.permission.POST_NOTIFICATIONS -> {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                                notificationPermissionLauncher.launch(perm.permission)
                                            }
                                        }
                                        else -> {
                                            multiplePermissionLauncher.launch(arrayOf(perm.permission))
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onPermissionsHandled()
                        onDismiss()
                    }
                ) {
                    Text("继续")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("跳过")
                }
            }
        )
    }
}

@Composable
private fun PermissionItem(
    permission: PermissionManager.Permission,
    onRequestPermission: (PermissionManager.Permission) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (permission.isGranted) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 权限图标
            val icon: ImageVector = when (permission.name) {
                "notifications" -> Icons.Default.Notifications
                "storage" -> Icons.Default.Storage
                "camera" -> Icons.Default.CameraAlt
                "location" -> Icons.Default.LocationOn
                else -> Icons.Default.Security
            }
            
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (permission.isGranted) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    stringResource(permission.nameResId),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    stringResource(permission.descriptionResId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (!permission.isRequired) {
                    Text(
                        "可选权限",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // 权限状态和操作按钮
            if (permission.isGranted) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "已授权",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                FilledTonalButton(
                    onClick = { onRequestPermission(permission) },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        "授权",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}