package ia.nktn.yourplace.data.booking

import ia.nktn.yourplace.retrofit.ApiService
import ia.nktn.yourplace.retrofit.Result
import ia.nktn.yourplace.retrofit.withExceptionHandling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.FormBody
import okhttp3.RequestBody
import javax.inject.Inject

class BookingsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun book(guestCount: Int, date: String, tableId: Int): Flow<Result<Any>> =
        flow {
            val result =
                withExceptionHandling {
                    val body: RequestBody =
                        FormBody.Builder()
                            .add("guest_count", guestCount.toString())
                            .add("reservation_date", date)
                            .add("table_number", tableId.toString())
                            .build()
                    apiService.bookResult(body)
                }
            emit(result)
        }
}
