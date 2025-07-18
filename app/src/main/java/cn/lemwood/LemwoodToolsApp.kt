package cn.lemwood

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemwoodToolsApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // 判断是否显示底部导航栏
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }
    
    // 判断是否可以返回
    val canNavigateBack = currentRoute !in bottomNavItems.map { it.route }
    
    // 获取当前页面标题
    val currentTitle = when (currentRoute) {
        Screen.Home.route -> Screen.Home.title
        Screen.Tools.route -> Screen.Tools.title
        Screen.Search.route -> Screen.Search.title
        Screen.Settings.route -> Screen.Settings.title
        Screen.Calculator.route -> Screen.Calculator.title
        Screen.Converter.route -> Screen.Converter.title
        Screen.QRCode.route -> Screen.QRCode.title
        Screen.TextTools.route -> Screen.TextTools.title
        Screen.ColorPicker.route -> Screen.ColorPicker.title
        Screen.Timer.route -> Screen.Timer.title
        Screen.Weather.route -> Screen.Weather.title
        Screen.Notes.route -> Screen.Notes.title
        else -> "柠枺工具箱"
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
                ToolScreen("计算器", "这里是计算器功能")
            }
            composable(Screen.Converter.route) {
                ToolScreen("单位转换", "这里是单位转换功能")
            }
            composable(Screen.QRCode.route) {
                ToolScreen("二维码", "这里是二维码功能")
            }
            composable(Screen.TextTools.route) {
                ToolScreen("文本工具", "这里是文本工具功能")
            }
            composable(Screen.ColorPicker.route) {
                ToolScreen("颜色选择器", "这里是颜色选择器功能")
            }
            composable(Screen.Timer.route) {
                ToolScreen("计时器", "这里是计时器功能")
            }
            composable(Screen.Weather.route) {
                ToolScreen("天气查询", "这里是天气查询功能")
            }
            composable(Screen.Notes.route) {
                ToolScreen("快速笔记", "这里是快速笔记功能")
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
            "功能开发中...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}