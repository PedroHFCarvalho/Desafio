package com.carvalho.desafio_itau.adapter.helpers

import com.carvalho.desafio_itau.model.ItemGithub

interface ItemClickListener {
    fun onItemClicked(itemGithub: ItemGithub)
}