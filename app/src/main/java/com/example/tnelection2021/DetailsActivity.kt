package com.example.tnelection2021

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tnelection2021.PdfDownload.DownloadFile
import com.example.tnelection2021.model.CandidateDetails
import kotlinx.android.synthetic.main.activity_details.*


public class DetailsActivity : AppCompatActivity()
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
        age.text = mCandidateDetails.candidate_age.toString()
        f_h_name.text = mCandidateDetails.candidate_f_h_name
        gender.text = mCandidateDetails.candidate_gender
        address.text = mCandidateDetails.candidate_address

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

    fun downloadPdf(view: View) {
       val path= mCandidateDetails.candidate_pdf;
        Toast.makeText(this,path,Toast.LENGTH_LONG).show();
         DownloadFile(this).execute(path,mCandidateDetails.candidate_name+"_"+mCandidateDetails.candidate_party+"_.pdf")


    }


}