package com.bsuir.drozd.app.screens.app.api

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.drozd.R
import com.bsuir.drozd.app.views.ApiViewModel
import com.bsuir.drozd.databinding.AppDialogBinding
import com.bsuir.drozd.databinding.FragmentHomeForViewingPage2Binding
import okhttp3.internal.notifyAll
import org.apache.commons.lang3.math.NumberUtils

class AppDialog(private val id: Long) : DialogFragment() {

    private lateinit var nameET: EditText
    private lateinit var surnameET: EditText
    private lateinit var patronymicET: EditText
    private lateinit var passportET: EditText
    private lateinit var mobileET: EditText
    private lateinit var amountOfDaysET: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        @SuppressLint("InflateParams") val root: View = inflater.inflate(R.layout.app_dialog, null)
        nameET = root.findViewById<EditText>(R.id.name_dialog)
        surnameET = root.findViewById<EditText>(R.id.surname_dialog)
        patronymicET = root.findViewById<EditText>(R.id.patronymic_dialog)
        passportET = root.findViewById<EditText>(R.id.passport_dialog)
        mobileET = root.findViewById<EditText>(R.id.mobile_dialog)
        amountOfDaysET = root.findViewById<EditText>(R.id.amountOfDays_dialog)

        return builder
            .setView(root)
            .setTitle("Заполните форму:")
            .setPositiveButton("Отправить") { dialog: DialogInterface?, which: Int ->
                if (nameET.text.toString() == "") sendMessage("Введите имя!")
                else if (surnameET.text.toString() == "") sendMessage("Введите фамилию!")
                else if (patronymicET.text.toString() == "") sendMessage("Введите отчество!")
                else if (passportET.text.toString() == "") sendMessage("Введите паспорт!")
                else if (mobileET.text.toString() == "") sendMessage("Введите номер мобильного!")
                else if (!NumberUtils.isParsable(mobileET.text.toString())) sendMessage("Вы ввели не номер!")
                else if (amountOfDaysET.text.toString() == "") sendMessage("Введите кол-во дней!")
                else if (!NumberUtils.isParsable(amountOfDaysET.text.toString())) sendMessage("Вы ввели не цифру!")
                else {
                    var name: String = nameET.text.toString()
                    var surname: String = surnameET.text.toString()
                    var patronymic: String = patronymicET.text.toString()
                    var passport: String = passportET.text.toString()
                    var mobile: Int = mobileET.text.toString().toInt()
                    var amountOfDays: Int = amountOfDaysET.text.toString().toInt()
                    Application.carId = id
                    Application.name = name
                    Application.surname = surname
                    Application.patronymic = patronymic
                    Application.passport = passport
                    Application.mobile = mobile
                    Application.amountOfDays = amountOfDays
                    sendMessage("Заявка оформлена!")
                    sendMessage("Нажмите на отправить заявку!")
                }
            }
            .setNegativeButton("Отменить", null)
            .create()
    }

    private fun sendMessage(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
    }


}