package com.yonggeun.recyclerview.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yonggeun.recyclerview.R
import com.yonggeun.recyclerview.adapter.AnimalAdapter
import com.yonggeun.recyclerview.data.Animal
import com.yonggeun.recyclerview.data.AnimalRepository
import com.yonggeun.recyclerview.data.AnimalViewModel
import com.yonggeun.recyclerview.data.AnimalViewModelFactory
import com.yonggeun.recyclerview.databinding.ActivityMainBinding
import com.yonggeun.recyclerview.ui.dialog.AnimalDataAddDialogFragment

class MainActivity : AppCompatActivity(), AnimalAdapter.ItemClickListener {

    private lateinit var animalAdapter: AnimalAdapter
    private var animalItems: MutableList<Animal> = mutableListOf()
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var viewModel: AnimalViewModel
    private lateinit var factory: AnimalViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val repository = AnimalRepository()
        factory = AnimalViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(AnimalViewModel::class.java)

        // MainActivity xml
        // variable name is viewModel
        // right viewModel is AnimalViewModel
        mainBinding.viewModel = viewModel

        // DataBinding lifecycleOwner Enrollment
        mainBinding.lifecycleOwner = this

        initRecyclerView()
        addData()

        // value has viewModel
        animalItems = viewModel.animals.value!!
    }

    private fun addData() {
        mainBinding.Add.setOnClickListener {
            addDialog()
        }
    }

    private fun initRecyclerView() {
        animalAdapter = AnimalAdapter(animalItems, this)
        val layoutManager = LinearLayoutManager(this)

        mainBinding.AnimalRecyclerView.apply {
            this.setHasFixedSize(true)
            this.adapter = animalAdapter
            this.layoutManager = layoutManager
        }

    }

    override fun onItemClick(view: View, position: Int) { // 클릭이벤트 콜백
        Toast.makeText(
            this@MainActivity,
            "이름: ${animalItems[position].name} 연락처: ${animalItems[position].phoneNumber}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        val popupMenu = PopupMenu(this, view, Gravity.END)
        menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item!!.itemId) {
                    R.id.AnimalDataDelete -> {
                        deleteDialog(position)
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }

    private fun deleteDialog(position: Int) {
        val dialog: AlertDialog = this@MainActivity.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            builder.apply {
                this.setMessage("${position+1}번째 내용을 삭제하시겠습니까?")
                this.setCancelable(false)
                this.setPositiveButton("삭제") { dialog, _ ->
                    // LiveData setValue
                    viewModel.remove(animalItems[position])
                    dialog.dismiss()
                }
                this.setNegativeButton("취소") { dialog, _ ->
                    dialog.cancel()
                }
            }
            builder.create()
        }
        dialog.show()
    }

    private fun addDialog() {
        val animalDataAddDialog = AnimalDataAddDialogFragment.getInstance(object : AnimalDataAddDialogFragment.OnClickEvent{
            override fun positiveButtonClick(animal: Animal) {
                viewModel.add(animal)
            }

            override fun negativeButtonClick() {
                Toast.makeText(this@MainActivity, "취소하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        })
        animalDataAddDialog.show(supportFragmentManager, "Add")
    }
}
