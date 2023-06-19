package com.example.customegallery.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.customegallery.databinding.FragmentMainBinding
import com.example.customegallery.permission.ManageRuntimePermissions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null


    lateinit var runtimePermission: ManageRuntimePermissions
    private val permissionsRequestCode = 123

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
            code = permissionsRequestCode,
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
        if (requestCode == permissionsRequestCode) {
            // Check if all requested permissions are granted
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                goToNextScreen()
            } else {
                closeApplication()
            }
        }
    }
    private fun goToNextScreen() {

    }

    private fun closeApplication(){
        this.requireActivity().finish()
    }

}
