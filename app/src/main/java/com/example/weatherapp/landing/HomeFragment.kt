package com.example.weatherapp.landing


import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.utils.AppConstants
import com.example.weatherapp.utils.showToast
import java.security.Permission


class HomeFragment : Fragment() {
private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.view=this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    fun onClick(view:View){
        when(view.id){

            R.id.btnCurrentCity->view.findNavController().navigate(R.id.action_homeFragment_to_currentCityTempFragment)

            R.id.btnSearchCities-> view.findNavController().navigate(R.id.action_homeFragment_to_currentTempFragment)

        }
    }











    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
