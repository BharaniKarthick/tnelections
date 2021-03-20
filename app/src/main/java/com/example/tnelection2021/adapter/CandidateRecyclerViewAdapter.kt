package com.example.tnelection2021.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tnelection2021.R
import com.example.tnelection2021.model.CandidateDetails
import kotlinx.android.synthetic.main.fragment_item.view.*

/**
 * Created by sathya-6501 on 20/03/21.
 */
class CandidateRecyclerViewAdapter(candidateDetailsList: ArrayList<CandidateDetails>) : RecyclerView.Adapter<CandidateRecyclerViewAdapter.ViewHolder?>()
{
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
        val mDetails = mCandidateDetailsList[position]

        holder.candidateNameTV.text = mDetails.candidate_name
        holder.candidatePartyTV.text = mDetails.candidate_party
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        var candidateNameTV = view.name
        var candidatePartyTV = view.party
        var mView = view
    }
}