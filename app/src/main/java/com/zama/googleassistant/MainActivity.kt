package com.zama.googleassistant

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.zama.googleassistant.R.layout.activity_assistant
import com.zama.googleassistant.assistant.AssistantActivity
import com.zama.googleassistant.assistant.ExploreActivity
import com.zama.googleassistant.data.Assistant
import com.zama.googleassistant.databinding.ActivityMainBinding
import com.zama.googleassistant.functions.GoogleLensActivity
import com.zama.googleassistant.utils.utils.setCustomActionBar

class MainActivity : AppCompatActivity() {
    lateinit var hiGoogle: ImageView
    lateinit var explore: ImageView
    lateinit var googleLens: ImageView
    lateinit var googleAssistant: ImageView
    private val Request_permission_Code:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCustomActionBar(supportActionBar, this)
        hiGoogle = findViewById(R.id.hiGoogle)
        explore = findViewById(R.id.action_explore)
        googleLens = findViewById(R.id.action_google_lens)
        googleAssistant = findViewById(R.id.googleAssistant)
        if ((ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) != PERMISSION_GRANTED)
        ) {
            checkPermission()

        }
        hiGoogle.setOnClickListener {
            startActivity(Intent(this, AssistantActivity::class.java))
        }

        explore.setOnClickListener {
            startActivity(Intent(this, ExploreActivity::class.java))
        }
        googleLens.setOnClickListener {
            startActivity(Intent(this, GoogleLensActivity::class.java))
        }
        googleAssistant.setOnClickListener {
            startActivity(Intent(this, AssistantActivity::class.java))
        }

    }

    private fun checkPermission() {
        ActivityCompat.requestPermissions(this,
        arrayOf(android.Manifest.permission.RECORD_AUDIO),
       Request_permission_Code)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==Request_permission_Code && grantResults.isNotEmpty())
        if(grantResults[0]== PERMISSION_GRANTED){
            Toast.makeText(this,"Permission Graned",Toast.LENGTH_SHORT).show()
        }
    }

}