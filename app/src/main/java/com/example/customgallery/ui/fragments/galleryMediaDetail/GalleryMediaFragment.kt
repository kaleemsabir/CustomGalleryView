package com.example.customgallery.ui.fragments.galleryMediaDetail

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.customgallery.BR
import com.example.customgallery.R
import com.example.customgallery.databinding.FragmentGalleryDetailViewBinding
import com.example.customgallery.ui.base.BaseFragment
import com.example.customgallery.ui.fragments.galleryMediaDetail.adapter.MediaViewAdapter
import com.example.customgallery.ui.fragments.galleryMediaDetail.viewmodel.GalleryMediaViewModel
import com.example.customgallery.ui.toolbar.ToolbarViewModel
import com.example.customgallery.utils.MEDIA_DATA
import com.gallerydemo.data.local.models.FolderMedia
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GalleryMediaFragment :
    BaseFragment<FragmentGalleryDetailViewBinding, GalleryMediaViewModel>(R.layout.fragment_gallery_detail_view){

    @Inject
    lateinit var toolBarViewModel :ToolbarViewModel
    @Inject
    lateinit var adapter : MediaViewAdapter
    private val mediaData by lazy {
        requireArguments().getParcelable<FolderMedia>(MEDIA_DATA)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModelClass(): Class<GalleryMediaViewModel> {
        return GalleryMediaViewModel::class.java
    }

    private fun init(){
        initData()
        initToolBarViewModel()
        initRecyclerView()
        initGalleryFolderListObserver()

    }
    private fun initGalleryFolderListObserver() {
        initClickListener()
    }
    private fun initData(){
        mediaData?.let {
            viewModel.setMediaItem(it)
        }

    }


    private fun initClickListener() {
        bindings.toolbar.back.setOnClickListener {
           val navController = Navigation.findNavController(bindings.root)
            navController.popBackStack()
        }
    }

    private fun initToolBarViewModel(){
        toolBarViewModel.isBackArrowShow = true
        toolBarViewModel.isSwitchStateShow = false
        toolBarViewModel.title =viewModel.getMediaItem().folderName
        bindings.toolbar.viewModel = toolBarViewModel
    }

    private fun initRecyclerView(){
        bindings.rvMedia.adapter = adapter
        adapter.setDataList(viewModel.getMediaItem().mediaListItem)
    }





}
