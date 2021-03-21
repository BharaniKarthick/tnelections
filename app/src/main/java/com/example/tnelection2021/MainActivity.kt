package com.example.tnelection2021

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tnelection2021.adapter.CandidateRecyclerViewAdapter
import com.example.tnelection2021.model.CandidateDetails
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    var jsonString="";
    var jsonObj: JSONObject? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Firebase.database.reference
      //  Log.i( "Got value")

        database.child("tn").get().addOnSuccessListener {
      //  database.get().addOnSuccessListener {
            Log.i("db", "Got value ${it.value}")

            jsonString = it.getValue().toString()
            try {
                jsonObj = JSONObject(jsonString)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            Log.i("db", "Got value "+jsonObj)

            //it.value as String

        }.addOnFailureListener{
            Log.e("db", "Error getting data", it)
        }



        loadAllViews()
    }

    override fun onStart() {
        super.onStart()

    }

    private fun loadAllViews()
    {
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
                    //text1(parent!!.adapter.getItem(position).toString())
                }

            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?)
        {

        }
    }

    private fun loadRecyclerView(assembyName: String, position: Int)
    {
        val list : ArrayList<CandidateDetails> = ArrayList()
//        Toast.makeText(this, jsonObj.toString(), Toast.LENGTH_LONG).show()
        Log.i("db","heere "+ jsonObj.toString())
        Log.i("db","heere "+ jsonObj?.get(assembyName).toString())
        recycler_view.invalidate()
        try {
            val jsonArray = jsonObj?.optJSONArray(assembyName);
            Log.i("db-currentasemly", jsonArray.toString())




           /* val mCandidateDetails1 = CandidateDetails("Hari", 32, "ADMK")
            val mCandidateDetails2 = CandidateDetails("Sakthi", 24, "BJP")
            val mCandidateDetails3 = CandidateDetails("Bharani", 25, "MNM")*/
            for (i in 0 until jsonArray!!.length()) {
                val jsonObject = jsonArray?.getJSONObject(i)
                val mCandidateDetails1 = CandidateDetails(jsonObject?.optString("name"), jsonObject?.optString("age")?.toInt(), jsonObject?.optString("party"))
                list.add(mCandidateDetails1);
            }


        }catch (e:Exception){

        }

//
//        Toast.makeText(this, jsonArray.toString(), Toast.LENGTH_LONG).show()

        //creating jsonobject
        //JSONObject(jsonString);
        //val obj=jsonString.get(assembyName)




       /* if(position == 0)
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
*/
        recycler_view.adapter = CandidateRecyclerViewAdapter(list)
        recycler_view.invalidate()
    }

    private fun text1(str: String)
    {
        Log.i("db", "faedadas value")
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}