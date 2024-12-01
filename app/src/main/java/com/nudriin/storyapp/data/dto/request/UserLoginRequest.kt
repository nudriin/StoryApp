package com.nudriin.storyapp.data.dto.request

import com.google.gson.annotations.SerializedName

data class UserLoginRequest(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String
)
