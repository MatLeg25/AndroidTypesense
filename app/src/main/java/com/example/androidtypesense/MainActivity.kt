package com.example.androidtypesense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidtypesense.ui.main.MainFragment
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        GlobalScope.launch {

            withContext(Dispatchers.IO) {
                //Init Typesense
                val client = TypesenseInitializer()

                delay(2000) //wait till client will be ready - temporary solution :)

                Log.w("elox","Test = $client")

                //client.createDoc()

                client.retrieve()
            }

        }
    }
}