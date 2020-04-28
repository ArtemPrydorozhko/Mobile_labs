package com.example.mobile_lab1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_output.*

class Output : AppCompatActivity() {

    lateinit var fileName: String
    var books: MutableList<Book> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)
    }

    override fun onStart() {
        super.onStart()
        fileName = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "books.json"
        books = FileService.getBooksFromFile(fileName)
        if (books.size == 0) {
            noBooksLabel.visibility = View.VISIBLE
        } else {
            noBooksLabel.visibility = View.INVISIBLE
        }
        list_books.adapter = CustomListAdapter(this, books)
    }

    private class CustomListAdapter(val context: Context, val books: MutableList<Book>): BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(context)
            val row = layoutInflater.inflate(R.layout.list_item, parent, false)
            val authorTextView = row.findViewById<TextView>(R.id.author)
            authorTextView.text = "${position + 1}. Author: ${books[position].author}"
            val yearTextView = row.findViewById<TextView>(R.id.year)
            yearTextView.text = "year: ${books[position].year}"
            return row
        }

        override fun getItem(position: Int): Any {
            return books[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return books.count()
        }

    }
}
