package com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.utils

import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.model.ElectroData

sealed class ApiState {

    class Success(var data:ElectroData):ApiState()
    class Failure(var msg:Throwable):ApiState()
    object Empty:ApiState()
    object Loading:ApiState()
}