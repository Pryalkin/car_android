package com.bsuir.drozd.app.screens.app.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bsuir.drozd.R
import com.bsuir.drozd.app.dto.answer.CarAnswerDTO
import com.bsuir.drozd.app.views.ApiViewModel
import com.bsuir.drozd.databinding.FragmentDetailCarBinding
import com.bumptech.glide.Glide

class DetailCarFragment  : Fragment() {

    lateinit var binding: FragmentDetailCarBinding
    private val viewModel by viewModels<ApiViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailCarBinding.inflate(inflater)
        val id: Long = requireArguments().getString(ARG_CAR_ID)!!.toLong()
        viewModel.getCar(id)
        observeMovie()
        return binding.root
    }

    private fun observeMovie() = viewModel.car.observe(viewLifecycleOwner) {
        binding.apply {
            tvCategory.text = "Категория: ${it.category}"
            tvBrand.text = "Марка: ${it.brand}"
            tvEngineCapacity.text = "Мощность двигателя: ${it.engineCapacity} л.с."
            tvVolumeCapacity.text = "Объем двигатеоя: ${it.volumeCapacity}"
            tvMachineDrive.text = "Привод: ${it.machineDrive}"
            tvColor.text = "Цвет: ${it.color}"
            tvConsumption.text = "Расход: ${it.consumption} л/100 км"
            tvNumberOfSeats.text = "Кол-во мест: ${it.numberOfSeats}"
            tvPrice.text = "Цена: ${it.price}$ в сутки"
            Glide.with(imageView.context)
                .load(it.image)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(imageView)
        }
    }

    companion object {
        private const val ARG_CAR_ID = "ARG_CAR_ID"
        fun newInstance(car: CarAnswerDTO): DetailCarFragment {
            val fragment = DetailCarFragment()
            fragment.arguments = bundleOf(ARG_CAR_ID to car.id.toString())
            return fragment
        }
    }
}