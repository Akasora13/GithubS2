package id.Farrel.githubs2.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.Farrel.githubs2.model.User
import id.Farrel.githubs2.network.ApiClient
import id.Farrel.githubs2.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class HomeViewModel : ViewModel() {
    val state = MutableLiveData<Map<String, Boolean>>()
    val liveDataUsers = MutableLiveData<ArrayList<User>>()
    val users = ArrayList<User>()

    fun getUsers(): MutableLiveData<ArrayList<User>> {
        return liveDataUsers
    }

    fun setUsers(searchQuery: String) {
        state.postValue(mapOf("loading" to true))
        val search = ApiClient.getService().search(searchQuery)
        search.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                state.postValue(mapOf("loading" to false))
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.users.size > 0) {
                            for (i in 0 until it.users.size) {
                                getUserDetail(it.users[i].username)
                            }
                            state.postValue(mapOf("empty" to false))
                        } else {
                            state.postValue(mapOf("empty" to true))
                        }
                    }
                } else {
                    state.postValue(mapOf("error" to true))
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                state.postValue(mapOf("loading" to false))
                state.postValue(mapOf("error" to true))
            }

            fun getUserDetail(username: String) {
                state.postValue(mapOf("loading" to true))
                val getDetail = ApiClient.getService().getDetail(username)
                getDetail.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        state.postValue(mapOf("loading" to false))
                        if (response.isSuccessful) {
                            response.body()?.let {
                                users.add(it)
                            }
                            liveDataUsers.postValue(users)
                        } else {
                            state.postValue(mapOf("error" to true))
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        state.postValue(mapOf("loading" to false))
                        state.postValue(mapOf("error" to true))
                    }
                })

            }

        })
    }

}