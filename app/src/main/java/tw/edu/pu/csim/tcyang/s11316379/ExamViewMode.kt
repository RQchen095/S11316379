package tw.edu.pu.csim.tcyang.s11316379

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class ExamViewModel : ViewModel() {
    // 成績狀態
    private val _score = mutableStateOf(0)
    val score: State<Int> = _score

    // 之後可以加其他狀態變數
}