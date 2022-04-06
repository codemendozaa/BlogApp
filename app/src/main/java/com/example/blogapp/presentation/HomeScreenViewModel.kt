package com.example.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.blogapp.core.Resource
import com.example.blogapp.domain.HomeScreenRepo
import kotlinx.coroutines.Dispatchers


class HomeScreenViewModel(private val repo:HomeScreenRepo):ViewModel() {

    fun fetchLatesPost() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getLatesPost())

        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class HomeScreenViewModelFactory(private val repo:HomeScreenRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }

}