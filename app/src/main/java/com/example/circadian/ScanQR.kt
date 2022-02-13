package com.example.circadian

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ScanQR : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)

        // To change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_dark)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            checkForPermission()
        }, 2000)
        //checkForPermission()
    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            goToMainActivity()
        } else
            requestForPermission()

    }

    private fun requestForPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToMainActivity()
            } else if (isUserPermanentlyDenied()) {
                showGoToAppSettingDialog()
            }
        }
    }

    private fun showGoToAppSettingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Grant Permissions!")
            .setMessage("We need Camera Permission to Scan QR Code. Go to App Settings and Manage permissions.")
            .setPositiveButton("Grant") { dialog, which ->
                goToAppSettings()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(
                    this,
                    "We need permissions to start this Application",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            .show()
    }

    private fun goToAppSettings() {
        val intent =
            Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    private fun isUserPermanentlyDenied(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA).not()
        } else {
            return false
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRestart() {
        super.onRestart()
        checkForPermission()
    }
}