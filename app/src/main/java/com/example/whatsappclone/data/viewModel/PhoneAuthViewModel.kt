package com.example.whatsappclone.data.viewModel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.data.model.phoneAuthUser
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject


//class ka object hilt bana ke dega
@HiltViewModel
class PhoneAuthViewModel  @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ViewModel() {
    private val _authState= MutableStateFlow<AuthState>(AuthState.Ideal)
    val authState=_authState.asStateFlow()

    private val UserRef=firebaseDatabase.reference.child("users")

    fun sendVerificationCode(phoneNo:String,activity: Activity) {

        _authState.value=AuthState.Loading

        val option= object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                Log.d("Phone Auth","On Code Sent triggered, verification ID:$id")
                _authState.value=AuthState.CodeSent(id)
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                sighInWithCredential(credential,activity)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Log.d("Phone Auth","Verification Failed: ${exception.message}")
                _authState.value=AuthState.Error(exception.message?:"Verification Failed")
            }
        }

        val phoneAuthOptions=PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNo)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(option)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }


    private fun sighInWithCredential(credential: PhoneAuthCredential,context: Context) {
        _authState.value=AuthState.Loading

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            task ->
            if(task.isSuccessful) {
                val user=firebaseAuth.currentUser
                val phoneauthUser=phoneAuthUser(
                    userId = user?.uid?:"",
                    phoneNo = user?.phoneNumber?:""
                )

                markUserSignedIn(context)
                _authState.value=AuthState.Success(phoneauthUser)

                fetchUserProfile(user?.uid?:"")
            } else{
                _authState.value=AuthState.Error(task.exception?.message?:"SignIn Failed")
            }
        }
    }

    private fun markUserSignedIn(context:Context) {
        val sharedPrefs=context.getSharedPreferences("app_prefs",Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("isSignedIn",true).apply()
    }

    private fun fetchUserProfile(userId:String) {
        val userRef=UserRef.child(userId)

        userRef.get().addOnSuccessListener {
            snapshot->

            if(snapshot.exists()) {
                val userProfile=snapshot.getValue(phoneAuthUser::class.java)

                if(userProfile!=null) {
                    _authState.value=AuthState.Success(userProfile)
                }
            }
        }.addOnFailureListener {
            _authState.value=AuthState.Error("failed to fetch user")
        }
    }

    fun verifyOtp(otp:String,context: Context) {
        val currentAuthState=_authState.value

        if(currentAuthState !is AuthState.CodeSent || currentAuthState.verificationId.isEmpty()) {
            Log.d("Phone Auth","Attempting to verify otp without a valid Id")

            _authState.value=AuthState.Error("Verification not Started or invalid Id")

            return
        }

        val credential=PhoneAuthProvider.getCredential(currentAuthState.verificationId,otp)
        sighInWithCredential(credential,context)
    }

    fun saveUserProfileUrl(
        userID: String,
        name: String,
        status: String,
        pfpUrl: String   // Cloudinary URL instead of Bitmap
    ) {
        val userProfile = phoneAuthUser(
            userId = userID,
            name = name,
            status = status,
            pfpUrl = pfpUrl,  // now storing the URL
            phoneNo = firebaseAuth.currentUser?.phoneNumber ?: ""
        )
        firebaseDatabase.reference.child("users").child(userID).setValue(userProfile)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseProfile", "User profile saved with Cloudinary URL.")
                } else {
                    Log.e("FirebaseProfile", "Save failed: ${task.exception?.message}")
                }
            }
    }


    private fun convertBitmaptoBase64(bitmap: Bitmap) :String{
        val byteArrayOutputStream=ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)

        val byteArray=byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray,Base64.DEFAULT)
    }

    fun resetAuthState() {
        _authState.value=AuthState.Ideal
    }

    fun signOut(activity: Activity) {
        firebaseAuth.signOut()
        val sharedPrefs=activity.getSharedPreferences("app_prefs",Context.MODE_PRIVATE)
        sharedPrefs.edit().putBoolean("isSignedIn",false).apply()
    }
}

//events fixed so sealed class
sealed class AuthState{

    object Ideal:AuthState()
    object Loading:AuthState()
    data class CodeSent(val verificationId:String):AuthState()
    data class Success(val user: phoneAuthUser) :AuthState()
    data class Error(val error:String) :AuthState()
}