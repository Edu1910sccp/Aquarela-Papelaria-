package br.edu.ifpe.aquarelapapelaria.model

// Fronteira: valores possíveis do status de um pedido e a ordem em que ele avança.
// O banco guarda o status como texto (String), conforme o PRD, seção 7.

object StatusPedido {
    const val ORCAMENTO = "ORCAMENTO"
    const val EM_PRODUCAO = "EM_PRODUCAO"
    const val PRONTO = "PRONTO"
    const val ENTREGUE_PAGO = "ENTREGUE_PAGO"

    // Devolve o próximo status da fila; se já estiver no último, continua nele.
    fun proximo(atual: String): String = when (atual) {
        ORCAMENTO -> EM_PRODUCAO
        EM_PRODUCAO -> PRONTO
        PRONTO -> ENTREGUE_PAGO
        else -> atual
    }
}