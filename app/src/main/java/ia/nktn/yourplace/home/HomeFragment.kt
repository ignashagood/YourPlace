package ia.nktn.yourplace.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ia.nktn.yourplace.createDate
import ia.nktn.yourplace.createTime
import ia.nktn.yourplace.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentHomeBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.dateButton?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                it.context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = createDate(selectedYear, selectedMonth, selectedDay)
                    viewModel.emitSelectedDate(selectedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding?.timeButton?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                it.context,
                { _, newHourOfDay, newMinute ->
                    val selectedTime = createTime(newHourOfDay, newMinute)
                    viewModel.emitSelectedTime(selectedTime)
                },
                hourOfDay,
                minute,
                true
            )
            timePickerDialog.show()
        }

        binding?.guestsButton?.setOnClickListener {
            showCustomDialog(requireContext()) { dialog, count ->
                viewModel.emitSelectedGuestCount(count)
                dialog.dismiss()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedDate.collect {
                binding?.dateButton?.text = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedTime.collect {
                binding?.timeButton?.text = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedGuestCount.collect {
                binding?.guestsButton?.text = "$it гостя"
            }
        }
    }
}