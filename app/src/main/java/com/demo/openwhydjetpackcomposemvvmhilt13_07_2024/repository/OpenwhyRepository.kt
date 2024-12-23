package com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.repository

import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.model.ElectroData
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpenwhyRepository @Inject constructor(private val apiService: ApiService) {

  //  suspend fun getApiData():Response<ElectroData> =apiService.getOpenwhydata()
   fun getApiData():Flow<ElectroData> = flow {
   val apiResult = apiService.getOpenwhydata()
   emit(apiResult)
  }.flowOn(Dispatchers.IO)
}