package com.yonggeun.recyclerview

import android.app.Application
import com.yonggeun.recyclerview.data.AnimalRepository

/**
 * RecyclerView
 * Class: MyApplication
 * Created by pyg10.
 * Created On 2021-01-15.
 * Description:
 */
class MyApplication : Application() {

    val repository by lazy {
        AnimalRepository()
    }
}