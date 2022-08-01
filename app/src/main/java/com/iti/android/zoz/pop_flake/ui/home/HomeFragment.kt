package com.iti.android.zoz.pop_flake.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.android.zoz.pop_flake.databinding.FragmentHomeBinding
import com.iti.android.zoz.pop_flake.ui.home.adapters.BoxOfficeAdapter
import com.iti.android.zoz.pop_flake.ui.home.adapters.ComingSoonAdapter
import com.iti.android.zoz.pop_flake.ui.home.adapters.FilmWithRatingAdapter

class HomeFragment : Fragment() {

    //    private val homeViewModel: HomeViewModel by viewModels{}

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _comingSoonAdapter: ComingSoonAdapter? = null
    private val comingSoonAdapter get() = _comingSoonAdapter!!

    private var _inTheatersAdapter: FilmWithRatingAdapter? = null
    private val inTheatersAdapter get() = _inTheatersAdapter!!

    private var _topRatedAdapter: FilmWithRatingAdapter? = null
    private val topRatedAdapter get() = _topRatedAdapter!!

    private var _boxOfficeAdapter: BoxOfficeAdapter? = null
    private val boxOfficeAdapter get() = _boxOfficeAdapter!!

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
        initializeComingSoonAdapter()
        initializeInTheatersAdapter()
        initializeTopRatedAdapter()
        initializeBoxOfficeAdapter()
    }

    private fun initializeBoxOfficeAdapter() {
        _boxOfficeAdapter = BoxOfficeAdapter()
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
        _topRatedAdapter = FilmWithRatingAdapter()
        binding.topRatedRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = topRatedAdapter
        }
    }

    private fun initializeInTheatersAdapter() {
        _inTheatersAdapter = FilmWithRatingAdapter()
        binding.topRatedRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = inTheatersAdapter
        }
    }

    private fun initializeComingSoonAdapter() {
        _comingSoonAdapter = ComingSoonAdapter()
        binding.topRatedRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = comingSoonAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _topRatedAdapter = null
        _inTheatersAdapter = null
        _comingSoonAdapter = null
        _boxOfficeAdapter = null
        _binding = null
    }
}