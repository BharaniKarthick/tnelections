package com.example.tnelection2021.model
import com.google.gson.annotations.SerializedName

data class AssemblyItem(
	val assembly_name: String? = null,

	@field:SerializedName("dharapuram", alternate = ["palladam", "tirupur"])
	val candidate_details_list: ArrayList<CandidateDetails>? = null
)
