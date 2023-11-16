package com.xiaosuli.passwordmanager.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaosuli.passwordmanager.entity.PasswordEntity
import com.xiaosuli.passwordmanager.utils.copyTextToClipboard
import com.xiaosuli.passwordmanager.utils.timestampToDuration

@Composable
fun PasswordCard(
    item: PasswordEntity,
    modifier: Modifier = Modifier,
    onDeleteClick: (Long) -> Unit,
) {
    val context = LocalContext.current
    var showDialog by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .padding(
                    top = 5.dp,
                    bottom = 10.dp,
                    start = 15.dp,
                    end = 15.dp
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("名称：")
                        }
                        withStyle(SpanStyle()) {
                            append(item.name)
                        }
                    },
                    fontSize = 14.sp,
                    modifier = Modifier
                )
                Row {
                    IconButton(onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "${item.number}\n${item.password}")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            Line()
            PasswordCardCopyItem("账号", item.number)
            Line()
            PasswordCardCopyItem("密码", item.password)
            Line()
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.category,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontSize = 13.sp
                )
                Text(
                    text = timestampToDuration(item.updateTime),
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontSize = 13.sp
                )
            }
        }
    }

    // 确认删除对话框
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = { onDeleteClick(item.id) }) {
                    Text(text = "确认")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = "取消")
                }
            },
            text = { Text(text = "确认删除这个密码吗？") },
            title = { Text(text = "删除密码") },
        )
    }
}

@Composable
private fun PasswordCardCopyItem(key: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${key}：")
                }
                withStyle(SpanStyle()) {
                    append(value)
                }
            },
            fontSize = 14.sp,
            modifier = Modifier
        )
        IconButton(onClick = {
            copyTextToClipboard(value)
        }) {
            Icon(
                imageVector = Icons.Rounded.ContentCopy,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Preview
@Composable
fun PasswordCardPreview() {
    val currentTime = System.currentTimeMillis()
    PasswordCard(
        item = PasswordEntity(
            id = currentTime,
            category = "银行卡",
            name = "建设银行",
            number = "62378940321987345",
            password = "12345678",
            createTime = currentTime,
            updateTime = currentTime,
        ),
        onDeleteClick = {},
    )
}