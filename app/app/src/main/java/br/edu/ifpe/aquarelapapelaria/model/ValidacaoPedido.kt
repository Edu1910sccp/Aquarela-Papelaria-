
package br.edu.ifpe.aquarelapapelaria.model

// Fronteira: valida os dados do pedido antes de salvar.
// Não acessa o banco de dados nem altera o estoque.

object ValidacaoPedido {

    // Retorna uma mensagem de erro ou null se o pedido for válido.
    fun validar(pedido: Pedido): String? {

        // Verifica se o cliente possui um ID válido.
        if (pedido.clienteId <= 0) {
            return "Selecione um cliente válido."
        }

        // Impede o cadastro de serviços sem nome.
        if (pedido.servico.isBlank()) {
            return "Informe o serviço."
        }

        // A quantidade deve ser maior que zero.
        if (pedido.quantidade <= 0) {
            return "Digite uma quantidade válida."
        }

        // O prazo deve ser um valor positivo em milissegundos.
        if (pedido.prazo <= 0) {
            return "Informe um prazo válido."
        }

        // O valor é armazenado em centavos e não pode ser negativo.
        if (pedido.valor < 0) {
            return "O valor não pode ser negativo."
        }

        // Impede o uso de quantidades negativas de material.
        if (pedido.quantidadeMaterial < 0) {
            return "Informe uma quantidade de material válida."
        }

        // Exige um material quando há consumo registrado.
        if (pedido.materialId == null && pedido.quantidadeMaterial > 0) {
            return "Selecione o material utilizado."
        }

        return null
    }
}