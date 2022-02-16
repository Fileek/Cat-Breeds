package com.storage.catbreeds.adapter

import androidx.recyclerview.widget.RecyclerView
import com.storage.catbreeds.databinding.CatItemBinding
import com.storage.catbreeds.entity.Cat

class CatViewHolder(
    private val binding: CatItemBinding,
    private val listener: CatListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cat: Cat) {

        val context = this@CatViewHolder.itemView.context

        binding.apply {
            breedTextView.text = cat.breed
            countryTextView.text = cat.country
            coatLengthTextView.text = context.getString(cat.coatLength.toStringResource())
            sizeTextView.text = context.getString(cat.size.toStringResource())

            editButton.setOnClickListener {
                listener.edit(cat)
            }
        }
    }
}
