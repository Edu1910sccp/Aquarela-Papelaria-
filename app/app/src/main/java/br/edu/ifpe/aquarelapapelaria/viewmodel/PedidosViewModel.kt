package br.edu.ifpe.aquarelapapelaria.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.aquarelapapelaria.R
import androidx.room.withTransaction
import br.edu.ifpe.aquarelapapelaria.data.local.AppDatabase
import br.edu.ifpe.aquarelapapelaria.data.repository.ClienteRepository
import br.edu.ifpe.aquarelapapelaria.data.repository.PedidoRepository
import br.edu.ifpe.aquarelapapelaria.model.Cliente
import br.edu.ifpe.aquarelapapelaria.model.Pedido
import br.edu.ifpe.aquarelapapelaria.model.ValidacaoPedido
import br.edu.ifpe.aquarelapapelaria.ui.UiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PedidosViewModel(application: Application) : AndroidViewModel(application) {

    private val pedidoRepository: PedidoRepository
    private val clienteRepository: ClienteRepository
    private val banco = AppDatabase.obterBanco(application)

    private val _uiState = MutableStateFlow<UiState>(UiState.Carregando)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var leituraJob: Job? = null

    var mensagem by mutableStateOf("")
        private set

    init {
        val db = AppDatabase.obterBanco(application)
        pedidoRepository = PedidoRepository(db.pedidoDao())
        clienteRepository = ClienteRepository(db.clienteDao())
        carregar()
    }

    fun carregar() {
        leituraJob?.cancel()
        leituraJob = viewModelScope.launch {
            _uiState.value = UiState.Carregando
            try {
                // Usando a relação PedidoComCliente para ter acesso ao nome do cliente na UI
                pedidoRepository.listarPedidosComCliente().collect { lista ->
                    _uiState.value = if (lista.isEmpty()) {
                        UiState.Vazio
                    } else {
                        UiState.Sucesso(lista)
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = UiState.Erro(R.string.erro_carregar_pedidos)
            }
        }
    }

    fun cadastrar(nomeCliente: String, servico: String, quantidadeTexto: String, prazoTexto: String) {
        val quantidade = quantidadeTexto.toIntOrNull()
        val dias = prazoTexto.toIntOrNull()

        if (nomeCliente.isBlank() || servico.isBlank() || quantidade == null || quantidade <= 0 || dias == null || dias !in 1..365) {
            mensagem = getApplication<Application>().getString(R.string.campos_invalidos)
            return
        }

        viewModelScope.launch {
            try {
                // Transação segura com Room Coroutines
                banco.withTransaction {
                    val clienteId = clienteRepository.inserir(
                        Cliente(nome = nomeCliente.trim(), telefone = "")
                    )

                    val pedido = Pedido(
                        clienteId = clienteId,
                        servico = servico.trim(),
                        quantidade = quantidade,
                        prazo = System.currentTimeMillis() + dias.toLong() * 86400000L
                    )

                    val erro = ValidacaoPedido.validar(pedido)
                    if (erro != null) throw IllegalArgumentException(erro)

                    pedidoRepository.inserir(pedido)
                }
                mensagem = getApplication<Application>().getString(R.string.pedido_cadastrado)
            } catch (e: Exception) {
                mensagem = getApplication<Application>().getString(R.string.erro_cadastrar_pedido)
            }
        }
    }

    fun limparMensagem() {
        mensagem = ""
    }
}
