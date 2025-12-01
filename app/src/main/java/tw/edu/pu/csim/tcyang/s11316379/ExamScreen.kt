package tw.edu.pu.csim.tcyang.s11316379

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    // 圖示大小 300px 轉換成 dp
    val iconSizeDp = with(density) { 300f.toDp() }
    // 螢幕一半高度轉成 dp
    val halfScreenHeightDp = with(density) { (screenHeightPx / 2).toDp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // ===== 中間內容 =====
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 圓形圖片
            Image(
                painter = painterResource(id = R.drawable.happy),
                contentDescription = "主圖",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "瑪利亞基金會服務大考驗", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "作者：資管三B 你的姓名", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "螢幕大小：${screenWidthPx} * ${screenHeightPx}",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "成績：${viewModel.score.value}分", fontSize = 14.sp)
        }

        // ===== 嬰幼兒：左邊，下方切齊螢幕 1/2 =====
        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = "嬰幼兒",
            modifier = Modifier
                .size(iconSizeDp)
                .offset(x = 0.dp, y = halfScreenHeightDp - iconSizeDp)
        )

        // ===== 兒童：右邊，下方切齊螢幕 1/2 =====
        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = "兒童",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.TopEnd)
                .offset(x = 0.dp, y = halfScreenHeightDp - iconSizeDp)
        )

        // ===== 成人：左下角 =====
        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = "成人",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.BottomStart)
        )

        // ===== 一般民眾：右下角 =====
        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = "一般民眾",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.BottomEnd)
        )
    }
}