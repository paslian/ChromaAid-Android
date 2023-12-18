package com.example.chromaaid.view.ui.Main.screen.person

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chromaaid.R
import com.example.chromaaid.view.ui.Main.header.Header
import com.example.chromaaid.view.ui.Main.screen.person.content.Deuteranopia
import com.example.chromaaid.view.ui.Main.screen.person.content.Protanopia
import com.example.chromaaid.view.ui.Main.screen.person.content.Tritanopia

@Composable
fun PersonScreen (
    modifier: Modifier = Modifier,
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        stringResource(id = R.string.Protanopia),
        stringResource(id = R.string.Deuteranopia),
        stringResource(id = R.string.Tritanopia)
    )

    Box(modifier = modifier) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
               Header(title = stringResource(R.string.menu_Person))
                Text(
                    text = stringResource(R.string.person_welcome),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.baloo_chettan_2)),
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                )
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    // Create tabs dynamically based on the list
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        )
                    }
                }
                when (selectedTabIndex) {
                    0 -> {
                        Protanopia()
                    }
                    1 -> {
                        Deuteranopia()
                    }
                    2 -> {
                       Tritanopia()
                    }
                }
            }
        }
    }
}