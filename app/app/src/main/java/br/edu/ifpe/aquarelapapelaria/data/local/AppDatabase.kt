
package br.edu.ifpe.aquarelapapelaria.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifpe.aquarelapapelaria.model.Cliente
import br.edu.ifpe.aquarelapapelaria.model.Pedido
import br.edu.ifpe.aquarelapapelaria.model.Produto

@Database(
    entities = [Cliente::class, Pedido::class, Produto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clienteDao(): ClienteDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun produtoDao(): ProdutoDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        fun obterBanco(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "aquarela.db"
                ).build().also { instancia = it }
            }
        }
    }
}
