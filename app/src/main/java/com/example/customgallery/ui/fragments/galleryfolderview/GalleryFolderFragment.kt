package com.example.customgallery.ui.fragments.galleryfolderview

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.customgallery.BR
import com.example.customgallery.R
import com.example.customgallery.databinding.FragmentGalleryViewBinding
import com.example.customgallery.ui.base.BaseFragment
import com.example.customgallery.ui.fragments.galleryfolderview.viewmodel.GalleryFolderViewModel
import com.example.customgallery.ui.toolbar.ToolbarViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GalleryFolderFragment :
    BaseFragment<FragmentGalleryViewBinding, GalleryFolderViewModel>(R.layout.fragment_gallery_view) {


    lateinit var toolBarViewModel :ToolbarViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBarViewModel()
        getDataFromGallery()
        initGalleryFolderListObserver()
    }


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModelClass(): Class<GalleryFolderViewModel> {
        return GalleryFolderViewModel::class.java
    }

    private fun initGalleryFolderListObserver() {
        initClickListener()

    }


    private fun initClickListener() {

        bindings.toolbar.back.setOnClickListener {
           val navController = Navigation.findNavController(bindings.root)
            navController.popBackStack()
        }
    }

    private fun initToolBarViewModel(){
        toolBarViewModel = ToolbarViewModel {

        }
        toolBarViewModel.isBackArrowShow = false
        toolBarViewModel.title = this.requireContext().getString(R.string.gallery)
        bindings.toolbar.viewModel = toolBarViewModel
    }

    private fun getDataFromGallery(){
        viewModel.fetchAllGalleryFolders { this.requireActivity().contentResolver }
        lifecycleScope.launch {
            viewModel.folderMediaList.collect { response ->
            }
        }
        viewModel.fetchAllGalleryFolders { this.requireContext().contentResolver }
    }




}
