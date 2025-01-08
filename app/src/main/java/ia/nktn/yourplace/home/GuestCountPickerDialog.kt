package ia.nktn.yourplace.home

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import ia.nktn.yourplace.R

fun showCustomDialog(context: Context, onNumberSelected: (AlertDialog, Int) -> Unit) {
    // Создаем разметку для диалога
    val dialogView = LayoutInflater.from(context).inflate(R.layout.guest_count_picker_dialog, null)

    // Создаем AlertDialog.Builder
    val builder = AlertDialog.Builder(context, R.style.RoundedDialogStyle)
        .setView(dialogView)         // Устанавливаем кастомную разметку

    // Ищем кнопки по ID
    val btn1: Button = dialogView.findViewById(R.id.guestCountPickerDialogBtn1)
    val btn2: Button = dialogView.findViewById(R.id.guestCountPickerDialogBtn2)
    val btn3: Button = dialogView.findViewById(R.id.guestCountPickerDialogBtn3)
    val btn4: Button = dialogView.findViewById(R.id.guestCountPickerDialogBtn4)
    val btn5: Button = dialogView.findViewById(R.id.guestCountPickerDialogBtn5)
    val btn6: Button = dialogView.findViewById(R.id.guestCountPickerDialogBtn6)

    val dialog = builder.create()

    // Устанавливаем обработчики для кнопок
    btn1.setOnClickListener {
        onNumberSelected(dialog, 1)  // Передаем выбранное число
    }
    btn2.setOnClickListener {
        onNumberSelected(dialog, 2)
    }
    btn3.setOnClickListener {
        onNumberSelected(dialog, 3)
    }
    btn4.setOnClickListener {
        onNumberSelected(dialog, 4)  // Передаем выбранное число
    }
    btn5.setOnClickListener {
        onNumberSelected(dialog, 5)
    }
    btn6.setOnClickListener {
        onNumberSelected(dialog, 6)
    }

    dialog.show()
}