package com.example.whatsappclone.presentation.UserProfileSetScreen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whatsappclone.Navigation.Routes
import com.example.whatsappclone.R
import com.example.whatsappclone.data.viewModel.PhoneAuthViewModel
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.Route

@Composable
fun  UserProfileSetScreen(phoneAuthViewModel: PhoneAuthViewModel= hiltViewModel(), navHostController: NavHostController) {

    var name by remember {
        mutableStateOf("")
    }

    var status by remember {
        mutableStateOf("")
    }

    var profileImageUrl by remember {
        mutableStateOf<Uri?>(null)
    }
    var bitmapImg by remember {
        mutableStateOf<Bitmap?>(null)
    }

   val firebaseAuth= Firebase.auth
    val phoneNo =firebaseAuth.currentUser?.phoneNumber?:""

    val userId =firebaseAuth.currentUser?.uid?:""

    val contex= LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            uri: Uri? ->
            profileImageUrl=uri

            uri?.let {

                bitmapImg =if(Build.VERSION.SDK_INT <28) {
                    @Suppress("DEPRECATION")
                    android.provider.MediaStore.Images.Media.getBitmap(contex.contentResolver,it)
                } else{
                    val source=ImageDecoder.createSource(contex.contentResolver,it)
                    ImageDecoder.decodeBitmap(source)
                }
            }
        }
    )

    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(128.dp).clip(CircleShape).border(2.dp, color = Color.Gray, shape = CircleShape).clickable {
                imagePickerLauncher.launch("image/*")
            }
        ) {
            if(bitmapImg!=null) {
                Image(
                    bitmapImg!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().clip(
                        CircleShape
                    ),
                    contentScale = ContentScale.Crop
                )
            } else if(profileImageUrl!=null) {

                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )

            }else{
                Image(
                    painter = painterResource(R.drawable.profile_placeholder),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$phoneNo"
        )

        Spacer(modifier = Modifier.padding(16.dp))

        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            placeholder = {
                Text("Name")
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

        TextField(
            value = status,
            onValueChange = {
                status = it
            },
            placeholder = {
                Text("Status")
            },
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
                Log.d("User details","$name $status $bitmapImg")
                phoneAuthViewModel.saveUserProfile(userId,name,status,bitmapImg!!)
                navHostController.navigate(Routes.HomeScreen)
            },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.darkGreen),
            )
        ) {
            Text(
                text = "Save"
            )
        }
    }


}