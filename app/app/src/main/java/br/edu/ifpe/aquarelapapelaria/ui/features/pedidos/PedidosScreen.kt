package br.edu.ifpe.aquarelapapelaria.ui.features.pedidos

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifpe.aquarelapapelaria.viewmodel.PedidosViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PedidosScreen(
    viewModel: PedidosViewModel = viewModel()
) {
    var nomeCliente by rememberSaveable { mutableStateOf("") }
    var servico by rememberSaveable { mutableStateOf("") }
    var quantidade by rememberSaveable { mutableStateOf("") }
    var prazo by rememberSaveable { mutableStateOf("") }

    val pedidos by viewModel.pedidos.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Cadastrar Pedido",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = nomeCliente,
            onValueChange = { nomeCliente = it },
            label = { Text("Nome do cliente") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = servico,
            onValueChange = { servico = it },
            label = { Text("Serviço") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = quantidade,
            onValueChange = { quantidade = it },
            label = { Text("Quantidade") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = prazo,
            onValueChange = { prazo = it },
            label = { Text("Prazo em dias") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                viewModel.cadastrar(
                    nomeCliente,
                    servico,
                    quantidade,
                    prazo
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar Pedido")
        }

        if (viewModel.mensagem.isNotBlank()) {
            Text(viewModel.mensagem)
        }

        HorizontalDivider()

        Text(
            text = "Pedidos cadastrados",
            style = MaterialTheme.typography.titleLarge
        )

        if (pedidos.isEmpty()) {
            Text("Nenhum pedido cadastrado.")
        }

        pedidos.forEach { pedido ->

            val data = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale("pt", "BR")
            ).format(Date(pedido.prazo))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Pedido ${pedido.id}")
                Text("Cliente ID: ${pedido.clienteId}")
                Text("Serviço: ${pedido.servico}")
                Text("Quantidade: ${pedido.quantidade}")
                Text("Prazo: $data")
                Text("Status: ${pedido.status}")
            }

            HorizontalDivider()
        }
    }
}