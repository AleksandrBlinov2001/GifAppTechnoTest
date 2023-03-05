package com.example.giff.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giff.DataObject
import com.example.giff.di.RetrofitProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GifListViewModel(
): ViewModel() {

    companion object {
        private const val defaultQuery = "popular"
    }

    private val api = RetrofitProvider.provideGiphyApi()
    val liveData = MutableLiveData<List<DataObject>>()

    fun fetchGifs(query: String = defaultQuery) {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        }

        val coroutineContext: CoroutineContext = Dispatchers.IO + exceptionHandler

        viewModelScope.launch(coroutineContext) {
            liveData.postValue(
                api.getGifs(query).res
            )
        }
    }
}