package com.bsuir.drozd.app.screens.app.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.drozd.app.views.ApiViewModel
import com.bsuir.drozd.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ApiViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        val role: String = viewModel.getRole()
        val pager = binding.home
        val pageAdapter: FragmentStateAdapter = HomeAdapter(requireActivity(), role)
        pager.adapter = pageAdapter

        val tabLayout: TabLayout = binding.tabHome
        val tabLayoutMediator = TabLayoutMediator(tabLayout, pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Аренда"
                }
                1 -> {
                    tab.text = "Заявки"
                }
                2 -> {
                    tab.text = "Админка"
                }
            }
        }
        tabLayoutMediator.attach()
        return binding.root
    }

}