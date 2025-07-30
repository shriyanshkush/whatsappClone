package com.example.whatsappclone.data.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.data.model.ChatListModel
import com.example.whatsappclone.data.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okio.IOException
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class BaseViewModel : ViewModel() {

    fun searchUserByPhoneNo(phoneNo : String,callback: (ChatListModel?)->Unit) {

        val currentUser=FirebaseAuth.getInstance().currentUser

        if(currentUser==null) {
            Log.d("BaseViewModel","User not Found")
            callback(null)
            return
        }
        //val normalizedPhone = if (!phoneNo.startsWith("+")) "+$phoneNo" else phoneNo

        val databaseReference=FirebaseDatabase.getInstance("https://whatsappclone-21e09-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users")

        databaseReference.orderByChild("phoneNo").equalTo(phoneNo)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.d("BaseViewModel","UserFound")
                        val userSnapshot = snapshot.children.first()
                        val user = userSnapshot.getValue(ChatListModel::class.java)
                        callback(user)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error fetching user: ${error.message} , ${error.details}")
                    callback(null)
                }
            })


    }


    fun getChatforUser(userId:String, callback: (List<ChatListModel>?) -> Unit) {
        var Chatref=FirebaseDatabase.getInstance().getReference("users/$userId/chats)")

        Chatref.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val ChatList= mutableListOf<ChatListModel>()

                    for (childSnapshot in snapshot.children) {
                        val chat=childSnapshot.getValue(ChatListModel::class.java)

                        if(chat!=null) {
                            ChatList.add(chat)
                        }
                    }
                    callback(ChatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel","Error fetching user chats:${error.message}")
                    callback(emptyList())
                }
            })
    }

    private val _chatlist= MutableStateFlow<List<ChatListModel>>(emptyList())
    val chatlist=_chatlist.asStateFlow()

    init {
        loadChatData()
    }

    private fun loadChatData() {
        val currentId=FirebaseAuth.getInstance().currentUser?.uid


        if(currentId!=null) {
            val chatRef=FirebaseDatabase.getInstance().getReference("chats")
                .orderByChild("uid").equalTo(currentId)
                .addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val chatList= mutableListOf<ChatListModel>()

                        for( childrensnapshot in snapshot.children) {
                            val chat=childrensnapshot.getValue(ChatListModel::class.java)
                            if(chat!=null) {
                                chatList.add(chat)
                            }
                        }
                        _chatlist.value=chatList
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("BaseViewModel","Error fetching user chats:${error.message}")
                    }
                })
        }
    }

    fun addChat(newChat: ChatListModel) {
        val currentUserId=FirebaseAuth.getInstance().currentUser?.uid

        if(currentUserId!=null) {
            val newChatRef=FirebaseDatabase.getInstance("https://whatsappclone-21e09-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("chats").push()
            newChat.copy(userId = currentUserId)

            newChatRef.setValue(newChat).addOnSuccessListener {
                Log.d("BaseViewModel","chat added successfully to database")
            }.addOnFailureListener {exception->

                Log.e("BaseViewmodel","failed to add chat:${exception.message}")
            }
        } else{
            Log.e("BaseViewModel","no user is authenticated")
        }
    }

    val databaseRef=FirebaseDatabase.getInstance().reference

    fun SendMsg(senderPhoneNo:String,reciverPhoneNo:String,messageText:String) {
        val msgId=databaseRef.push().key?:return

        val message=Message(senderPhoneNo=senderPhoneNo,messageText=messageText, timeStamp = System.currentTimeMillis())

        databaseRef.child("messages")
            .child(senderPhoneNo)
            .child(reciverPhoneNo)
            .child(msgId)
            .setValue(message)

        databaseRef.child("messages")
            .child(reciverPhoneNo)
            .child(senderPhoneNo)
            .child(msgId)
            .setValue(message)


    }

    fun getMessage(senderPhoneNo:String,reciverPhoneNo:String,onNewMessage: (Message)->Unit) {
        val messageRef=databaseRef.child("messages")
            .child(senderPhoneNo)
            .child(reciverPhoneNo)


        messageRef.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message=snapshot.getValue(Message::class.java)
                if(message!=null) {
                    onNewMessage(message)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getLastMsgforChat(senderPhoneNo: String,reciverPhoneNo: String,onLastMessageFetched: (String,String) -> Unit) {
        val chatRef=FirebaseDatabase.getInstance().reference.child("messages")
            .child(senderPhoneNo)
            .child(reciverPhoneNo)

        chatRef.orderByChild("timeStamp").limitToLast(1).addListenerForSingleValueEvent(
            object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        val lastMessage=snapshot.children.firstOrNull()?.child("message")?.value as? String

                        val timestamp=snapshot.children.firstOrNull()?.child("timestamp")?.value as? String

                        onLastMessageFetched(
                            lastMessage?:"No Message",
                            timestamp?:"--:--"
                        )
                    } else{

                        onLastMessageFetched(
                            "No Message",
                             "--:--"
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("BaseViewModel","Error fetching Last Message")

                    onLastMessageFetched(
                        "No Message",
                        "--:--"
                    )
                }
            }
        )
    }

    fun loadChatList(currentUserPhoneNo:String,onChatListLoaded:(List<ChatListModel>)->Unit) {
        val chatlist= mutableListOf<ChatListModel>()
        val chatRef=FirebaseDatabase.getInstance().reference
            .child("chats")
            .child(currentUserPhoneNo)

        chatRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()) {
                    snapshot.children.forEach {
                        child->
                        val phoneNo=child.key?:return@forEach
                        val name=child.child("name").value as? String
                        val image=child.child("image").value as? String

                        val profileImage=image?.let {
                            decodeBase64toBitmap(it)
                        }

                        getLastMsgforChat(currentUserPhoneNo,phoneNo) {
                            lastmsg,timestamp ->

                            chatlist.add(
                                ChatListModel(
                                    name=name,
                                    profileImage=image,
                                    message = lastmsg,
                                    time=timestamp
                                )
                            )
                        }

                        if(chatlist.size==snapshot.childrenCount.toInt()) {
                            onChatListLoaded(chatlist)
                        }
                    }
                } else{
                    onChatListLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("BaseViewModel","chatlist not loaded")
                onChatListLoaded(emptyList())
            }
        })
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBase64toBitmap(base64Image:String): Bitmap? {
        return try {
            val decodeByte= Base64.decode(base64Image,android.util.Base64.DEFAULT)

            BitmapFactory.decodeByteArray(decodeByte,0,decodeByte.size)
        } catch (e:IOException) {
            Log.d("BaseViewModel","error decoding file")
            null
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun base64ToBitmap(base64String: String):Bitmap?{
        return try {
            val decodeByte= Base64.decode(base64String,android.util.Base64.DEFAULT)
            val inputStream:InputStream=ByteArrayInputStream(decodeByte)
            BitmapFactory.decodeStream(inputStream)
        } catch (e:IOException) {
            Log.d("BaseViewModel","error decoding file")
            null
        }
    }
}