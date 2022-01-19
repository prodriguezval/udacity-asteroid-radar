package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.infrastructure.database.SpaceDatabase
import com.udacity.asteroidradar.infrastructure.repository.NasaRepository

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val spaceDatabase = SpaceDatabase.getInstance(application)
        val nasaRepository = NasaRepository(spaceDatabase.asteroidDao)
        val mainViewModelFactory = MainViewModelFactory(nasaRepository, application)

        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        binding.viewModel = viewModel
        val asteroidAdapter = AsteroidAdapter(AsteroidListener { asteroid ->
            viewModel.onAsteroidClick(asteroid)
        })
        binding.asteroidRecycler.adapter = asteroidAdapter
        viewModel.asteroids.observe(viewLifecycleOwner, { asteroids ->
            if (asteroids != null) {
                asteroidAdapter.submitList(asteroids)
            }
        })
        viewModel.navigateToAsteroidDetailFragment.observe(viewLifecycleOwner, { asteroid ->
            asteroid?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onDetailFragmentNavigated()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_asteroids_menu -> viewModel.onClickViewWeekAsteroids()
            R.id.show_today_asteroids_menu -> viewModel.onClickTodayAsteroids()
            R.id.show_saved_asteroid_menu -> viewModel.onClickSavedAsteroids()
        }
        return true
    }
}
