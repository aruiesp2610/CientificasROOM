package com.example.busschedule.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.busschedule.ui.CientificaDetalleScreen
import com.example.busschedule.ui.CientificaViewModel
import com.example.busschedule.ui.FullCientificaScreen
import com.example.busschedule.ui.StartScreen

enum class CientificaScreens {
    FullCientifica,
    StartScreen,
    DetalleCientifica
}

@Composable
fun FuncionNavegacion( // NAVEGACION
    viewModel: CientificaViewModel = viewModel(factory = CientificaViewModel.factory)
) {
    val navController = rememberNavController()
    val fullSchedule by viewModel.getFullCientificas().collectAsState(emptyList())
    val onBackHandler = {
        navController.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = CientificaScreens.StartScreen.name
    ) {
        composable(CientificaScreens.StartScreen.name) {
            StartScreen(viewModel, navController)
        }

        composable(CientificaScreens.FullCientifica.name) {
            FullCientificaScreen(
                cientificas = fullSchedule,
                onCientificaClick = { cientificaName ->
                    navController.navigate(
                        "${CientificaScreens.DetalleCientifica.name}/$cientificaName"
                    )
                }
            )
        }

        val cientificaArgument = "nombre"
        composable(
            route = CientificaScreens.DetalleCientifica.name + "/{$cientificaArgument}",
            arguments = listOf(navArgument(cientificaArgument) { type = NavType.StringType })
        ) { backStackEntry ->
            val cientificaName = backStackEntry.arguments?.getString(cientificaArgument)
                ?: error("cientificaArgument no puede ser null")
            val routeSchedule by viewModel.getDetalleCientifica(cientificaName).collectAsState(emptyList())
            CientificaDetalleScreen(
                cientificaName = cientificaName,
                cientificas = routeSchedule,
                onBack = { onBackHandler() }
            )
        }
    }
}