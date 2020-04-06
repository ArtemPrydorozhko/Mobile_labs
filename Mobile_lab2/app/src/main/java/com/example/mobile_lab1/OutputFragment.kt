package com.example.mobile_lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_output.*

class OutputFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxBus.listen(PrintMessageEvent::class.java).subscribe{
            if (it.author != "" && it.year != "") {
                resultText.text = "${it.author} ${it.year}"
            } else {
                Toast.makeText(requireContext(), getString(R.string.warning_message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_output, container, false)
    }
}