package com.yonggeun.recyclerview.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.yonggeun.recyclerview.R
import com.yonggeun.recyclerview.data.Animal
import com.yonggeun.recyclerview.databinding.DialogAnimalAddBinding
import com.yonggeun.recyclerview.util.dialogFragmentResize

/**
 * RecyclerView
 * Class: AnimalDataAddDialogFragment
 * Created by pyg10.
 * Created On 2021-01-13.
 * Description:
 */

class AnimalDataAddDialogFragment : DialogFragment() {

    private val binding get() = _binding!!
    private var _binding: DialogAnimalAddBinding? = null
    private var type = "Cat"

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
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_animal_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialog = this@AnimalDataAddDialogFragment
        val layoutParam = WindowManager.LayoutParams().apply {
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.8f
        }

        dialog?.window?.attributes = layoutParam

        selectionSpinner()
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize(this@AnimalDataAddDialogFragment, 0.95f, 0.5f)
    }

    fun positive() {
        val name = binding.editName.text.toString()
        val phoneNum = binding.editPhoneNumber.text.toString()

        if (name.isNotEmpty() && phoneNum.isNotEmpty()) {
            val animal = Animal(
                type,
                name,
                phoneNum
            )
            clickListener?.positiveButtonClick(animal)
            exit()
        } else {
            Toast.makeText(requireContext(), "이름이나 휴대폰번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    fun negative() {
        clickListener?.negativeButtonClick()
        exit()
    }

    private fun exit() {
        val fragment = fragmentManager?.findFragmentByTag("Add")
        if (fragment is AnimalDataAddDialogFragment)
            fragment.dismiss()
    }

    private fun selectionSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AnimalType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.kindSelect.adapter = adapter
        }

        binding.kindSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = binding.kindSelect.selectedItem.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}