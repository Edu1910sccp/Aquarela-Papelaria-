package br.edu.ifpe.aquarelapapelaria.model

// Fronteira: entidade Cliente (tabela "clientes" no Room). Só guarda dados; sem regra de negócio.

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val telefone: String,
    val email: String? = null,
    val observacoes: String? = null
)
