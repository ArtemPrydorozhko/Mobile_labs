package com.example.mobile_lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var author : String = ""
    var year : String = ""
    val header : MutableList<String> = ArrayList()
    val body : MutableList<MutableList<String>> = ArrayList()
    lateinit var fileName: String
    var books: MutableList<Book> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileName = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "books.json"

        fillAuthors()

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
                resultText.text = "$author $year added"
                books.add(Book(author, year))
            } else {
                Toast.makeText(this, getString(R.string.warning_message), Toast.LENGTH_SHORT).show()
            }
        }

        getBooksBtn.setOnClickListener {
            FileService.saveBooksToFile(fileName, books)
            val intent = Intent(this, Output::class.java)
            startActivity(intent)
        }

        clearBtn.setOnClickListener {
            books.clear()
            FileService.saveBooksToFile(fileName, books)
        }
    }

    private fun fillAuthors() {
        val authors : MutableList<String> = ArrayList()
        authors.add(getString(R.string.author1))
        authors.add(getString(R.string.author2))
        authors.add(getString(R.string.author3))
        authors.add(getString(R.string.author4))
        authors.add(getString(R.string.author5))

        header.add(getString(R.string.author_header))
        body.add(authors)
    }

    override fun onStart() {
        super.onStart()
        books = FileService.getBooksFromFile(fileName)
    }

    override fun onStop() {
        super.onStop()
        FileService.saveBooksToFile(fileName, books)
    }
}
