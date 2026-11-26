package br.edu.ifpe.aquarelapapelaria.ui.features.clientes

import androidx.annotation.StringRes
import br.edu.ifpe.aquarelapapelaria.model.Cliente

// Fronteira: representa os quatro estados da tela de Clientes.
sealed interface ClienteUiState {
    data object Carregando : ClienteUiState
    data object Vazio : ClienteUiState
    data class Erro(@StringRes val mensagemRes: Int) : ClienteUiState
    data class Sucesso(val clientes: List<Cliente>) : ClienteUiState
}
