package com.nudriin.storyapp.data.dto.request

import com.google.gson.annotations.SerializedName

data class UserRegisterRequest(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)
