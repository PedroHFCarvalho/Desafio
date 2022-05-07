package com.carvalho.desafio_itau.model

import com.google.gson.annotations.SerializedName

data class RepositoryGithub(
    @SerializedName("items")
    val items: List<ItemGithub>?
    )