package com.example.giff.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giff.DataObject
import com.example.giff.R

class GifListFragment: Fragment() {

    companion object {

        fun newInstance() = GifListFragment()
    }

    private lateinit var viewModel: GifListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GifListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_gif_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val searchEditText = view.findViewById<EditText>(R.id.editTextView)
        val searchButton = view.findViewById<Button>(R.id.searchButton)

        searchButton.setOnClickListener {
            viewModel.fetchGifs(searchEditText.text.toString().trim())
        }

        val gifs = mutableListOf<DataObject>()
        val adapter = GifsAdapter(requireContext(), gifs)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.fetchGifs()

        adapter.setOnItemClickListener(object : GifsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val url = adapter.gifs.getOrNull(position)?.images?.ogImage?.url.orEmpty()

                parentFragmentManager.commit {
                    add(R.id.container, GifFragment.newInstance(url), null,)
                    addToBackStack(null)
                }
            }
        })

        viewModel.liveData.observe(viewLifecycleOwner) { giphs ->
            adapter.submitList(giphs)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}