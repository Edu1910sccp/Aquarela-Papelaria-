package br.edu.ifpe.aquarelapapelaria.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.aquarelapapelaria.model.Cliente
import br.edu.ifpe.aquarelapapelaria.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Insert
    suspend fun inserir(produto: Produto): Long

    @Update
    suspend fun atualizar(produto: Produto)

    @Delete
    suspend fun excluir(produto: Produto)

    @Query("SELECT * FROM produtos ORDER BY nome COLLATE NOCASE ASC")
    fun listarTodos(): Flow<List<Produto>>

    @Query("SELECT * FROM produtos WHERE id = :id")
    suspend fun buscarPorId(id: Long): Produto?

    @Query("SELECT * FROM produtos WHERE nome LIKE '%' || :termo || '%' ORDER BY nome COLLATE NOCASE ASC")
    fun buscarPorNome(termo: String): Flow<List<Produto>>
}
