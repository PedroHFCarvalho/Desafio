package com.carvalho.desafio_itau.adapter

import com.carvalho.desafio_itau.model.ItemGithub
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carvalho.desafio_itau.R
import com.carvalho.desafio_itau.adapter.helpers.ItemClickListener
import com.carvalho.desafio_itau.databinding.CardviewListagemBinding

class AdapterList(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<AdapterList.CardViewHolder>() {

    var contents = emptyList<ItemGithub>()

    class CardViewHolder(var binding: CardviewListagemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            CardviewListagemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        holder.binding.tvNomeRepositorio.text = contents[position].name
        holder.binding.tvDescricaoRepositorio.text = contents[position].description
        holder.binding.tvForks.text = contents[position].forksCount.toString()
        holder.binding.tvStars.text = contents[position].stargazersCount.toString()
        holder.binding.tvNomeAutor.text = contents[position].owner.login

        Glide.with(holder.itemView.context).load(contents[position].owner.avatarURL)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_account_circle)
            .into(holder.binding.imAutor)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(contents[position])
        }

    }

    override fun getItemCount(): Int {
        return contents.size
    }

    fun setList(list: List<ItemGithub>) {
        contents = list
        notifyItemInserted(itemCount)
    }
}