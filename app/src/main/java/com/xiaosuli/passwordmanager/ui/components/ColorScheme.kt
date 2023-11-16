package com.xiaosuli.passwordmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaosuli.passwordmanager.ui.theme.PasswordManagerTheme

data class ColorScheme(
    val text: String,
    val color: Color,
    val blackText: Boolean = true
)

@Composable
fun ColorScheme(modifier: Modifier = Modifier) {

    val colorSchemes = listOf(
        ColorScheme("primary", MaterialTheme.colorScheme.primary, false),
        ColorScheme("onPrimary", MaterialTheme.colorScheme.onPrimary),
        ColorScheme("primaryContainer", MaterialTheme.colorScheme.primaryContainer),
        ColorScheme("onPrimaryContainer", MaterialTheme.colorScheme.onPrimaryContainer, false),
        ColorScheme("inversePrimary", MaterialTheme.colorScheme.inversePrimary),
        ColorScheme("secondary", MaterialTheme.colorScheme.secondary),
        ColorScheme("onSecondary", MaterialTheme.colorScheme.onSecondary),
        ColorScheme("secondaryContainer", MaterialTheme.colorScheme.secondaryContainer),
        ColorScheme("onSecondaryContainer", MaterialTheme.colorScheme.onSecondaryContainer, false),
        ColorScheme("tertiary", MaterialTheme.colorScheme.tertiary, false),
        ColorScheme("onTertiary", MaterialTheme.colorScheme.onTertiary),
        ColorScheme("tertiaryContainer", MaterialTheme.colorScheme.tertiaryContainer),
        ColorScheme("onTertiaryContainer", MaterialTheme.colorScheme.onTertiaryContainer, false),
        ColorScheme("background", MaterialTheme.colorScheme.background),
        ColorScheme("onBackground", MaterialTheme.colorScheme.onBackground, false),
        ColorScheme("surface", MaterialTheme.colorScheme.surface),
        ColorScheme("onSurface", MaterialTheme.colorScheme.onSurface, false),
        ColorScheme("surfaceTint", MaterialTheme.colorScheme.surfaceTint),
        ColorScheme("surfaceVariant", MaterialTheme.colorScheme.surfaceVariant),
        ColorScheme("onSurfaceVariant", MaterialTheme.colorScheme.onSurfaceVariant, false),
        ColorScheme("inverseSurface", MaterialTheme.colorScheme.inverseSurface, false),
        ColorScheme("inverseOnSurface", MaterialTheme.colorScheme.inverseOnSurface),
        ColorScheme("outline", MaterialTheme.colorScheme.outline, false),
        ColorScheme("outlineVariant", MaterialTheme.colorScheme.outlineVariant),
        ColorScheme("error", MaterialTheme.colorScheme.error, false),
        ColorScheme("onError", MaterialTheme.colorScheme.onError),
        ColorScheme("errorContainer", MaterialTheme.colorScheme.errorContainer),
        ColorScheme("onErrorContainer", MaterialTheme.colorScheme.onErrorContainer, false),
    )

    LazyGridList(colorSchemes, modifier)
}

@Composable
private fun LazyGridList(
    colorSchemes: List<ColorScheme>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(colorSchemes) { colorScheme ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(colorScheme.color)
            ) {
                Text(
                    text = colorScheme.text,
                    color = if (colorScheme.blackText)
                        MaterialTheme.colorScheme.onBackground
                    else
                        MaterialTheme.colorScheme.background
                )
            }
        }
    }
}

@Composable
private fun LazyColumnList(colorSchemes: List<ColorScheme>) {
    LazyColumn {
        items(colorSchemes.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(colorSchemes[index].color)
            ) {
                Text(
                    text = colorSchemes[index].text,
                    color = if (colorSchemes[index].blackText)
                        MaterialTheme.colorScheme.onBackground
                    else
                        MaterialTheme.colorScheme.background
                )
            }
        }
    }
}

@Composable
private fun ForEachList(colorSchemes: List<ColorScheme>) {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        colorSchemes.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(it.color)
            ) {
                Text(
                    text = it.text,
                    color = if (it.blackText)
                        MaterialTheme.colorScheme.onBackground
                    else
                        MaterialTheme.colorScheme.background
                )
            }
        }
    }
}

@Preview
@Composable
fun ColorSchemePreview() {
    PasswordManagerTheme {
        ColorScheme()
    }
}