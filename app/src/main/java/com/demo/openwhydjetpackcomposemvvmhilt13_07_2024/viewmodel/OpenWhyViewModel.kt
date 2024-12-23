package com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.model.ElectroData
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.model.Tracks
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.repository.OpenwhyRepository
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenWhyViewModel @Inject constructor(private val repository: OpenwhyRepository):ViewModel() {
   /* val electoData:LiveData<List<Tracks>> get() = _electoData
    private var _electoData = MutableLiveData<List<Tracks>>()

    val error:LiveData<String> get() = _error
    private var _error = MutableLiveData<String>()

    fun getData(){
        viewModelScope.launch {
            val response = repository.getApiData()
            if (response.isSuccessful){
                _electoData.value = response.body()?.tracks
            }else{
                _error.value = response.errorBody().toString()
            }
        }
    }*/


  /*  val electoData:LiveData<ApiState> get() = _electoData
    private var _electoData = MutableLiveData<ApiState>()

    val error:LiveData<String> get() = _error
    private var _error = MutableLiveData<String>()

    fun getData(){
        viewModelScope.launch {
            repository.getApiData().onStart {
                _electoData.value = ApiState.Loading
            }.catch {
                _electoData.value = ApiState.Failure(it)
            }.collect{
                _electoData.value = ApiState.Success(it)
            }
        }
    }*/

   /* val electoData:LiveData<ApiState> = repository.getApiData().map {
        ApiState.Success(it)
    }.onStart {
        Log.d("Meera","Flow is onStart")
    }.onCompletion {
        Log.d("Meera","Flow is onCompletion")
    }.asLiveData()*/

    val electoDataflow: Flow<ApiState> = repository.getApiData().map {
        ApiState.Success(it)
    }.onCompletion {
        Log.d("Meera","Flow is completed")
    }.stateIn(initialValue = ApiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000))
}