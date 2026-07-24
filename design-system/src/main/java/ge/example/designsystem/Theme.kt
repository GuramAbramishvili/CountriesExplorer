package ge.example.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = DsColors.Primary,
    onPrimary = DsColors.OnPrimary,
    secondary = DsColors.Secondary,
    onSecondary = DsColors.OnSecondary,
    background = DsColors.Background,
    onBackground = DsColors.OnBackground,
    surface = DsColors.Surface,
    onSurface = DsColors.OnSurface,
    onSurfaceVariant = DsColors.OnSurfaceVariant,
    outline = DsColors.Outline,
    error = DsColors.Error
)

private val DarkColors = darkColorScheme(
    primary = DsColors.PrimaryDark,
    onPrimary = DsColors.PrimaryVariant,
    secondary = DsColors.Secondary,
    onSecondary = DsColors.OnSecondary,
    background = DsColors.BackgroundDark,
    onBackground = DsColors.OnBackgroundDark,
    surface = DsColors.SurfaceDark,
    onSurface = DsColors.OnSurfaceDark,
    onSurfaceVariant = DsColors.OnSurfaceVariant,
    outline = DsColors.OutlineDark,
    error = DsColors.Error
)

/**
 * აპლიკაციის ერთადერთი თემა
 */
@Composable
fun CountriesExplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = DsTypography,
        content = content
    )
}
