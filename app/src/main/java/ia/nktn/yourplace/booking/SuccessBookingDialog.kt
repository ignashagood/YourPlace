package ia.nktn.yourplace.booking

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import ia.nktn.yourplace.R

fun showSuccessBookingDialog(context: Context, date: String, time: String, onButtonClick: (AlertDialog) -> Unit) {
    val dialogView = LayoutInflater.from(context).inflate(R.layout.success_book_dialog, null)

    val builder = AlertDialog.Builder(context, R.style.RoundedDialogStyle).setView(dialogView)

    val datetimeTextView: TextView = dialogView.findViewById(R.id.datetime_text)
    val confirmButton: TextView = dialogView.findViewById(R.id.backToHomeButton)

    datetimeTextView.text = "Будем ждать вас $date в $time"

    val dialog = builder.create()


    confirmButton.setOnClickListener {
        onButtonClick(dialog)
    }

    dialog.show()
}
