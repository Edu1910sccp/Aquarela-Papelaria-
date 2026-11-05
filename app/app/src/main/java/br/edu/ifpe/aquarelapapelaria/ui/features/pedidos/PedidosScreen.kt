
package br.edu.ifpe.aquarelapapelaria.ui.features.pedidos

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifpe.aquarelapapelaria.R
import br.edu.ifpe.aquarelapapelaria.ui.UiState
import br.edu.ifpe.aquarelapapelaria.viewmodel.PedidosViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Fronteira: apresenta o cadastro e a lista de pedidos.
// Existe para conectar o usuário aos dados do ViewModel.
// Sem ela, o cadastro e os quatro estados não aparecem.
@Composable
fun PedidosScreen(
    viewModel: PedidosViewModel = viewModel()
) {
    var nomeCliente by rememberSaveable {
        mutableStateOf("")
    }

    var servico by rememberSaveable {
        mutableStateOf("")
    }

    var quantidade by rememberSaveable {
        mutableStateOf("")
    }

    var prazo by rememberSaveable {
        mutableStateOf("")
    }

    // Observa os quatro estados do ViewModel.
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = stringResource(
                R.string.pedidos_cadastrar_titulo
            ),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = nomeCliente,
            onValueChange = { nomeCliente = it },
            label = {
                Text(stringResource(R.string.pedidos_nome_cliente))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = servico,
            onValueChange = { servico = it },
            label = {
                Text(stringResource(R.string.pedidos_servico_campo))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = quantidade,
            onValueChange = { quantidade = it },
            label = {
                Text(stringResource(R.string.pedidos_quantidade_campo))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = prazo,
            onValueChange = { prazo = it },
            label = {
                Text(stringResource(R.string.pedidos_prazo_dias))
            },
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
            Text(
                stringResource(R.string.pedidos_cadastrar_titulo)
            )
        }

        if (viewModel.mensagem.isNotBlank()) {
            Text(viewModel.mensagem)
        }

        HorizontalDivider()

        Text(
            text = stringResource(R.string.pedidos_lista_titulo),
            style = MaterialTheme.typography.titleLarge
        )

        // Fronteira: desenha os quatro estados da consulta.
        // Garante que a tela informe cada situação.
        // Sem este when, erros e carregamento ficam invisíveis.
        when (val atual = estado) {

            UiState.Carregando -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        stringResource(R.string.pedidos_carregando)
                    )
                }
            }

            UiState.Vazio -> {
                Text(
                    text = stringResource(R.string.pedidos_vazios)
                )
            }

            is UiState.Erro -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(atual.mensagemRes)
                    )

                    Button(
                        onClick = {
                            viewModel.carregar()
                        }
                    ) {
                        Text(
                            stringResource(R.string.tentar_novamente)
                        )
                    }
                }
            }

            is UiState.Sucesso -> {

                atual.itens.forEach { pedido ->

                    val data = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale("pt", "BR")
                    ).format(Date(pedido.prazo))

                    Column(
                        verticalArrangement =
                            Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            stringResource(
                                R.string.pedidos_numero,
                                pedido.id
                            )
                        )

                        Text(
                            stringResource(
                                R.string.pedidos_cliente_id,
                                pedido.clienteId
                            )
                        )

                        Text(
                            stringResource(
                                R.string.pedidos_servico_linha,
                                pedido.servico
                            )
                        )

                        Text(
                            stringResource(
                                R.string.pedidos_quantidade_linha,
                                pedido.quantidade
                            )
                        )

                        Text(
                            stringResource(
                                R.string.pedidos_prazo_linha,
                                data
                            )
                        )

                        Text(
                            stringResource(
                                R.string.pedidos_status_linha,
                                pedido.status
                            )
                        )
                    }

                    HorizontalDivider()
                }
            }
        }
    }
}
