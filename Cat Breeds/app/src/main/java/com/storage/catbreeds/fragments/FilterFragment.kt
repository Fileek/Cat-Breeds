package com.storage.catbreeds.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.storage.catbreeds.R

class FilterFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.filter_preferences, rootKey)
    }
}
