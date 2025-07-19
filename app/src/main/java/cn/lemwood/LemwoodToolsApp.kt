package cn.lemwood

import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.lemwood.navigation.Screen
import cn.lemwood.navigation.bottomNavItems
import cn.lemwood.ui.components.BottomNavigationBar
import cn.lemwood.ui.components.TopAppBarWithMenu
import cn.lemwood.ui.screens.*
import cn.lemwood.ui.theme.LemwoodToolsTheme
import cn.lemwood.utils.InitializationManager
import cn.lemwood.utils.LanguageManager
import cn.lemwood.utils.rememberCurrentLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemwoodToolsApp() {
    LemwoodToolsTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val currentRoute = navBackStackEntry?.destination?.route
        val context = LocalContext.current
        val currentLanguage = rememberCurrentLanguage()
        
        // 监听初始化状态
        val initState by InitializationManager.initializationState.collectAsState()
        var showInitialization by remember { mutableStateOf(true) }
        
        // 当初始化完成时，隐藏初始化界面
        LaunchedEffect(initState.isCompleted) {
            if (initState.isCompleted) {
                showInitialization = false
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
                        },
                        onLanguageChange = { language ->
                            LanguageManager.setLanguage(context, language)
                        },
                        currentLanguage = currentLanguage
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(
                        navController = navController,
                        currentDestination = currentDestination
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
                    SettingsScreen(
                        onLanguageChange = { language ->
                            LanguageManager.setLanguage(context, language)
                        },
                        currentLanguage = currentLanguage
                    ) 
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
    }
}