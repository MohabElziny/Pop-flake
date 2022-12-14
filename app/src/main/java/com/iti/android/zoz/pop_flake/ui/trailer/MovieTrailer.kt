package com.iti.android.zoz.pop_flake.ui.trailer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.iti.android.zoz.pop_flake.R
import com.iti.android.zoz.pop_flake.data.ResultState
import com.iti.android.zoz.pop_flake.data.pojos.MovieTrailer
import com.iti.android.zoz.pop_flake.databinding.FragmentMovieTrailerBinding
import com.iti.android.zoz.pop_flake.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.buffer

@AndroidEntryPoint
class MovieTrailer : Fragment() {

    private val movieTrailerViewModel: MovieTrailerViewModel by viewModels()
    private val args: MovieTrailerArgs by navArgs()

    private var _movieTrailerBinding: FragmentMovieTrailerBinding? = null
    private val movieTrailerBinding get() = _movieTrailerBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _movieTrailerBinding = FragmentMovieTrailerBinding.inflate(inflater, container, false)
        return movieTrailerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieTrailerViewModel.prepareVideo(args.movieId)
        viewModelObservation()
        prepareMediaController()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun prepareMediaController() {
        movieTrailerBinding.trailerVideoView.settings.javaScriptEnabled = true
        movieTrailerBinding.trailerVideoView.webViewClient = object : WebViewClient() {
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

    private fun viewModelObservation() {
        lifecycleScope.launchWhenStarted {
            movieTrailerViewModel.trailerUrl.buffer().collect { resultState ->
                when (resultState) {
                    ResultState.EmptyResult -> {
                        showSnackBar(getString(R.string.no_trailer_found))
                        movieTrailerBinding.progressBar2.visibility = View.GONE
                    }
                    is ResultState.Error -> {
                        showSnackBar(resultState.errorString)
                        movieTrailerBinding.progressBar2.visibility = View.GONE
                    }
                    ResultState.Loading -> movieTrailerBinding.progressBar2.visibility = View.VISIBLE
                    is ResultState.Success -> handleSuccessState(resultState.data)
                }
            }
        }
    }

    private fun handleSuccessState(movieTrailer: MovieTrailer) {
        movieTrailerBinding.apply {
            textView.text = movieTrailer.title
            trailerVideoView.loadUrl(movieTrailer.videoLinkEmbed)
            progressBar2.visibility = View.GONE
            textView.visibility = View.VISIBLE
            trailerVideoView.visibility = View.VISIBLE
            trailerVideoView.requestFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _movieTrailerBinding = null
    }

}