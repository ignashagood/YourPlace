import android.content.SharedPreferences

class ExpiringSharedPreferences(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val TIMESTAMP_SUFFIX = "_timestamp"
        private const val EXPIRATION = 24 * 60 * 60 * 1000
    }

    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.putLong("$key$TIMESTAMP_SUFFIX", System.currentTimeMillis())
        editor.apply()
    }

    fun getString(key: String): String? {
        val timestamp = sharedPreferences.getLong("$key$TIMESTAMP_SUFFIX", 0L)
        val currentTime = System.currentTimeMillis()

        return if (currentTime - timestamp <= EXPIRATION) {
            sharedPreferences.getString(key, null)
        } else {
            remove(key)
            null
        }
    }

    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.remove("$key$TIMESTAMP_SUFFIX")
        editor.apply()
    }
}
