package id.Farrel.githubs2.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import id.Farrel.githubs2.R
import id.Farrel.githubs2.database.FavoriteHelper
import id.Farrel.githubs2.databinding.ActivityFavoriteBinding
import id.Farrel.githubs2.helper.MappingHelper
import id.Farrel.githubs2.interfaces.UserInterface
import id.Farrel.githubs2.model.User
import id.Farrel.githubs2.ui.detail.DetailActivity
import id.Farrel.githubs2.util.hide
import id.Farrel.githubs2.util.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity(), UserInterface {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.title = ""

        loadNotesAsync()

        adapter = FavoriteAdapter(this)
        adapter.userInterface = this
        binding.rvFavorite.adapter = adapter

        binding.actionBack.setOnClickListener{
            onBackPressed()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_refresh){
            loadNotesAsync()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUserClicked(view: View, user: User) {
        val intent = Intent(
            this, DetailActivity::class.java
        )
        intent.putExtra(DetailActivity.USER, user)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun loadNotesAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            emptyState(true)
            loadingState(true)
            val favHelper = FavoriteHelper.getInstance(applicationContext)
            favHelper.open()
            val defferedFav = async(Dispatchers.IO) {
                val cursor = favHelper.queryAll()
                MappingHelper.mapToCursorArrayList(cursor)
            }
            loadingState(false)
            val user = defferedFav.await()
            favHelper.close()
            if (user.size > 0){
                adapter.listFavorite = user  // error
                emptyState(false)
            }else{
                adapter.listFavorite = ArrayList()
                emptyState(true)
            }
        }
    }

    private fun emptyState(state: Boolean) {
        if (state) {
            binding.apply {
                emptyUserImage.show()
                rvFavorite.hide()
            }
        } else {
            binding.apply {
                emptyUserImage.hide()
                rvFavorite.show()
            }
        }
        binding.progressBarHome.hide()
    }

    private fun loadingState(state: Boolean) {
        if (state) {
            binding.progressBarHome.show()
        } else {
            binding.progressBarHome.hide()
        }

    }

}