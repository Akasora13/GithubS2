package id.Farrel.githubs2.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.Farrel.githubs2.model.User
import id.Farrel.githubs2.ui.detail.follower.FollowerFragment
import id.Farrel.githubs2.ui.detail.following.FollowingFragment

class DetailPagerAdapter(private val activity: DetailActivity, private val user: User) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FollowerFragment.newInstance(user)
            1 -> return FollowingFragment.newInstance(user)
            else -> return FollowerFragment.newInstance(user)
        }
    }
}