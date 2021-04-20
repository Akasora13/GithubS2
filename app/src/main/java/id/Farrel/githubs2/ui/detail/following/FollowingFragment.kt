package id.Farrel.githubs2.ui.detail.following

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.Farrel.githubs2.databinding.FragmentFollowingBinding
import id.Farrel.githubs2.interfaces.UserInterface
import id.Farrel.githubs2.model.User
import id.Farrel.githubs2.ui.detail.DetailActivity
import id.Farrel.githubs2.util.hide
import id.Farrel.githubs2.util.show

class FollowingFragment: Fragment(), UserInterface {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var user: User
    private lateinit var viewModel: FollowingViewModel
    private lateinit var following: ArrayList<User>
    private lateinit var adapter: FollowingAdapter

        companion object {
            fun newInstance(user:User): FollowingFragment {
                val args = Bundle()
                args.putParcelable(DetailActivity.USER, user)

                val fragment = FollowingFragment()
                fragment.arguments = args
                return fragment
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getParcelable<User>(DetailActivity.USER) as User //gatau lagi T-T
        following = ArrayList()

        viewModel =  ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]

        adapter = FollowingAdapter(requireActivity())
        adapter.userInterface = this
        binding.recyclerViewFollowingUsers.adapter = adapter

        showFollowing(user.username)
    }

    private fun setViewModel(){
        viewModel.getUserData().observe(requireActivity(), { result ->
            if (result!=null){
                following = result
                adapter.submitList(following)
                isEmpty(false)
            } else isEmpty(true)
        })
        viewModel.loadingState.observeForever {
            loadingState(it)
        }
        viewModel.errorState.observeForever {
            if (it == true){
                viewModel.errorState.postValue(false)
            }
        }
    }

    private fun showFollowing(username: String){
        following.clear()
        viewModel.setFollowing(username)
        setViewModel()
    }

    private fun isEmpty(state: Boolean) {
        if (state) {
            binding.textViewEmpty.show()
            binding.recyclerViewFollowingUsers.hide()
        } else {
            binding.textViewEmpty.hide()
            binding.recyclerViewFollowingUsers.show()
        }
    }

    private fun loadingState(state: Boolean) {
        if (state) {
            binding.textViewEmpty.hide()
            binding.progressBarFollowing.show()
        } else {
            binding.progressBarFollowing.hide()
        }
    }

    override fun onUserClicked(view: View, user: User) {
        val intent = Intent(
            requireActivity(), DetailActivity::class.java
        )
        intent.putExtra(DetailActivity.USER, user)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        requireActivity().startActivity(intent)
    }


}