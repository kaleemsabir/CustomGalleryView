package com.example.customgallery.ui.fragments.MainView

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.customgallery.R
import com.example.customgallery.databinding.FragmentMainBinding
import com.example.customgallery.permission.ManageRuntimePermissions
import com.example.customgallery.utils.PERMISSION_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var runtimePermission: ManageRuntimePermissions


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding
            .inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRuntimePermission()
    }

    private fun initRuntimePermission() {
        runtimePermission = ManageRuntimePermissions(fragment = this,
            permissionsList = getRunTimeGalleryReadPermissions(),
            code = PERMISSION_REQUEST_CODE,
            callBackPermissionGranted = {
                goToNextScreen()
            },
            callBackPermissionCancel = {
               closeApplication()
            }
        )
        runtimePermission.checkPermissions()
    }

    private fun getRunTimeGalleryReadPermissions(): Array<String> {
        return if (isGranularPermissionsSupport()) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun isGranularPermissionsSupport() =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Check if all requested permissions are granted
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                goToNextScreen()
            } else {
                closeApplication()
            }
        }
    }
    private fun goToNextScreen() {
        binding.let {
            Navigation.findNavController(it.root).navigate(R.id.action_mainFragment_to_gallery_folder)
        }
    }

    private fun closeApplication(){
        this.requireActivity().finish()
    }

    override fun onResume() {
        super.onResume()
        runtimePermission.checkPermissions()
    }

}
