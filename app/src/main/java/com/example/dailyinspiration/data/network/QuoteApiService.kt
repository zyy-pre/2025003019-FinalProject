package com.example.dailyinspiration.data.network

import retrofit2.http.GET

data class QuoteDto(
    val id: Int,
    val quote: String,
    val author: String,
    val tags: List<String>?,
    val category: String?
)

interface QuoteApiService {
    @GET("quotes/random")
    suspend fun getRandomQuote(): QuoteDto
}
