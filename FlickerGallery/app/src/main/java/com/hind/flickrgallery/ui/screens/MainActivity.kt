package com.hind.flickrgallery.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hind.flickrgallery.R
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Open public feeds activity after 1 second
        GlobalScope.launch {
            //Wait for one second in background
            withContext(Dispatchers.Default){
                delay(UIConstants.MAIN_SCREEN_DELAY_TIME)
            }
            //Navigate to public feeds
            startActivity(Intent(this@MainActivity,PublicFeedsActivity::class.java))
            finish()//Close this activity
        }
    }
}