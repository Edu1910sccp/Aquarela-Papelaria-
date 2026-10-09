package br.edu.ifpe.aquarelapapelaria.ui.navigation

// Fronteira: grafo de navegação. Cada nova tela é registrada aqui com um composable(...).

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifpe.aquarelapapelaria.ui.features.pedidos.PedidosPlaceholderScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavTarget.Pedidos.rota
    ) {
        composable(NavTarget.Pedidos.rota) {
            PedidosPlaceholderScreen()
        }
    }
}
