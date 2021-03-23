package com.example.tnelection2021.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tnelection2021.OnListFragmentInteractionListener
import com.example.tnelection2021.R
import com.example.tnelection2021.model.CandidateDetails
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_item.view.*

/**
 * Created by sathya-6501 on 20/03/21.
 */
class CandidateRecyclerViewAdapter(val context : Context, candidateDetailsList: ArrayList<CandidateDetails>, interfaceReceiver : OnListFragmentInteractionListener) : RecyclerView.Adapter<CandidateRecyclerViewAdapter.ViewHolder?>()
{
    private var mListener: OnListFragmentInteractionListener = interfaceReceiver
    private var mCandidateDetailsList: ArrayList<CandidateDetails> = candidateDetailsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCandidateDetailsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val mCandidateDetails = mCandidateDetailsList[position]

        holder.candidateNameTV.text = mCandidateDetails.candidate_name
        holder.candidatePartyTV.text = mCandidateDetails.candidate_party

        Glide.with(context).load(mCandidateDetails.candidate_photo).into(holder.profile)

        holder.mView.setOnClickListener {

            mListener.onListFragmentInteraction(mCandidateDetails)
        }

    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        var candidateNameTV = view.name
        var candidatePartyTV = view.party
        var profile = view.profile_photo
        var mView = view
    }
}