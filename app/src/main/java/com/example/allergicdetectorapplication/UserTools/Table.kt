package com.example.allergicdetectorapplication.UserTools

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allergicdetectorapplication.Account.MainActivity
import com.example.allergicdetectorapplication.Account.SignUp
import com.example.allergicdetectorapplication.R
import com.example.allergicdetectorapplication.databinding.FragmentTableBinding
import com.google.firebase.auth.FirebaseAuth

class Table : Fragment() {
    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!
    private val recyclerAdapter = RecyclerAdapter()
    private lateinit var clnButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTableBinding.inflate(inflater, container, false)



        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        initRecycler()
        initViewPager()
        initListeners()
    }

    private fun initViewPager() {
        val pagerAdapter = ViewPagerAdapter()
        binding.viewPager.adapter = pagerAdapter
        pagerAdapter.setNewItems(periods)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initListeners() {
        with(binding) {

            region1text.setOnClickListener {
                turnOffAllRegions()
                turnOnRegion(region1text)
            }
            region2text.setOnClickListener {
                turnOffAllRegions()
                turnOnRegion(region2text)
            }
            region3text.setOnClickListener {
                turnOffAllRegions()
                turnOnRegion(region3text)
            }
            region4text.setOnClickListener {
                turnOffAllRegions()
                turnOnRegion(region4text)
            }
        }
    }

    private fun turnOnRegion(region: TextView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            region.setBackgroundColor(requireContext().getColor(R.color.button_color))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun turnOffAllRegions() {
        with(binding) {
            region1text.setBackgroundColor(requireContext().getColor(R.color.white))
            region2text.setBackgroundColor(requireContext().getColor(R.color.white))
            region3text.setBackgroundColor(requireContext().getColor(R.color.white))
            region4text.setBackgroundColor(requireContext().getColor(R.color.white))
        }
    }

    private fun initRecycler() {
        binding.pylkiRecycler.layoutManager = LinearLayoutManager(context)
        binding.pylkiRecycler.adapter = recyclerAdapter
        recyclerAdapter.setNewItems(pylki.filter { it.isUsed })
    }

    companion object {
        val pylki = listOf(
            Pylek("Leszczyna",2, isUsed = true),
            Pylek("Brzoza", 3, isUsed = true),
            Pylek("Szczaw", 2, isUsed = true),
            Pylek("Pokrzywa", 1, isUsed = true),
            Pylek("Cladosporium", 2, isUsed = true)
        )
        val periods = listOf(
            "styczen",
            "luty",
            "marzec",
            "kwiecien",
            "maj",
            "czerwiec",
            "lipiec",
            "sierpien",
            "wrzesien",
            "pazdziernik",
            "listopad",
            "grudzien"
        )
    }
}