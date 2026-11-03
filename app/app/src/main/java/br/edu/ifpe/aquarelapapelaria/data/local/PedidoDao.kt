package br.edu.ifpe.aquarelapapelaria.data.local

// Fronteira: acesso ao banco para a tabela "pedidos". Só consultas SQL; sem regra de negócio.

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.aquarelapapelaria.model.Pedido
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    @Insert
    suspend fun inserir(pedido: Pedido): Long

    @Update
    suspend fun atualizar(pedido: Pedido)

    @Delete
    suspend fun excluir(pedido: Pedido)

    // RF01: fila ordenada do prazo mais próximo ao mais distante
    @Query("SELECT * FROM pedidos ORDER BY prazo ASC")
    fun listarPorPrazo(): Flow<List<Pedido>>

    @Query("SELECT * FROM pedidos WHERE id = :id")
    suspend fun buscarPorId(id: Long): Pedido?

    @Query("SELECT * FROM pedidos WHERE clienteId = :clienteId ORDER BY prazo ASC")
    fun listarPorCliente(clienteId: Long): Flow<List<Pedido>>

    // RF03: muda só o status, sem regravar o pedido inteiro
    @Query("UPDATE pedidos SET status = :status WHERE id = :id")
    suspend fun atualizarStatus(id: Long, status: String)
}