package com.example.whatsappclone.Navigation
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.whatsappclone.data.viewModel.BaseFeatureViewModel
import com.example.whatsappclone.data.viewModel.PhoneAuthViewModel
import com.example.whatsappclone.presentation.ChatScreen.ChatScreen
import com.example.whatsappclone.presentation.Screens.splashScreen
import com.example.whatsappclone.presentation.Settings.Settings
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
            val baseViewModel: BaseFeatureViewModel = hiltViewModel()
            val authViewModel :PhoneAuthViewModel= hiltViewModel()
            UserRegistration(navController, phoneAuthViewModel = authViewModel,baseViewModel)
        }

        composable<Routes.WelcomScreen> {
            WelcomeScreen(navController)
        }

        composable<Routes.UserProfileSetScreen> {
            val authViewModel :PhoneAuthViewModel= hiltViewModel()
            UserProfileSetScreen(authViewModel,navController)
        }

        composable<Routes.HomeScreen> {
            val baseViewModel: BaseFeatureViewModel = hiltViewModel()
            HomeScreen(navController,baseViewModel)
        }

        composable<Routes.UpdateScreen> {
            UpdateScreen(navController)
        }

        composable<Routes.CallScreen> {
            CallScreen(navController)
        }

        composable<Routes.CommuntiyScreen> {
            Community(navController)
        }

        composable<Routes.Settings> {
            val authViewModel :PhoneAuthViewModel= hiltViewModel()
            Settings(authViewModel,navController)
        }

        composable(
            route = Routes.ChatScreen.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("pfpUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val pfpUrl = backStackEntry.arguments?.getString("pfpUrl") ?: ""

            ChatScreen(
                chatId = userId,
                userName = name,
                profileUrl = pfpUrl,
                viewModel = hiltViewModel(),
                navController = navController
            )
        }



    }
}