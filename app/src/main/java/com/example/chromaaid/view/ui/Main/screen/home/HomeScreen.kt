package com.example.chromaaid.view.ui.Main.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chromaaid.R
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.chromaaid.view.ui.Detect.DetectImgGalleryActivity
import com.example.chromaaid.view.ui.Main.header.Header
import com.example.chromaaid.view.ui.scanner.ScanActivity


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    context: Context
){
    LaunchedEffect(Unit) {
        viewModel.initialize(context)
    }
    Box(modifier = modifier) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
              Header(title = stringResource(R.string.menu_home))

                val annotatedText = buildAnnotatedString {
                    append(stringResource(R.string.welcome_1) + " ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                        append(stringResource(R.string.welcome_2))
                    }
                }
                Text(
                    text = annotatedText,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.baloo_chettan_2)),
                        fontWeight = FontWeight(400),
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
                        .padding(10.dp)
                        .clickable {
                            val intent = Intent(context, ScanActivity::class.java)
                            context.startActivity(intent)
                        },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.scanner_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    )
                    Text(
                        text = stringResource(R.string.take_photo),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.baloo_chettan_2)),
                            fontWeight = FontWeight(400),
                        ),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                    )
                }
                Divider(
                    color = MaterialTheme.colorScheme.onBackground,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val itemCount = viewModel.spList.value?.size ?: 0
                    val rows = (itemCount + 2) / 3
                    val rowHeights = (0 until rows).map { index ->
                        if (index == 0) {
                            110.dp
                        } else {
                            100.dp
                        }
                    }
                    val totalHeight = rowHeights.sumOf { it.value.toInt() }.dp
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (viewModel.spList.value.isNullOrEmpty()) 100.dp else totalHeight)
                            .background(color = colorResource(id = R.color.blue_light))
                    ) {
                        if (viewModel.spList.value.isNullOrEmpty()) {
                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.eyes))
                            val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
                            LottieAnimation(
                                composition = composition,
                                progress = { progress },
                            )
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(90.dp),
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier
                            ) {
                                items(viewModel.spList.value?.size ?: 0) { index ->
                                    Card(
                                        modifier = Modifier
                                            .clickable{
                                                val intent = Intent(context,
                                                    DetectImgGalleryActivity::class.java)
                                                intent.putExtra("photoIndex",index)
                                                context.startActivity(intent)
                                            },
                                        shape = RoundedCornerShape(5.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .height(90.dp)
                                                .fillMaxWidth()
                                                .background(color = Color.Gray),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            viewModel.spList.value?.getOrNull(index)?.let { photo ->
                                                val bitmapOptions = BitmapFactory.Options()
                                                bitmapOptions.inSampleSize = 2

                                                val bitmap = BitmapFactory.decodeFile(photo.absolutePath, bitmapOptions)
                                                Image(
                                                    bitmap = bitmap.asImageBitmap(),
                                                    contentDescription = null,
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .clip(RoundedCornerShape(8.dp))
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



