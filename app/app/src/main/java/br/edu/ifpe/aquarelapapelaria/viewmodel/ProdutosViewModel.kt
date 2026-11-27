package br.edu.ifpe.aquarelapapelaria.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.aquarelapapelaria.R
import br.edu.ifpe.aquarelapapelaria.data.local.AppDatabase
import br.edu.ifpe.aquarelapapelaria.model.Produto
import br.edu.ifpe.aquarelapapelaria.ui.features.produtos.UiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProdutosViewModel(application: Application) : AndroidViewModel(application) {

    private val banco = AppDatabase.obterBanco(application)

    private val _uiState = MutableStateFlow<UiState>(UiState.Carregando)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var mensagem by mutableStateOf("")
        private set

    private var leituraJob: Job? = null

    init {
        carregar()
    }

    fun carregar() {
        leituraJob?.cancel()
        leituraJob = viewModelScope.launch {
            _uiState.value = UiState.Carregando
            try {
                banco.produtoDao().listarTodos().collect { lista ->
                    _uiState.value = if (lista.isEmpty()) {
                        UiState.Vazio
                    } else {
                        UiState.Sucesso(lista)
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = UiState.Erro(R.string.produtos_erro)
            }
        }
    }

    fun cadastrar(nome: String, marca: String, quantidadeStr: String, obs: String) {
        val qtd = quantidadeStr.toIntOrNull() ?: 0

        if (nome.isBlank() || marca.isBlank() || qtd <= 0) {
            mensagem = getApplication<Application>().getString(R.string.campos_invalidos)
            return
        }

        viewModelScope.launch {
            try {
                val produto = Produto(
                    nome = nome.trim(),
                    marca = marca.trim(),
                    quantidade = qtd,
                    observacoes = obs.trim()
                )
                banco.produtoDao().inserir(produto)
                mensagem = getApplication<Application>().getString(R.string.produtos_sucesso)
            } catch (e: Exception) {
                mensagem = getApplication<Application>().getString(R.string.produtos_falha)
            }
        }
    }
    
    fun limparMensagem() {
        mensagem = ""
    }
}
