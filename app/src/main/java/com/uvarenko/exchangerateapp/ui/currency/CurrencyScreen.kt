package com.uvarenko.exchangerateapp.ui.currency

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvarenko.exchangerateapp.domain.CurrencyModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    goBack: () -> Unit = {}
) {
    val vm = koinViewModel<CurrencyViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            var query by remember { mutableStateOf("") }
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = query,
                        onValueChange = {
                            query = it
                            vm.filter(query)
                        },
                        placeholder = { Text("Search...") },
                        singleLine = true,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        query = ""
                                        vm.filter(query)
                                    }
                                ) {
                                    Icon(Icons.Filled.Clear, contentDescription = "Clear Text")
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val currencyState by vm.currencyState.collectAsStateWithLifecycle()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(currencyState) { index, item ->
                CurrencyItem(
                    currencyModel = item,
                    onClick = { vm.select(index) }
                )
            }
        }
    }
}

@Preview
@Composable
fun CurrencyItem(
    modifier: Modifier = Modifier,
    currencyModel: CurrencyModel = CurrencyModel("UAH", false),
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        border = if (currencyModel.selected) BorderStroke(2.dp, Color.Black) else null,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = currencyModel.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}