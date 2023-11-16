package com.xiaosuli.passwordmanager.ui.screen


import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaosuli.passwordmanager.entity.PasswordEntity
import com.xiaosuli.passwordmanager.ui.components.Line
import com.xiaosuli.passwordmanager.ui.components.showToast
import com.xiaosuli.passwordmanager.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("ReturnFromAwaitPointerEventScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Add(
    appViewModel: AppViewModel,
    onBackClick: () -> Unit,
) {
    val categorys = appViewModel.categoryIcon.keys.toList()
    var category by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val animatedOffset = remember {
        Animatable(Offset(0f, 0f), Offset.VectorConverter)
    }
    val coroutineScope = rememberCoroutineScope()

    Column {
        CenterAlignedTopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                titleContentColor = MaterialTheme.colorScheme.inverseSurface
            ),
            title = {
                Text(text = "添加")
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    val currentTime = System.currentTimeMillis()
                    if (category == "" || name == "" || number == "" || password == "") {
                        showToast("以上内容均不能为空！")
                    } else {
                        appViewModel.insertPassword(
                            PasswordEntity(
                                id = currentTime,
                                category = category,
                                name = name,
                                number = number,
                                password = password,
                                createTime = currentTime,
                                updateTime = currentTime,
                            )
                        )
                        onBackClick()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        )

        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(15.dp),
                ) {
                    Text(text = "类别", modifier = Modifier.padding(end = 10.dp))
                    AddItemTextField(
                        value = category,
                        placeholder = "点此自定义你的类别",
                        modifier = Modifier.weight(1f),
                        onValueChange = { category = it },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next)
                    )
                    Box(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                coroutineScope.launch {
                                    while (true) {
                                        // 获取点击位置
                                        val boxOffset = awaitPointerEventScope {
                                            awaitFirstDown().position
                                        }
                                        expanded = true
                                        launch {
                                            animatedOffset.animateTo(
                                                boxOffset,
                                                animationSpec = spring(stiffness = Spring.StiffnessLow)
                                            )
                                        }

                                    }
                                }
                            }
                    ) {
                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier.size(23.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null,
                            )
                        }
                        Box(modifier = Modifier.offset {
                            IntOffset(
                                animatedOffset.value.x.roundToInt(),
                                animatedOffset.value.y.roundToInt()
                            )
                        }) {
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                categorys.forEach {
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = it)
                                        },
                                        onClick = {
                                            category = it
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = "名称", modifier = Modifier.padding(end = 10.dp))
                    AddItemTextField(
                        value = name,
                        placeholder = "点此自定义你的名称",
                        onValueChange = { name = it },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )
                }
            }
            ElevatedCard(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 15.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "账号", modifier = Modifier.padding(end = 10.dp))
                        AddItemTextField(
                            value = number,
                            placeholder = "点此输入你的账号",
                            onValueChange = { number = it },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                        )
                    }
                    Line()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "密码", modifier = Modifier.padding(end = 10.dp))
                        AddItemTextField(
                            value = password,
                            placeholder = "点此输入你的密码",
                            onValueChange = { password = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddItemTextField(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        modifier = modifier
            .fillMaxWidth(),
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.inverseSurface
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.inverseSurface),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterEnd,
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 16.sp,
                    )
                }
                /*TODO:光标位置不居右*/
                innerTextField()
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
    )
}

@Preview
@Composable
fun AddPreview() {
}

