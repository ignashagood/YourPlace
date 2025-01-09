package ia.nktn.yourplace.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    private var selectedTableId: Int? = null

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
                listOf(
                    table1,
                    table2,
                    table3,
                    table4,
                    table5,
                    table6,
                    table7,
                    table8,
                    table9,
                    table10,
                    table11,
                    table12,
                    table13,
                    table14,
                    table15,
                    table16
                )
            val context = requireContext()
            tables.forEach { table ->
                table.setOnClickListener {
                    val selectedTable =
                        viewModel.selectedTableId.value?.let { tableId ->
                            this@SelectTableFragment.view?.findViewById<TextView>(tableId)
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
                            table.id
                        }
                    )
                }
            }

            bookButton.setOnClickListener {

            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.selectedTableId.collect {
                    bookButton.isEnabled = it != null
                }
            }
        }
    }
}