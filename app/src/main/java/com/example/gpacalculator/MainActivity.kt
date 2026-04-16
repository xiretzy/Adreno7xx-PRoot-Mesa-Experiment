package com.example.gpacalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var coursesContainer: LinearLayout
    private lateinit var resultText: TextView

    private val gradeValues = mapOf(
        "A+" to 4.0,
        "A" to 4.0,
        "B+" to 3.5,
        "B" to 3.0,
        "C+" to 2.5,
        "C" to 2.0,
        "D" to 1.0,
        "F" to 0.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coursesContainer = findViewById(R.id.coursesContainer)
        resultText = findViewById(R.id.resultText)

        val addCourseButton: Button = findViewById(R.id.addCourseButton)
        val calculateButton: Button = findViewById(R.id.calculateButton)

        repeat(3) { addCourseRow() }

        addCourseButton.setOnClickListener { addCourseRow() }
        calculateButton.setOnClickListener { calculateGpa() }
    }

    private fun addCourseRow() {
        val row = LayoutInflater.from(this).inflate(R.layout.row_course, coursesContainer, false)
        val gradeSpinner: Spinner = row.findViewById(R.id.gradeSpinner)
        val grades = gradeValues.keys.toList()

        gradeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            grades
        )

        coursesContainer.addView(row)
    }

    private fun calculateGpa() {
        var totalPoints = 0.0
        var totalCredits = 0.0

        for (i in 0 until coursesContainer.childCount) {
            val row = coursesContainer.getChildAt(i)
            val gradeSpinner: Spinner = row.findViewById(R.id.gradeSpinner)
            val creditsEditText: EditText = row.findViewById(R.id.creditsInput)

            val creditText = creditsEditText.text.toString().trim()
            if (creditText.isEmpty()) {
                creditsEditText.error = getString(R.string.enter_credits)
                return
            }

            val credits = creditText.toDoubleOrNull()
            if (credits == null || credits <= 0.0) {
                creditsEditText.error = getString(R.string.invalid_credits)
                return
            }

            val selectedGrade = gradeSpinner.selectedItem.toString()
            val gradeValue = gradeValues[selectedGrade] ?: 0.0

            totalPoints += gradeValue * credits
            totalCredits += credits
        }

        if (totalCredits == 0.0) {
            resultText.text = getString(R.string.no_data)
            return
        }

        val gpa = totalPoints / totalCredits
        resultText.text = getString(R.string.gpa_result, gpa)
    }
}
