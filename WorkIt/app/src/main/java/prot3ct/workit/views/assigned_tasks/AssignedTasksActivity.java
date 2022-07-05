package prot3ct.workit.views.assigned_tasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import prot3ct.workit.R;
import prot3ct.workit.views.assigned_tasks.base.AssignedTasksContract;
import prot3ct.workit.views.navigation.DrawerUtil;

public class AssignedTasksActivity extends AppCompatActivity {
    private AssignedTasksContract.Presenter presenter;
    private AssignedTasksFragment assignedTasksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_tasks);
        Toolbar toolbar = this.findViewById(R.id.id_drawer_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Assigned Tasks");
        DrawerUtil drawer = new DrawerUtil(this, toolbar);
        drawer.getDrawer();

        assignedTasksFragment =
                (AssignedTasksFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (assignedTasksFragment == null) {
            assignedTasksFragment = AssignedTasksFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, assignedTasksFragment)
                    .commit();
        }

        this.presenter = new AssignedTasksPresenter(assignedTasksFragment, this);
        assignedTasksFragment.setPresenter(this.presenter);
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
                assignedTasksFragment.filterTask(newText);
                return true;
            }
        });
        return true;
    }
}
