package com.wcc.whatdoilearn.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wcc.whatdoilearn.data.LearnedItemsDatabase
import com.wcc.whatdoilearn.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.itemsLearnedRecyclerView
        val adapter = LearnedItemsAdapter()
        recyclerView.adapter = adapter

        val database = LearnedItemsDatabase.getDatabase(this, CoroutineScope(Dispatchers.IO))
        val learnedItemsDao = database.learnedItemDao()

        val learnedItems = learnedItemsDao.getAll()
        learnedItems.observe(this, Observer {
            adapter.data = it
        })

        binding.newItemButton.setOnClickListener {
            val intent = Intent(this, NewItemActivity::class.java)
            startActivity(intent)
        }
    }
}