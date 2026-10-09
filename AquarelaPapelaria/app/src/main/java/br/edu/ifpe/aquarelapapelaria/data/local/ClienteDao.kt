package br.edu.ifpe.aquarelapapelaria.data.local

// Fronteira: acesso ao banco para a tabela "clientes". Só consultas SQL; sem regra de negócio.

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.aquarelapapelaria.model.Cliente
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Insert
    suspend fun inserir(cliente: Cliente): Long

    @Update
    suspend fun atualizar(cliente: Cliente)

    @Delete
    suspend fun excluir(cliente: Cliente)

    @Query("SELECT * FROM clientes ORDER BY nome COLLATE NOCASE ASC")
    fun listarTodos(): Flow<List<Cliente>>

    @Query("SELECT * FROM clientes WHERE id = :id")
    suspend fun buscarPorId(id: Long): Cliente?

    @Query("SELECT * FROM clientes WHERE nome LIKE '%' || :termo || '%' ORDER BY nome COLLATE NOCASE ASC")
    fun buscarPorNome(termo: String): Flow<List<Cliente>>
}
