package cn.lemwood

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.lemwood.navigation.Screen
import cn.lemwood.navigation.bottomNavItems
import cn.lemwood.ui.components.BottomNavigationBar
import cn.lemwood.ui.components.TopAppBarWithMenu
import cn.lemwood.ui.components.PermissionRequestDialog
import cn.lemwood.ui.screens.*
import cn.lemwood.ui.theme.LemwoodToolsTheme
import cn.lemwood.utils.InitializationManager
import cn.lemwood.utils.ThemeManager
import cn.lemwood.utils.SettingsManager
import cn.lemwood.utils.PermissionManager
import cn.lemwood.utils.rememberIsDarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemwoodToolsApp() {
    val context = LocalContext.current
    val isDarkTheme = rememberIsDarkTheme()
    
    LemwoodToolsTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        // 监听初始化状态
        val initState by InitializationManager.initializationState.collectAsState()
        var showInitialization by remember { mutableStateOf(true) }
        
        // 权限对话框状态
        val showPermissionDialog by SettingsManager.showPermissionDialog.collectAsState()
        val permissionState by PermissionManager.permissionState.collectAsState()
        var showPermissionRequest by remember { mutableStateOf(false) }
        
        // 当初始化完成时，隐藏初始化界面并检查是否需要显示权限对话框
        LaunchedEffect(initState.isCompleted) {
            if (initState.isCompleted) {
                showInitialization = false
                // 检查是否需要显示权限对话框
                if (showPermissionDialog && !PermissionManager.areAllRequiredPermissionsGranted()) {
                    showPermissionRequest = true
                }
            }
        }
    
    // 如果需要显示初始化界面
        if (showInitialization) {
            InitializationScreen(
                onInitializationComplete = {
                    showInitialization = false
                }
            )
            return@LemwoodToolsTheme
        }
        
        // 判断是否显示底部导航栏
        val showBottomBar = currentRoute in bottomNavItems.map { it.route }
        
        Scaffold(
            topBar = {
                if (showBottomBar) {
                    TopAppBarWithMenu(
                        title = when (currentRoute) {
                            Screen.Home.route -> stringResource(R.string.home)
                            Screen.Tools.route -> stringResource(R.string.tools)
                            Screen.Search.route -> stringResource(R.string.search)
                            Screen.Settings.route -> stringResource(R.string.settings)
                            else -> stringResource(R.string.app_name)
                        }
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) { 
                    HomeScreen(
                        navController = navController
                    ) 
                }
                composable(Screen.Tools.route) { 
                    ToolsScreen(
                        navController = navController
                    ) 
                }
                composable(Screen.Search.route) { 
                    SearchScreen(
                        navController = navController
                    ) 
                }
                composable(Screen.Settings.route) { 
                    SettingsScreen(navController = navController) 
                }
                
                // 工具页面 - 使用占位符界面
                composable("calculator") { PlaceholderScreen("计算器") }
                composable("unit_converter") { PlaceholderScreen("单位转换器") }
                composable("qr_generator") { PlaceholderScreen("二维码生成器") }
                composable("text_tools") { PlaceholderScreen("文本工具") }
                composable("color_picker") { PlaceholderScreen("颜色选择器") }
                composable("timestamp_converter") { PlaceholderScreen("时间戳转换器") }
                composable("password_generator") { PlaceholderScreen("密码生成器") }
                composable("todo_list") { PlaceholderScreen("待办事项") }
            }
        }
        
        // 权限请求对话框
        if (showPermissionRequest) {
            PermissionRequestDialog(
                onDismiss = { showPermissionRequest = false },
                onDontShowAgain = {
                    SettingsManager.setShowPermissionDialog(false)
                    showPermissionRequest = false
                }
            )
        }
    }
}