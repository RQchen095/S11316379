package tw.edu.pu.csim.tcyang.s11316379

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

// 服務名稱
val serviceNames = listOf(
    "極早期療育",
    "離島服務",
    "極重多障",
    "輔具服務"
)

// 服務對應的正確角色索引（0=嬰幼兒, 1=兒童, 2=成人, 3=一般民眾）
val serviceCorrectTarget = listOf(0, 1, 2, 3)

// 角色名稱
val targetNames = listOf("嬰幼兒", "兒童", "成人", "一般民眾")

@Composable
fun ExamScreen(viewModel: ExamViewModel = viewModel()) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    val iconSizePx = 300f
    val iconSizeDp = with(density) { 300f.toDp() }
    val halfScreenHeightDp = with(density) { (screenHeightPx / 2).toDp() }

    // 服務圖示清單
    val serviceIcons = listOf(
        R.drawable.service0,
        R.drawable.service1,
        R.drawable.service2,
        R.drawable.service3
    )

    // 隨機圖示索引
    var currentIconIndex by remember { mutableIntStateOf(Random.nextInt(serviceIcons.size)) }

    // 掉落圖示的位置
    var fallingX by remember { mutableFloatStateOf(screenWidthPx / 2 - iconSizePx / 2) }
    var fallingY by remember { mutableFloatStateOf(0f) }

    // 是否暫停
    var isPaused by remember { mutableStateOf(false) }

    // 角色位置
    val babyX = 0f
    val babyY = screenHeightPx / 2 - iconSizePx

    val childX = screenWidthPx - iconSizePx
    val childY = screenHeightPx / 2 - iconSizePx

    val adultX = 0f
    val adultY = screenHeightPx - iconSizePx

    val elderX = screenWidthPx - iconSizePx
    val elderY = screenHeightPx - iconSizePx

    // 檢查碰撞
    fun checkCollision(): Int {
        if (fallingX < babyX + iconSizePx && fallingX + iconSizePx > babyX &&
            fallingY < babyY + iconSizePx && fallingY + iconSizePx > babyY) {
            return 0
        }
        if (fallingX < childX + iconSizePx && fallingX + iconSizePx > childX &&
            fallingY < childY + iconSizePx && fallingY + iconSizePx > childY) {
            return 1
        }
        if (fallingX < adultX + iconSizePx && fallingX + iconSizePx > adultX &&
            fallingY < adultY + iconSizePx && fallingY + iconSizePx > adultY) {
            return 2
        }
        if (fallingX < elderX + iconSizePx && fallingX + iconSizePx > elderX &&
            fallingY < elderY + iconSizePx && fallingY + iconSizePx > elderY) {
            return 3
        }
        return -1
    }

    // 每 0.1 秒往下掉 20px
    LaunchedEffect(Unit) {
        while (true) {
            delay(100L)

            if (isPaused) continue

            fallingY += 20f

            val hitTarget = checkCollision()

            if (hitTarget != -1) {
                val correctTarget = serviceCorrectTarget[currentIconIndex]
                val serviceName = serviceNames[currentIconIndex]
                val correctTargetName = targetNames[correctTarget]

                if (hitTarget == correctTarget) {
                    viewModel.addScore()
                } else {
                    viewModel.minusScore()
                }

                // 顯示 Toast
                Toast.makeText(
                    context,
                    "${serviceName}，屬於${correctTargetName}方面的服務",
                    Toast.LENGTH_SHORT
                ).show()

                // 移到螢幕外並暫停
                fallingY = -iconSizePx
                isPaused = true

                delay(3000L)

                // 3 秒後重新開始
                currentIconIndex = Random.nextInt(serviceIcons.size)
                fallingX = screenWidthPx / 2 - iconSizePx / 2
                fallingY = 0f
                isPaused = false

            } else if (fallingY > screenHeightPx) {
                // 掉出螢幕底部
                currentIconIndex = Random.nextInt(serviceIcons.size)
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
            Text(text = "作者：資管二B 陳若綺", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "螢幕大小：${screenWidthPx} * ${screenHeightPx}",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "成績：${viewModel.score.intValue}分", fontSize = 14.sp)
        }

        // ===== 嬰幼兒 =====
        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = "嬰幼兒",
            modifier = Modifier
                .size(iconSizeDp)
                .offset(x = 0.dp, y = halfScreenHeightDp - iconSizeDp)
        )

        // ===== 兒童 =====
        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = "兒童",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.TopEnd)
                .offset(x = 0.dp, y = halfScreenHeightDp - iconSizeDp)
        )

        // ===== 成人 =====
        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = "成人",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.BottomStart)
        )

        // ===== 一般民眾 =====
        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = "一般民眾",
            modifier = Modifier
                .size(iconSizeDp)
                .align(Alignment.BottomEnd)
        )

        // ===== 掉落的服務圖示 =====
        Image(
            painter = painterResource(id = serviceIcons[currentIconIndex]),
            contentDescription = "服務圖示",
            modifier = Modifier
                .size(iconSizeDp)
                .offset { IntOffset(fallingX.toInt(), fallingY.toInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        fallingX += dragAmount.x
                    }
                }
        )
    }
}