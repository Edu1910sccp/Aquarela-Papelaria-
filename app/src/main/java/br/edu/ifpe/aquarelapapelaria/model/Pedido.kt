package br.edu.ifpe.aquarelapapelaria.model

// Fronteira: entidade Pedido (tabela "pedidos" no Room). Só guarda dados; sem regra de negócio.

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pedidos",
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["id"],
            childColumns = ["clienteId"],
            onDelete = ForeignKey.RESTRICT // não deixa excluir cliente que tem pedido
        )
    ],
    indices = [Index("clienteId"), Index("materialId"), Index("prazo")]
)
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clienteId: Long,                        // quem pediu (Cliente.id)
    val servico: String,                        // ex.: "Cópia colorida A4"
    val quantidade: Int = 1,
    val valor: Long = 0,                        // em centavos
    val status: String = StatusPedido.ORCAMENTO,
    val materialId: Long? = null,               // material a descontar (Material.id); sem FK por enquanto
    val quantidadeMaterial: Int = 0             // quanto do material o pedido usa
)