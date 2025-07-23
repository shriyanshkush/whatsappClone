package com.example.whatsappclone.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.presentation.Screens.splashScreen
import com.example.whatsappclone.presentation.UserProfileSetScreen.UserProfileSetScreen
import com.example.whatsappclone.presentation.UserRegistration.UserRegistration
import com.example.whatsappclone.presentation.callScreen.CallScreen
import com.example.whatsappclone.presentation.community.Community
import com.example.whatsappclone.presentation.homescreen.HomeScreen
import com.example.whatsappclone.presentation.upadteScreen.UpdateScreen
import com.example.whatsappclone.presentation.welcomscreen.WelcomeScreen

@Composable
fun NavigationSystem() {
    val navController=rememberNavController()

    NavHost(
        startDestination = Routes.SplashScreen, navController = navController
    ) {

        composable<Routes.SplashScreen> {
            splashScreen(navController)
        }

        composable<Routes.UserRegisterScreen> {
            UserRegistration(navController)
        }

        composable<Routes.WelcomScreen> {
            WelcomeScreen(navController)
        }

        composable<Routes.HomeScreen> {
            HomeScreen()
        }

        composable<Routes.UpdateScreen> {
            UpdateScreen()
        }

        composable<Routes.CallScreen> {
            CallScreen()
        }

        composable<Routes.CommuntiyScreen> {
            Community()
        }

        composable<Routes.UserProfileSetScreen> {
            UserProfileSetScreen(navHostController =navController)
        }
    }
}