package ia.nktn.yourplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ia.nktn.yourplace.databinding.MyBookingsFragmentBinding

class MyBookingsFragment : Fragment() {

    private var binding: MyBookingsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = MyBookingsFragmentBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }
}