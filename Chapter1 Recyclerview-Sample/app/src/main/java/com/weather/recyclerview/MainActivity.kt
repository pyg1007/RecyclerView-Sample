package com.weather.recyclerview

import android.app.Dialog
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.recyclerview.adapter.AnimalAdapter
import com.weather.recyclerview.data.Animal
import com.weather.recyclerview.dialog.AnimalDataAddDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AnimalAdapter.ItemClickListener {

    private lateinit var animalAdapter: AnimalAdapter
    private lateinit var animal: MutableList<Animal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setData()
        initRecyclerView()
        addData()
    }

    private fun addData() {
        Add.setOnClickListener {
            addDialog()
        }
    }

    private fun initRecyclerView() {
        animalAdapter = AnimalAdapter(animal, this)
        val layoutManager = LinearLayoutManager(this)
        AnimalRecyclerView.apply {
            this.setHasFixedSize(true)
            this.layoutManager = layoutManager
            this.adapter = animalAdapter
        }
    }

    //Sample Data
    private fun setData() {
        animal = mutableListOf()
        animal.add(Animal("Cat", "나비", "010-0000-0000"))
        animal.add(Animal("Cat", "나나", "010-0000-0001"))
        animal.add(Animal("Cat", "냐옹", "010-0000-0002"))
        animal.add(Animal("Cat", "별이", "010-0000-0003"))
        animal.add(Animal("Dog", "봄이", "010-0000-0004"))
    }

    override fun onItemClick(view: View, position: Int) { // 클릭이벤트 콜백
        Toast.makeText(
            this@MainActivity,
            "이름: ${animal[position].name} 연락처: ${animal[position].phoneNumber}",
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
        val dialog: AlertDialog? = this@MainActivity.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            builder.apply {
                this.setMessage("${position}번째 내용을 삭제하시겠습니까?")
                this.setCancelable(false)
                this.setPositiveButton("삭제") { dialog, _ ->
                    animalAdapter.animalsDataDelete(position)
                    dialog.dismiss()
                }
                this.setNegativeButton("취소") { dialog, _ ->
                    dialog.cancel()
                }
            }
            builder.create()
        }
        dialog?.show()
    }

    private fun addDialog() {
        val animalDataAddDialog =
            AnimalDataAddDialog(this, object : AnimalDataAddDialog.OnClickListener {
                override fun positiveButtonClick(view: View, animal: Animal) {
                    animalAdapter.animalsDataAdd(animal)
                }

                override fun negativeButtonClick(view: View) {
                    Toast.makeText(this@MainActivity, "취소하셨습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        animalDataAddDialog.show()
        dialogResize(animalDataAddDialog)
    }

    private fun dialogResize(dialog: Dialog) {
        val display = windowManager.defaultDisplay
        val size = Point()

        display.getSize(size)

        val window = dialog.window

        val x = (size.x * 0.95f).toInt()
        val y = (size.y * 0.5f).toInt()

        window?.setLayout(x, y)
    }

}
