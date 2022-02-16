package com.storage.catbreeds.adapter

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper

class SwipeHelper(mAdapter: CatAdapter, context: Context) :
    ItemTouchHelper(SwipeCallback(mAdapter, context))
