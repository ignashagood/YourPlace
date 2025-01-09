package ia.nktn.yourplace.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ia.nktn.yourplace.data.auth.AuthRepository
import ia.nktn.yourplace.retrofit.Result
import ia.nktn.yourplace.retrofit.Token
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _viewMode = MutableStateFlow<AuthFragment.ViewMode>(AuthFragment.ViewMode.Login)
    val viewMode: StateFlow<AuthFragment.ViewMode> by ::_viewMode

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> by ::_password

    private val _checkPassword = MutableStateFlow("")
    val checkPassword: StateFlow<String> by ::_checkPassword

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> by ::_passwordError

    private val _authErrorMessage = MutableStateFlow("")
    val authErrorMessage: StateFlow<String> by ::_authErrorMessage

    private val _authToken = MutableStateFlow(Token("", ""))
    val authToken: StateFlow<Token> by ::_authToken

    fun changeViewMode() {
        viewModelScope.launch {
            _viewMode.value =
                if (_viewMode.value is AuthFragment.ViewMode.Login) {
                    AuthFragment.ViewMode.Register
                } else {
                    AuthFragment.ViewMode.Login
                }
        }
    }

    fun authenticateUser(email: String, password: String) {
        viewModelScope.launch {
            if (_viewMode.value is AuthFragment.ViewMode.Login) {
                loginUser(email, password)
            } else {
                registerUser(email, password)
            }
        }
    }

    fun onPasswordChanged(text: String) {
        _password.value = text
        checkFieldsEquality()
    }

    fun onCheckPasswordChanged(text: String) {
        _checkPassword.value = text
        checkFieldsEquality()
    }

    private suspend fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            repository.registerUser(email, password).collect { result ->
                when (result) {
                    is Result.Success -> loginUser(email, password)
                    is Result.Error -> _authErrorMessage.value = result.message
                }
            }
        }
    }

    private suspend fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(email, password).collect {
                when (it) {
                    is Result.Success -> _authToken.value = it.data
                    is Result.Error -> _authErrorMessage.value = it.message
                }
            }
        }
    }

    private fun checkFieldsEquality() {
        if (_password.value != _checkPassword.value) {
            _passwordError.value = "Пароли не совпадают"
        } else {
            _passwordError.value = null
        }
    }
}
