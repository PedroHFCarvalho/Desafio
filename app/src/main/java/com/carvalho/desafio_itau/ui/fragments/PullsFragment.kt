package com.carvalho.desafio_itau.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.carvalho.desafio_itau.MainViewModel
import com.carvalho.desafio_itau.R
import com.carvalho.desafio_itau.adapter.AdapterPulls
import com.carvalho.desafio_itau.databinding.FragmentPullsBinding
import com.carvalho.desafio_itau.model.pull_requests.PullRequest


class PullsFragment : Fragment() {

    private lateinit var binding: FragmentPullsBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapterList: AdapterPulls
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var nameRepos: String
    private lateinit var owner: String
    private var contPage: Int = 1

    private var list: MutableList<PullRequest?> = mutableListOf()

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
                it.body()!!.forEach { item ->
                    if (!list.contains(item)) {
                        list.add(item)
                    }
                }
            }

            //Log.d("Listagem Pulls", it.body().toString())
            includeContentsInPage()
            includeContentsInHeader()
            binding.progressBar.visibility = View.INVISIBLE
            binding.rvPulls.visibility = View.VISIBLE
            binding.clContentCollapsing.visibility = View.VISIBLE
        }

        binding.nsvScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            //Log.i("scrollY", scrollY.toString())
            //Log.i("measuredHeight", v.measuredHeight.toString())
            //Log.i("measuredHeight getChildAt", v.getChildAt(0).measuredHeight.toString())
            if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                contPage++
                binding.progressBar.visibility = View.VISIBLE
                getContentsForList(owner, nameRepos, contPage)
                //Log.i("msg Scroll end", contPage.toString())
            }
        })

        return binding.root
    }

    private fun includeContentsInHeader() {
        binding.tvNameRespos.text = nameRepos
        binding.tvOwner.text = owner
    }

    private fun includeContentsInPage() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvPulls.visibility = View.INVISIBLE
        binding.clContentCollapsing.visibility = View.GONE
        setAdapter()
        setListInAdapter()
    }

    private fun recoverData() {
        if (viewModel.itemSelect != null) {
            nameRepos = viewModel.itemSelect?.name!!
            owner = viewModel.itemSelect?.owner?.login!!

            Glide.with(requireContext()).load(viewModel.itemSelect!!.owner.avatarURL)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_account_circle)
                .into(binding.imOwnerRepos)

            getContentsForList(owner, nameRepos, contPage)
        }
    }

    private fun getContentsForList(owner: String, nameRepos: String, contPage: Int) {
        viewModel.getPullRequests(owner, nameRepos, contPage)
    }

    private fun setListInAdapter() {
        adapterList.setList(list)
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
        binding.clContentCollapsing.visibility = View.GONE
        list.clear()
        contPage = 1
        super.onResume()
    }
}