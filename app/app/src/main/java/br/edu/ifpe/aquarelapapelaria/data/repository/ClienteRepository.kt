package br.edu.ifpe.aquarelapapelaria.data.repository

import br.edu.ifpe.aquarelapapelaria.data.local.ClienteDao
import br.edu.ifpe.aquarelapapelaria.model.Cliente
import kotlinx.coroutines.flow.Flow

class ClienteRepository(private val clienteDao: ClienteDao) {

    fun listarTodos(): Flow<List<Cliente>> = clienteDao.listarTodos()

    fun buscarPorNome(termo: String): Flow<List<Cliente>> = clienteDao.buscarPorNome(termo)

    suspend fun buscarPorId(id: Long): Cliente? = clienteDao.buscarPorId(id)

    suspend fun inserir(cliente: Cliente): Long = clienteDao.inserir(cliente)

    suspend fun atualizar(cliente: Cliente) = clienteDao.atualizar(cliente)

    suspend fun excluir(cliente: Cliente) = clienteDao.excluir(cliente)
}
