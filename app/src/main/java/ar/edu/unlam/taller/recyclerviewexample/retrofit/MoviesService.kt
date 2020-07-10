package ar.edu.unlam.taller.recyclerviewexample.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("search?media=movie")
    fun search(
        @Query(value = "term") term: String
    ): Call<Movies>

}

data class Movies(
    @SerializedName(value = "resultCount")
    val resultCount: Int,

    @SerializedName(value = "results")
    val results: List<Movie>
)

data class Movie(
    @SerializedName(value = "trackId")
    val id: Long,
    @SerializedName(value = "trackName")
    val name: String,
    @SerializedName(value = "longDescription")
    val description: String,
    @SerializedName(value = "trackTimeMillis")
    val length: Long,
    @SerializedName(value = "artistName")
    val director: String,
    @SerializedName(value = "primaryGenreName")
    val genre: String,
    @SerializedName(value = "contentAdvisoryRating")
    val contentRating: String,
    @SerializedName(value = "artworkUrl100", alternate = ["artworkUrl60", "artworkUrl30"])
    val imageUrl: String
)