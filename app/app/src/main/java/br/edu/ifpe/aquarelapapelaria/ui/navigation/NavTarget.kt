package br.edu.ifpe.aquarelapapelaria.ui.navigation

// Fronteira: lista de rotas (telas) do app. Por enquanto só a tela inicial;
// Estoque e formulários entram nos commits de cada integrante.

sealed class NavTarget(val rota: String) {
    data object Pedidos : NavTarget("pedidos")
}
