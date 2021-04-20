package id.Farrel.githubs2.ui.detail.follower

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.Farrel.githubs2.databinding.FragmentFollowerBinding
import id.Farrel.githubs2.interfaces.UserInterface
import id.Farrel.githubs2.model.User
import id.Farrel.githubs2.ui.detail.DetailActivity
import id.Farrel.githubs2.util.hide
import id.Farrel.githubs2.util.show

class FollowerFragment : Fragment(), UserInterface {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var viewModel: FollowerViewModel
    private lateinit var adapter: FollowerAdapter
    private lateinit var user: User
    private lateinit var followers: ArrayList<User>


    companion object{
        fun newInstance(user: User): FollowerFragment {
            val args = Bundle()
            args.putParcelable(DetailActivity.USER, user)

            val fragment = FollowerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getParcelable<User>(DetailActivity.USER) as User
        followers = ArrayList()

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[FollowerViewModel::class.java]

        adapter = FollowerAdapter(requireContext())
        adapter.userInterface = this
        binding.rvFollowerUsers.adapter = adapter

        showFollowers(user.username)

    }

    private fun showFollowers(username: String) {
        followers.clear()
        viewModel.setFollower(username)
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.getUserData().observe(requireActivity(), { result ->
            if (result != null) {
                followers = result
                adapter.submitList(followers)
                isEmpty(false)
            } else {
                isEmpty(true)
            }
        })
        viewModel.loadingState.observeForever {
            loadingState(it)
        }
        viewModel.errorState.observeForever {
            if (it == true) {
                viewModel.errorState.postValue(false)
            }
        }
    }

                private fun isEmpty(state: Boolean) {
            if (state) {
                binding.textViewEmpty.show()
                binding.rvFollowerUsers.hide()
            } else {
                binding.textViewEmpty.hide()
                binding.rvFollowerUsers.show()
            }
        }

                private fun loadingState(state: Boolean) {
            if (state) {
                binding.textViewEmpty.hide()
                binding.progressBarFollower.show()
            } else binding.progressBarFollower.hide()
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
