package br.edu.ifpe.aquarelapapelaria.ui.theme

// Fronteira: tema Material 3 do app, usando as cores do PRD.

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaClaro = lightColorScheme(
    primary = AzulAquarela,
    secondary = LaranjaSuave,
    background = FundoClaro,
    surface = FundoClaro,
    onBackground = TextoEscuro,
    onSurface = TextoEscuro
)

@Composable
fun AquarelaPapelariaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        typography = Typography,
        content = content
    )
}
