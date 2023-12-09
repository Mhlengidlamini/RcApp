package com.example.myrcapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class TutorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tutors)

        val spinnerMyModules = findViewById<Spinner>(R.id.spinnerMyModules)
        val spinnerTutors = findViewById<Spinner>(R.id.spinnerTutors)

        // Define module names
        val modules = arrayOf("My Modules", "Module 1", "Module 2", "Module 3")

        // Populate the "My Modules" spinner
        val myModulesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, modules)
        myModulesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMyModules.adapter = myModulesAdapter

        // Initialize the tutors array
        val tutorsMap = mapOf(
            "Module 1" to arrayOf("Tutor 1", "Tutor 2"),
            "Module 2" to arrayOf("Tutor A", "Tutor B"),
            "Module 3" to arrayOf("Tutor X", "Tutor Y")
        )

        // Set up a listener for the "My Modules" spinner
        spinnerMyModules.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected module
                val selectedModule = modules[position]

                // Populate the "Tutors" spinner based on the selected module
                val selectedTutors = tutorsMap[selectedModule] ?: arrayOf()
                val tutorsAdapter = ArrayAdapter(this@TutorsActivity, android.R.layout.simple_spinner_item, selectedTutors)
                tutorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerTutors.adapter = tutorsAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected
            }
        }
    }
}
