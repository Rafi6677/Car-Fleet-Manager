package com.example.carfleetmanager.presentation.carlist

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.databinding.ItemCarBinding

class CarsAdapter : RecyclerView.Adapter<CarsAdapter.CarsViewHolder>() {

    private var onItemClickListener: ((Car) -> Unit)? = null

    private val callback = object: DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val binding = ItemCarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = differ.currentList[position]
        return holder.bind(car)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Car)->Unit) {
        onItemClickListener = listener
    }

    inner class CarsViewHolder(
        private val binding: ItemCarBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(car: Car) {
            binding.registrationNumberTextView.text = car.registration
            binding.brandTextView.text = car.brand
            binding.modelTextView.text = car.model

            ImageViewCompat.setImageTintList(
                    binding.colorImageView,
                    ColorStateList.valueOf(
                            Color.parseColor(car.color)
                    )
            )

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(car)
                }
            }
        }

    }

}