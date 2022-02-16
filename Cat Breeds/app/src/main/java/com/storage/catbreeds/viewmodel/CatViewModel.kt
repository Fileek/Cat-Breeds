package com.storage.catbreeds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.storage.catbreeds.data.CatRepository
import com.storage.catbreeds.entity.Cat
import kotlinx.coroutines.launch

class CatViewModel(private val repository: CatRepository) : ViewModel() {

    val allCats: LiveData<List<Cat>> get() = repository.allCats.asLiveData()

    fun insert(cat: Cat) = viewModelScope.launch { repository.insert(cat) }

    fun update(cat: Cat) = viewModelScope.launch { repository.update(cat) }

    fun delete(cat: Cat) = viewModelScope.launch { repository.delete(cat) }
}
