package com.carvalho.desafio_itau.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.desafio_itau.MainViewModel
import com.carvalho.desafio_itau.adapter.AdapterList
import com.carvalho.desafio_itau.adapter.AdapterPulls
import com.carvalho.desafio_itau.databinding.FragmentPullsBinding
import com.carvalho.desafio_itau.model.ItemGithub
import com.carvalho.desafio_itau.model.pull_requests.PullRequest


class PullsFragment : Fragment() {

    private lateinit var binding: FragmentPullsBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapterList: AdapterPulls
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var nameRepos: String
    private lateinit var owner: String
    private lateinit var pullsOpen: String

    private var list: List<PullRequest>? = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPullsBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = null

        setupLayoutList()
        recoverData()
        includeContentsInPage()

        viewModel.responsePullRequests.observe(viewLifecycleOwner) {
            if (it.body() != null) {
                list = it.body()!!
                pullsOpen = list?.size.toString()
            }

            Log.d("Listagem Pulls", it.body().toString())
            includeContentsInPage()
            includeContentsInHeader()
            binding.progressBar.visibility = View.INVISIBLE
            binding.rvPulls.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun includeContentsInHeader() {
        binding.tvNameRespos.text = nameRepos
        binding.tvOwner.text = owner
        binding.tvPullsOpen.text = pullsOpen
    }

    private fun includeContentsInPage() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvPulls.visibility = View.INVISIBLE
        setAdapter()
        setListInAdapter()
    }

    private fun recoverData() {
        if (viewModel.itemSelect != null) {
            nameRepos = viewModel.itemSelect?.name!!
            owner = viewModel.itemSelect?.owner?.login!!
            getContentsForList(owner!!, nameRepos!!)
        }
    }

    private fun getContentsForList(owner: String, nameRepos: String) {
        viewModel.getPullRequests(owner, nameRepos)
    }

    private fun setListInAdapter() {
        adapterList.setList(list!!)
    }

    private fun setAdapter() {
        adapterList = AdapterPulls()
        binding.rvPulls.adapter = adapterList
    }

    private fun setupLayoutList() {
        binding.rvPulls.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        binding.rvPulls.layoutManager = layoutManager
    }

    override fun onResume() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvPulls.visibility = View.INVISIBLE
        list = null
        super.onResume()
    }
}