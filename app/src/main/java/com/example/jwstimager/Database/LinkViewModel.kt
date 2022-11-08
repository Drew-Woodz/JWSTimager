package com.example.jwstimager.Database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class LinkViewModel(private val repository: LinkRepository) : ViewModel() {

    // Using LiveData and caching what allLinks returns has several benefits :
    // - We can put an observer on the data (instead of polling for changes) and only update
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allLinks: LiveData<List<Link>> = repository.allLinks.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(link: Link) = viewModelScope.launch {
        repository.insert(link)
    }
}

class LinkViewModelFactory(private val repository: LinkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LinkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LinkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}