package com.anoopvt.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.anoopvt.artbooktesting.R
import com.anoopvt.artbooktesting.adapter.ImageRecyclerAdapter
import com.anoopvt.artbooktesting.databinding.FragmentImageApiBinding
import com.anoopvt.artbooktesting.viewmodel.ArtViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ImageApiFragment  constructor(val imageRecyclerAdapter: ImageRecyclerAdapter) :
    Fragment(R.layout.fragment_image_api) {

     val viewModel: ArtViewModel by activityViewModels()


    private var fragmentBinding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch(IO) {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObservers()

        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setItemClickListener {
            viewModel.setSelectedImage(it)
            findNavController().popBackStack()

        }

    }

    private fun subscribeToObservers() {
        viewModel.uiState.observe(viewLifecycleOwner,{
            updateValuesToUi(it)
        })
    }

    private fun updateValuesToUi(state: ArtViewModel.ActionCenterState) {

        if (state.loading){
            fragmentBinding?.progressBar?.visibility = View.VISIBLE
       }
        else{
            fragmentBinding?.progressBar?.visibility = View.GONE
        }

        imageRecyclerAdapter.images = state.mainList

        if (state.error){
            Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT)
                .show()
        }


    }


    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }

}