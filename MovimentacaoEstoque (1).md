**MovimentacaoEstoque**

**Arquivo:** `app/src/main/java/br/edu/ifpe/aquarelapapelaria/model/MovimentacaoEstoque.kt`
**Responsável:** David

**O que é**

Entidade do Room que representa uma movimentação do estoque, ou seja, cada vez que um material entra ou sai. Ela forma o histórico de entradas e baixas da papelaria. Só guarda dados, sem regra de negócio.

**Campos**

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `materialId` | Long | sim | qual material foi movimentado |
| `pedidoId` | Long? | não | preenchido quando a baixa vem de um pedido |
| `tipo` | String | sim | ENTRADA, SAIDA ou AJUSTE |
| `quantidade` | Int | sim | quantidade movimentada |
| `data` | Long | sim | momento do registro, em milissegundos |

**Código**

```kotlin
package br.edu.ifpe.aquarelapapelaria.model

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
```

**DAO relacionado**

`MovimentacaoEstoqueDao.kt` (pasta `data/local`) oferece:

- `inserir`: salva uma movimentação
- `listarTodas`: lista todas, da mais recente para a mais antiga
- `listarPorMaterial`: lista as de um material
- `listarPorPedido`: lista as de um pedido

O histórico só insere e lista, nunca edita (conforme o PRD, seção 7).
