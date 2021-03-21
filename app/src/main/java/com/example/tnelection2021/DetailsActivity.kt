package com.example.tnelection2021

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tnelection2021.model.CandidateDetails
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity()
{
    lateinit var mCandidateDetails : CandidateDetails
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        mCandidateDetails = intent.extras!!["candidate_details"] as CandidateDetails

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        name.text = mCandidateDetails.candidate_name
        party.text = mCandidateDetails.candidate_party

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}