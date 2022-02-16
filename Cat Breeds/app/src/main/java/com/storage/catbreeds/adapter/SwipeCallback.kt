package com.storage.catbreeds.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.storage.catbreeds.R

class SwipeCallback(adapter: CatAdapter, context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val mAdapter: CatAdapter = adapter
    private val icon: Drawable =
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_48)!!
    private val redBackground: ColorDrawable = ColorDrawable(Color.RED)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        mAdapter.deleteItem(position)
        redBackground.setBounds(0, 0, 0, 0)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        with(viewHolder.itemView) {
            val iconMargin = (height - icon.intrinsicHeight) / 2
            val iconTop = top + (height - icon.intrinsicHeight) / 2
            val iconBottom = iconTop + icon.intrinsicHeight

            when {
                dX > 0 -> { // Swiping to the right
                    val iconLeft = left + iconMargin
                    val iconRight = left + iconMargin + icon.intrinsicWidth
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    redBackground.setBounds(left, top, left + dX.toInt(), bottom)
                }
                dX < 0 -> { // Swiping to the left
                    val iconLeft = right - iconMargin - icon.intrinsicWidth
                    val iconRight = right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    redBackground.setBounds(right + dX.toInt(), top, right, bottom)
                }
                else ->
                    redBackground.setBounds(0, 0, 0, 0) // view is unSwiped
            }
            redBackground.draw(c)
            icon.draw(c)
        }
    }
}
