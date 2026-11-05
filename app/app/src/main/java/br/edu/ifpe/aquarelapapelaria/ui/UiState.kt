package br.edu.ifpe.aquarelapapelaria.ui

import androidx.annotation.StringRes
import br.edu.ifpe.aquarelapapelaria.model.Pedido

// Fronteira: representa os quatro estados da tela.
// Separa os resultados da consulta da apresentação.
// Sem ela, a tela perde o contrato dos quatro estados.
sealed interface UiState {

    data object Carregando : UiState

    data object Vazio : UiState

    data class Erro(
        @StringRes val mensagemRes: Int
    ) : UiState

    data class Sucesso(
        val itens: List<Pedido>
    ) : UiState
}
