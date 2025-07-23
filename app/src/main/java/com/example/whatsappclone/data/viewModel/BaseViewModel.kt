package com.example.whatsappclone.data.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.data.model.ChatListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class BaseViewModel : ViewModel() {

    fun searchUserByPhoneNo(phoneNo : String,callback: (ChatListModel?)->Unit) {

        val currentUser=FirebaseAuth.getInstance().currentUser

        if(currentUser!=null) {
            Log.d("BaseViewModel","User not Found")

            callback(null)

            return
        }

        val databaseReference=FirebaseDatabase.getInstance().getReference("users")

        databaseReference.orderByChild("phoneNo").equalTo(phoneNo)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userSnapshot = snapshot.children.first()
                        val user = userSnapshot.getValue(ChatListModel::class.java)
                        callback(user)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error fetching user: ${error.message} | ${error.details}")
                    callback(null)
                }
            })


    }
}