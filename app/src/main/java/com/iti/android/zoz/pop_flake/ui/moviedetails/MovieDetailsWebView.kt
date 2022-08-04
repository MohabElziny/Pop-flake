package com.iti.android.zoz.pop_flake.ui.moviedetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.iti.android.zoz.pop_flake.databinding.FragmentMovieDetailsWebViewBinding
import com.iti.android.zoz.pop_flake.utils.DETAILS_BASE_URL

class MovieDetailsWebView : Fragment() {

    private val args: MovieDetailsWebViewArgs by navArgs()

    private var _movieDetailsWebViewBinding: FragmentMovieDetailsWebViewBinding? = null
    private val movieDetailsWebViewBinding get() = _movieDetailsWebViewBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _movieDetailsWebViewBinding =
            FragmentMovieDetailsWebViewBinding.inflate(inflater, container, false)
        return movieDetailsWebViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareWebView()
        initializeSwipeRefresh()
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        movieDetailsWebViewBinding.apply {
            movieDetailsWebView.loadUrl(DETAILS_BASE_URL.plus(args.movieId))
            movieDetailsWebView.requestFocus()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun initializeSwipeRefresh() {
        movieDetailsWebViewBinding.swipeRefresh.setOnRefreshListener {
            movieDetailsWebViewBinding.swipeRefresh.isRefreshing = true
            loadMovieDetails()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun prepareWebView() {
        movieDetailsWebViewBinding.movieDetailsWebView.settings.javaScriptEnabled = true
        movieDetailsWebViewBinding.movieDetailsWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _movieDetailsWebViewBinding = null
    }

}