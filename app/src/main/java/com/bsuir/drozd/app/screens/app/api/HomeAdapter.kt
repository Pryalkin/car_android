package com.bsuir.drozd.app.screens.app.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.drozd.app.dto.utils.Role

class HomeAdapter (fragmentActivity: FragmentActivity, var role: String?) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        var count = when (role) {
            Role.ROLE_ADMIN.name -> {
                3
            }
            Role.ROLE_USER.name -> {
                2
            }
            else -> {0}
        }
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return HomeForViewingPage2Fragment.newInstance(position)
    }
}