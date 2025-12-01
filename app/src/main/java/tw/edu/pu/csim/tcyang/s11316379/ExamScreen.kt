package tw.edu.pu.csim.tcyang.s11316379

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExamScreen(viewModel: ExamViewModel = viewModel()) {
    // 取得螢幕寬度與高度 (px)
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    // 黃色背景，內容置中
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 圓形圖片
        Image(
            painter = painterResource(id = R.drawable.happy), // 換成你的圖片
            contentDescription = "圖片",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp)) // 間距 10dp

        // 標題文字
        Text(
            text = "瑪利亞基金會服務大考驗",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 作者資訊 - 改成你的系級與姓名
        Text(
            text = "作者：資管二B 陳若綺",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 顯示螢幕寬度與高度
        Text(
            text = "螢幕大小：${screenWidthPx} * ${screenHeightPx}",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 成績
        Text(
            text = "成績：${viewModel.score.value}分",
            fontSize = 14.sp
        )
    }
}