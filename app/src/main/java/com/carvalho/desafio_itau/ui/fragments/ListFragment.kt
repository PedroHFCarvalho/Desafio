package com.carvalho.desafio_itau.ui.fragments

import com.carvalho.desafio_itau.model.ItemGithub
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.desafio_itau.MainViewModel
import com.carvalho.desafio_itau.R
import com.carvalho.desafio_itau.adapter.AdapterList
import com.carvalho.desafio_itau.adapter.helpers.ItemClickListener
import com.carvalho.desafio_itau.databinding.FragmentListBinding

class ListFragment : Fragment(), ItemClickListener {

    private lateinit var binding: FragmentListBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapterList: AdapterList
    private lateinit var layoutManager: LinearLayoutManager

    private var list: MutableList<ItemGithub> = mutableListOf()
    private var contPage = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = null

        setupLayoutList()
        getContentsForList(contPage)
        includeContentsInPage()

        viewModel.responseRepositoryGit.observe(viewLifecycleOwner) {
            if (it.body()!!.items != null) {
                it.body()!!.items!!.forEach { item ->
                    list.add(item)
                }
            }
            Log.d("Listagem", it.body()!!.items.toString())
            includeContentsInPage()
        }

        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    getNextPage()
                }
                super.onScrolled(recyclerView, dx, dy)
            }

            private fun getNextPage() {
                val visibleItemCont = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapterList.itemCount
                if ((visibleItemCont + pastVisibleItem) >= total) {
                    contPage++
                    Log.d("Cont Page", contPage.toString())
                    getContentsForList(contPage)
                    includeContentsInPage()
                }
            }
        })

        return binding.root
    }

    private fun includeContentsInPage() {
        if (::adapterList.isInitialized) {
            if (binding.rvList.adapter == null) {
                setAdapter()
                setListInAdapter()
            } else {
                setListInAdapter()
            }
        } else {
            setAdapter()
            setListInAdapter()
        }

    }

    private fun setListInAdapter() {
        adapterList.setList(list)
    }

    private fun setAdapter() {
        adapterList = AdapterList(this)
        binding.rvList.adapter = adapterList
    }

    private fun setupLayoutList() {
        binding.rvList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        binding.rvList.layoutManager = layoutManager
    }

    private fun getContentsForList(page: Int) {
        viewModel.getLanguageForPage(page)
    }

    override fun onItemClicked(itemGithub: ItemGithub) {
        viewModel.itemSelect = itemGithub
        findNavController().navigate(R.id.action_listFragment_to_pullsFragment)
    }

}