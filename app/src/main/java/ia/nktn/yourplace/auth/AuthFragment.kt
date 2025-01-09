package ia.nktn.yourplace.auth

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
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
import ia.nktn.yourplace.databinding.AuthFragmentBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {

    sealed class ViewMode {
        data object Login : ViewMode()
        data object Register : ViewMode()
    }

    var viewMode = ViewMode.Login

    private val viewModel: AuthViewModel by viewModels()

    private var binding: AuthFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = AuthFragmentBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewMode.collect { viewMode ->
                when (viewMode) {
                    is ViewMode.Login -> binding?.apply {
                        title.text = "Авторизация"
                        support.text = "Нет аккаунта?"
                        confirmButton.text = "Войти"
                        passwordCheckTextField.visibility = View.GONE
                        val changeModeText = "Зарегистрируйтесь"
                        changeMode.text = SpannableString(changeModeText).apply {
                            setSpan(
                                UnderlineSpan(),
                                0,
                                changeModeText.length,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }

                    is ViewMode.Register -> binding?.apply {
                        title.text = "Создать аккаунт"
                        support.text = "Уже зарегистрированы?"
                        confirmButton.text = "Перейти к сервису"
                        passwordCheckTextField.visibility = View.VISIBLE
                        val changeModeText = "Войти"
                        changeMode.text = SpannableString(changeModeText).apply {
                            setSpan(
                                UnderlineSpan(),
                                0,
                                changeModeText.length,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }
                }
            }
        }

        binding?.apply {
            changeMode.setOnClickListener {
                viewModel.changeViewMode()
            }

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
                val email = email.text
                val password = password.text
                if (email.isNullOrEmpty()) {
                    emailTextField.error = "Заполните поле"
                    return@setOnClickListener
                }
                if (password.isNullOrEmpty()) {
                    passwordTextField.error = "Заполните поле"
                    return@setOnClickListener
                }
                viewModel.authenticateUser(email.toString(), password.toString())
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