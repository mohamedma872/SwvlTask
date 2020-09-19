package com.swvl.androidtask.movieslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swvl.androidtask.R
import com.swvl.androidtask.commons.data.local.Movie
import kotlinx.android.synthetic.main.item_movies_list.view.*

class MoviesAdapter
    : ListAdapter<Movie, MoviesAdapter.ListViewHolder>(MovieWithUserDC()) {

    var interaction: Interaction? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies_list, parent, false), interaction
    )

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    fun swapData(data: List<Movie>) {
        submitList(data.toMutableList())
    }

    inner class ListViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val clicked = getItem(adapterPosition)
            interaction?.postClicked(clicked)
        }

        fun bind(item: Movie) = with(itemView) {
            tvTitle.text = item.title
            tvYear.text = item.year.toString()
            tvCast.text = item.cast.joinToString(" - ")
            tvGenre.text = item.genres.joinToString(" - ")
            tvrating.text = item.rating.toString()

            //SharedItem transition
            ViewCompat.setTransitionName(tvTitle, item.title)
            ViewCompat.setTransitionName(tvYear, item.year.toString())
            ViewCompat.setTransitionName(tvCast, item.cast.joinToString(" - "))
            ViewCompat.setTransitionName(tvGenre, item.genres.joinToString(","))
            ViewCompat.setTransitionName(tvrating, item.rating.toString())
        }
    }

    interface Interaction {
        fun postClicked(
            movieObject: Movie
        )
    }

    private class MovieWithUserDC : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}