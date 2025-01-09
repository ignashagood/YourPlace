package ia.nktn.yourplace.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ia.nktn.yourplace.MainActivity
import ia.nktn.yourplace.booking.BookingFragment
import ia.nktn.yourplace.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    private var binding: HomeFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = HomeFragmentBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startBookingButton?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(BookingFragment())
        }
    }
}