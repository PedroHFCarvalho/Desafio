package com.carvalho.desafio_itau.repository

import com.carvalho.desafio_itau.data.RetrofitInstance
import com.carvalho.desafio_itau.model.RepositoryGithub
import com.carvalho.desafio_itau.model.pull_requests.PullRequest
import retrofit2.Response

class Repository {

    suspend fun getLanguageForPage(
        page: Int
    ): Response<RepositoryGithub> {
        return RetrofitInstance.api.getLanguageForPage("Java", "forks", page)
    }

    suspend fun getPullRequests(
        owner: String,
        repos: String,
        page: Int
    ): Response<List<PullRequest>> {
        return RetrofitInstance.api.getPullRequests(owner, repos, "all", "popularity", page)
    }

}