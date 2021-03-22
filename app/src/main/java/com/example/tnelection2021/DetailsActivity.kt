package com.example.tnelection2021

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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

        setProfilePic()
        name.text = mCandidateDetails.candidate_name
        party.text = mCandidateDetails.candidate_party
        video_url.text=mCandidateDetails.candidate_video_url

    }

    private fun setProfilePic()
    {
        Glide.with(this).load(mCandidateDetails.candidate_photo).into(profile_photo)
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

    fun showVideo(view: View)
    {
        val intent = Intent(this, VideoPlayer::class.java)
        intent.putExtra("candidate_details", mCandidateDetails)
        startActivity(intent)

    }


}