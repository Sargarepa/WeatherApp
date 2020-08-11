package com.example.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.domain.Weather
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.databinding.WeatherItemBinding
import com.example.weatherapp.viewmodels.HomeViewModel
import com.example.weatherapp.viewmodels.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeViewmodelFactory: ViewModelFactory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, homeViewmodelFactory)
            .get(HomeViewModel::class.java)
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


        return binding.root
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