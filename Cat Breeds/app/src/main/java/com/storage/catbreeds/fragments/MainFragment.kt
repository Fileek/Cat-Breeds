package com.storage.catbreeds.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.storage.catbreeds.R
import com.storage.catbreeds.adapter.CatAdapter
import com.storage.catbreeds.adapter.CatListener
import com.storage.catbreeds.adapter.SwipeHelper
import com.storage.catbreeds.databinding.MainFragmentBinding
import com.storage.catbreeds.entity.Cat
import com.storage.catbreeds.extension.CATS_LIST_KEY
import com.storage.catbreeds.extension.CAT_KEY
import com.storage.catbreeds.main.CatsApplication
import com.storage.catbreeds.viewmodel.CatViewModel
import com.storage.catbreeds.viewmodel.CatViewModelFactory

class MainFragment : Fragment(R.layout.main_fragment), CatListener {

    private var catsList: List<Cat>? = null
    private val catViewModel: CatViewModel by viewModels {
        CatViewModelFactory((requireActivity().application as CatsApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = MainFragmentBinding.bind(view)

        val catAdapter = CatAdapter(this)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val useRoom = prefs.getBoolean(getString(R.string.room_checkbox_key), false)

        var sortColumn: String? = null
        if (prefs.getBoolean(getString(R.string.sort_switch_key), false))
            sortColumn = prefs.getString(getString(R.string.sort_selector_key), null)

        (requireActivity().application as CatsApplication).repository.apply {
            this.useRoom = useRoom
            this.sortColumn = sortColumn
        }

        catViewModel.allCats.observe(viewLifecycleOwner, { cats ->
            cats?.let {
                catsList = it
                catAdapter.submitList(it)
            }
        })

        binding.apply {
            databaseImplementationTextView.text = if (useRoom) "Room" else "Cursor"

            listItems.apply {
                this.layoutManager = LinearLayoutManager(context)
                this.adapter = catAdapter
                SwipeHelper(catAdapter, context) // add delete by swipe functionality
                    .attachToRecyclerView(this)
            }
            addButton.setOnClickListener {
                val action = MainFragmentDirections.addAction()
                findNavController().navigate(action)
            }
        }
    }

    override fun add(cat: Cat) {
        catViewModel.insert(cat)
    }

    override fun edit(cat: Cat) {
        val dialog = EditCatDialog()
        dialog.arguments = bundleOf(
            CAT_KEY to cat,
            CATS_LIST_KEY to catsList
        )
        dialog.show(requireActivity().supportFragmentManager, null)
    }

    override fun delete(cat: Cat) {
        catViewModel.delete(cat)
    }

    override fun onDetach() {
        super.onDetach()
        catsList = null
    }
}
