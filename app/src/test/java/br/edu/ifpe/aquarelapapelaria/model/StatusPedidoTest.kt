
package br.edu.ifpe.aquarelapapelaria.model

// Fronteira: testa a ordem dos status de um pedido.
// Nao acessa banco de dados nem modifica pedidos.

import org.junit.Assert.assertEquals
import org.junit.Test

class StatusPedidoTest {

    @Test
    fun orcamentoParaProducao() {
        assertEquals(
            StatusPedido.EM_PRODUCAO,
            StatusPedido.proximo(StatusPedido.ORCAMENTO)
        )
    }

    @Test
    fun producaoParaPronto() {
        assertEquals(
            StatusPedido.PRONTO,
            StatusPedido.proximo(StatusPedido.EM_PRODUCAO)
        )
    }

    @Test
    fun prontoParaEntregue() {
        assertEquals(
            StatusPedido.ENTREGUE_PAGO,
            StatusPedido.proximo(StatusPedido.PRONTO)
        )
    }

    @Test
    fun entreguePermaneceEntregue() {
        assertEquals(
            StatusPedido.ENTREGUE_PAGO,
            StatusPedido.proximo(StatusPedido.ENTREGUE_PAGO)
        )
    }
}
