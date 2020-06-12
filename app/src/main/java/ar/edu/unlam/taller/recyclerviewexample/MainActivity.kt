package ar.edu.unlam.taller.recyclerviewexample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import ar.edu.unlam.taller.recyclerviewexample.retrofit.Movies
import ar.edu.unlam.taller.recyclerviewexample.retrofit.RetrofitMoviesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var moviesService: RetrofitMoviesService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependencies()
        setSearchViewListener()
    }

    private fun injectDependencies() {
        moviesService = RetrofitMoviesService()
    }

    private fun setSearchViewListener() {
        findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    onSearch(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
    }

    private fun onSearch(query: String?) {
        query?.run {
            moviesService.search(query)
                .enqueue(object : Callback<Movies> {
                    override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                        if (response.isSuccessful) {
                            Log.d("MOVIES", response.body()!!.toString())
                        } else {
                            showError()
                        }
                    }

                    override fun onFailure(call: Call<Movies>, t: Throwable) {
                        showError()
                    }
                })
        }
    }

    private fun showError() {
        Toast.makeText(this@MainActivity, R.string.error_movies, Toast.LENGTH_SHORT).show()
    }

}