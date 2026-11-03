
package br.edu.ifpe.aquarelapapelaria.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.withTransaction
import br.edu.ifpe.aquarelapapelaria.data.local.AppDatabase
import br.edu.ifpe.aquarelapapelaria.model.Cliente
import br.edu.ifpe.aquarelapapelaria.model.Pedido
import br.edu.ifpe.aquarelapapelaria.model.ValidacaoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PedidosViewModel(application: Application) :
    AndroidViewModel(application) {

    private val banco = AppDatabase.obterBanco(application)

    val pedidos = banco.pedidoDao().listarPorPrazo()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    var mensagem by mutableStateOf("")
        private set

    fun cadastrar(
        nomeCliente: String,
        servico: String,
        quantidadeTexto: String,
        prazoTexto: String
    ) {
        val quantidade = quantidadeTexto.toIntOrNull()
        val dias = prazoTexto.toIntOrNull()

        if (nomeCliente.isBlank() || servico.isBlank() ||
            quantidade == null || quantidade <= 0 ||
            dias == null || dias !in 1..365
        ) {
            mensagem = "Preencha os campos corretamente."
            return
        }

        viewModelScope.launch {
            try {
                banco.withTransaction {
                    val clienteId = banco.clienteDao().inserir(
                        Cliente(
                            nome = nomeCliente.trim(),
                            telefone = ""
                        )
                    )

                    val pedido = Pedido(
                        clienteId = clienteId,
                        servico = servico.trim(),
                        quantidade = quantidade,
                        prazo = System.currentTimeMillis() +
                                dias.toLong() * 86400000L
                    )

                    val erro = ValidacaoPedido.validar(pedido)

                    if (erro != null) {
                        throw IllegalArgumentException(erro)
                    }

                    banco.pedidoDao().inserir(pedido)
                }

                mensagem = "Pedido cadastrado!"

            } catch (e: Exception) {
                mensagem = "Erro ao cadastrar pedido."
            }
        }
    }
}
