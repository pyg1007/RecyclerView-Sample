package com.weather.recyclerview.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.weather.recyclerview.R
import com.weather.recyclerview.data.Animal
import com.weather.recyclerview.util.dialogFragmentResize

/**
 * RecyclerView
 * Class: AnimalDataAddDialogFragment
 * Created by pyg10.
 * Created On 2021-01-13.
 * Description:
 */
class AnimalDataAddDialogFragment : DialogFragment() {

    private val fragmentView get() = _fragmentView
    private var _fragmentView: View? = null
    private var type = "Cat"
    private lateinit var name: EditText
    private lateinit var phoneNum: EditText
    private lateinit var spinner: Spinner

    interface OnClickEvent {
        fun positiveButtonClick(animal: Animal)
        fun negativeButtonClick()
    }

    companion object {
        private var clickListener: OnClickEvent? = null
        fun getInstance(listener: OnClickEvent): AnimalDataAddDialogFragment {
            clickListener = listener
            return AnimalDataAddDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentView = inflater.inflate(R.layout.dialog_animal_add, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutParam = WindowManager.LayoutParams().apply {
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.8f
        }
        dialog?.window?.attributes = layoutParam

        setSpinner()
        initUI()
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize(this@AnimalDataAddDialogFragment, 0.95f, 0.5f)
    }

    private fun initUI() {
        fragmentView?.let {
            name = it.findViewById(R.id.EditName)
            phoneNum = it.findViewById(R.id.EditPhoneNumber)
            spinner = it.findViewById(R.id.KindSelect)
        }
        setButton()
    }

    private fun setButton() {
        fragmentView?.let {
            it.findViewById<Button>(R.id.Cancel).setOnClickListener {
                negative()
            }
            it.findViewById<Button>(R.id.Add).setOnClickListener {
                positive()
            }
        }
    }

    private fun positive() {
        val strName = name.text.toString()
        val strPhoneNum = phoneNum.text.toString()

        if (strName.isNotEmpty() && strPhoneNum.isNotEmpty()) {
            val animal = Animal(type, strName, strPhoneNum)
            clickListener?.positiveButtonClick(animal)
            exit()
        } else {
            Toast.makeText(requireContext(), "이름이나 휴대폰번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun negative() {
        clickListener?.negativeButtonClick()
        exit()
    }

    private fun exit() {
        val fragment = fragmentManager?.findFragmentByTag("Add")
        if (fragment is AnimalDataAddDialogFragment)
            fragment.dismiss()
    }

    private fun setSpinner() {
        val spinner = fragmentView?.findViewById<Spinner>(R.id.KindSelect)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AnimalType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner?.adapter = adapter
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = spinner?.selectedItem.toString()
            }
        }
    }


}