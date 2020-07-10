package ar.edu.unlam.taller.recyclerviewexample

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unlam.taller.recyclerviewexample.databinding.ActivityMainBinding
import ar.edu.unlam.taller.recyclerviewexample.retrofit.Movies
import ar.edu.unlam.taller.recyclerviewexample.retrofit.RetrofitMoviesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var moviesService: RetrofitMoviesService
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        injectDependencies()
        setSearchViewListener()
        setupRecyclerView()
    }

    private fun injectDependencies() {
        moviesService = RetrofitMoviesService()
        moviesAdapter = MoviesAdapter { movie ->
            Toast.makeText(this@MainActivity, movie.name, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = moviesAdapter
    }

    private fun setSearchViewListener() {
        binding.searchView.setOnQueryTextListener(
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
                            val movies = response.body()!!
                            moviesAdapter.updateMovies(movies.results)
                            moviesAdapter.notifyDataSetChanged()
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