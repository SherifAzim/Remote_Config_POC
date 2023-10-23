package com.sherif.remoteconfigpoc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.sherif.cash.CashActivity
import com.sherif.remoteconfigpoc.ui.theme.RemoteConfigPocTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

   // private val remoteConfig: FirebaseRemoteConfig by lazy { initRemoteConfig() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemoteConfigPocTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    bb()
                }
            }
        }


    }

    private fun initRemoteConfig(): FirebaseRemoteConfig {
        return Firebase.remoteConfig
            .apply {
                val configSettings = remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 0
                }
                setConfigSettingsAsync(configSettings)

                fetchAndActivate().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "onCreate: Remote Configs loaded Successfully")

                    } else {
                        Log.d(TAG, "onCreate: Remote Configs Failed")
                    }
                }
            }
    }
    private fun fetchRemoteConfig() {
//        remoteConfig.getString("test_parameter").also {
//            Toast.makeText(this, "value = $it", Toast.LENGTH_LONG).show()
//        }
    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

    }

    @Composable
    fun bb() {
        TextButton(
            onClick = {  //fetchRemoteConfig()
                      startActivity(Intent(this@MainActivity, CashActivity::class.java))
                      },
        ) { Text(text = "Fetch") }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        RemoteConfigPocTheme {
            Greeting("Android")
            bb()
        }
    }
}