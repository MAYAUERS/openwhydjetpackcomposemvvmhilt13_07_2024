package com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.network

import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.model.ElectroData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    //https://openwhyd.org/hot/electro?format=json
    @GET("hot/electro?format=json")
   // suspend fun getOpenwhydata():Response<ElectroData>
    suspend fun getOpenwhydata():ElectroData
}