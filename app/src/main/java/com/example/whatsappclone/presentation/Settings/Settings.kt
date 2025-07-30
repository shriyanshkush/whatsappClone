package com.example.whatsappclone.presentation.Settings

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.whatsappclone.Navigation.Routes
import com.example.whatsappclone.R
import com.example.whatsappclone.data.viewModel.PhoneAuthViewModel


@SuppressLint("ContextCastToActivity")
@Composable
fun Settings(authViewModel: PhoneAuthViewModel,navHostController: NavHostController) {
    val activity = LocalContext.current as Activity

    Scaffold {
        paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            IconButton(
                onClick = {
                    authViewModel.signOut(activity)
                    navHostController.navigate(Routes.UserRegisterScreen) {
                        popUpTo(Routes.Settings) { inclusive = true }
                    }
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.signout_svgrepo_com),
                    contentDescription = null
                )
            }
        }
    }
}