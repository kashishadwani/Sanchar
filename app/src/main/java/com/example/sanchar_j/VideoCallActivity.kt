package com.example.sanchar_j

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import android.view.View.OnClickListener

class VideoCallActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var receiverUserId: EditText
    private lateinit var videoCallBtn: ZegoSendCallInvitationButton
    private lateinit var audioCallBtn: ZegoSendCallInvitationButton
    private lateinit var buttonLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video_call)

        receiverUserId = findViewById(R.id.receiver_user_id_text_field)
        textView = findViewById(R.id.user_id_text_view)
        videoCallBtn = findViewById(R.id.video_call_btn)
        audioCallBtn = findViewById(R.id.audio_call_btn)
        buttonLayout = findViewById(R.id.buttons_layout)

        val userId = intent.getStringExtra("userID")
        val username = intent.getStringExtra("username")
        textView.text = "Hi $username!"

        receiverUserId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val _receiverId = receiverUserId.text.toString()
                if (_receiverId.isNotEmpty()){

                    buttonLayout.visibility = View.VISIBLE
                    videoCallBtn.setOnClickListener(OnClickListener {
                        startvideocall(_receiverId)
                    })
                    audioCallBtn.setOnClickListener(OnClickListener{
                        startaudiocall(_receiverId)
                    })

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun startvideocall(receiverId: String){
        videoCallBtn.setIsVideoCall(true)
        videoCallBtn.resourceID = "zego_uikit_call"
        videoCallBtn.setInvitees(listOf(ZegoUIKitUser(receiverId)))
    }

    private fun startaudiocall(receiverId: String){
        audioCallBtn.setIsVideoCall(false)
        audioCallBtn.resourceID = "zego_uikit_call"
        audioCallBtn.setInvitees(listOf(ZegoUIKitUser(receiverId)))
    }
}