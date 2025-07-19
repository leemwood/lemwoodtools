package cn.lemwood.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import cn.lemwood.R
import cn.lemwood.utils.InitializationManager

@Composable
fun InitializationScreen(
    onInitializationComplete: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val initState by InitializationManager.initializationState.collectAsState()
    
    // 启动初始化流程
    LaunchedEffect(Unit) {
        scope.launch {
            InitializationManager.startInitialization(context)
        }
    }
    
    // 当初始化完成时，调用回调
    LaunchedEffect(initState.isCompleted) {
        if (initState.isCompleted) {
            kotlinx.coroutines.delay(1000) // 显示完成状态1秒
            onInitializationComplete()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 应用图标和标题
        Icon(
            Icons.Default.Build,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            stringResource(R.string.init_welcome_message),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // 初始化进度
        if (initState.totalSteps > 0) {
            InitializationProgress(
                currentStep = initState.currentStep,
                totalSteps = initState.totalSteps,
                steps = initState.steps,
                isCompleted = initState.isCompleted,
                hasError = initState.hasError
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 错误处理
        if (initState.hasError) {
            ErrorSection(
                onRetry = {
                    scope.launch {
                        InitializationManager.reset()
                        InitializationManager.startInitialization(context)
                    }
                }
            )
        }
    }
}

@Composable
private fun InitializationProgress(
    currentStep: Int,
    totalSteps: Int,
    steps: List<InitializationManager.InitializationStep>,
    isCompleted: Boolean,
    hasError: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 总体进度条
        val progress = if (isCompleted) 1f else currentStep.toFloat() / totalSteps.toFloat()
        
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = if (hasError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 进度文本
        Text(
            if (isCompleted) {
                stringResource(R.string.init_completed)
            } else if (hasError) {
                stringResource(R.string.init_error)
            } else {
                stringResource(R.string.init_progress, currentStep + 1, totalSteps)
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 步骤列表
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(steps) { index, step ->
                InitializationStepItem(
                    step = step,
                    isActive = index == currentStep && !isCompleted,
                    stepNumber = index + 1
                )
            }
        }
    }
}

@Composable
private fun InitializationStepItem(
    step: InitializationManager.InitializationStep,
    isActive: Boolean,
    stepNumber: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isActive) 4.dp else 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 步骤图标
            val icon: ImageVector = when {
                step.isError -> Icons.Default.Error
                step.isCompleted -> Icons.Default.CheckCircle
                isActive -> Icons.Default.Refresh
                else -> Icons.Default.Circle
            }
            
            val iconColor = when {
                step.isError -> MaterialTheme.colorScheme.error
                step.isCompleted -> MaterialTheme.colorScheme.primary
                isActive -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outline
            }
            
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = iconColor
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    stringResource(step.nameResId),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = if (isActive) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
                
                Text(
                    stringResource(step.descriptionResId),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isActive) {
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
            
            // 加载动画
            if (isActive && !step.isCompleted) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun ErrorSection(
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Error,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            stringResource(R.string.init_error_message),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.retry))
        }
    }
}