package com.xiaosuli.passwordmanager.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchTextField(
    value:String,
    placeholder:String,
    onValueChange:(String)->Unit,
    onClearClick:()->Unit,
    onIconSearchClick:()->Unit,
    onKeyboardSearchClick: KeyboardActionScope.()->Unit,
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .width(280.dp)
            .padding(10.dp),
        singleLine = true,
        placeholder = {
            Text(text = placeholder)
        },
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        leadingIcon = {
            IconButton(onClick = onIconSearchClick) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        trailingIcon = {
            IconButton(onClick =onClearClick) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = onKeyboardSearchClick
        )
    )
}