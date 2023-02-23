package com.example.androidtypesense

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var typesenseClient: TypesenseInitializer
    private val COLLECTION_NAME = "intermariumCountries"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init Typesense client
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    typesenseClient = TypesenseInitializer()
                    val connectionState = typesenseClient.connectionState
                    val connectionStateMsg = "Connection state = $connectionState"
                    runOnUiThread {
                        //display connection state, if success sets the UI functions
                        Toast.makeText(this@MainActivity, connectionStateMsg, Toast.LENGTH_SHORT).show()
                        if (connectionState) setUpUi()
                    }
                } catch (e: Exception) {
                    e.message?.let { Log.e("ts", it) }
                    e.printStackTrace()
                }
            }
        }

    }

    private fun setUpUi() {
        //set action when click search icon
        findViewById<ImageView>(R.id.ic_search)?.setOnClickListener { onSearchIconClick() }
        //set action when typing
        findViewById<EditText>(R.id.et_search)?.doOnTextChanged { text, _, _, _ -> search(text.toString()) }
    }

    private fun search(text: String, showDetails: Boolean = false) {
        Log.i("ts", "Searching text: $text")
        lifecycleScope.launch {

            var resultString = ""
            var details = ""

            //do search and map response to resultString
            withContext(Dispatchers.IO) {
                typesenseClient.search(COLLECTION_NAME, text)?.let { result ->
                    result.hits.forEachIndexed { index, searchResultHit ->
                        resultString += "${index + 1}. ${searchResultHit.document["countryName"]}, ${searchResultHit.document["capital"]} \n"
                    }
                    details = "Found: ${result.found} in ${result.searchTimeMs}ms"
                }
            }

            //update UI
            runOnUiThread {
                if (showDetails) Toast.makeText(this@MainActivity, details, Toast.LENGTH_SHORT).show()
                updateResults(resultString)
            }

        }
    }

    private fun onSearchIconClick() {
        val text = findViewById<EditText>(R.id.et_search).text.toString()
        search(text, true)
        hideKeyboard()
    }

    private fun updateResults(results: String) {
        findViewById<TextView>(R.id.tv_results).text = results
    }

    private fun hideKeyboard() {
        val view = findViewById<EditText>(R.id.et_search)
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}












