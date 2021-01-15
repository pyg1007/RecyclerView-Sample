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
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import com.yonggeun.recyclerview.R
import com.yonggeun.recyclerview.adapter.AnimalAdapter
import com.yonggeun.recyclerview.data.Animal
import com.yonggeun.recyclerview.databinding.ActivityMainBinding
import com.yonggeun.recyclerview.ui.dialog.AnimalDataAddDialogFragment

class MainActivity : AppCompatActivity(), AnimalAdapter.ItemClickListener {

    private lateinit var animalAdapter: AnimalAdapter
    private lateinit var animals: ObservableArrayList<Animal>

    // DataBinding
    // Rebuild when an error occurs
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect DataBinding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setData()
        initRecyclerView()
        addData()
    }

    private fun addData() {
        mainBinding.add.setOnClickListener {
            addDialog()
        }
    }

    private fun initRecyclerView() {
        animalAdapter = AnimalAdapter(animals, this)
        val layoutManager = LinearLayoutManager(this)
        mainBinding.animalRecyclerView.apply {
            this.setHasFixedSize(true)
            this.adapter = animalAdapter
            this.layoutManager = layoutManager
        }

        // RecyclerView Xml
        // use androidx.databinding.ObservableArrayList
        // app:item = "@{animalList}"
        mainBinding.animalList = animals
    }

    //Sample Data
    private fun setData() {
        animals = ObservableArrayList()
        animals.add(Animal("Cat", "나비", "010-0000-0000"))
        animals.add(Animal("Cat", "나나", "010-0000-0001"))
        animals.add(Animal("Cat", "냐옹", "010-0000-0002"))
        animals.add(Animal("Cat", "별이", "010-0000-0003"))
        animals.add(Animal("Dog", "봄이", "010-0000-0004"))
    }

    override fun onItemClick(view: View, position: Int) { // 클릭이벤트 콜백
        Toast.makeText(
            this@MainActivity,
            "이름: ${animals[position].name} 연락처: ${animals[position].phoneNumber}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        val popupMenu = PopupMenu(this, view, Gravity.END)
        menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item!!.itemId) {
                    R.id.animalDataDelete -> {
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
                this.setMessage("${position}번째 내용을 삭제하시겠습니까?")
                this.setCancelable(false)
                this.setPositiveButton("삭제") { dialog, _ ->
                    animals.removeAt(position)
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
                animals.add(animal)
            }

            override fun negativeButtonClick() {
                Toast.makeText(this@MainActivity, "취소하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        })
        animalDataAddDialog.show(supportFragmentManager, "Add")
    }
}
