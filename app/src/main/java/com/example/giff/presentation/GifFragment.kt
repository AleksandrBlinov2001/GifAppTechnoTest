package com.example.giff.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.giff.R

class GifFragment: Fragment() {

    companion object {

        fun newInstance(url: String) = GifFragment().apply {
            this.arguments = bundleOf(
                "url" to url
            )
        }
    }

    private val url: String by lazy {
        arguments?.getString("url").orEmpty()
    }

    private lateinit var viewModel: GifViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GifViewModel::class.java)
        return inflater.inflate(R.layout.activity_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        //val titleTextView = view.findViewById<TextView>(R.id.titleTextView)


        Glide.with(this)
            .load(url)
            .into(imageView)
    }
}