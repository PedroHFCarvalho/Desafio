package com.carvalho.desafio_itau.model

import com.google.gson.annotations.SerializedName

data class ItemGithub(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: OwnerGithub,
    @SerializedName("description")
    val description: String,
    @SerializedName("fork")
    val fork: Boolean,
    @SerializedName("url")
    val url: String,
    @SerializedName("forks_count")
    val forksCount: Long,
    @SerializedName("forks")
    val forks: Long,
    @SerializedName("stargazers_count")
    val stargazersCount: Long
)
