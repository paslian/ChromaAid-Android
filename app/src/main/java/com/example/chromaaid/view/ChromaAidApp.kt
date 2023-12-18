package com.example.chromaaid.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chromaaid.R
import com.example.chromaaid.view.ui.Main.nav.NavigationItem
import com.example.chromaaid.view.ui.Main.nav.Screen
import com.example.chromaaid.view.ui.Main.screen.home.HomeScreen
import com.example.chromaaid.view.ui.Main.screen.home.HomeViewModel
import com.example.chromaaid.view.ui.Main.screen.person.PersonScreen
import com.example.chromaaid.view.ui.setting.SettingActivity

@Composable
fun ChromaAidApp (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    context: Context
) {
    val homeViewModel = viewModel<HomeViewModel>()
    Scaffold(
    bottomBar = {
            BottomBar(navController)
    },
    modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route) {
                HomeScreen(viewModel = homeViewModel, context = context)
            }
            composable(Screen.Profile.route) {
                PersonScreen()
            }
            composable(Screen.Setting.route) {
                val intent = Intent(context, SettingActivity::class.java)
                context.startActivity(intent)
            }
        }
    }


}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_Person),
                icon = Icons.Default.Person,
                screen = Screen.Profile
            ),
            NavigationItem(
                title = stringResource(R.string.settings),
                icon = Icons.Default.Settings,
                screen = Screen.Setting
            ),
        )
        navigationItems.map { item ->
            val isSelected = currentRoute == item.screen.route
            NavigationBarItem(
                icon = {
                    val iconColor = if (isSelected) {
                        colorResource(id = R.color.blue_light)
                    } else {
                        Color.Gray
                    }
                    Icon(
                        imageVector = item.icon,
                        tint = iconColor,
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(36.dp)
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}