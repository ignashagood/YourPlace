package ia.nktn.yourplace.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ia.nktn.yourplace.data.booking.BookingsRepository
import ia.nktn.yourplace.formatDate
import ia.nktn.yourplace.formatTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: BookingsRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(formatDate(Date()))
    val selectedDate: StateFlow<String> by ::_selectedDate

    private val _selectedTime = MutableStateFlow(formatTime(Date()))
    val selectedTime: StateFlow<String> by ::_selectedTime

    private val _selectedGuestCount = MutableStateFlow(2)
    val selectedGuestCount: StateFlow<Int> by ::_selectedGuestCount

    private val _selectedTableId = MutableStateFlow<Int?>(null)
    val selectedTableId: StateFlow<Int?> by ::_selectedTableId

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

    fun emitSelectedTableId(id: Int?) =
        viewModelScope.launch {
            _selectedTableId.emit(id)
        }

    fun bookTable() {

    }
}