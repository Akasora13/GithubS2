package id.Farrel.githubs2.ui.detail.following

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.Farrel.githubs2.R
import id.Farrel.githubs2.databinding.ItemUserBinding
import id.Farrel.githubs2.interfaces.UserInterface
import id.Farrel.githubs2.model.User

class FollowingAdapter(private val context: Context) :
    androidx.recyclerview.widget.ListAdapter<User, FollowingAdapter.ViewHolder>(DiffCallback()) {

    var userInterface: UserInterface? = null

    inner class ViewHolder(private val binding: ItemUserBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, userInterface: UserInterface?) {
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.photoListuser)

            binding.apply {
                tvUsernameListuser.text = user.username
                tvFollower.text = String.format(context.resources.getString(R.string.n_follower), user.followers)
                tvFollowing.text = String.format(context.resources.getString(R.string.n_following), user.following)

                constraintLayoutUserRoot.setOnClickListener {
                    userInterface?.onUserClicked(
                        binding.root, user
                    )
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false), context
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), userInterface)
    }

}

class DiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.username == newItem.username
}