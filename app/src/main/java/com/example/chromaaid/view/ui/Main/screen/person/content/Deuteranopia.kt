package com.example.chromaaid.view.ui.Main.screen.person.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
import com.example.chromaaid.view.ui.Main.screen.person.CharacteristicItem

@Composable
fun Deuteranopia (){
    val askText = buildAnnotatedString {
        append(stringResource(R.string.type_welcome_1) + " ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
            append(stringResource(R.string.deuteranopia_welcome_1))
        }
    }
    Text(
        text = askText,
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.baloo_chettan_2)),
            fontWeight = FontWeight(400),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    )
    val ansText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
            append(stringResource(R.string.Deuteranopia) + " ")
        }
        append(stringResource(R.string.deuteranopia_ans_1) + " ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
            append(stringResource(R.string.deuteranopia_ans_2) + " ")
        }
        withStyle(style = SpanStyle(color = colorResource(id = R.color.green), fontWeight = FontWeight.ExtraBold)) {
            append(stringResource(R.string.color_green))
        }
        append(".")
    }
    Text(
        text = ansText,
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.baloo_chettan_2)),
            fontWeight = FontWeight(400),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.green_palette),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(205.dp)
                .height(139.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Image(
            painter = painterResource(id = R.drawable.deuteranopia),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(126.dp)
                .height(144.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.characteristics_title),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.baloo_chettan_2)),
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        CharacteristicItem(R.string.characteristics_deu_1)
        CharacteristicItem(R.string.characteristics_deu_2)
        CharacteristicItem(R.string.characteristics_deu_3)
    }
}