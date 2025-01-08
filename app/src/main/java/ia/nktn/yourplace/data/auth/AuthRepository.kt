package ia.nktn.yourplace.data.auth

import ia.nktn.yourplace.data.auth.models.UserRegisterResponse
import ia.nktn.yourplace.retrofit.ApiService
import ia.nktn.yourplace.retrofit.Token
import ia.nktn.yourplace.retrofit.UserRegister
import ia.nktn.yourplace.retrofit.withExceptionHandling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import javax.inject.Inject
import ia.nktn.yourplace.retrofit.Result as Result

class AuthRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun registerUser(email: String, password: String): Flow<Result<UserRegisterResponse>> =
        flow {
            val result =
                withExceptionHandling {
                    val user = UserRegister(email, password)
                    apiService.registerUser(user)
                }
            emit(result)
        }

    suspend fun loginUser(email: String, password: String): Flow<Result<Token>> =
        flow {
            val result =
                withExceptionHandling {
                    val body: RequestBody =
                        FormBody.Builder()
                            .add("username", email)
                            .add("password", password)
                            .build()
                    apiService.loginUser(body)
                }
            emit(result)
        }
}