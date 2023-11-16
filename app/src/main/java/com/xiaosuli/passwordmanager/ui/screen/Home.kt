package com.xiaosuli.passwordmanager.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaosuli.passwordmanager.entity.CategoryInfo
import com.xiaosuli.passwordmanager.ui.theme.PasswordManagerTheme
import com.xiaosuli.passwordmanager.viewmodel.AppViewModel

@Composable
fun Home(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    onCategoryCardClick:(String)->Unit
) {
    val categoryInfo = appViewModel.categoryInfo.collectAsState(
        listOf(CategoryInfo("空", -1))
    )
    val categoryIcon = appViewModel.categoryIcon

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(
            items = categoryInfo.value,
            key = { item -> item.category }
        ) { categoryInfo ->
            CategoryCard(categoryInfo, categoryIcon,onCategoryCardClick)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategoryCard(
    categoryInfo: CategoryInfo,
    categoryIcon: Map<String, ImageVector>,
    onCategoryCardClick:(String)->Unit
) {
    Card(
        onClick = {
            onCategoryCardClick(categoryInfo.category)
        },
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = categoryIcon[categoryInfo.category] ?: Icons.Rounded.MoreHoriz,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(35))
                    .background(MaterialTheme.colorScheme.surfaceTint)
                    .padding(15.dp),
                tint = MaterialTheme.colorScheme.inverseOnSurface
            )
            Text(
                text = categoryInfo.category,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Text(
                text = "(${categoryInfo.count})",
                fontSize = 14.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    PasswordManagerTheme {
        CategoryCard(
            categoryInfo = CategoryInfo("default", 2),
            categoryIcon = mapOf(
                "default" to Icons.Rounded.MoreHoriz,
            ),
            onCategoryCardClick = {}
        )
    }
}