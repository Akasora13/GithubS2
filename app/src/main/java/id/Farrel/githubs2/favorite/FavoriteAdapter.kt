package id.Farrel.githubs2.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.Farrel.githubs2.R
import id.Farrel.githubs2.databinding.ItemFavoriteBinding
import id.Farrel.githubs2.interfaces.UserInterface
import id.Farrel.githubs2.model.User

class FavoriteAdapter(private val context: Context) : // cross mix dari kodingan dicoding sama farhan, kalo recycler error disini salahnya
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var userInterface: UserInterface? = null
    var listFavorite = ArrayList<User>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {


        fun bind(user: User, userInterface: UserInterface?) {
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.photoListuser) //kayaknya bikin error kata parsing data masing string bukan byte


            binding.apply {
                tvUsernameListuser.text = user.username
                tvFollower.text =
                    String.format(context.resources.getString(R.string.n_follower), user.followers)
                tvFollowing.text =
                    String.format(context.resources.getString(R.string.n_following), user.following)

                constraintLayoutUserRoot.setOnClickListener {
                    userInterface?.onUserClicked(
                        binding.root,
                        user
                    )

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false), context)

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position], userInterface)
    }

    override fun getItemCount(): Int = this.listFavorite.size

}

