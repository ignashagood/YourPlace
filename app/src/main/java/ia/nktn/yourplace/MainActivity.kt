package ia.nktn.yourplace

import ExpiringSharedPreferences
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ia.nktn.yourplace.auth.AuthFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startFragment: Fragment
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val expiringSharedPreferences = ExpiringSharedPreferences(sharedPreferences)
        val authToken = expiringSharedPreferences.getString("auth_token")
        startFragment =
            if (authToken?.isNotEmpty() == true) {
                PagerFragment()
            } else {
                AuthFragment()
            }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, startFragment)
            .commit()
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction =
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                .replace(R.id.fragment_container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }
}
