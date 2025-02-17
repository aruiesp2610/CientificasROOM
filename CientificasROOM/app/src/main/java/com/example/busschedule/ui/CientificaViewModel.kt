/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.busschedule.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.busschedule.CientificaApplication
import com.example.busschedule.data.Cientifica
import com.example.busschedule.data.CientificaDao
import com.example.busschedule.navigation.CientificaScreens
import kotlinx.coroutines.flow.Flow

/*
 * ViewModel para listado de cientificas.
 * contiene métodos para acceder a Room DB a través de [cientificaDao]
 */
class CientificaViewModel(private val cientificaDao: CientificaDao): ViewModel() {

    // Obtiene el horario completo de autobuses desde Room DB
    fun getFullCientificas(): Flow<List<Cientifica>> = cientificaDao.getAll()

    // Obtiene el horario del autobús según el nombre de la parada de Room DB
    fun getDetalleCientifica(cientificaName: String): Flow<List<Cientifica>> =
        cientificaDao.getByCientificaName(cientificaName)

    fun botonInicio(navController : NavController){
        navController.navigate(CientificaScreens.FullCientifica.name)
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CientificaApplication)
                CientificaViewModel(application.database.busScheduleDao())
            }
        }
    }
}
