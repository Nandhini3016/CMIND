package com.example.cmind

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cmind.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.lengthButton.setOnClickListener {
            viewModel.validateAndRun {
                val trimmed = it.trim()
                if (trimmed.isEmpty()) "0" else trimmed.length.toString()
            }
        }

        binding.reverseButton.setOnClickListener {
            viewModel.validateAndRun { it.trim().reversed() }
        }

        binding.appendButton.setOnClickListener {
            viewModel.validateAndRun { "Hello $it" }
        }

        binding.numericButton.setOnClickListener {
            viewModel.validateAndRun {
                if (it.matches(Regex("^\\d+$"))) "Yes" else "No"
            }
        }

        binding.clearButton.setOnClickListener {
            viewModel.clear()
        }
    }
}