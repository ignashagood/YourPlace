package ia.nktn.yourplace.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ia.nktn.yourplace.formatDate
import ia.nktn.yourplace.formatTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel : ViewModel() {

    private val _selectedDate = MutableStateFlow(formatDate(Date()))
    val selectedDate: StateFlow<String> by ::_selectedDate

    private val _selectedTime = MutableStateFlow(formatTime(Date()))
    val selectedTime: StateFlow<String> by ::_selectedTime

    private val _selectedGuestCount = MutableStateFlow(2)
    val selectedGuestCount: StateFlow<Int> by ::_selectedGuestCount

    fun emitSelectedDate(date: Date) =
        viewModelScope.launch {
            _selectedDate.emit(formatDate(date))
        }

    fun emitSelectedTime(date: Date) =
        viewModelScope.launch {
            _selectedTime.emit(formatTime(date))
        }

    fun emitSelectedGuestCount(count: Int) =
        viewModelScope.launch {
            _selectedGuestCount.emit(count)
        }
}