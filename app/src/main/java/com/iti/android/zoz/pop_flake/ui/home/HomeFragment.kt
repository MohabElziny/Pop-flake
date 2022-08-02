package com.iti.android.zoz.pop_flake.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.android.zoz.pop_flake.databinding.FragmentHomeBinding
import com.iti.android.zoz.pop_flake.pojos.BoxOfficeMovie
import com.iti.android.zoz.pop_flake.pojos.Movie
import com.iti.android.zoz.pop_flake.pojos.TopMovie
import com.iti.android.zoz.pop_flake.ui.home.adapters.BoxOfficeAdapter
import com.iti.android.zoz.pop_flake.ui.home.adapters.ComingSoonAdapter
import com.iti.android.zoz.pop_flake.ui.home.adapters.InTheaterAdapter
import com.iti.android.zoz.pop_flake.ui.home.adapters.TopRatedAdapter

class HomeFragment : Fragment() {

    //    private val homeViewModel: HomeViewModel by viewModels{}

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

        comingSoonAdapter.setComingSoonMoviesList(comingList)
        inTheatersAdapter.setInTheaterMoviesList(inThList)
        topRatedAdapter.setTopRatedMoviesList(topList)
        boxOfficeAdapter.setBoxOfficeMoviesList(boxList)
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
        _topRatedAdapter = TopRatedAdapter()
        binding.topRatedRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = topRatedAdapter
        }
    }

    private fun initializeInTheatersAdapter() {
        _inTheatersAdapter = InTheaterAdapter()
        binding.inTheatersRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = inTheatersAdapter
        }
    }

    private fun initializeComingSoonAdapter() {
        _comingSoonAdapter = ComingSoonAdapter()
        binding.comingSoonRecyclerview.apply {
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

    private val comingList: List<Movie> = listOf(
        Movie(
            "tt12593682",
            "Bullet Train",
            "2022",
            "05 August 2022",
            "https://imdb-api.com/images/128x176/nopicture.jpg"
        ),
        Movie(
            "tt8110652",
            "Bodies Bodies Bodies",
            "2022",
            "05 August 2022",
            "https://imdb-api.com/images/128x176/nopicture.jpg"
        ),
        Movie(
            "tt11952606",
            "Easter Sunday",
            "2022",
            "05 August 2022",
            "https://imdb-api.com/images/128x176/nopicture.jpg"
        )
    )

    private val inThList: List<Movie> = listOf(
        Movie(
            "tt10954984",
            "Nope",
            "2022",
            "22 Jul 2022",
            "https://m.media-amazon.com/images/M/MV5BMGIyNTI3NWItNTJkOS00MGYyLWE4NjgtZDhjMWQ4Y2JkZTU5XkEyXkFqcGdeQXVyNjY1MTg4Mzc@._V1_UX128_CR0,12,128,176_AL_.jpg",
            "7.5"
        ),
        Movie(
            "tt10648342",
            "Thor: Love and Thunder",
            "2022",
            "08 Jul 2022",
            "https://m.media-amazon.com/images/M/MV5BYmMxZWRiMTgtZjM0Ny00NDQxLWIxYWQtZDdlNDNkOTEzYTdlXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UX128_CR0,12,128,176_AL_.jpg",
            "6.8"
        ),
        Movie(
            "tt7144666",
            "The Black Phone",
            "2021",
            "24 Jun 2022",
            "https://m.media-amazon.com/images/M/MV5BOWVmNTBiYTUtZWQ3Yi00ZDlhLTgyYjUtNzBhZjM3YjRiNGRkXkEyXkFqcGdeQXVyNzYyOTM1ODI@._V1_UX128_CR0,12,128,176_AL_.jpg",
            "7"
        )
    )

    private val topList: List<TopMovie> = listOf(
        TopMovie(
            "tt0111161",
            "1",
            "The Shawshank Redemption",
            "1994",
            "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_Ratio0.6716_AL_.jpg",
            "9.2"
        ),
        TopMovie(
            "tt0068646",
            "2",
            "The Godfather",
            "1972",
            "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_Ratio0.7015_AL_.jpg",
            "9.2"
        ),
        TopMovie(
            "tt0468569",
            "3",
            "The Dark Knight",
            "2008",
            "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_Ratio0.6716_AL_.jpg",
            "9.0"
        )
    )

    private val boxList: List<BoxOfficeMovie> = listOf(
        BoxOfficeMovie(
            "tt8912936",
            "1",
            "DC League of Super-Pets",
            "https://m.media-amazon.com/images/M/MV5BNjA5ZDBlMDMtZTM3Zi00MGEwLWJkZmMtMGEyOGRmMTA2NWQ3XkEyXkFqcGdeQXVyMTUzOTcyODA5._V1_UX128_CR0,3,128,176_AL_.jpg",
            "$23.0M",
            "$23.0M",
            "1"
        ),
        BoxOfficeMovie(
            "tt10954984",
            "2",
            "Nope",
            "https://m.media-amazon.com/images/M/MV5BMGIyNTI3NWItNTJkOS00MGYyLWE4NjgtZDhjMWQ4Y2JkZTU5XkEyXkFqcGdeQXVyNjY1MTg4Mzc@._V1_UX128_CR0,3,128,176_AL_.jpg",
            "$18.5M",
            "$80.6M",
            "2"
        ),
        BoxOfficeMovie(
            "tt10648342",
            "3",
            "Thor: Love and Thunder",
            "https://m.media-amazon.com/images/M/MV5BYmMxZWRiMTgtZjM0Ny00NDQxLWIxYWQtZDdlNDNkOTEzYTdlXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UX128_CR0,3,128,176_AL_.jpg",
            "$13.1M",
            "$301.5M",
            "4"
        )
    )
}