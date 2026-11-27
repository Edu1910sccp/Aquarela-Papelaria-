package br.edu.ifpe.aquarelapapelaria.model

import androidx.room.Embedded
import androidx.room.Relation

data class PedidoComCliente(
    @Embedded val pedido: Pedido,
    @Relation(
        parentColumn = "clienteId",
        entityColumn = "id"
    )
    val cliente: Cliente
)
