package com.carvalho.desafio_itau.data

import com.carvalho.desafio_itau.model.RepositoryGithub
import com.carvalho.desafio_itau.model.pull_requests.PullRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    suspend fun getLanguageForPage(
        @Query("q") language: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Response<RepositoryGithub>

    @GET("repos/{owner}/{repository}/pulls")
    suspend fun getPullRequests(
        @Path("owner") owner: String,
        @Path("repository") repos: String,
        @Query("state") state: String,
        @Query("sort") sort: String,
        @Query("direction") direction: String,
        @Query("page") page: Int
    ): Response<List<PullRequest>>
}