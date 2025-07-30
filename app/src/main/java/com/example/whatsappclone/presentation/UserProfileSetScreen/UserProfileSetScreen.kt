package com.example.whatsappclone.presentation.UserProfileSetScreen

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whatsappclone.Navigation.Routes
import com.example.whatsappclone.R
import com.example.whatsappclone.data.viewModel.PhoneAuthViewModel
import com.example.whatsappclone.util.CloudinaryUploader
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun UserProfileSetScreen(
    phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val firebaseAuth = Firebase.auth
    val phoneNo = firebaseAuth.currentUser?.phoneNumber ?: ""
    val userId = firebaseAuth.currentUser?.uid ?: ""
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            profileImageUri = uri
        }
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable { imagePickerLauncher.launch("image/*") }
        ) {
            when {
                profileImageUri != null -> {
                    AsyncImage(
                        model = profileImageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                else -> {
                    Image(
                        painter = painterResource(R.drawable.profile_placeholder),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = phoneNo)

        Spacer(modifier = Modifier.padding(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Name") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = colorResource(R.color.lightGreen),
                focusedIndicatorColor = colorResource(R.color.lightGreen)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = status,
            onValueChange = { status = it },
            placeholder = { Text("Status") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = colorResource(R.color.lightGreen),
                focusedIndicatorColor = colorResource(R.color.lightGreen)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (profileImageUri != null) {
                    val folderPath = "whatsapp-clone/users/$userId"
                    val fileName = "profile.jpg"

                    CloudinaryUploader.uploadImage(
                        context = context,
                        imageUri = profileImageUri!!,
                        folderPath = folderPath,
                        fileName = fileName
                    ) { imageUrl ->
                        if (imageUrl != null) {
                            phoneAuthViewModel.saveUserProfileUrl(userId, name, status, imageUrl)
                            navHostController.navigate(Routes.HomeScreen)
                        } else {
                            Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.darkGreen),
            )
        ) {
            Text(text = "Save")
        }
    }
}
