package id.Farrel.githubs2.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import id.Farrel.githubs2.R
import id.Farrel.githubs2.databinding.ActivityHomeBinding
import id.Farrel.githubs2.favorite.FavoriteActivity
import id.Farrel.githubs2.interfaces.UserInterface
import id.Farrel.githubs2.model.User
import id.Farrel.githubs2.service.Reminder.ReminderReceiver
import id.Farrel.githubs2.ui.detail.DetailActivity
import id.Farrel.githubs2.util.hide
import id.Farrel.githubs2.util.show

class HomeActivity : AppCompatActivity(), UserInterface {

    companion object {
        var REMINDER_FLAG = false
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: HomeAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var reminderReceiver: ReminderReceiver

    private var users = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.title = ""


        adapter = HomeAdapter(this)
        adapter.userInterface = this
        binding.recyclerViewUsers.adapter = adapter

        reminderReceiver = ReminderReceiver()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]

        searchUser()
        setViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_languagesChange -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.action_dailyReminder -> {
                REMINDER_FLAG = if (REMINDER_FLAG == false) {
                    reminderReceiver.setAlarm(this)
                    true
                } else {
                    reminderReceiver.cancelAlarm(this)
                    false
                }
            }
            R.id.action_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun searchUser() {
        binding.searchviewHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        users.clear()
                        viewModel.setUsers(query)
                        setViewModel()
                        emptyState(false)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }

    private fun setViewModel() {
        viewModel.getUsers().observe(this, { result ->
            if (result != null && result.size > 0) {
                users = result
                adapter.submitList(users)
                adapter.notifyDataSetChanged()
                emptyState(false)
            } else emptyState(true)
        })

        viewModel.state.observeForever {
            it["loading"]?.let { isLoading ->
                loadingState(isLoading)
            }
            it["empty"]?.let { isEmpty ->
                emptyState(isEmpty)
            }
            it["error"]?.let { isError ->
                if (isError) {
                    viewModel.state.postValue(mapOf("error" to false))
                }
            }
        }
    }

    private fun emptyState(state: Boolean) {
        if (state) {
            binding.apply {
                emptyUserImage.show()
                recyclerViewUsers.hide()
            }
        } else {
            binding.apply {
                emptyUserImage.hide()
                recyclerViewUsers.show()
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

    override fun onUserClicked(view: View, user: User) {
        val intent = Intent(
            this, DetailActivity::class.java
        )
        intent.putExtra(DetailActivity.USER, user)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}