package com.example.tnelection2021

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tnelection2021.adapter.CandidateRecyclerViewAdapter
import com.example.tnelection2021.model.AssemblyItem
import com.example.tnelection2021.model.CandidateDetails
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity(), OnListFragmentInteractionListener {

    private lateinit var database: DatabaseReference
    val GSON                    = Gson()

    var jsonString="";
    var jsonObj: JSONObject? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Firebase.database.reference

        getDateFromFireBase()
        loadAllViews()
    }

    private fun getDateFromFireBase()
    {
        database.child("tn").get().addOnSuccessListener {
            Log.i("db", "Got value ${it.value}")

            jsonString = it.getValue().toString()
            try {
                jsonObj = JSONObject(jsonString)
            } catch (e: JSONException) {
                Log.e("db", "Error parign json", e)
                e.printStackTrace()
            }

        }.addOnFailureListener{
            Log.e("db", "Error getting data", it)
            Toast.makeText(this,"Client is Offline ",Toast.LENGTH_LONG).show();
        }

    }

    override fun onStart() {
        super.onStart()

    }

    private fun loadAllViews()
    {
        if(jsonObj == null)
        {
            getDateFromFireBase()
        }

        filter_spinner.adapter = ArrayAdapter(
            this, R.layout.simple_spinner_item, resources.getStringArray(
                R.array.assembly_filter_array
            )
        ).apply {
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
                    loadRecyclerView(parent!!.adapter.getItem(position).toString(), position)
                }

            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?)
        {

        }
    }

    private fun loadRecyclerView(assembyName: String, position: Int)
    {
        var list : ArrayList<CandidateDetails> = ArrayList()

        recycler_view.invalidate()

        try {

            val assemblyItemList = GSON.fromJson(jsonObj.toString(), AssemblyItem::class.java)

            if(assemblyItemList.candidate_details_list!!.size > 0)
            {
                list.addAll(assemblyItemList.candidate_details_list)
            }

        }catch (e:Exception){

        }

        recycler_view.adapter = CandidateRecyclerViewAdapter(list, this)
        recycler_view.invalidate()
    }

    override fun onListFragmentInteraction(item: CandidateDetails)
    {
        openDetailsActivity(item)
    }

    private fun openDetailsActivity(item: CandidateDetails)
    {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("candidate_details", item)
        startActivity(intent)
    }
}