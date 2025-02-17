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

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.busschedule.R
import com.example.busschedule.data.Cientifica



@Composable
fun StartScreen(viewModel: CientificaViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedButton(
            onClick = { viewModel.botonInicio(navController) },
            border = BorderStroke(4.dp, Color.Black),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "INICIAR",
                fontSize = 50.sp
            )
        }
    }
}

@Composable
fun FullCientificaScreen(
    cientificas: List<Cientifica>,
    onCientificaClick: (String) -> Unit,
) {
    CientificaScreen(
        cientificas = cientificas,
        onCientificaClick = onCientificaClick
    )
}

@Composable
fun CientificaScreen(
    cientificas: List<Cientifica>,
    cientificaName: String? = null,
    onCientificaClick: ((String) -> Unit)? = null
) {
    Column(
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 25.dp,
                    bottom = dimensionResource(R.dimen.padding_medium),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                )
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("MUJERES CIENTÍFICAS")
        }
        Divider()
        ListaCientificas(
            cientificas = cientificas,
            onScheduleClick = onCientificaClick
        )
    }
}

/*
 * Componible para ListaCientificas que muestra la lista de científicas
 */
@Composable
fun ListaCientificas( // LISTA DE CIENTIFICAS DENTRO DE LA SCREEN
    cientificas: List<Cientifica>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onScheduleClick: ((String) -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(
            items = cientificas,
            key = { busSchedule -> busSchedule.id }
        ) { schedule ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = onScheduleClick != null) {
                        onScheduleClick?.invoke(schedule.nombre)
                    }
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = schedule.nombre,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = dimensionResource(R.dimen.font_large).value.sp,
                        fontWeight = FontWeight(300)
                    )
                )
            }
        }
    }
}

@Composable
fun CientificaDetalleScreen( // Pantalla de detalle de cientifica
    cientificaName: String,
    cientificas: List<Cientifica>,
    onBack: () -> Unit = {}
) {
    BackHandler { onBack() }
    CientificaScreen(
        cientificas = cientificas,
        cientificaName = cientificaName
    )
}
