package com.example.december2023.voice.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todolist.ui.detailpage.DetailPage
import com.todolist.ui.homepage.HomePage
import com.todolist.ui.listpage.ListPage

sealed class Routes(
    val route: String,
) {
    object HOME : Routes("Home")
    object DETAIL : Routes("Detail")
    object LIST : Routes("List")
}

@Composable
fun Navigation() {

    var nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.HOME.route)
    {
        composable(Routes.HOME.route)
        {
            HomePage(nav = nav)
        }
        composable(Routes.LIST.route)
        {
            ListPage(nav = nav)
        }
        composable(
            "${Routes.DETAIL.route}/{id}",
        )
        {
            DetailPage(nav = nav)
        }
    }
}