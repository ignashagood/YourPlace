package ia.nktn.yourplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ia.nktn.yourplace.databinding.FragmentPagerBinding

class PagerFragment : Fragment() {

    private var binding: FragmentPagerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentPagerBinding.inflate(inflater, container, false).run {
        binding = this
        pager.adapter = PagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        pager.isUserInputEnabled = false
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.page_1 -> {
                        pager.currentItem = 0
                        true
                    }
                    R.id.page_2 -> {
                        pager.currentItem = 1
                        true
                    }
                    R.id.page_3 -> {
                        pager.currentItem = 2
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        binding?.run {
            pager.adapter = null
            binding = null
        }
        super.onDestroyView()
    }
}