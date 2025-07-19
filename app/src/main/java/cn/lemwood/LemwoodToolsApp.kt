package cn.lemwood

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.lemwood.navigation.Screen
import cn.lemwood.navigation.bottomNavItems
import cn.lemwood.ui.components.BottomNavigationBar
import cn.lemwood.ui.components.TopAppBarWithMenu
import cn.lemwood.ui.screens.HomeScreen
import cn.lemwood.ui.screens.ToolsScreen
import cn.lemwood.ui.screens.SearchScreen
import cn.lemwood.ui.screens.SettingsScreen
import cn.lemwood.utils.LanguageManager
import cn.lemwood.utils.rememberCurrentLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemwoodToolsApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val currentLanguage = rememberCurrentLanguage()
    
    // 判断是否显示底部导航栏
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }
    
    // 判断是否可以返回
    val canNavigateBack = currentRoute !in bottomNavItems.map { it.route }
    
    // 获取当前页面标题
    val currentTitle = when (currentRoute) {
        Screen.Home.route -> stringResource(R.string.nav_home)
        Screen.Tools.route -> stringResource(R.string.nav_tools)
        Screen.Search.route -> stringResource(R.string.nav_search)
        Screen.Settings.route -> stringResource(R.string.nav_settings)
        Screen.Calculator.route -> stringResource(R.string.tool_calculator)
        Screen.Converter.route -> stringResource(R.string.tool_converter)
        Screen.QRCode.route -> stringResource(R.string.tool_qrcode)
        Screen.TextTools.route -> stringResource(R.string.tool_text_tools)
        Screen.ColorPicker.route -> stringResource(R.string.tool_color_picker)
        Screen.Timer.route -> stringResource(R.string.tool_timer)
        Screen.Weather.route -> stringResource(R.string.tool_weather)
        Screen.Notes.route -> stringResource(R.string.tool_notes)
        else -> stringResource(R.string.app_name)
    }
    
    Scaffold(
        topBar = {
            TopAppBarWithMenu(
                title = currentTitle,
                canNavigateBack = canNavigateBack,
                onNavigateBack = { navController.popBackStack() },
                onMenuClick = { /* TODO: 实现侧边菜单 */ }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            // 避免重复导航到同一页面
                            launchSingleTop = true
                            // 清除回退栈到起始页面
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            // 恢复状态
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // 底部导航页面
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.Tools.route) {
                ToolsScreen(navController)
            }
            composable(Screen.Search.route) {
                SearchScreen(navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController)
            }
            
            // 工具页面
            composable(Screen.Calculator.route) {
                ToolScreen(
                    title = stringResource(R.string.tool_calculator),
                    content = stringResource(R.string.tool_calculator_desc)
                )
            }
            composable(Screen.Converter.route) {
                ToolScreen(
                    title = stringResource(R.string.tool_converter),
                    content = stringResource(R.string.tool_converter_desc)
                )
            }
            composable(Screen.QRCode.route) {
                ToolScreen(
                    title = stringResource(R.string.tool_qrcode),
                    content = stringResource(R.string.tool_qrcode_desc)
                )
            }
            composable(Screen.TextTools.route) {
                ToolScreen(
                    title = stringResource(R.string.tool_text_tools),
                    content = stringResource(R.string.tool_text_tools_desc)
                )
            }
            composable(Screen.ColorPicker.route) {
                ToolScreen(
                    title = stringResource(R.string.tool_color_picker),
                    content = stringResource(R.string.tool_color_picker_desc)
                )
            }
            composable(Screen.Timer.route) {
                ToolScreen(
                    title = stringResource(R.string.tool_timer),
                    content = stringResource(R.string.tool_timer_desc)
                )
            }
            composable(Screen.Weather.route) {
                ToolScreen(
                    title = stringResource(R.string.tool_weather),
                    content = stringResource(R.string.tool_weather_desc)
                )
            }
            composable(Screen.Notes.route) {
                ToolScreen(
                    title = stringResource(R.string.tool_notes),
                    content = stringResource(R.string.tool_notes_desc)
                )
            }
        }
    }
}

@Composable
fun ToolScreen(title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Build,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            stringResource(R.string.feature_in_development),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}