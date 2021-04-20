package id.Farrel.githubs2.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import id.Farrel.githubs2.model.User

data class ApiResponse(
    @Expose
    @SerializedName("total_count") val totalCount: Int,
    @Expose
    @SerializedName("incomplete_result") val incompleteResult: Boolean,
    @Expose
    @SerializedName("items") val users: ArrayList<User>
)

