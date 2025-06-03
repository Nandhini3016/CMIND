package com.example.cmind

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val alphanumericRegex = Regex("^[a-zA-Z0-9 ]*$")

    val inputText = MutableLiveData("")
    val outputText = MutableLiveData("")
    val errorText = MutableLiveData("")

    fun validateAndRun(action: (String) -> String) {
        val input = inputText.value ?: ""
        if (!input.matches(alphanumericRegex)) {
            errorText.value = "Only alphanumeric characters and spaces allowed"
            outputText.value = ""
        } else {
            errorText.value = ""
            outputText.value = action(input)
        }
    }

    fun clear() {
        inputText.value = ""
        outputText.value = ""
        errorText.value = ""
    }
}