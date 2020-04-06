package com.example.mobile_lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragments()
    }

    private fun showFragments() {
        val transaction = supportFragmentManager.beginTransaction()
        val inputFragment = InputFragment()
        val outputFragment = OutputFragment()
        transaction.add(R.id.fragment_input_container, inputFragment)
        transaction.add(R.id.fragment_output_container, outputFragment)
        transaction.commit()
    }
}
