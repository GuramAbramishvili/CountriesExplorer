package ge.example.countriesexplorer.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ge.example.designsystem.components.DsPrimaryButton
import ge.example.designsystem.components.DsSecondaryButton
import ge.example.designsystem.components.DsSectionTitle

/**
 * ფილტრის მოდალი - აპლიკაციის მესამე გვერდი. მომხმარებელი ირჩევს რეგიონს
 * და აპლიკაცია ამ არჩევანს უბრუნებს სიის ეკრანს onApply callback-ის საშუალებით.
 */
@Composable
fun FilterModalScreen(
    viewModel: FilterViewModel,
    currentRegion: String?,
    onApply: (String?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedRegion by remember { mutableStateOf(currentRegion) }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            DsSectionTitle(text = "რეგიონის ფილტრი")

            when (val state = uiState) {
                is FilterUiState.Loading -> Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is FilterUiState.Error -> Text(
                    text = "შეცდომა: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                is FilterUiState.Success -> Column(modifier = Modifier.padding(top = 12.dp)) {
                    RegionOption(
                        label = "ყველა რეგიონი",
                        selected = selectedRegion == null,
                        onClick = { selectedRegion = null }
                    )
                    state.regions.forEach { region ->
                        RegionOption(
                            label = region,
                            selected = selectedRegion == region,
                            onClick = { selectedRegion = region }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DsSecondaryButton(text = "გაუქმება", modifier = Modifier.fillMaxWidth().weight(1f), onClick = onDismiss)
                DsPrimaryButton(
                    text = "გამოყენება",
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    onClick = { onApply(selectedRegion) }
                )
            }
        }
    }
}

@Composable
private fun RegionOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = selected, onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 8.dp))
    }
}
