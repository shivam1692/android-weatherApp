package com.example.weatherapp.currenttemp.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider

import com.example.weatherapp.R
import com.example.weatherapp.base.MyApplication
import com.example.weatherapp.base.WeatherRepo
import com.example.weatherapp.currentcitytemp.repository.Repository
import com.example.weatherapp.currentcitytemp.viewmodel.ViewModelFactory
import com.example.weatherapp.currenttemp.adapter.CurrentTempAdapter
import com.example.weatherapp.currenttemp.viewmodel.CurrentTempViewModel
import com.example.weatherapp.databinding.CurrentTempFragmentBinding
import com.example.weatherapp.network.ApiClient
import com.example.weatherapp.utils.AppConstants
import com.example.weatherapp.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class CurrentTempFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentTempFragment()
    }


    @Inject
    lateinit var respository:WeatherRepo

    private lateinit var viewModel: CurrentTempViewModel
    private lateinit var binding:CurrentTempFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CurrentTempFragmentBinding.inflate(inflater,container,false)
        binding.view=this
        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyApplication.get(context).appComponent.inject(this)
        viewModel = ViewModelProvider(this,ViewModelFactory(respository,MyApplication.get(context))).get(CurrentTempViewModel::class.java)
        binding.viewModel =viewModel
        setDataToBindingLayout()
        setupRecyclerView()

    }

    private fun setupRecyclerView(){
        with(binding){
            rvCurrentTemp.adapter=CurrentTempAdapter()
        }
    }

    private fun setDataToBindingLayout(){
        with(binding){
            edtCity.setOnEditorActionListener(object:TextView.OnEditorActionListener{
                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                    if(actionId==EditorInfo.IME_ACTION_SEARCH){
                            validateView()
                        return true
                    }
                    return false
                }

            })
        }
    }



    fun onClick(view:View){
        when(view.id){
            R.id.ivSearchIcon-> validateView()
        }
    }

    /**
     * Validate the data entered for search. Hide soft keyboard once the user tap on search
     *
     */
   private fun validateView(){
       with(binding){
           inputLayout.error = this@CurrentTempFragment.viewModel.validate(edtCity.text.toString()).also {
               if(it.isEmpty()){
                   ivSearchIcon.hideKeyboard()
                   this@CurrentTempFragment.viewModel.showLoading()
                   this@CurrentTempFragment.viewModel.getCurrentTemperature()

               }
           }
       }

    }





}
