package com.example.giff.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giff.DataObject
import com.example.giff.R

class GifsAdapter(val context: Context, val gifs: MutableList<DataObject>) :
    RecyclerView.Adapter<GifsAdapter.ViewHolder>() {

    lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false), mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = gifs[position]
        Glide.with(context)
            .load(data.images.ogImage.url)
            .placeholder(R.drawable.img)
            .into(holder.imageView)
    }
    private val limit = 25

    override fun getItemCount(): Int {
        return if (gifs.size > limit){
            limit
        } else {
            gifs.size
        }
    }

    fun submitList(newList: List<DataObject>) {
        gifs.clear()
        gifs.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {

        val imageView = itemView.findViewById<ImageView>(R.id.ivGif)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

}
