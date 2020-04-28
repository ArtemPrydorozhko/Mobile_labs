package com.example.mobile_lab1

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.IOException

object FileService {
    fun getBooksFromFile(fileName: String): MutableList<Book> {
        var gson = Gson()
        var inputString: String
        try {
            val bufferedReader: BufferedReader = File(fileName).bufferedReader()
            inputString = bufferedReader.use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return mutableListOf()
        }

        val listBookType = object : TypeToken<MutableList<Book>>() {}.type
        return gson.fromJson(inputString, listBookType)
    }

    fun saveBooksToFile(fileName: String, books: MutableList<Book>) {
        var gson = Gson()
        var jsonString: String = gson.toJson(books)

        val file = File(fileName)
        file.writeText(jsonString)
    }
}