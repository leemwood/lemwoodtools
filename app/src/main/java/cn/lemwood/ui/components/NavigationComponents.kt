package cn.lemwood.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.lemwood.R
import cn.lemwood.navigation.Screen
import cn.lemwood.utils.HapticFeedbackHelper
import cn.lemwood.utils.rememberHapticFeedbackEnabled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithMenu(
    title: String,
    canNavigateBack: Boolean = false,
    onNavigateBack: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    val context = LocalContext.current
    val enableHapticFeedback = rememberHapticFeedbackEnabled()
    
    TopAppBar(
        title = {
            Text(
                title,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = {
                    if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                        HapticFeedbackHelper.buttonClickVibration(context)
                    }
                    onNavigateBack()
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "返回"
                    )
                }
            }
        },
        actions = {
            actions()
            IconButton(onClick = {
                if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                    HapticFeedbackHelper.buttonClickVibration(context)
                }
                onMenuClick()
            }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "菜单"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val enableHapticFeedback = rememberHapticFeedbackEnabled()
    
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        cn.lemwood.navigation.bottomNavItems.forEach { screen ->
            val label = when (screen.route) {
                Screen.Home.route -> stringResource(R.string.nav_home)
                Screen.Tools.route -> stringResource(R.string.nav_tools)
                Screen.Search.route -> stringResource(R.string.nav_search)
                Screen.Settings.route -> stringResource(R.string.nav_settings)
                else -> screen.title
            }
            
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = label
                    )
                },
                label = { Text(label) },
                selected = currentRoute == screen.route,
                onClick = { 
                    if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                        HapticFeedbackHelper.buttonClickVibration(context)
                    }
                    onNavigate(screen.route) 
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}