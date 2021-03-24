package com.example.tnelection2021

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.tnelection2021.adapter.CandidateRecyclerViewAdapter
import com.example.tnelection2021.model.CandidateDetails
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity(), OnListFragmentInteractionListener {

    private lateinit var database: DatabaseReference

    var jsonString="";
    var jsonObj: JSONObject? = null
    private var mSatesArrayList : ArrayList<String> = ArrayList()
    private var isStatesLoaded = false
    private var isJsonLoaded = false


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Firebase.database.reference

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        getDateFromFireBase()
        loadAllViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Feedback")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId == Menu.FIRST)
        {
            sendFeedback()
            //Toast.makeText(this, "Feedback sent...", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)

    }

    private fun sendFeedback() {
        val mailto = arrayOf("kernelkidsapp@gmail.com")
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_EMAIL, mailto)
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Android app feedback")
        sendIntent.type = "plain/text"
        try
        {
            try
            {
                startActivity(Intent.createChooser(sendIntent, "Send Email Via"))
            }
            catch (e: ActivityNotFoundException)
            {
                e.printStackTrace()
            }

        }
        catch (e: PackageManager.NameNotFoundException)
        {
            e.printStackTrace()
        }
    }


    private fun getDateFromFireBase()
    {
        database.child("tn_states").get().addOnSuccessListener {

            val string = it.value.toString()
            setStatesArrayList(string)
            isStatesLoaded = true

            handleProgressBar()
        }

        database.child("tn").get().addOnSuccessListener {
            Log.i("db", "Got value ${it.value}")

            jsonString = it.getValue().toString()
            try {
                jsonObj = JSONObject(jsonString)
            } catch (e: JSONException) {
                Log.e("db", "Error parign json", e)
                e.printStackTrace()
            }

            isJsonLoaded = true

            handleProgressBar()


        }.addOnFailureListener{
            Log.e("db", "Error getting data", it)
            Toast.makeText(this,"Client is Offline ",Toast.LENGTH_LONG).show();
        }

    }

    private fun handleProgressBar() {
        if(isJsonLoaded&&isStatesLoaded)
        {
            progess_bar.visibility = View.GONE
        }
    }

    private fun setStatesArrayList(string: String)
    {
        val alist = string.split(",")
        mSatesArrayList  = ArrayList()
        mSatesArrayList.addAll(alist)

        setAdapter()
    }

    private fun setAdapter()
    {
        filter_spinner.adapter = ArrayAdapter(
                this, R.layout.simple_spinner_item, mSatesArrayList).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        }

        filter_spinner.onItemSelectedListener = onFilterSelectedListener
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
    }

    private val onFilterSelectedListener = object : AdapterView.OnItemSelectedListener
    {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
        {
            loadRecyclerView(parent!!.adapter.getItem(position).toString(), position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?)
        {

        }
    }

    private fun loadRecyclerView(assembyName: String, position: Int)
    {
        var list : ArrayList<CandidateDetails> = ArrayList()

        recycler_view.invalidate()

        list = getCandidateListFromJson(assembyName)

        recycler_view.adapter = CandidateRecyclerViewAdapter(this, list, this)
        recycler_view.invalidate()
    }

    private fun getCandidateListFromJson(assembyName: String): ArrayList<CandidateDetails>
    {
        var list : ArrayList<CandidateDetails> = ArrayList()

        val jsonArray = jsonObj?.optJSONArray(assembyName.trim())

        if(jsonArray != null)
        {
            for (i in 0 until jsonArray!!.length() )
            {
                val jsonObject = jsonArray.getJSONObject(i)
                var mCandidateDetails = CandidateDetails()


                mCandidateDetails.candidate_photo = jsonObject?.optString("candidate_photo")
                mCandidateDetails.candidate_name = jsonObject?.optString("candidate_name")
                mCandidateDetails.candidate_age = jsonObject?.optInt("candidate_age")
                mCandidateDetails.candidate_party = jsonObject?.optString("candidate_party")
                mCandidateDetails.candidate_video_url = jsonObject?.optString("candidate_video_url")
                mCandidateDetails.candidate_address = jsonObject?.optString("candidate_address")
                mCandidateDetails.candidate_gender = jsonObject?.optString("candidate_gender")
                mCandidateDetails.candidate_pdf = jsonObject?.optString("candidate_pdf")
                mCandidateDetails.candiate_application_uploaded = jsonObject?.optString("candiate_application_uploaded")
                mCandidateDetails.candidate_affidavit_upload = jsonObject?.optString("candidate_affidavit_upload")
                mCandidateDetails.candidate_constituency = jsonObject?.optString("candidate_constituency")
                mCandidateDetails.candidate_current_status = jsonObject?.optString("candidate_current_status")
                mCandidateDetails.candidate_state = jsonObject?.optString("candidate_state")
                mCandidateDetails.candidate_f_h_name = jsonObject?.optString("candidate_f_h_name")

                list.add(mCandidateDetails)
            }
        }

        return list
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