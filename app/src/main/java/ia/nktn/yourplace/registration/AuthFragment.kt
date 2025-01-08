package ia.nktn.yourplace.registration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ia.nktn.yourplace.MainActivity
import ia.nktn.yourplace.PagerFragment
import ia.nktn.yourplace.databinding.FragmentAuthBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()

    private var binding: FragmentAuthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentAuthBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            email.doOnTextChanged { text, _, _, _ ->
                if (text?.isNotEmpty() == true) {
                    emailTextField.isErrorEnabled = false
                }
            }

            password.doOnTextChanged { text, _, _, _ ->
                viewModel.onPasswordChanged(text.toString())
            }

            checkPassword.doOnTextChanged { text, _, _, _ ->
                viewModel.onCheckPasswordChanged(text.toString())
            }

            viewLifecycleOwner.lifecycleScope.launch {
                launch {
                    viewModel.passwordError.collect { error ->
                        passwordCheckTextField.error = error
                    }
                }
                launch {
                    viewModel.registerErrorMessage.collect {
                        if (it == "REGISTER_USER_ALREADY_EXISTS") {
                            emailTextField.isErrorEnabled = true
                            emailTextField.error = "Такой пользователь уже существует"
                        } else {
                            errorMessage.text = it
                            errorMessage.isVisible = true
                        }
                    }
                }
                launch {
                    viewModel.authToken.collect {
                        if (it.access_token.isNotEmpty()) {
                            saveToken(it.access_token)
                            (activity as? MainActivity)?.replaceFragment(PagerFragment())
                        }
                    }
                }
            }

            confirmButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    val email = email.text
                    val password = password.text
                    if (email.isNullOrEmpty()) {
                        emailTextField.error = "Заполните поле"
                        return@launch
                    }
                    if (password.isNullOrEmpty()) {
                        passwordTextField.error = "Заполните поле"
                        return@launch
                    }
                    viewModel.registerUser(email.toString(), password.toString())
                }
            }
        }
    }

    private fun saveToken(token: String) {
        val sharedPreferences =
            requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.putBoolean("is_logged_in", true)
        editor.apply()
    }
}