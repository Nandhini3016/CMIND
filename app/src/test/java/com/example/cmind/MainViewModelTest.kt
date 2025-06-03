package com.example.cmind

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `length of normal input returns correct length`() {
        viewModel.inputText.value = "hello"
        viewModel.validateAndRun {
            val trimmed = it.trim()
            if (trimmed.isEmpty()) "0" else trimmed.length.toString()
        }
        assertEquals("5", viewModel.outputText.value)
        assertEquals("", viewModel.errorText.value)
    }

    @Test
    fun `length of empty string returns 0`() {
        viewModel.inputText.value = "   "
        viewModel.validateAndRun {
            val trimmed = it.trim()
            if (trimmed.isEmpty()) "0" else trimmed.length.toString()
        }
        assertEquals("0", viewModel.outputText.value)
    }

    @Test
    fun `reverse valid input returns reversed string`() {
        viewModel.inputText.value = "abc"
        viewModel.validateAndRun { it.trim().reversed() }
        assertEquals("cba", viewModel.outputText.value)
    }

    @Test
    fun `append adds Hello prefix`() {
        viewModel.inputText.value = "Bob"
        viewModel.validateAndRun { "Hello $it" }
        assertEquals("Hello Bob", viewModel.outputText.value)
    }

    @Test
    fun `numeric returns Yes for numeric input`() {
        viewModel.inputText.value = "123456"
        viewModel.validateAndRun {
            if (it.matches(Regex("^\\d+$"))) "Yes" else "No"
        }
        assertEquals("Yes", viewModel.outputText.value)
    }

    @Test
    fun `numeric returns No for non-numeric input`() {
        viewModel.inputText.value = "123abc"
        viewModel.validateAndRun {
            if (it.matches(Regex("^\\d+$"))) "Yes" else "No"
        }
        assertEquals("No", viewModel.outputText.value)
    }

    @Test
    fun `input with special characters sets error`() {
        viewModel.inputText.value = "abc@123"
        viewModel.validateAndRun { it.reversed() }
        assertEquals("", viewModel.outputText.value)
        assertEquals("Only alphanumeric characters and spaces allowed", viewModel.errorText.value)
    }

    @Test
    fun `clear resets all fields`() {
        viewModel.inputText.value = "Test"
        viewModel.outputText.value = "Output"
        viewModel.errorText.value = "Error"
        viewModel.clear()

        assertEquals("", viewModel.inputText.value)
        assertEquals("", viewModel.outputText.value)
        assertEquals("", viewModel.errorText.value)
    }
}