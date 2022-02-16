package com.storage.catbreeds.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.storage.catbreeds.R
import com.storage.catbreeds.databinding.EditCatDialogBinding
import com.storage.catbreeds.entity.Cat
import com.storage.catbreeds.entity.CatSize
import com.storage.catbreeds.entity.CoatLength
import com.storage.catbreeds.extension.CATS_LIST_KEY
import com.storage.catbreeds.extension.CAT_KEY
import com.storage.catbreeds.extension.getCatSize
import com.storage.catbreeds.extension.getCoatLength
import com.storage.catbreeds.extension.validate
import com.storage.catbreeds.viewmodel.CatViewModel

class EditCatDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val catViewModel: CatViewModel by activityViewModels()
        val binding = EditCatDialogBinding.inflate(LayoutInflater.from(context))
        val editCat = arguments?.get(CAT_KEY) as Cat

        binding.apply {

            breedEditText.setText(editCat.breed)
            countryEditText.setText(editCat.country)
            coatLengthSpinner.setSelection(
                when (editCat.coatLength) {
                    CoatLength.Hairless -> 0
                    CoatLength.Short -> 1
                    CoatLength.Semi_Long -> 2
                    CoatLength.Long -> 3
                }
            )
            sizeSpinner.setSelection(
                when (editCat.size) {
                    CatSize.Small -> 0
                    CatSize.Medium -> 1
                    CatSize.Large -> 2
                }
            )

            saveButton.setOnClickListener {
                if (countryEditText.validate() and breedEditText.validate()) {

                    val newCat = composeCat(binding, editCat.id)
                    @Suppress("Unchecked_cast")
                    val catsList = arguments?.get(CATS_LIST_KEY) as List<Cat>

                    if ((editCat.breed != newCat.breed && !catsList.any { it.breed == newCat.breed }) ||
                        (editCat.breed == newCat.breed && editCat != newCat)
                    ) {
                        catViewModel.update(newCat)
                        dismiss()
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.cat_already_exists_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun composeCat(binding: EditCatDialogBinding, catId: Int): Cat {
        return binding.run {
            Cat(
                breedEditText.text.toString(),
                countryEditText.text.toString(),
                coatLengthSpinner.getCoatLength(),
                sizeSpinner.getCatSize(),
                catId
            )
        }
    }
}
