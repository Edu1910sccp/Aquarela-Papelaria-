package br.edu.ifpe.aquarelapapelaria.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produtos")
data class Produto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val marca: String,
    val quantidade: Int,
    val observacoes: String? = null
)