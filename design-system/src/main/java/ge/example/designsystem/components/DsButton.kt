package ge.example.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ძირითადი (filled) ღილაკი, გამოსაყენებელი მთელ აპლიკაციაში ერთგვაროვანი
 * იერსახის შესანარჩუნებლად.
 */
@Composable
fun DsPrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = text, modifier = Modifier.padding(vertical = 4.dp))
    }
}

/**
 * მეორეხარისხოვანი (outlined) ღილაკი, გამოსაყენებელი დამატებითი
 * მოქმედებებისთვის (მაგ. "გასუფთავება", "გაუქმება").
 */
@Composable
fun DsSecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text = text, modifier = Modifier.padding(vertical = 4.dp))
    }
}
