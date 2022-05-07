package com.carvalho.desafio_itau

import com.carvalho.desafio_itau.model.RepositoryGithub
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carvalho.desafio_itau.data.RetrofitInstance
import com.carvalho.desafio_itau.model.ItemGithub
import com.carvalho.desafio_itau.model.pull_requests.PullRequest
import com.carvalho.desafio_itau.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _responseRepositoryGit = MutableLiveData<Response<RepositoryGithub>>()
    val responseRepositoryGit: LiveData<Response<RepositoryGithub>> = _responseRepositoryGit

    private val _responsePullRequests = MutableLiveData<Response<List<PullRequest>>>()
    val responsePullRequests: LiveData<Response<List<PullRequest>>> = _responsePullRequests

    var itemSelect: ItemGithub? = null

    fun getLanguageForPage(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getLanguageForPage(page)
                _responseRepositoryGit.value = response
            } catch (e: Exception) {
                Log.e("Err", e.message.toString())
            }
        }
    }

    fun getPullRequests(owner: String, repos: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPullRequests(owner, repos)
                _responsePullRequests.value = response
            } catch (e: Exception) {
                Log.e("Err", e.message.toString())
            }
        }
    }


}