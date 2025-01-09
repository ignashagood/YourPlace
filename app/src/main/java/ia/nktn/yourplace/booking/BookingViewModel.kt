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
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import ia.nktn.yourplace.retrofit.Result as Result

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: BookingsRepository
) : ViewModel() {

    private val _selectedDateFormatted = MutableStateFlow(formatDate(Date()))
    private val _selectedDate = MutableStateFlow(Date())
    val selectedDate: StateFlow<String> by ::_selectedDateFormatted

    private val _selectedTimeFormatted = MutableStateFlow(formatTime(Date()))
    private val _selectedTime = MutableStateFlow(Date())
    val selectedTime: StateFlow<String> by ::_selectedTimeFormatted

    private val _selectedGuestCount = MutableStateFlow(2)
    val selectedGuestCount: StateFlow<Int> by ::_selectedGuestCount

    private val _selectedTableId = MutableStateFlow<Int?>(null)
    val selectedTableId: StateFlow<Int?> by ::_selectedTableId

    private val _bookResult = MutableStateFlow<Result<Any>?>(null)
    val bookResult: StateFlow<Result<Any>?> by ::_bookResult

    fun emitSelectedDate(date: Date) =
        viewModelScope.launch {
            _selectedDate.emit(date)
            _selectedDateFormatted.emit(formatDate(date))
        }

    fun emitSelectedTime(date: Date) =
        viewModelScope.launch {
            _selectedTime.emit(date)
            _selectedTimeFormatted.emit(formatTime(date))
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
        val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC)
        val calendar = Calendar.getInstance()

        calendar.time = _selectedDate.value

        val timeCalendar = Calendar.getInstance()
        timeCalendar.time = _selectedTime.value

        calendar[Calendar.HOUR_OF_DAY] = timeCalendar[Calendar.HOUR_OF_DAY]
        calendar[Calendar.MINUTE] = timeCalendar[Calendar.MINUTE]
        calendar[Calendar.SECOND] = timeCalendar[Calendar.SECOND]
        calendar[Calendar.MILLISECOND] = timeCalendar[Calendar.MILLISECOND]

        val combinedDate: Date = calendar.time
        val formattedDate = formatter.format(combinedDate.toInstant())
        viewModelScope.launch {
            _selectedTableId.value?.let {
                repository.book(_selectedGuestCount.value, formattedDate, it).collect { result ->
                    _bookResult.value = result
                }
            }
        }
    }
}
