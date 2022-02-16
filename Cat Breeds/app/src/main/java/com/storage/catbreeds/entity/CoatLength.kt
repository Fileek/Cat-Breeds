package com.storage.catbreeds.entity

import com.storage.catbreeds.R

enum class CoatLength {
    Hairless, Short, Semi_Long, Long;

    fun toStringResource(): Int =
        when (this) {
            Hairless -> R.string.hairless_coat_length
            Short -> R.string.short_coat_length
            Semi_Long -> R.string.semi_long_coat_length
            Long -> R.string.long_coat_length
        }
}
