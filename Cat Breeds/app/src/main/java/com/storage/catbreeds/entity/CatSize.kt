package com.storage.catbreeds.entity

import com.storage.catbreeds.R

enum class CatSize {
    Small, Medium, Large;

    fun toStringResource(): Int =
        when (this) {
            Small -> R.string.small_size
            Medium -> R.string.medium_size
            Large -> R.string.large_size
        }
}
