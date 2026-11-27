package br.edu.ifpe.aquarelapapelaria.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.edu.ifpe.aquarelapapelaria.R
import br.edu.ifpe.aquarelapapelaria.ui.features.clientes.ClientesScreen
import br.edu.ifpe.aquarelapapelaria.ui.features.pedidos.PedidosScreen
import br.edu.ifpe.aquarelapapelaria.ui.features.produtos.ProdutosScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    val itens = listOf(
        NavTarget.Pedidos,
        NavTarget.Clientes,
        NavTarget.Produtos
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                itens.forEach { tela ->
                    val selecionado = currentDestination?.hierarchy?.any { it.route == tela.rota } == true

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = when (tela) {
                                    NavTarget.Pedidos -> Icons.Default.List
                                    NavTarget.Clientes -> Icons.Default.Person
                                    NavTarget.Produtos -> Icons.Default.ShoppingCart
                                },
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                stringResource(
                                    when (tela) {
                                        NavTarget.Pedidos -> R.string.nav_pedidos
                                        NavTarget.Clientes -> R.string.nav_clientes
                                        NavTarget.Produtos -> R.string.nav_estoque
                                    }
                                )
                            )
                        },
                        selected = selecionado,
                        onClick = {
                            navController.navigate(tela.rota) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavTarget.Pedidos.rota,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavTarget.Pedidos.rota) {
                PedidosScreen()
            }
            composable(NavTarget.Clientes.rota) {
                ClientesScreen()
            }
            composable(NavTarget.Produtos.rota) {
                ProdutosScreen()
            }
        }
    }
}
