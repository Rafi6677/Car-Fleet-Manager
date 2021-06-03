package com.example.carfleetmanager.presentation.addnewcar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.carfleetmanager.data.model.Owner
import com.example.carfleetmanager.databinding.ItemOwnerBinding

class OwnersAdapter : RecyclerView.Adapter<OwnersAdapter.OwnersViewHolder>() {

    private var onItemClickListener: ((Owner) -> Unit)? = null

    private val callback = object: DiffUtil.ItemCallback<Owner>() {
        override fun areItemsTheSame(oldItem: Owner, newItem: Owner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Owner, newItem: Owner): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    fun setOnItemClickListener(clickListener: (Owner) -> Unit) {
        onItemClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnersViewHolder {
        val binding = ItemOwnerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return OwnersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OwnersViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class OwnersViewHolder(
            private val binding: ItemOwnerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(owner: Owner) {
            binding.firstNameValueTextView.text = owner.firstName
            binding.lastNameValueTextView.text = owner.lastName
            binding.birthDateValueTextView.text = owner.birthDate.substringBefore("T")

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(owner)
                }
            }
        }

    }

}