package com.uvarenko.exchangerateapp.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvarenko.exchangerateapp.db.ExchangeRateEntity
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(addCurrency: () -> Unit = {}) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Exchange Rates",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addCurrency) {
                Icon(Icons.Filled.Add, "Add currency.")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val vm = koinViewModel<HomeViewModel>()
            val exchangeRateSate by vm.exchangeRateSate.collectAsStateWithLifecycle()
            val requestState by vm.requestState.collectAsStateWithLifecycle()
            if (requestState != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    text = requestState?.message.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (exchangeRateSate.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = rememberLazyListState(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(exchangeRateSate, key = { it.currency }) { item ->
                        ExchangeRateItem(
                            modifier = Modifier.animateItem(),
                            exchangeRate = item,
                            onDelete = vm::remove
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExchangeRateItem(
    modifier: Modifier = Modifier,
    exchangeRate: ExchangeRateEntity = ExchangeRateEntity(currency = "UAH", rate = 41.25),
    onDelete: (ExchangeRateEntity) -> Unit = {}
) {
    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = { Box(modifier = Modifier.fillMaxSize()) }
    ) {
        Card(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = exchangeRate.currency,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                AnimatedContent(exchangeRate.rate) { targetRate ->
                    Text(
                        text = String.format(Locale.getDefault(), "%.2f", targetRate),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }

    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            LaunchedEffect(dismissState.currentValue) {
                onDelete(exchangeRate)
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }

        }

        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(dismissState.currentValue) {
                dismissState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }

        SwipeToDismissBoxValue.Settled -> Unit
    }
}