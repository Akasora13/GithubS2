package id.Farrel.githubs2.interfaces

import android.view.View
import id.Farrel.githubs2.model.User

interface UserInterface {
    fun onUserClicked(view:View, user : User)

}