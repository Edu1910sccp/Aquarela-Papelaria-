package br.edu.ifpe.aquarelapapelaria.ui.features.pedidos

// Fronteira: tela provisória só para provar que o projeto compila e navega.
// Será substituída pela tela real de Pedidos (fila por prazo).

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.edu.ifpe.aquarelapapelaria.R

@Composable
fun PedidosPlaceholderScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.placeholder_pedidos),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
