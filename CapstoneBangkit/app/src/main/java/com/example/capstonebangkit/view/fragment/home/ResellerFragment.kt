package com.example.capstonebangkit.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonebangkit.R
import com.example.capstonebangkit.adapter.ListResellerAdapter
import com.example.capstonebangkit.databinding.FragmentResellerBinding
import com.example.capstonebangkit.model.Reseller
import com.example.capstonebangkit.utils.DataCallbackReseller
import com.example.capstonebangkit.viewmodel.ResellerViewModel
import com.example.capstonebangkit.viewmodel.ViewModelFactory


class ResellerFragment : Fragment(),DataCallbackReseller {


    private var _binding: FragmentResellerBinding? = null
    private val binding get() = _binding!!
    private lateinit var listResellerAdapter: ListResellerAdapter
    private lateinit var viewModel : ResellerViewModel
    private lateinit var viewModelFactory: ViewModelFactory



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResellerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResellerViewModel::class.java)
        if (activity != null) {
            requireActivity().onBackPressedDispatcher.addCallback(object :
                OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_resellerFragment_to_homeFragment)
                }
            })
            binding.imgBack.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_resellerFragment_to_homeFragment)
            }
            listResellerAdapter = ListResellerAdapter(this)
            init()


            val input = binding.edtInput.text
            binding.imgSearch.setOnClickListener {
                if (input.isNotEmpty()){
                    binding.imgEmpty.visibility = View.INVISIBLE
                    binding.spinKit.visibility = View.VISIBLE
                    observe()
                }
            }

        }
    }
    private fun observe(){
        viewModel.getReseller().observe(viewLifecycleOwner, Observer {

            binding.spinKit.visibility = View.GONE
            listResellerAdapter.setData(it)
            binding.rvReseller.visibility = View.VISIBLE
        })
    }

    private fun init (){
        with(binding.rvReseller) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = listResellerAdapter

        }
    }



    override fun onCallback(reseller: Reseller) {
        val extraData = ResellerFragmentDirections.actionResellerFragmentToDetailFragmentReseller(reseller)
        view?.findNavController()?.navigate(extraData)
    }


}