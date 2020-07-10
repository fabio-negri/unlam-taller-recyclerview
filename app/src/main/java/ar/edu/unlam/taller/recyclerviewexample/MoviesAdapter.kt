package ar.edu.unlam.taller.recyclerviewexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unlam.taller.recyclerviewexample.databinding.ListItemMovieBinding
import ar.edu.unlam.taller.recyclerviewexample.retrofit.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter(
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    private val moviesList = mutableListOf<Movie>()

    override fun getItemCount() = moviesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieBinding = ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(movieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.binding.title.text = movie.name
        holder.binding.genre.text = movie.genre
        holder.binding.runtime.text = movie.length.toString()
        holder.binding.description.text = movie.description

        Picasso.get()
            .load(movie.imageUrl)
            .into(holder.binding.image)

        holder.itemView.setOnClickListener { clickListener(movie) }
    }

    fun updateMovies(results: List<Movie>?) {
        moviesList.clear()
        if (results != null) {
            moviesList.addAll(results)
        }
    }

}

class MovieViewHolder(val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
