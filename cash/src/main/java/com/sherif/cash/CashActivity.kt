package com.sherif.cash

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

private const val TAG = "CashActivity"
class CashActivity : ComponentActivity() {

    private val remoteConfig: FirebaseRemoteConfig by lazy { initRemoteConfig() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)

        val btn : Button = findViewById(R.id.btn_button)
        val text: TextView = findViewById(R.id.textView)

        btn.setOnClickListener {
            fetchRemoteConfig().also { text.text = it }
        }

    }

    private fun fetchRemoteConfig():String =
       remoteConfig.getString("test_parameter").also {
            Toast.makeText(this, "value = $it", Toast.LENGTH_LONG).show()
        }

    private fun initRemoteConfig(): FirebaseRemoteConfig {
        return Firebase.remoteConfig
            .apply {
                val configSettings =remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 0
                }
                setConfigSettingsAsync(configSettings)

                fetchAndActivate().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(
                            TAG,
                            "onCreate: Remote Configs loaded Successfully"
                        )

                    } else {
                        Log.d(TAG, "onCreate: Remote Configs Failed")
                    }
                }
            }
    }





}