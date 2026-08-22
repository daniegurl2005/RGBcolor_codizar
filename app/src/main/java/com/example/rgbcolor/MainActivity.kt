package com.example.rgbcolor

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: com.google.android.material.appbar.MaterialToolbar

    private lateinit var redInput: TextInputEditText
    private lateinit var greenInput: TextInputEditText
    private lateinit var blueInput: TextInputEditText

    private lateinit var redInputLayout: TextInputLayout
    private lateinit var greenInputLayout: TextInputLayout
    private lateinit var blueInputLayout: TextInputLayout

    private lateinit var createColorButton: Button
    private lateinit var colorDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "My Application"
        toolbar.setTitleTextColor(Color.WHITE)

        redInput = findViewById(R.id.redInput)
        greenInput = findViewById(R.id.greenInput)
        blueInput = findViewById(R.id.blueInput)

        redInputLayout = findViewById(R.id.redInputLayout)
        greenInputLayout = findViewById(R.id.greenInputLayout)
        blueInputLayout = findViewById(R.id.blueInputLayout)

        createColorButton = findViewById(R.id.createColorButton)
        colorDisplay = findViewById(R.id.colorDisplay)

        createColorButton.setOnClickListener {
            createRGBColor()
        }
    }

    private fun createRGBColor() {

        val red = redInput.text.toString().trim()
        val green = greenInput.text.toString().trim()
        val blue = blueInput.text.toString().trim()

        var isValid = true

        // Clear previous errors
        redInputLayout.error = null
        greenInputLayout.error = null
        blueInputLayout.error = null

        // Validate Red
        if (!isValidHexValue(red)) {
            redInputLayout.error =
                "Enter exactly 2 hexadecimal characters"
            isValid = false
        }

        // Validate Green
        if (!isValidHexValue(green)) {
            greenInputLayout.error =
                "Enter exactly 2 hexadecimal characters"
            isValid = false
        }

        // Validate Blue
        if (!isValidHexValue(blue)) {
            blueInputLayout.error =
                "Enter exactly 2 hexadecimal characters"
            isValid = false
        }

        // Stop if any input is invalid
        if (!isValid) {
            return
        }

        // Create six-character hexadecimal color
        val hexColor = "#$red$green$blue"

        try {

            // Convert hexadecimal string to Android Color
            val color = Color.parseColor(hexColor)

            // Change preview background
            colorDisplay.setBackgroundColor(color)

            // Display hexadecimal value
            colorDisplay.text = hexColor.uppercase()

            // Convert RGB values to decimal
            val redValue = red.toInt(16)
            val greenValue = green.toInt(16)
            val blueValue = blue.toInt(16)

            // Calculate brightness
            val brightness =
                (redValue * 299 +
                        greenValue * 587 +
                        blueValue * 114) / 1000

            // Choose readable text color
            if (brightness < 128) {
                colorDisplay.setTextColor(Color.WHITE)
            } else {
                colorDisplay.setTextColor(Color.BLACK)
            }

        } catch (e: IllegalArgumentException) {

            colorDisplay.text = "Invalid color"
        }
    }

    private fun isValidHexValue(value: String): Boolean {

        return value.length == 2 &&
                value.matches(
                    Regex("^[0-9A-Fa-f]{2}$")
                )
    }
}