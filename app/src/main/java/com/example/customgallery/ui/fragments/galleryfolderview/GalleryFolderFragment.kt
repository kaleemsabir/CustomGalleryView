package com.example.customgallery.ui.fragments.galleryfolderview

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.customgallery.BR
import com.example.customgallery.R
import com.example.customgallery.databinding.FragmentGalleryViewBinding
import com.example.customgallery.ui.base.BaseFragment
import com.example.customgallery.ui.fragments.galleryfolderview.adapter.GalleryFolderViewAdapter
import com.example.customgallery.ui.fragments.galleryfolderview.viewmodel.GalleryFolderViewModel
import com.example.customgallery.ui.toolbar.ToolbarViewModel
import com.example.customgallery.utils.MEDIA_DATA
import com.gallerydemo.data.local.models.FolderMedia
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class GalleryFolderFragment :
    BaseFragment<FragmentGalleryViewBinding, GalleryFolderViewModel>(R.layout.fragment_gallery_view),GalleryFolderViewAdapter.GalleryFolderClickListener {


    private lateinit var toolBarViewModel :ToolbarViewModel
     @Inject
     lateinit var adapter : GalleryFolderViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModelClass(): Class<GalleryFolderViewModel> {
        return GalleryFolderViewModel::class.java
    }

    private fun init(){
        initToolBarViewModel()
        initProgressObserver()
        initRecyclerView()
        getDataFromGallery()
        initGalleryFolderListObserver()
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
                if(response.isNotEmpty()) {
                    adapter.setDataList(response)
                }
            }
        }
    }
    private fun initProgressObserver() {
        lifecycleScope.launch {
            viewModel.isLoading.collect {
                    when {
                        it -> {
                            bindings.progressBar.visibility = View.VISIBLE
                        }
                        else -> {
                            bindings.progressBar.visibility = View.GONE
                        }
                    }
                }
        }
    }

    private fun initRecyclerView(){
        bindings.rvGalleryFragment.adapter = adapter
        adapter.setClickListener(galleryFolderClickListener = this )
    }

    override fun onItemClick(foldermedia: FolderMedia) {
        val bundle = Bundle().apply {
            putParcelable(MEDIA_DATA, foldermedia)
        }
        bindings.let {
            Navigation.findNavController(it.root).navigate(R.id.action_folder_detail,bundle)
        }

    }




}
