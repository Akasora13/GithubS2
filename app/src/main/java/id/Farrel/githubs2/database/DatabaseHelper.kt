package id.Farrel.githubs2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import id.Farrel.githubs2.database.DatabaseContract.FavoriteColumns.Companion._ID

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbfavorite"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAVORITE =
                    "CREATE TABLE $TABLE_NAME" +
                    " ($_ID LONG PRIMARY KEY," +
                    " $LOGIN TEXT NO NULL," +
                    " $NAME TEXT NO NULL," +
                    " $COMPANY TEXT NO NULL," +
                    " $LOCATION TEXT NO NULL," +
                    " $BIO TEXT NO NULL," +
                    " $PUBLIC_REPOS TEXT NO NULL," +
                    " $FOLLOWERS TEXT NO NULL," +
                    " $FOLLOWING TEXT NO NULL," +
                    " $FOLLOWERS_URL TEXT NO NULL," +
                    " $FOLLOWING_URL TEXT NO NULL," +
                    " $AVATAR_URL TEXT NO NULL," +
                    " $IS_FAVORITE INTEGER NO NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}