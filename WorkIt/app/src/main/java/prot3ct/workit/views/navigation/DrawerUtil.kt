package prot3ct.workit.views.navigation

import android.app.Activity
import com.mikepenz.materialdrawer.Drawer
import android.graphics.Bitmap
import prot3ct.workit.data.remote.AuthData
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import prot3ct.workit.view_models.ProfileDetailsViewModel
import android.graphics.BitmapFactory
import prot3ct.workit.R
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import android.content.Intent
import android.util.Base64
import androidx.appcompat.widget.Toolbar
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import prot3ct.workit.data.remote.UserData
import prot3ct.workit.views.list_tasks.ListTasksActivity
import prot3ct.workit.views.my_tasks.MyTasksActivity
import prot3ct.workit.views.completed_tasks.CompletedTasksActivity
import prot3ct.workit.views.assigned_tasks.AssignedTasksActivity
import prot3ct.workit.views.profile.ProfileActivity
import prot3ct.workit.views.list_dialogs.ListDialogsActivity
import prot3ct.workit.views.login.LoginActivity

class DrawerUtil(private val activity: Activity, private val toolbar: Toolbar) {
    private lateinit var drawer: Drawer
    private lateinit var picture: Bitmap
    private val userData: UserData = UserData(activity.baseContext)
    private val authData: AuthData = AuthData(activity.baseContext)
    private val loggedInUserId: Int = authData.loggedInUserId
    private lateinit var headerResult: AccountHeader
    private lateinit var profileDrawer: ProfileDrawerItem

    fun getDrawer() {
        setupDrawer()
        userData.getProfileDetails(loggedInUserId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<ProfileDetailsViewModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(profile: ProfileDetailsViewModel) {
                        updateDrawer(profile)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {}
                })
    }

    private fun updateDrawer(profile: ProfileDetailsViewModel) {
        profileDrawer.withName(profile.fullName)
        profileDrawer.withEmail(profile.email)
        val decodedString = Base64.decode(profile.pictureAsString, Base64.DEFAULT)
        picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        profileDrawer.withIcon(picture)
        headerResult.updateProfile(profileDrawer)
    }

    private fun setupDrawer() {
        profileDrawer = ProfileDrawerItem().withName("").withEmail("")
            .withIcon(activity.resources.getDrawable(R.drawable.blank_profile_picture))
        headerResult = AccountHeaderBuilder()
            .withActivity(activity)
            .withHeaderBackground(R.drawable.navigation_background)
            .withTextColor(activity.resources.getColor(R.color.md_black_1000))
            .addProfiles(
                profileDrawer
            )
            .withOnAccountHeaderListener { _, _, _ -> false }
            .build()
        drawer = DrawerBuilder()
            .withActivity(activity)
            .withToolbar(toolbar)
            .withAccountHeader(headerResult)
            .addDrawerItems(
                createBrowseTasksDrawerItem(),
                createMyTasksDrawerItem(),
                createMyCompletedTasksDrawerItem(),
                createAssignedTasksDrawerItem(),
                createProfileDrawerItem(),
                createDialogDrawerItem(),
                createLogoutDrawerItem()
            )
            .build()
    }

    private fun createBrowseTasksDrawerItem(): PrimaryDrawerItem {
        return PrimaryDrawerItem()
            .withIdentifier(0)
            .withName("Browse tasks")
            .withOnDrawerItemClickListener { _, _, _ ->
                val intent = Intent(activity.baseContext, ListTasksActivity::class.java)
                activity.startActivity(intent)
                true
            }
    }

    private fun createMyTasksDrawerItem(): PrimaryDrawerItem {
        return PrimaryDrawerItem()
            .withIdentifier(1)
            .withName("My tasks")
            .withOnDrawerItemClickListener { _, _, _ ->
                val intent = Intent(activity.baseContext, MyTasksActivity::class.java)
                activity.startActivity(intent)
                true
            }
    }

    private fun createMyCompletedTasksDrawerItem(): PrimaryDrawerItem {
        return PrimaryDrawerItem()
            .withIdentifier(2)
            .withName("Completed tasks")
            .withOnDrawerItemClickListener { _, _, _ ->
                val intent = Intent(activity.baseContext, CompletedTasksActivity::class.java)
                activity.startActivity(intent)
                true
            }
    }

    private fun createAssignedTasksDrawerItem(): PrimaryDrawerItem {
        return PrimaryDrawerItem()
            .withIdentifier(3)
            .withName("Assigned to me tasks")
            .withOnDrawerItemClickListener { _, _, _ ->
                val intent = Intent(activity.baseContext, AssignedTasksActivity::class.java)
                activity.startActivity(intent)
                true
            }
    }

    private fun createProfileDrawerItem(): PrimaryDrawerItem {
        return PrimaryDrawerItem()
            .withIdentifier(4)
            .withName("My profile")
            .withOnDrawerItemClickListener { _, _, _ ->
                val intent = Intent(activity.baseContext, ProfileActivity::class.java)
                intent.putExtra("userId", loggedInUserId)
                intent.putExtra("myProfile", true)
                activity.startActivity(intent)
                true
            }
    }

    private fun createDialogDrawerItem(): PrimaryDrawerItem {
        return PrimaryDrawerItem()
            .withIdentifier(4)
            .withName("My Dialogs")
            .withOnDrawerItemClickListener { _, _, _ ->
                val intent = Intent(activity.baseContext, ListDialogsActivity::class.java)
                activity.startActivity(intent)
                true
            }
    }

    private fun createLogoutDrawerItem(): PrimaryDrawerItem {
        return PrimaryDrawerItem()
            .withIdentifier(5)
            .withName("Logout Me")
            .withTextColor(activity.resources.getColor(R.color.md_red_900))
            .withOnDrawerItemClickListener { _, _, _ ->
                val intent = Intent(activity.baseContext, LoginActivity::class.java)
                activity.startActivity(intent)
                authData.logoutUser()
                true
            }
    }

    fun setSelection(id: Int) {
        drawer.setSelection(id.toLong())
    }

}