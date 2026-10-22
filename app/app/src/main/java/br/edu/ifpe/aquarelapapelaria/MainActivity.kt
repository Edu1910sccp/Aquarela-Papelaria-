package br.edu.ifpe.aquarelapapelaria

// Fronteira: ponto de entrada do app. Só liga o tema e a navegação; não tem regra de negócio.

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.ifpe.aquarelapapelaria.ui.navigation.AppNavGraph
import br.edu.ifpe.aquarelapapelaria.ui.theme.AquarelaPapelariaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AquarelaPapelariaTheme {
                AppNavGraph()
            }
        }
    }
}
