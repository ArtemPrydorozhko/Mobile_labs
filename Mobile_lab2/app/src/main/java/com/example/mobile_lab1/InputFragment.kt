package com.example.mobile_lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_input.*

class InputFragment: Fragment() {

    private val header : MutableList<String> = ArrayList()
    private val body : MutableList<MutableList<String>> = ArrayList()
    private var author: String = ""
    private var year: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authors : MutableList<String> = ArrayList()
        authors.add(getString(R.string.author1_name))
        authors.add(getString(R.string.author2_name))
        authors.add(getString(R.string.author3_name))
        authors.add(getString(R.string.author4_name))
        authors.add(getString(R.string.author5_name))

        header.add(getString(R.string.authors_title))
        body.add(authors)
//        val group1 = requireView().findViewById<ExpandableListView>(R.id.group1)
        group1.setAdapter(ExpandableListAdapter(requireContext(), header, body))
        group1.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            author = body[groupPosition].get(childPosition)
            true
        }

        group2.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = requireView().findViewById(checkedId)
            year = radio.text as String
        }

        okBtn.setOnClickListener {
            RxBus.publish(PrintMessageEvent(author, year))
        }
    }
}