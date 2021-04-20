package id.Farrel.githubs2.ui.detail

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import id.Farrel.githubs2.R
import id.Farrel.githubs2.database.DatabaseContract
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.BIO
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.COMPANY
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWERS
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWERS_URL
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.FOLLOWING_URL
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.IS_FAVORITE
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.LOCATION
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.LOGIN
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.NAME
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.PUBLIC_REPOS
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion._ID
import id.Farrel.githubs2.database.FavoriteHelper
import id.Farrel.githubs2.databinding.ActivityDetailBinding
import id.Farrel.githubs2.favorite.FavoriteActivity
import id.Farrel.githubs2.favorite.FavoriteAdapter
import id.Farrel.githubs2.model.User

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailPagerAdapter
    private lateinit var user: User
    private lateinit var fav_adapter: FavoriteAdapter
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var favoriteActivity: FavoriteActivity


    companion object {
        const val USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fav_adapter = FavoriteAdapter(this)
        favoriteHelper = FavoriteHelper(this)
        favoriteHelper.open()

        favoriteActivity = FavoriteActivity()

        binding.ivActionBack.setOnClickListener { onBackPressed() }

        if (!intent.hasExtra(USER)) {
            onBackPressed()
        }
        user = intent?.getParcelableExtra<User>(USER) as User //gatau deh error kenapa

        Glide.with(this)
            .load(user.avatar)
            .into(binding.ivAvatar)
        binding.tvName.text = user.name
        binding.tvCompany.text = user.company

        initViewPager()
        isFavorite()

        binding.sbFavorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                user.is_Favorite = 1
                addFavorite()

            } else {
                user.is_Favorite = 0
                deleteFavorite()
            }
        }


    }

    private fun initViewPager() {
        adapter = DetailPagerAdapter(this, user)
        binding.viewPagerDetail.adapter = adapter
        binding.viewPagerDetail.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabLayoutDetail,
            binding.viewPagerDetail
        ) { tab, position ->
            when (position) {
                0 -> tab.text = String.format(
                    resources.getString(R.string.n_follower),
                    user.followers
                )
                1 -> tab.text = String.format(
                    resources.getString(R.string.n_following),
                    user.following
                )
            }
        }.attach()
    }

    private fun addFavorite(){
        val values = ContentValues()
        values.put(_ID, user.id)
        values.put(LOGIN, user.username)
        values.put(NAME, user.name)
        values.put(COMPANY, user.company)
        values.put(LOCATION, user.location)
        values.put(BIO, user.bio)
        values.put(PUBLIC_REPOS, user.repositories)
        values.put(FOLLOWERS, user.followers)
        values.put(FOLLOWING, user.following)
        values.put(FOLLOWERS_URL, user.followersUrl)
        values.put(FOLLOWING_URL, user.followingUrl)
        values.put(AVATAR_URL, user.avatar)
        values.put(IS_FAVORITE, user.is_Favorite)

        favoriteHelper.insert(values)
    }

    private fun deleteFavorite(){
        favoriteHelper.deleteById(user?.id.toString()).toLong()

    }

    private fun isFavorite(){
        if (user.is_Favorite == 1){
            binding.sbFavorite.toggle()
        }
    }


}