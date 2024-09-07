package com.example.quoteappretrofit

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quoteappretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getQuote()

        binding.nextBtn.setOnClickListener {
            getQuote()
        }
    }

    private fun getQuote() {
        setInProgress(true)
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.quoteApi.getRandomQuote()
                runOnUiThread {
                    setInProgress(false)
                    response.body()?.first()?.let {
                        setUI(it)
                    }
                }
            }catch (e:Exception){
                    e.printStackTrace()
            }
        }

    }

    private fun setUI(quoteModel: QuoteModel){
        binding.qText.text = quoteModel.q
        Log.d("QuoteText", "${quoteModel.q}")
        binding.qAuthor.text = quoteModel.a
    }

    private fun setInProgress(inProgress: Boolean){
        if (inProgress){
            binding.progressBar.visibility = VISIBLE
            binding.nextBtn.visibility = GONE
        } else {
            binding.progressBar.visibility = GONE
            binding.nextBtn.visibility = VISIBLE

        }
    }
}