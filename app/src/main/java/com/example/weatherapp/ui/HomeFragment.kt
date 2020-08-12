package com.example.weatherapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.application
import com.example.weatherapp.data.domain.Weather
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.databinding.WeatherItemBinding
import com.example.weatherapp.viewmodels.HomeViewModel
import com.example.weatherapp.viewmodels.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    private val REQUEST_CODE_LOCATION_PERMISSION = 1

    @Inject
    lateinit var homeViewModelFactory: ViewModelFactory


    private val viewModel: HomeViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, homeViewModelFactory)
            .get(HomeViewModel::class.java)
    }

    private var viewModelAdapter: HomeAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.weather.observe(this, Observer { weather ->
            weather.apply {
                viewModelAdapter?.submitList(weather)
            }
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        requireContext().application.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        binding.setLifecycleOwner(viewLifecycleOwner)

        viewModelAdapter = HomeAdapter()

        binding.updateButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                viewModel.refreshLocationData()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(
                        requireContext(),
                        "Location permission is required to show weather data.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION_PERMISSION
                )
            }
        }


        binding.homeRecyclerView.apply {
            adapter = viewModelAdapter
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.refreshLocationData()
            } else {
                Toast.makeText(requireContext(), "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    class HomeAdapter() : ListAdapter<Weather, WeatherViewHolder>(WEATHER_COMPARATOR) {

        override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
            holder.viewDataBinding.weather = getItem(position)
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
            val viewDataBinding: WeatherItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                WeatherViewHolder.LAYOUT,
                parent,
                false
            )

            return WeatherViewHolder(viewDataBinding)
        }

        companion object {
            private val WEATHER_COMPARATOR = object : DiffUtil.ItemCallback<Weather>() {
                override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean =
                    oldItem.date == newItem.date

                override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean =
                    oldItem == newItem
            }
        }
    }

    class WeatherViewHolder(val viewDataBinding: WeatherItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.weather_item
        }
    }
}