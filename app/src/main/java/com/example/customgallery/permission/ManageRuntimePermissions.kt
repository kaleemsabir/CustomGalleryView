package com.example.customgallery.permission

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.customgallery.R


class ManageRuntimePermissions (
    private val fragment: Fragment,
    private val permissionsList: Array<String>,
    private val code: Int,
    val callBackPermissionGranted: () -> Unit,
    val callBackPermissionCancel: () -> Unit) {

    fun checkPermissions() {
        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            showDialogPermissionNotGranted()
        } else {
            callBackPermissionGranted()
        }
    }

    private fun showDialogPermissionNotGranted() {
        val builder = AlertDialog.Builder(fragment.requireContext())
        builder.setTitle(R.string.permission_title)
        builder.setMessage(R.string.permission_required_description)
        builder.setPositiveButton(R.string.ok) { _, _ -> requestPermissions() }
        builder.setNeutralButton(R.string.cancel) { _, _ ->
            callBackPermissionCancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun isPermissionsGranted(): Int {
        var permissionCount = 0;
        for (permission in permissionsList) {
            permissionCount += ContextCompat.checkSelfPermission(fragment.requireContext(), permission)
        }
        return permissionCount
    }

    private fun requestPermissions() {
        val permission = findDeniedPermissions()
        if (ActivityCompat.shouldShowRequestPermissionRationale(fragment.requireActivity(), permission)) {
            permissionSettingsDialog()
        } else {
            ActivityCompat.requestPermissions(fragment.requireActivity(), permissionsList, code)
        }
    }

    private fun findDeniedPermissions(): String {
        for (permission in permissionsList) {
            if (ContextCompat.checkSelfPermission(fragment.requireContext(), permission)
                == PackageManager.PERMISSION_DENIED) return permission
        }
        return ""
    }

    private fun permissionSettingsDialog() {
        val alertDialog: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(fragment.requireContext())
            .setTitle(fragment.getString(R.string.app_name))
            .setMessage(fragment.getString(R.string.permissions_setting_message))
            .setPositiveButton(R.string.settings) { dialog, _ ->
                openSettings()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> callBackPermissionCancel() }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", fragment.requireContext().packageName, null)
        intent.data = uri
        fragment.startActivity(intent)
    }


}