package br.edu.ifpe.aquarelapapelaria.data.repository

import br.edu.ifpe.aquarelapapelaria.data.local.PedidoDao
import br.edu.ifpe.aquarelapapelaria.model.Pedido
import br.edu.ifpe.aquarelapapelaria.model.PedidoComCliente
import kotlinx.coroutines.flow.Flow

class PedidoRepository(private val pedidoDao: PedidoDao) {

    fun listarPorPrazo(): Flow<List<Pedido>> = pedidoDao.listarPorPrazo()

    fun listarPedidosComCliente(): Flow<List<PedidoComCliente>> = pedidoDao.listarPedidosComCliente()

    fun listarPorCliente(clienteId: Long): Flow<List<Pedido>> = pedidoDao.listarPorCliente(clienteId)

    suspend fun buscarPorId(id: Long): Pedido? = pedidoDao.buscarPorId(id)

    suspend fun buscarPedidoComClientePorId(id: Long): PedidoComCliente? =
        pedidoDao.buscarPedidoComClientePorId(id)

    suspend fun inserir(pedido: Pedido): Long = pedidoDao.inserir(pedido)

    suspend fun atualizar(pedido: Pedido) = pedidoDao.atualizar(pedido)

    suspend fun atualizarStatus(id: Long, status: String) = pedidoDao.atualizarStatus(id, status)

    suspend fun excluir(pedido: Pedido) = pedidoDao.excluir(pedido)
}
