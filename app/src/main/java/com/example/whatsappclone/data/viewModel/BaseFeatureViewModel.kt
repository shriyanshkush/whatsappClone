package com.example.whatsappclone.data.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.data.model.ChatListModel1
import com.example.whatsappclone.data.model.ChatUiState
import com.example.whatsappclone.data.model.Message1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BaseFeatureViewModel : ViewModel() {

    private val databaseRef = FirebaseDatabase.getInstance("https://whatsappclone-21e09-default-rtdb.asia-southeast1.firebasedatabase.app").reference
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    //Safe call operator (?.) → If currentUser is not null, it retrieves the user's unique ID (UID).
    //If currentUser is null (user not signed in), then currentUserId will be null.


    private val _chatList=MutableStateFlow<List<ChatListModel1>>(emptyList())
    val chatList=_chatList.asStateFlow()
    //asStateFlow() converts MutableStateFlow to an immutable StateFlow for exposing data to UI safely.
    //Prevents UI from modifying the state directly.

    private val _chatUiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val chatUiState = _chatUiState.asStateFlow()


    fun getCurrentUserDetails(
        userId: String,
        callback: (String?, String?) -> Unit
    ) {
        databaseRef.child("users").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java)
                        val profileUrl = snapshot.child("pfpUrl").getValue(String::class.java)
                        callback(name, profileUrl)
                    } else {
                        callback(null, null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Failed to load user details: ${error.message}")
                    callback(null, null)
                }
            })
    }




    /**
     * ✅ Search user by phone number
     */
    fun searchUserByPhoneNo(phoneNo: String, callback: (String?, String?, String?) -> Unit) {
        databaseRef.child("users")
            .orderByChild("phoneNo").equalTo(phoneNo)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.children.first()
                        val userId = user.key
                        val name = user.child("name").getValue(String::class.java)
                        val profile = user.child("pfpUrl").getValue(String::class.java)
                        callback(userId, name, profile)
                    } else {
                        callback(null, null, null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error: ${error.message}")
                    callback(null, null, null)
                }
            })
    }

    //Add user to chatList

    fun addUsertoChat(senderName:String,senderPfpUrl:String,reciverName:String,reciverUserId:String,reciverPfpUrl:String) {
        if(currentUserId==null) return

        if(currentUserId==reciverUserId) return

        val chatId = if (currentUserId < reciverUserId) "${currentUserId}_${reciverUserId}" else "${reciverUserId}_${currentUserId}"

        val senderChatData = mapOf(
            "reciverUserId" to reciverUserId,
            "reciverName" to reciverName,
            "reciverPfpUrl" to reciverPfpUrl,
            "lastMessage" to "No Message",
            "lastMessageTimestamp" to 0
        )


        val reciverChatData = mapOf(
            "reciverUserId" to currentUserId,
            "reciverName" to senderName,
            "reciverPfpUrl" to senderPfpUrl,
            "lastMessage" to "No Message",
            "lastMessageTimestamp" to 0
        )

        Log.d("chatsenderpfp",senderPfpUrl)

        Log.d("ChatDataLog", "Sender Chat Data: $senderChatData")
        Log.d("ChatDataLog", "Receiver Chat Data: $reciverChatData")

        val updates = mapOf(
            "userChats/$currentUserId/$chatId" to senderChatData,
            "userChats/$reciverUserId/$chatId" to reciverChatData
        )

        databaseRef.updateChildren(updates).addOnSuccessListener {
            Log.d("BaseViewModel", "Chat added successfully")
        }.addOnFailureListener {
            Log.e("BaseViewModel", "Failed to add chat: ${it.message}")
        }
    }


    // Send message & update last message

    fun sendMessage(receiverId: String, message: Message1) {
        if (currentUserId == null) return
        val chatId = if (currentUserId < receiverId) "${currentUserId}_${receiverId}" else "${receiverId}_${currentUserId}"
        val msgId = databaseRef.push().key ?: return
        val timeStamp = System.currentTimeMillis()


        databaseRef.child("messages").child(chatId).child(msgId).setValue(message)

        val updates = mapOf(
            "userChats/$currentUserId/$chatId/lastMessage" to message.messageText,
            "userChats/$currentUserId/$chatId/lastMessageTimestamp" to timeStamp,
            "userChats/$receiverId/$chatId/lastMessage" to message.messageText,
            "userChats/$receiverId/$chatId/lastMessageTimestamp" to timeStamp
        )

        databaseRef.updateChildren(updates)
    }

    //Load chat list for current user

    fun loadChatList() {
        if (currentUserId == null) return
        databaseRef.child("userChats").child(currentUserId)
            .orderByChild("lastMessageTimestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chats = mutableListOf<ChatListModel1>()
                    for (child in snapshot.children) {
                        val chat = child.getValue(ChatListModel1::class.java)
                        if (chat != null) chats.add(chat)
                    }
                    _chatList.value = chats.sortedByDescending { it.lastMessageTimestamp ?: 0 }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error loading chats: ${error.message}")
                }
            })
    }

    fun observeMessages(otherUserId: String) {
        if (currentUserId == null) {
            _chatUiState.value = ChatUiState.Error("User not logged in")
            return
        }

        val chatId = if (currentUserId < otherUserId) "${currentUserId}_${otherUserId}" else "${otherUserId}_${currentUserId}"

        _chatUiState.value = ChatUiState.Loading

        databaseRef.child("messages").child(chatId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<Message1>()
                    for (child in snapshot.children) {
                        val message = child.getValue(Message1::class.java)
                        if (message != null) messages.add(message)
                    }
                    if (messages.isEmpty()) {
                        _chatUiState.value = ChatUiState.Empty
                    } else {
                        _chatUiState.value = ChatUiState.Success(messages.sortedBy { it.timestamp })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _chatUiState.value = ChatUiState.Error(error.message)
                }
            })
    }

}

/*
Firebase Realtime Database has 3 main types of listeners:

addListenerForSingleValueEvent() → Reads data once (one-time fetch).

addValueEventListener() → Listens to any changes in the whole node (real-time updates).

addChildEventListener() → Listens to child-level changes (added, changed, removed, moved).
* */