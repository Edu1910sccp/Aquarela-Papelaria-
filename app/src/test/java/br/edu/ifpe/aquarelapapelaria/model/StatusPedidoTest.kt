
package br.edu.ifpe.aquarelapapelaria.model

// Fronteira: testa a ordem dos status de um pedido.
// Nao acessa banco de dados nem modifica pedidos.

import org.junit.Assert.assertEquals
import org.junit.Test

class StatusPedidoTest {

    // Verifica se um pedido em orcamento avanca para producao.
    @Test
    fun orcamentoParaProducao() {
        assertEquals(
            StatusPedido.EM_PRODUCAO,
            StatusPedido.proximo(StatusPedido.ORCAMENTO)
        )
    }

    // Verifica se um pedido em producao avanca para pronto.
    @Test
    fun producaoParaPronto() {
        assertEquals(
            StatusPedido.PRONTO,
            StatusPedido.proximo(StatusPedido.EM_PRODUCAO)
        )
    }

    // Verifica se um pedido pronto avanca para entregue e pago.
    @Test
    fun prontoParaEntregue() {
        assertEquals(
            StatusPedido.ENTREGUE_PAGO,
            StatusPedido.proximo(StatusPedido.PRONTO)
        )
    }

    // Verifica se um pedido entregue permanece no status final.
    @Test
    fun entreguePermaneceEntregue() {
        assertEquals(
            StatusPedido.ENTREGUE_PAGO,
            StatusPedido.proximo(StatusPedido.ENTREGUE_PAGO)
        )
    }
}

