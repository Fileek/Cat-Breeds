package com.storage.catbreeds.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.storage.catbreeds.R
import com.storage.catbreeds.databinding.AddCatFragmentBinding
import com.storage.catbreeds.entity.Cat
import com.storage.catbreeds.extension.getCatSize
import com.storage.catbreeds.extension.getCoatLength
import com.storage.catbreeds.extension.validate
import com.storage.catbreeds.viewmodel.CatViewModel

class AddCatFragment : Fragment(R.layout.add_cat_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = AddCatFragmentBinding.bind(view)
        var catsList: List<Cat> = emptyList()
        val catViewModel: CatViewModel by activityViewModels()

        catViewModel.allCats.observe(viewLifecycleOwner, { cats ->
            catsList = cats
        })

        binding.apply {

            breedEditText.requestFocus()

            addCatButton.setOnClickListener {

                if (countryEditText.validate() and breedEditText.validate()) {

                    val breed = breedEditText.text.toString()

                    if (catsList.any { it.breed == breed })
                        Toast.makeText(
                            context,
                            getString(R.string.cat_already_exists_toast),
                            Toast.LENGTH_LONG
                        ).show()
                    else {
                        val newCat = Cat(
                            breed,
                            countryEditText.text.toString(),
                            coatLengthSpinner.getCoatLength(),
                            sizeSpinner.getCatSize()
                        )
                        catViewModel.insert(newCat)
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }
}
