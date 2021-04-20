package id.Farrel.githubs2.database

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FavoriteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite"
            const val _ID = "_id"
            const val LOGIN = "login"
            const val NAME = "name"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val BIO = "bio"
            const val PUBLIC_REPOS = "public_repos"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val FOLLOWERS_URL = "followers_url"
            const val FOLLOWING_URL = "following_url"
            const val AVATAR_URL = "avatar_url"
            const val IS_FAVORITE = "is_favorite"
        }

    }

}