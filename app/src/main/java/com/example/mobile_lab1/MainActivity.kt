package com.example.mobile_lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var author : String = ""
    var year : String = ""
    val header : MutableList<String> = ArrayList()
    val body : MutableList<MutableList<String>> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authors : MutableList<String> = ArrayList()
        authors.add("author1")
        authors.add("author2")
        authors.add("author3")
        authors.add("author4")
        authors.add("author5")

        header.add("Authors")
        body.add(authors)

        group1.setAdapter(ExpandableListAdapter(this, header, body))
        group1.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            author = body[groupPosition].get(childPosition)
            true
        }

        group2.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            year = radio.text as String
        }

        okBtn.setOnClickListener {
            if (author != "" && year != "") {
                resultText.text = "$author $year"
            } else {
                Toast.makeText(this, "please select", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
