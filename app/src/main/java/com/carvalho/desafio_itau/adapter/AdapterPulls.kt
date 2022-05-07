package com.carvalho.desafio_itau.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carvalho.desafio_itau.databinding.CardviewPullsBinding
import com.carvalho.desafio_itau.model.pull_requests.PullRequest

class AdapterPulls() : RecyclerView.Adapter<AdapterPulls.CardViewHolder>() {

    var contents = emptyList<PullRequest>()

    class CardViewHolder(var binding: CardviewPullsBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            CardviewPullsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.tvTitulo.text = contents[position].title
        holder.binding.tvDescricao.text = contents[position].body
        holder.binding.tvUser.text = contents[position].user.login

        when (contents[position].state) {
            "open" -> {
                holder.binding.tvStatus.setTextColor(Color.GREEN)
            }
            "closed" -> {
                holder.binding.tvStatus.setTextColor(Color.RED)
            }
        }
        holder.binding.tvStatus.text = contents[position].state.replaceFirstChar { it.uppercase() }


        Glide.with(holder.itemView.context).load(contents[position].user.avatar_url).circleCrop()
            .into(holder.binding.imUser)
    }

    override fun getItemCount(): Int {
        return contents.size
    }

    fun setList(list: List<PullRequest>) {
        contents = list
        notifyDataSetChanged()
    }
}