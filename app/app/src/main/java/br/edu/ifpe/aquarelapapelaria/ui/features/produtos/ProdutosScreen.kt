package br.edu.ifpe.aquarelapapelaria.ui.features.produtos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifpe.aquarelapapelaria.R
import br.edu.ifpe.aquarelapapelaria.viewmodel.ProdutosViewModel

@Composable
fun ProdutosScreen(
    viewModel: ProdutosViewModel = viewModel()
) {
    var nome by rememberSaveable { mutableStateOf("") }
    var marca by rememberSaveable { mutableStateOf("") }
    var quantidade by rememberSaveable { mutableStateOf("") }
    var obs by rememberSaveable { mutableStateOf("") }

    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.produtos_cadastrar),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text(stringResource(R.string.produtos_nome)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = marca,
            onValueChange = { marca = it },
            label = { Text(stringResource(R.string.produtos_marca)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quantidade,
            onValueChange = { quantidade = it },
            label = { Text(stringResource(R.string.produtos_quantidade)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = obs,
            onValueChange = { obs = it },
            label = { Text(stringResource(R.string.produtos_obs)) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.cadastrar(nome, marca, quantidade, obs)
                if (viewModel.mensagem.contains(stringResource(R.string.produtos_sucesso))) {
                    nome = ""
                    marca = ""
                    quantidade = ""
                    obs = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.produtos_cadastrar))
        }

        if (viewModel.mensagem.isNotBlank()) {
            Text(
                text = viewModel.mensagem,
                color = if (viewModel.mensagem.contains("Erro") || viewModel.mensagem.contains("Preencha")) 
                    MaterialTheme.colorScheme.error 
                else 
                    MaterialTheme.colorScheme.primary
            )
        }

        HorizontalDivider()

        Text(
            text = stringResource(R.string.produtos_titulo),
            style = MaterialTheme.typography.titleLarge
        )

        Box(modifier = Modifier.weight(1f)) {
            when (val atual = estado) {
                UiState.Carregando -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                UiState.Vazio -> {
                    Text(
                        text = stringResource(R.string.produtos_vazio),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UiState.Erro -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(atual.mensagemRes))
                        Button(onClick = { viewModel.carregar() }) {
                            Text(stringResource(R.string.tentar_novamente))
                        }
                    }
                }

                is UiState.Sucesso -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(atual.produtos) { produto ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(text = produto.nome, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        text = stringResource(R.string.produtos_marca_label, produto.marca),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = stringResource(R.string.produtos_quantidade_label, produto.quantidade),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    if (!produto.observacoes.isNullOrBlank()) {
                                        Text(text = produto.observacoes, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
