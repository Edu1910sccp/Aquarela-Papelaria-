
package br.edu.ifpe.aquarelapapelaria.model

// Fronteira: testa as validacoes dos dados de Pedido.
// Nao utiliza banco de dados nem interface grafica.

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ValidacaoPedidoTest {

    // Cria um pedido valido para os testes.
    private fun pedidoValido() = Pedido(
        clienteId = 1,
        servico = "Copia A4",
        quantidade = 10,
        prazo = 1791504000000L,
        valor = 500
    )

    @Test
    fun aceitaPedidoValido() {
        assertNull(ValidacaoPedido.validar(pedidoValido()))
    }

    @Test
    fun rejeitaServicoVazio() {
        val pedido = pedidoValido().copy(servico = "")

        assertEquals(
            "Informe o serviço.",
            ValidacaoPedido.validar(pedido)
        )
    }

    @Test
    fun rejeitaQuantidadeNegativa() {
        val pedido = pedidoValido().copy(quantidade = -1)

        assertEquals(
            "Digite uma quantidade válida.",
            ValidacaoPedido.validar(pedido)
        )
    }

    @Test
    fun rejeitaPrazoInvalido() {
        val pedido = pedidoValido().copy(prazo = 0)

        assertEquals(
            "Informe um prazo válido.",
            ValidacaoPedido.validar(pedido)
        )
    }

    @Test
    fun rejeitaValorNegativo() {
        val pedido = pedidoValido().copy(valor = -100)

        assertEquals(
            "O valor não pode ser negativo.",
            ValidacaoPedido.validar(pedido)
        )
    }
}