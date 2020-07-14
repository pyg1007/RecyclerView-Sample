package com.yonggeun.recyclerview.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.yonggeun.recyclerview.R
import com.yonggeun.recyclerview.data.Animal
import kotlinx.android.synthetic.main.activity_animal_data_add_dialog.*

class AnimalDataAddDialog(context: Context, private val listener: OnClickListener) :
    Dialog(context) {

    private var type: String? = null

    // Click Interface
    interface OnClickListener {
        fun positiveButtonClick(view: View, animal: Animal)
        fun negativeButtonClick(view: View)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Touch Background Screen No Cancel
        setCanceledOnTouchOutside(false)
        val layoutParam: WindowManager.LayoutParams = WindowManager.LayoutParams()
        layoutParam.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParam.dimAmount = 0.8f
        window?.attributes = layoutParam

        setContentView(R.layout.activity_animal_data_add_dialog)
        setSpinner()
        initUI()
    }

    private fun initUI() {
        Cancel.setOnClickListener {
            listener.negativeButtonClick(it)
            cancel()
        }

        Add.setOnClickListener {
            val name = EditName.text.toString()
            val phoneNum = EditPhoneNumber.text.toString()
            val animal = Animal(type as String, name, phoneNum)
            listener.positiveButtonClick(it, animal)
            dismiss()
        }
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            context,
            R.array.AnimalType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            KindSelect.adapter = adapter
        }

        KindSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = KindSelect.selectedItem.toString()
            }
        }
    }
}
