package prot3ct.workit.views.navigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import prot3ct.workit.R;
import prot3ct.workit.data.remote.AuthData;
import prot3ct.workit.data.remote.UserData;
import prot3ct.workit.view_models.ProfileDetailsViewModel;
import prot3ct.workit.views.assigned_tasks.AssignedTasksActivity;
import prot3ct.workit.views.completed_tasks.CompletedTasksActivity;
import prot3ct.workit.views.list_dialogs.ListDialogsActivity;
import prot3ct.workit.views.list_tasks.ListTasksActivity;
import prot3ct.workit.views.login.LoginActivity;
import prot3ct.workit.views.profile.ProfileActivity;
import prot3ct.workit.views.my_tasks.MyTasksActivity;

public class DrawerUtil {
    private final Activity activity;
    private final Toolbar toolbar;
    private Drawer drawer = null;
    private Bitmap picture;
    private UserData userData;
    private AuthData authData;
    private int loggedInUserId;
    AccountHeader headerResult;
    ProfileDrawerItem profileDrawer;

    public DrawerUtil(Activity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;
        userData = new UserData(activity.getBaseContext());
        authData = new AuthData(activity.getBaseContext());
        loggedInUserId = authData.getLoggedInUserId();
    }

    public void getDrawer() {
        setupDrawer();
        userData.getProfileDetails(loggedInUserId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<ProfileDetailsViewModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ProfileDetailsViewModel profile) {
                        updateDrawer(profile);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void updateDrawer(final ProfileDetailsViewModel profile) {
        profileDrawer.withName(profile.getFullName());
        profileDrawer.withEmail(profile.getEmail());
        if(profile.getPictureAsString() != null) {
            byte[] decodedString = Base64.decode(profile.getPictureAsString(), Base64.DEFAULT);
            picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileDrawer.withIcon(picture);
        }
        headerResult.updateProfile(profileDrawer);
    }

    private void setupDrawer() {
        profileDrawer = new ProfileDrawerItem().withName("").withEmail("").withIcon(activity.getResources().getDrawable(R.drawable.blank_profile_picture));
        headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.navigation_background)
                .withTextColor(activity.getResources().getColor(R.color.md_black_1000))
                .addProfiles(
                        profileDrawer
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        drawer = new DrawerBuilder()
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
                .build();
    }

    private PrimaryDrawerItem createBrowseTasksDrawerItem() {
        return new PrimaryDrawerItem()
            .withIdentifier(0)
            .withName("Browse tasks")
            .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(activity.getBaseContext(), ListTasksActivity.class);
                    activity.startActivity(intent);
                return true;
                }
            });
    }

    private PrimaryDrawerItem createMyTasksDrawerItem() {
        return new PrimaryDrawerItem()
            .withIdentifier(1)
            .withName("My tasks")
            .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(activity.getBaseContext(), MyTasksActivity.class);
                activity.startActivity(intent);
                return true;
                }
            });
    }

    private PrimaryDrawerItem createMyCompletedTasksDrawerItem() {
        return new PrimaryDrawerItem()
            .withIdentifier(2)
            .withName("Completed tasks")
            .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(activity.getBaseContext(), CompletedTasksActivity.class);
                    activity.startActivity(intent);
                return true;
                }
            });
    }

    private PrimaryDrawerItem createAssignedTasksDrawerItem() {
        return new PrimaryDrawerItem()
            .withIdentifier(3)
            .withName("Assigned to me tasks")
            .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(activity.getBaseContext(), AssignedTasksActivity.class);
                    activity.startActivity(intent);
                return true;
            }
        });
    }

    private PrimaryDrawerItem createProfileDrawerItem() {
        return new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName("My profile")
                .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = new Intent(activity.getBaseContext(), ProfileActivity.class);
                        intent.putExtra("userId", loggedInUserId);
                        intent.putExtra("myProfile", true);
                        activity.startActivity(intent);
                        return true;
                    }
                });
    }

    private PrimaryDrawerItem createDialogDrawerItem() {
        return new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName("My Dialogs")
                .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = new Intent(activity.getBaseContext(), ListDialogsActivity.class);
                        activity.startActivity(intent);
                        return true;
                    }
                });
    }

    private PrimaryDrawerItem createLogoutDrawerItem() {
        return new PrimaryDrawerItem()
            .withIdentifier(5)
            .withName("Logout Me")
            .withTextColor(activity.getResources().getColor(R.color.md_red_900))
            .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent intent = new Intent(activity.getBaseContext(), LoginActivity.class);
                activity.startActivity(intent);
                authData.logoutUser();
                return true;
                }
            });
    }

    public void setSelection(int id) {
        this.drawer.setSelection(id);
    }
}
