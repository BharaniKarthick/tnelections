package com.example.tnelection2021.model

import java.io.Serializable

data class CandidateDetails(

	val candidate_name: String? = null,
	val candidate_age: Int? = null,
	val candidate_party: String? = null,
	val candidate_video_url: String? = null,
	val candidate_address: String? = null,
	val candidate_gender: String? = null,
	val candidate_f_h_name: String? = null,
	val candidate_status: String? = null
) : Serializable
