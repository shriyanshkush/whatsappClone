package com.example.whatsappclone.presentation.welcomscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.Navigation.Routes
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.Screens.splashScreen

@Composable
fun WelcomeScreen(navHostController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.whatsapp_sticker),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )

        Text(
            text = "Welcome To WhatsApp",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(
                text = "Privacy Policy",
                color= Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Privacy Policy",
                color= colorResource(R.color.lightGreen)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Tap Again and Continue",
                color= Color.Gray
            )
        }

        Row {
            Text(
                text = "Accept the",
                color= Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Terms and Services",
                color= colorResource(R.color.lightGreen)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navHostController.navigate(Routes.UserRegisterScreen)
            },

            modifier = Modifier.size(width = 280.dp, height = 43.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.darkGreen)
            )
        ) {
            Text(
                text = "Agree And Continue",
                color = Color.White,
                fontSize = 16.sp
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun WelcomScreenPreview() {
    val navController = rememberNavController()
    WelcomeScreen(navHostController = navController)
}