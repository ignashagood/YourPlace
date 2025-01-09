package ia.nktn.yourplace

import ExpiringSharedPreferences
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ia.nktn.yourplace.auth.AuthFragment
import ia.nktn.yourplace.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    private var binding: ProfileFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ProfileFragmentBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.exit?.setOnClickListener {
            val sharedPreferences =
                requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val expiringSharedPreferences = ExpiringSharedPreferences(sharedPreferences)
            expiringSharedPreferences.remove("auth_token")
            (activity as? MainActivity)?.replaceFragment(AuthFragment(), false)
        }
    }
}
