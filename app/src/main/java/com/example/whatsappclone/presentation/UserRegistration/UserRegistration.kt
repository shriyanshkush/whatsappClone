package com.example.whatsappclone.presentation.UserRegistration

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.whatsappclone.Navigation.Routes
import com.example.whatsappclone.R
import com.example.whatsappclone.data.viewModel.AuthState
import com.example.whatsappclone.data.viewModel.BaseFeatureViewModel
import com.example.whatsappclone.data.viewModel.BaseViewModel
import com.example.whatsappclone.data.viewModel.PhoneAuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("ContextCastToActivity")
@Composable
fun UserRegistration(navController: NavController, phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel(),baseViewModel: BaseFeatureViewModel) {

    val authState by phoneAuthViewModel.authState.collectAsState()
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedCountry by remember {
        mutableStateOf("India")
    }

    var countrycode by remember {
        mutableStateOf("+91")
    }

    var phoneNo by remember {
        mutableStateOf("")
    }

    var otp by remember {
        mutableStateOf("")
    }

    var verificationId by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Your Phone No.",
            fontSize = 24.sp,
            color = colorResource(R.color.lightGreen),
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        val infoText = buildAnnotatedString {
            append("WhatsApp will need to verify your phone number. ")
            withStyle(style = SpanStyle(color = colorResource(R.color.lightGreen))) {
                append("What's my number?")
            }
        }

        Text(
            text = infoText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            fontSize = 16.sp,
            lineHeight = 20.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.width(230.dp)) {
                Text(
                    text = selectedCountry,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    tint = colorResource(R.color.lightGreen)
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 66.dp),
            thickness = 2.dp,
            color = colorResource(R.color.lightGreen)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("India", "USA", "China", "UK").forEach { country ->
                DropdownMenuItem(text = { Text(text = country) }, onClick = {
                    selectedCountry = country
                    countrycode = when (country) {
                        "India" -> "+91"
                        "USA" -> "+1"
                        "China" -> "+86"
                        "UK" -> "+44"
                        else -> "+91"
                    }
                    expanded = false
                })
            }
        }

        when (authState) {
            is AuthState.Ideal, is AuthState.Loading, is AuthState.CodeSent -> {

                if (authState is AuthState.CodeSent) {
                    verificationId = (authState as AuthState.CodeSent).verificationId
                }

                if (verificationId == null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                        TextField(
                            value = countrycode,
                            onValueChange = {
                                countrycode = it
                            },
                            modifier = Modifier.width(70.dp),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = colorResource(R.color.lightGreen),
                                focusedIndicatorColor = colorResource(R.color.lightGreen)
                            )
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        TextField(
                            value = phoneNo,
                            onValueChange = {
                                phoneNo = it
                            },
                            placeholder = {
                                Text("phone Number")
                            },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = colorResource(R.color.lightGreen),
                                focusedIndicatorColor = colorResource(R.color.lightGreen)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (phoneNo.isNotEmpty()) {
                                var fullphoneNumber = "$countrycode$phoneNo"
                                phoneAuthViewModel.sendVerificationCode(fullphoneNumber, activity)
                            } else {
                                Toast.makeText(context, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.darkGreen)
                        )
                    ) {
                        Text(
                            text = "Send OTP",
                            fontSize = 16.sp
                        )
                    }

                    if (authState is AuthState.Loading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }

                } else {
                    // otp input screen
                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "Enter Otp",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.darkGreen)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = otp,
                        onValueChange = {
                            otp = it
                        },
                        placeholder = {
                            Text("OTP")
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = colorResource(R.color.lightGreen),
                            focusedIndicatorColor = colorResource(R.color.lightGreen)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (otp.isNotEmpty() && verificationId != null) {
                                phoneAuthViewModel.verifyOtp(otp, context)
                            } else {
                                Toast.makeText(context, "Please Enter a valid OTP", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.darkGreen)
                        )
                    ) {
                        Text(
                            text = "Verify OTP"
                        )
                    }

                    if (authState is AuthState.Loading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }
                }
            }

            is AuthState.Success -> {
                Log.d("phone Auth", "Login Success")

                phoneAuthViewModel.resetAuthState()

                val currUserId=FirebaseAuth.getInstance().currentUser?.uid

                if(currUserId!=null) {
                    baseViewModel.getCurrentUserDetails(currUserId) {
                        name,pfp ->
                        if(name!=null) {
                            navController.navigate(Routes.HomeScreen) {
                                popUpTo<Routes.UserRegisterScreen> {
                                    inclusive = true
                                }
                            }
                        } else{
                            navController.navigate(Routes.UserProfileSetScreen) {
                                popUpTo<Routes.UserRegisterScreen> {
                                    inclusive = true
                                }
                            }
                        }
                    }
                } else{
                    navController.navigate(Routes.UserProfileSetScreen) {
                        popUpTo<Routes.UserRegisterScreen> {
                            inclusive = true
                        }
                    }
                }
            }

            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}