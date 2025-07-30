package com.example.whatsappclone.presentation.Screens
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.Navigation.Routes
import com.example.whatsappclone.R
import kotlinx.coroutines.delay

@Composable
fun splashScreen(navHostController: NavHostController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(1000)

        val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isSignedIn = sharedPrefs.getBoolean("isSignedIn", false)

        if (isSignedIn) {
            navHostController.navigate(Routes.HomeScreen) {
                popUpTo(Routes.SplashScreen) { inclusive = true }
            }
        } else {
            navHostController.navigate(Routes.WelcomScreen) {
                popUpTo(Routes.SplashScreen) { inclusive = true }
            }
        }


    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues) // <--- Apply scaffold padding here
        ) {
            Image(
                painter = painterResource(R.drawable.whatsapp_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )

            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "From",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(10.dp))
                Row {
                    Icon(
                        painter = painterResource(R.drawable.meta),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = colorResource(R.color.lightGreen)
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = "Meta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    splashScreen(navHostController = navController)
}