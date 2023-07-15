package com.example.tugaswebview.ui.webview

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.tugaswebview.R
import com.example.tugaswebview.customload.setProgressDialog
import com.example.tugaswebview.databinding.FragmentWebviewBinding

class WebViewFragment : Fragment() {
    companion object {
        fun newInstance() = WebViewFragment()
    }

    private lateinit var viewModel: ViewModel
    private var webviewBinding:FragmentWebviewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentWebviewBinding.bind(view)
        webviewBinding = binding

        val load = activity?.let {
            setProgressDialog(it, "Sabar yaa...")
        }

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object  : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                load?.show()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                load?.dismiss()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                load?.dismiss()

                binding.imgError.visibility = View.VISIBLE
                binding.webView.visibility = View.GONE

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                    Toast.makeText(context, error?.description, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Web error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.webView.loadUrl("https://nurulhudapurbalingga.com/")
    }
}