package com.storage.catbreeds.extension

import android.widget.EditText
import android.widget.Spinner
import com.storage.catbreeds.R
import com.storage.catbreeds.entity.CatSize
import com.storage.catbreeds.entity.CoatLength

fun String.toCatSize(): CatSize =
    when (this) {
        "Small" -> CatSize.Small
        "Medium" -> CatSize.Medium
        else -> CatSize.Large
    }

fun String.toCoatLength(): CoatLength =
    when (this) {
        "Hairless" -> CoatLength.Hairless
        "Short" -> CoatLength.Short
        "Semi_Long" -> CoatLength.Semi_Long
        else -> CoatLength.Long
    }

fun Spinner.getCatSize(): CatSize =
    when (this.selectedItemPosition) {
        0 -> CatSize.Small
        1 -> CatSize.Medium
        else -> CatSize.Large
    }

fun Spinner.getCoatLength(): CoatLength =
    when (this.selectedItemPosition) {
        0 -> CoatLength.Hairless
        1 -> CoatLength.Short
        2 -> CoatLength.Semi_Long
        else -> CoatLength.Long
    }

fun EditText.validate(): Boolean = this.run {
    if (text.toString() == "") {
        requestFocus()
        error = context.getString(R.string.blank_edit_text_error)
        false
    } else true
}
