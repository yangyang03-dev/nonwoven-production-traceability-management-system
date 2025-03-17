package com.example.product.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route : String
)
val listOfNavItems = listOf(
    NavItem(
        label = "溯源信息",
        icon = Icons.Default.Info,
        route = Screens.HomeScreen.name
    ),
    NavItem(label = "原材料",
        icon = Icons.Default.Build,
        route = Screens.MScreen.name),
    NavItem(label = "用户",
        icon = Icons.Default.Person,
        route = Screens.SettingsScreen.name)
)