package ia.nktn.yourplace.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import ia.nktn.yourplace.MainActivity
import ia.nktn.yourplace.R
import ia.nktn.yourplace.createDate
import ia.nktn.yourplace.createTime
import ia.nktn.yourplace.databinding.BookingFragmentBinding
import ia.nktn.yourplace.home.showCustomDialog
import kotlinx.coroutines.launch
import java.util.Calendar

class BookingFragment : Fragment() {

    private val viewModel: BookingViewModel by activityViewModels()

    private var binding: BookingFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = BookingFragmentBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.dateValue?.setOnClickListener {
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

        binding?.timeValue?.setOnClickListener {
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

        binding?.guestsValue?.setOnClickListener {
            showCustomDialog(requireContext()) { dialog, count ->
                viewModel.emitSelectedGuestCount(count)
                dialog.dismiss()
            }
        }

        binding?.bookButton?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(SelectTableFragment())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedDate.collect {
                binding?.dateValue?.setText(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedTime.collect {
                binding?.timeValue?.setText(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedGuestCount.collect {
                binding?.guestsValue?.setText(
                    when (it) {
                        1, 5, 6 -> resources.getString(R.string.one_person, it)
                        else -> resources.getString(R.string.many_person, it)
                    }
                )
            }
        }
    }
}