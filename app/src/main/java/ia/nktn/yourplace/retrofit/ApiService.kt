package ia.nktn.yourplace.retrofit

import ia.nktn.yourplace.data.auth.models.UserRegisterResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("api/v1/auth/register")
    suspend fun registerUser(@Body user: UserRegister): Response<UserRegisterResponse>

    @POST("api/v1/auth/login")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun loginUser(@Body body: RequestBody): Response<Token>
}

data class UserRegister(
    val email: String,
    val password: String
)

data class Token(
    val access_token: String,
    val token_type: String
)

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

suspend fun <T : Any> withExceptionHandling(block: suspend () -> Response<T>): Result<T> {

    return try {
        val response = block()
        ResponseBody
        if (response.isSuccessful) {
            Result.Success(response.body() as T)
        } else {
            val errorDetail = response.errorBody()?.string()
            Result.Error(errorDetail?.substring(11, errorDetail.length - 2) ?: response.code().toString())
        }
    } catch (e: Exception) {
        Result.Error(e.toString())
    }
}