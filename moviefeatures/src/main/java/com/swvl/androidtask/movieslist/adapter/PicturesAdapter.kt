package com.swvl.androidtask.movieslist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.swvl.androidtask.R
import com.swvl.androidtask.commons.data.remote.Photo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_pictures_list.*


class PicturesAdapter(
    private val list: MutableList<Photo>, private val context: Context
) :
    RecyclerView.Adapter<PicturesAdapter.VH>() {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pictures_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val pic = list[position]
        Picasso.get()
            .load(pic.url_l)
            .centerCrop()
            .fit()
            .placeholder(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.placeholder
                )!!
            )
            .error(ContextCompat.getDrawable(holder.itemView.context, R.drawable.placeholder)!!)
            .priority(Picasso.Priority.HIGH)
            .into(holder.iv)

    }

    fun addPhotos(photos: List<Photo>) {
        this.list.clear()
        this.list.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}