package com.example.androidtutorial

import androidx.lifecycle.*
import kotlinx.coroutines.launch


//The ViewModel's role is to provide data to the UI and survive configuration changes.
//A ViewModel acts as a communication center between the Repository and the UI.
// You can also use a ViewModel to share data between fragments.


class WordViewModel(private val repository: WordRepository) : ViewModel()
{

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(WordViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}