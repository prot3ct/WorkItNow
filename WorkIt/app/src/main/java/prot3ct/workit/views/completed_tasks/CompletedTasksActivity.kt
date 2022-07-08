package prot3ct.workit.views.completed_tasks;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.stepstone.apprating.listener.RatingDialogListener;

import prot3ct.workit.R;
import prot3ct.workit.views.completed_tasks.base.CompletedTasksContract;
import prot3ct.workit.views.navigation.DrawerUtil;

public class CompletedTasksActivity extends AppCompatActivity implements RatingDialogListener {
    private CompletedTasksContract.Presenter presenter;
    private CompletedTasksFragment completedTasksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);
        Toolbar toolbar = this.findViewById(R.id.id_drawer_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Completed Tasks");
        DrawerUtil drawer = new DrawerUtil(this, toolbar);
        drawer.getDrawer();

        completedTasksFragment =
                (CompletedTasksFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (completedTasksFragment == null) {
            completedTasksFragment = CompletedTasksFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, completedTasksFragment)
                    .commit();
        }

        this.presenter = new CompletedTasksPresenter(completedTasksFragment, this);
        completedTasksFragment.setPresenter(this.presenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);

        MenuItem ourSearchItem = menu.findItem(R.id.menu_search);

        SearchView sv = (SearchView) ourSearchItem.getActionView();
        sv.setQueryHint("Search by title");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                completedTasksFragment.filterTask(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onPositiveButtonClicked(int value, String description) {
        completedTasksFragment.postRaiting(value, description);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }
}
