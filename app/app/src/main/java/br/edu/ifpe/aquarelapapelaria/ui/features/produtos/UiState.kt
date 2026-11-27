package br.edu.ifpe.aquarelapapelaria.ui.features.produtos

import androidx.annotation.StringRes
import br.edu.ifpe.aquarelapapelaria.model.Produto

sealed interface UiState {
    data object Carregando : UiState
    data object Vazio : UiState
    data class Erro(@StringRes val mensagemRes: Int) : UiState
    data class Sucesso(val produtos: List<Produto>) : UiState
}
