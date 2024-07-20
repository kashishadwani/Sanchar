package com.example.sanchar_j

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.request.ExplainScope
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class MainActivity : AppCompatActivity() {

    lateinit var userIdTextField: EditText
    lateinit var usernameTextField:EditText
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        PermissionX.init(this)
            .permissions(Manifest.permission.SYSTEM_ALERT_WINDOW)
            .onExplainRequestReason { scope, deniedList ->
                val message = "We need your consent for the following permissions in order to use the offline call function properly"
                scope.showRequestReasonDialog(deniedList, message, "Allow", "Deny")
            }
            .request { allGranted, grantedList, deniedList ->
                // Handle the result of the permission request
            }

        userIdTextField = findViewById(R.id.user_id_text_field)
        usernameTextField = findViewById(R.id.username_text_field)
        button = findViewById(R.id.button_next)

        button.setOnClickListener{
            val userId = userIdTextField.text.toString()
            val username = usernameTextField.text.toString()
            if(userId.isNotEmpty()){
                val intent = Intent(this@MainActivity, VideoCallActivity::class.java)
                intent.putExtra("userID",userId)
                intent.putExtra("username", username)
                startActivity(intent)

                videocallServices(userId,username)
            }
        }
    }

    private fun videocallServices(userId: String,username: String){
        val application: android.app.Application = application // Android's application context
        val appID: Long = 364936204// yourAppID
        val appSign: String = "29bd8f97648cf38c757f42c66d7a8ff8a994d8ef94a461b9ef7c91c4b70e87ce" // yourAppSign
        val userID: String = userId// yourUserID, userID should only contain numbers, English characters, and '_'.
        val userName: String = username// yourUserName

        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()

        ZegoUIKitPrebuiltCallService.init(application, appID, appSign, userID, userName, callInvitationConfig)

    }

    override fun onDestroy() {
        super.onDestroy()

        ZegoUIKitPrebuiltCallInvitationService.unInit()
    }
}