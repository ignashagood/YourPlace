package ia.nktn.yourplace.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import ia.nktn.yourplace.R
import ia.nktn.yourplace.databinding.SelectTableFragmentBinding
import kotlinx.coroutines.launch

class SelectTableFragment : Fragment() {

    private val viewModel: BookingViewModel by activityViewModels()

    private var binding: SelectTableFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = SelectTableFragmentBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            val tables =
                mapOf(
                    table1 to 1,
                    table2 to 2,
                    table3 to 3,
                    table4 to 4,
                    table5 to 5,
                    table6 to 6,
                    table7 to 7,
                    table8 to 8,
                    table9 to 9,
                    table10 to 10,
                    table11 to 11,
                    table12 to 12,
                    table13 to 13,
                    table14 to 14,
                    table15 to 15,
                    table16 to 16
                )
            val context = requireContext()
            tables.keys.forEach { table ->
                table.setOnClickListener {
                    val selectedTable =
                        viewModel.selectedTableId.value?.let { tableId ->
                            tables.entries
                                .find { it.value == tableId }
                                ?.key
                        }
                    viewModel.emitSelectedTableId(
                        if (selectedTable == table) {
                            table.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.brown_light)
                            )
                            null
                        } else {
                            selectedTable?.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.brown_light)
                            )
                            table.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.brown_dark)
                            )
                            tables[table]
                        }
                    )
                }
            }

            bookButton.setOnClickListener {
                viewModel.bookTable()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.selectedTableId.collect {
                    bookButton.isEnabled = it != null
                }
            }
        }
    }
}
