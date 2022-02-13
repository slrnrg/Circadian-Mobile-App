package com.example.circadian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_scan.view.*
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * created by Isaac Folarin - 18/01/22
 */
class ScanFragment : Fragment(), ZXingScannerView.ResultHandler {
    companion object {
        fun newInstance(): ScanFragment {
            return ScanFragment()
        }
    }
    private lateinit var mView: View

    private lateinit var scannerView: ZXingScannerView

   // lateinit var scannerView: ZBarScannerView

    // lateinit var resultDialog: QrCodeResultDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_scan, container, false)
        //init()
        initViews()
        onClicks()
        return mView.rootView
    }

    private fun initViews() {
        initializeQRCamera()
        //setResultDialog()
    }


    private fun initializeQRCamera() {
        scannerView = ZXingScannerView(context)
        scannerView.setResultHandler(this)
        scannerView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        scannerView.setBorderColor(ContextCompat.getColor(requireContext(), R.color.blue))
        scannerView.setLaserColor(ContextCompat.getColor(requireContext(), R.color.blue))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setAutoFocus(true)
        startQRCamera()
        mView.containerScanner.addView(scannerView)
    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }

    private fun resetPreview() {
        scannerView.stopCamera()
        scannerView.startCamera()
        scannerView.stopCameraPreview()
        scannerView.resumeCameraPreview(this)
    }

    private fun onClicks() {
        mView.flashToggle.setOnClickListener {
            if (mView.flashToggle.isSelected) {
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
    }

    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    private fun onQrResult(contents: String?) {
        if (contents.isNullOrEmpty())
            showToast("Empty Qr Result")
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun handleResult(rawResult: com.google.zxing.Result?) {
        Toast.makeText(requireContext(),rawResult?.text, Toast.LENGTH_SHORT).show()
    }


}