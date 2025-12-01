package tw.edu.pu.csim.tcyang.s11316379

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ExamScreen(viewModel: ExamViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    // 圖示大小
    val iconSizeDp = with(density) { 300f.toDp() }
    val iconSizePx = 300f
    val halfScreenHeightDp = with(density) { (screenHeightPx / 2).toDp() }

    // 服務圖示清單（換成你的圖片）
    val serviceIcons = listOf(
        R.drawable.service1,
        R.drawable.service2,
        R.drawable.service3,
        R.drawable.service0,
    )

    // 隨機圖示索引
    var currentIconIndex by remember { mutableIntStateOf(Random.nextInt(serviceIcons.size)) }

    // 掉落圖示的位置
    var fallingX by remember { mutableFloatStateOf(screenWidthPx / 2 - iconSizePx / 2) }
    var fallingY by remember { mutableFloatStateOf(0f) }

    // 每 0.1 秒往下掉 20px
    LaunchedEffect(currentIconIndex) {
        while (true) {
            delay(100L)  // 0.1 秒
            fallingY += 20f  // 往下 20px

            // 如果碰到螢幕下方
            if (fallingY > screenHeightPx) {
                // 重新產生隨機圖示
                currentIconIndex = Random.nextInt(serviceIcons.size)
                // 回到螢幕上方中間
                fallingX = screenWidthPx / 2 - iconSizePx / 2
                fallingY = 0f
            }
        }
    }

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

        // ===== 掉落的服務圖示（可左右拖曳）=====
        Image(
            painter = painterResource(id = serviceIcons[currentIconIndex]),
            contentDescription = "服務圖示",
            modifier = Modifier
                .size(iconSizeDp)
                .offset { IntOffset(fallingX.toInt(), fallingY.toInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        // 只允許左右拖曳
                        fallingX += dragAmount.x
                    }
                }
        )
    }
}