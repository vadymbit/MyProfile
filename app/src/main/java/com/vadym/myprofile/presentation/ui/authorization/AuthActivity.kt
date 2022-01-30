package com.vadym.myprofile.presentation.ui.authorization

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vadym.myprofile.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Debuging", "${this.javaClass.simpleName}  Destroyed")
    }
}