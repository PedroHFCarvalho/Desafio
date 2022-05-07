package com.carvalho.desafio_itau.model

import com.google.gson.annotations.SerializedName

data class OwnerGithub(
    @SerializedName("login")
    val login: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatar_url")
    val avatarURL: String,

)
