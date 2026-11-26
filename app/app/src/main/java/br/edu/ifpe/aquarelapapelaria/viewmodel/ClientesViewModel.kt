package br.edu.ifpe.aquarelapapelaria.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.aquarelapapelaria.R
import br.edu.ifpe.aquarelapapelaria.data.local.AppDatabase
import br.edu.ifpe.aquarelapapelaria.ui.features.clientes.ClienteUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientesViewModel(application: Application) : AndroidViewModel(application) {

    private val banco = AppDatabase.obterBanco(application)

    private val _uiState = MutableStateFlow<ClienteUiState>(ClienteUiState.Carregando)
    val uiState: StateFlow<ClienteUiState> = _uiState.asStateFlow()

    private var leituraJob: Job? = null

    init {
        carregar()
    }

    fun carregar() {
        leituraJob?.cancel()
        leituraJob = viewModelScope.launch {
            _uiState.value = ClienteUiState.Carregando
            try {
                banco.clienteDao().listarTodos().collect { lista ->
                    _uiState.value = if (lista.isEmpty()) {
                        ClienteUiState.Vazio
                    } else {
                        ClienteUiState.Sucesso(lista)
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = ClienteUiState.Erro(R.string.clientes_erro)
            }
        }
    }
}
