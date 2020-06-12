package ar.edu.unlam.taller.recyclerviewexample.retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitMoviesService {

    private val service: MoviesService = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MoviesService::class.java)

    fun search(term: String): Call<Movies> {
        return service.search(term)
    }

}