package com.example.tnelection2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tnelection2021.adapter.CandidateRecyclerViewAdapter
import com.example.tnelection2021.model.CandidateDetails
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadAllViews()
    }

    private fun loadAllViews()
    {
        filter_spinner.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, resources.getStringArray(R.array.assembly_filter_array)).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        }

        filter_spinner.onItemSelectedListener = onFilterSelectedListener

    }

    private val onFilterSelectedListener = object : AdapterView.OnItemSelectedListener
    {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
        {
            when(position)
            {
                else ->
                {
                    loadRecyclerView(position)
                    text1(parent!!.adapter.getItem(position).toString())
                }

            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?)
        {

        }
    }

    private fun loadRecyclerView(position: Int)
    {
        recycler_view.invalidate()
        val list : ArrayList<CandidateDetails> = ArrayList()
        val mCandidateDetails1 = CandidateDetails("Hari", 32, "ADMK")
        val mCandidateDetails2 = CandidateDetails("Sakthi", 24, "BJP")
        val mCandidateDetails3 = CandidateDetails("Bharani", 25, "MNM")

        if(position == 0)
        {
            list.add(mCandidateDetails1)
        }
        else if(position == 1)
        {
            list.add(mCandidateDetails1)
            list.add(mCandidateDetails2)
        }
        else
        {
            list.add(mCandidateDetails1)
            list.add(mCandidateDetails2)
            list.add(mCandidateDetails3)
        }

        recycler_view.adapter = CandidateRecyclerViewAdapter(list)
        recycler_view.invalidate()
    }

    private fun text1(str : String)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}