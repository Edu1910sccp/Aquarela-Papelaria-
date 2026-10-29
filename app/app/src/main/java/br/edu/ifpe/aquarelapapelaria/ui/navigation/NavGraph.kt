package br.edu.ifpe.aquarelapapelaria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifpe.aquarelapapelaria.ui.features.pedidos.PedidosScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavTarget.Pedidos.rota
    ) {
        composable(NavTarget.Pedidos.rota) {
            PedidosScreen()
        }
    }
}
