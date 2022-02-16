package com.storage.catbreeds.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.snackbar.Snackbar
import com.storage.catbreeds.R
import com.storage.catbreeds.databinding.CatItemBinding
import com.storage.catbreeds.entity.Cat

class CatAdapter(
    private val listener: CatListener,
) : ListAdapter<Cat, CatViewHolder>(itemComparator) {

    private var parentView: View? = null
    private var mRecentlyDeletedItem: Cat? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatItemBinding.inflate(layoutInflater, parent, false)
        parentView = parent
        return CatViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun deleteItem(position: Int) {
        getItem(position).let {
            mRecentlyDeletedItem = it
            listener.delete(it)
        }
        showUndoSnackbar()
    }

    private fun showUndoSnackbar() {
        parentView?.let {
            Snackbar.make(
                it, R.string.snack_bar_text,
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.snack_bar_undo_button) { undoDelete() }
                .show()
        }
    }

    private fun undoDelete() {
        mRecentlyDeletedItem?.let { listener.add(it) }
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: Cat, newItem: Cat) = Any()
        }
    }
}
