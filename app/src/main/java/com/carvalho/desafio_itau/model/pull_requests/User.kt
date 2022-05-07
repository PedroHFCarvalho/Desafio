package com.carvalho.desafio_itau.model.pull_requests

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    var avatar_url: String,
)
