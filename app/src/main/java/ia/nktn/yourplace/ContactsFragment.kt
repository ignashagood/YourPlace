package ia.nktn.yourplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ia.nktn.yourplace.databinding.ContactsFragmentBinding

class ContactsFragment : Fragment() {

    private var binding: ContactsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ContactsFragmentBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }
}