package br.edu.ifpe.aquarelapapelaria.data.repository

import br.edu.ifpe.aquarelapapelaria.data.local.ProdutoDao
import br.edu.ifpe.aquarelapapelaria.model.Produto
import kotlinx.coroutines.flow.Flow

class ProdutoRepository(private val produtoDao: ProdutoDao) {

    fun listarTodos(): Flow<List<Produto>> = produtoDao.listarTodos()

    fun buscarPorNome(termo: String): Flow<List<Produto>> = produtoDao.buscarPorNome(termo)

    suspend fun buscarPorId(id: Long): Produto? = produtoDao.buscarPorId(id)

    suspend fun inserir(produto: Produto): Long = produtoDao.inserir(produto)

    suspend fun atualizar(produto: Produto) = produtoDao.atualizar(produto)

    suspend fun excluir(produto: Produto) = produtoDao.excluir(produto)
}
