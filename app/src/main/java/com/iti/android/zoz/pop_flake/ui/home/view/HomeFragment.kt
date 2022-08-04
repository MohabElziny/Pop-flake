package com.iti.android.zoz.pop_flake.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.iti.android.zoz.pop_flake.R
import com.iti.android.zoz.pop_flake.data.ResultState
import com.iti.android.zoz.pop_flake.databinding.FragmentHomeBinding
import com.iti.android.zoz.pop_flake.ui.home.adapters.*
import com.iti.android.zoz.pop_flake.ui.home.viewmodel.HomeViewModel
import com.iti.android.zoz.pop_flake.utils.ZoomOutPageTransformer
import com.iti.android.zoz.pop_flake.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _comingSoonAdapter: ComingSoonAdapter? = null
    private val comingSoonAdapter get() = _comingSoonAdapter!!

    private var _inTheatersAdapter: InTheaterAdapter? = null
    private val inTheatersAdapter get() = _inTheatersAdapter!!

    private var _topRatedAdapter: TopRatedAdapter? = null
    private val topRatedAdapter get() = _topRatedAdapter!!

    private var _boxOfficeAdapter: BoxOfficeAdapter? = null
    private val boxOfficeAdapter get() = _boxOfficeAdapter!!

    private var _trailersAdapter: TrailerViewPagerAdapter? = null
    private val trailersAdapter get() = _trailersAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeTrailerViewPagerAdapter()
        initializeComingSoonAdapter()
        initializeInTheatersAdapter()
        initializeTopRatedAdapter()
        initializeBoxOfficeAdapter()
        initializeSwipeRefresh()

        homeViewModel.getMovies()
        homeViewModelObserve()
    }

    private fun initializeTrailerViewPagerAdapter() {
        _trailersAdapter = TrailerViewPagerAdapter(showTrailer)
        binding.viewPager2.apply {
            adapter = trailersAdapter
            binding.wormDotsIndicator.attachTo(this)
            val zoomOutPageTransformer = ZoomOutPageTransformer()
            setPageTransformer { page, position ->
                zoomOutPageTransformer.transformPage(page, position)
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    homeViewModel.setManualViewPagerPosition(position)
                }
            })
        }
    }

    private fun initializeBoxOfficeAdapter() {
        _boxOfficeAdapter = BoxOfficeAdapter(showMovieDetails)
        binding.boxOfficeRecyclerview.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                RecyclerView.VERTICAL,
                false
            )
            adapter = boxOfficeAdapter
        }
    }

    private fun initializeTopRatedAdapter() {
        _topRatedAdapter = TopRatedAdapter(showMovieDetails)
        binding.topRatedRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = topRatedAdapter
        }
    }

    private fun initializeInTheatersAdapter() {
        _inTheatersAdapter = InTheaterAdapter(showMovieDetails)
        binding.inTheatersRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = inTheatersAdapter
        }
    }

    private fun initializeComingSoonAdapter() {
        _comingSoonAdapter = ComingSoonAdapter(showMovieDetails)
        binding.comingSoonRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = comingSoonAdapter
        }
    }

    private fun initializeSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            homeViewModel.getMovies()
            binding.swiperefresh.isRefreshing = true
        }
    }

    private fun homeViewModelObserve() {
        posterObservation()
        comingSoonMoviesObservation()
        inTheatersMoviesObservation()
        topRatedMoviesObservation()
        boxOfficeMoviesObservation()
        visibilityObservation()
    }

    private fun posterObservation() {
        binding.viewPager2.visibility = View.GONE
        binding.wormDotsIndicator.visibility = View.GONE
        lifecycleScope.launch {
            homeViewModel.postersList.observe(viewLifecycleOwner) {
                trailersAdapter.setPostersList(it)
                homeViewModel.startSwap()
            }
        }
        lifecycleScope.launch {
            homeViewModel.viewPagerPosition.observe(viewLifecycleOwner) {
                binding.viewPager2.setCurrentItem(it, true)
            }
        }
    }

    private fun comingSoonMoviesObservation() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.comingSoonMovies.buffer().collect { comingSoonMoviesResult ->
                when (comingSoonMoviesResult) {
                    ResultState.EmptyResult -> showSnackBar(getString(R.string.no_coming_soon_movies))
                    is ResultState.Error -> showSnackBar(comingSoonMoviesResult.errorString)
                    ResultState.Loading -> {
                        binding.swiperefresh.isRefreshing = true
                        binding.comingSoonRecyclerview.visibility = View.INVISIBLE
                    }
                    is ResultState.Success -> comingSoonAdapter.setComingSoonMoviesList(
                        comingSoonMoviesResult.data
                    )
                }
            }
        }
    }

    private fun inTheatersMoviesObservation() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.inTheaterMovies.buffer().collect { inTheatersMoviesResult ->
                when (inTheatersMoviesResult) {
                    ResultState.EmptyResult -> showSnackBar(getString(R.string.no_in_theaters_movies))
                    is ResultState.Error -> showSnackBar(inTheatersMoviesResult.errorString)
                    ResultState.Loading -> {
                        binding.swiperefresh.isRefreshing = true
                        binding.inTheatersRecyclerview.visibility = View.INVISIBLE
                    }
                    is ResultState.Success -> inTheatersAdapter.setInTheaterMoviesList(
                        inTheatersMoviesResult.data
                    )
                }
            }
        }
    }

    private fun topRatedMoviesObservation() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.topRatedMovies.buffer().collect { topRatedMoviesResult ->
                when (topRatedMoviesResult) {
                    ResultState.EmptyResult -> showSnackBar(getString(R.string.no_top_rated_movies))
                    is ResultState.Error -> showSnackBar(topRatedMoviesResult.errorString)
                    ResultState.Loading -> {
                        binding.swiperefresh.isRefreshing = true
                        binding.topRatedRecyclerview.visibility = View.INVISIBLE
                    }
                    is ResultState.Success -> topRatedAdapter.setTopRatedMoviesList(
                        topRatedMoviesResult.data
                    )
                }
            }
        }
    }

    private fun boxOfficeMoviesObservation() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.boxOfficeMovies.buffer().collect { boxOfficeMoviesResult ->
                when (boxOfficeMoviesResult) {
                    ResultState.EmptyResult -> showSnackBar(getString(R.string.no_box_office_movies))
                    is ResultState.Error -> showSnackBar(boxOfficeMoviesResult.errorString)
                    ResultState.Loading -> {
                        binding.swiperefresh.isRefreshing = true
                        binding.boxOfficeRecyclerview.visibility = View.INVISIBLE
                    }
                    is ResultState.Success -> boxOfficeAdapter.setBoxOfficeMoviesList(
                        boxOfficeMoviesResult.data
                    )
                }
            }
        }
    }

    private fun visibilityObservation() {
        lifecycleScope.launch {
            homeViewModel.receivedAllData.observe(viewLifecycleOwner) { receivedAll ->
                if (receivedAll) {
                    binding.apply {
                        binding.swiperefresh.isRefreshing = false
                        boxOfficeRecyclerview.visibility = View.VISIBLE
                        inTheatersRecyclerview.visibility = View.VISIBLE
                        comingSoonRecyclerview.visibility = View.VISIBLE
                        topRatedRecyclerview.visibility = View.VISIBLE
                        viewPager2.visibility = View.VISIBLE
                        wormDotsIndicator.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private val showTrailer: (String) -> Unit = { movieId ->
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToMovieTrailer(movieId)
        )
    }

    private val showMovieDetails: (String) -> Unit = { movieId ->
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToMovieDetailsWebView(movieId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.stopSwap()
        _topRatedAdapter = null
        _inTheatersAdapter = null
        _comingSoonAdapter = null
        _boxOfficeAdapter = null
        _binding = null
    }
}