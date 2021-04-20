package id.Farrel.githubs2.helper

import android.database.Cursor
import id.Farrel.githubs2.database.DatabaseContract
import id.Farrel.githubs2.model.User

object MappingHelper {

    fun mapToCursorArrayList(favCursor: Cursor?): ArrayList<User> {
        val favList = ArrayList<User>()

        favCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOGIN))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val company =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                val location =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
                val bio = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BIO))
                val public_repos =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.PUBLIC_REPOS))
                val followers =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS))
                val following =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING))
                val followers_url =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS_URL))
                val following_url =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING_URL))
                val avatar_url =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR_URL))
                val is_favorite =
                    getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.IS_FAVORITE))
                favList.add(
                    User(
                        id,
                        login,
                        name,
                        company,
                        location,
                        bio,
                        public_repos,
                        followers,
                        following,
                        followers_url,
                        following_url,
                        avatar_url,
                        is_favorite
                    )
                )
            }
        }
        return favList
    }

}