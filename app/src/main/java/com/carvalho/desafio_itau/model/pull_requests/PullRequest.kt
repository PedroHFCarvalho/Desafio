package com.carvalho.desafio_itau.model.pull_requests

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("state")
    var state: String,
    @SerializedName("user")
    var user: User,
    @SerializedName("body")
    var body: String,
)