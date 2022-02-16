package com.storage.catbreeds.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.storage.catbreeds.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
