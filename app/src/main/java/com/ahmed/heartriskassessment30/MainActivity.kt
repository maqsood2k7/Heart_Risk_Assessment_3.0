package com.ahmed.heartriskassessment30

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mWebView = findViewById<View>(R.id.webView) as WebView
        mWebView.loadUrl("http://ncpca.ml/")

        val webSettings: WebSettings = mWebView.settings
        webSettings.javaScriptEnabled = true

        mWebView.webViewClient = WebViewClient()
        mWebView.canGoBack()
        mWebView.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK

                && event.action == MotionEvent.ACTION_UP
                && mWebView.canGoBack()) {
                mWebView.goBack()
                return@OnKeyListener true
            }
            false
        })

        mWebView.webViewClient = object:WebViewClient() {
            override fun shouldOverrideUrlLoading(view:WebView, url:String):Boolean {
                if (url.endsWith(".pdf"))
                {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(android.net.Uri.parse(url), "application/pdf")
                    try
                    {
                        view.context.startActivity(intent)
                    }
                    catch (e: ActivityNotFoundException) {
                        //user does not have a pdf viewer installed
                    }
                }
                else
                {
                    mWebView.loadUrl(url)
                }
                return true
            }
        }
    }
}