package com.bsuir.drozd.app.screens.app.api


import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.drozd.app.dto.answer.CarAnswerDTO
import com.bsuir.drozd.R
import com.bsuir.drozd.databinding.ItemCarBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.Collections.emptyList


interface CarActionListener {
    fun onCarDetails(car: CarAnswerDTO)
    fun apply(car: CarAnswerDTO)
    fun send(car: CarAnswerDTO)
}

class CarDiffCallback(
    private val oldList: List<CarAnswerDTO>,
    private val newList: List<CarAnswerDTO>,
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCar = oldList[oldItemPosition]
        val newCar = newList[newItemPosition]
        return oldCar.id == newCar.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCar = oldList[oldItemPosition]
        val newCar = newList[newItemPosition]
        return oldCar == newCar
    }
}

class CarAdapter(
    private val actionListener: CarActionListener,
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>(), View.OnClickListener {

    class CarViewHolder(val binding: ItemCarBinding): RecyclerView.ViewHolder(binding.root)

    var cars: List<CarAnswerDTO> = emptyList()
        set(newValue) {
            val diffCallback = CarDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = car
            moreImageViewButton.tag = car
            brandTV.setText(car.brand)
            volumeCapacityTV.setText("Объем двигателя: ${car.volumeCapacity}")
            consumptionTV.setText("Расход: ${car.consumption} л/100 км")
            numberOfSeatsTV.setText("Кол-во мест: ${car.numberOfSeats}")
            priceTV.setText("Цена в сутки: ${car.price}$")
            Glide.with(carImageView.context)
                .load(car.image)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(carImageView)
        }
    }

    override fun getItemCount(): Int = cars.size

    override fun onClick(v: View) {
        val car = v.tag as CarAnswerDTO
        when (v.id){
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onCarDetails(car)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val car = v.tag as CarAnswerDTO
        if(car.id != Application.carId)
            popupMenu.menu.add(0, APPLY, Menu.NONE, context.getString(R.string.apply))
        if(car.id == Application.carId)
            popupMenu.menu.add(0, SEND, Menu.NONE, context.getString(R.string.send))
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                APPLY -> {
                    actionListener.apply(car)
                }
                SEND -> {
                    actionListener.send(car)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val APPLY = 1
        private const val SEND = 2

    }

}