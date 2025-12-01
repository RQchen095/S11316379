package tw.edu.pu.csim.tcyang.s11316379

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class ExamViewModel : ViewModel() {
    // 成績
    var score = mutableIntStateOf(0)

    // 目前碰撞結果訊息
    var message = mutableStateOf("")

    // 是否暫停中
    var isPaused = mutableStateOf(false)

    // 加分
    fun addScore() {
        score.intValue += 1
    }

    // 減分
    fun minusScore() {
        score.intValue -= 1
    }
}