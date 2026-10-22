package br.edu.ifpe.aquarelapapelaria.model

// Fronteira: entidade MovimentacaoEstoque (histórico de entradas e baixas do estoque). Só guarda dados; sem regra de negócio.

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimentacoes_estoque")
data class MovimentacaoEstoque(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val materialId: Long,
    val pedidoId: Long? = null,
    val tipo: String,
    val quantidade: Int,
    val data: Long
)
