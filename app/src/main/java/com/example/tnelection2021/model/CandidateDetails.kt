package com.example.tnelection2021.model

import java.io.Serializable

data class CandidateDetails(

		var candidate_photo : String? = null,
		var candidate_name: String? = null,
		var candidate_age: Int? = null,
		var candidate_party: String? = null,
		var candidate_video_url: String? = null,
		var candidate_address: String? = null,
		var candidate_gender: String? = null,
		var candidate_f_h_name: String? = null,
		var candidate_status: String? = null,
		var candidate_pdf: String? = null,
		var candiate_application_uploaded: String? = null,
		var candidate_affidavit_upload: String? = null,
		var candidate_constituency: String? = null,
		var candidate_current_status: String? = null,
		var candidate_state: String? = null
) : Serializable
