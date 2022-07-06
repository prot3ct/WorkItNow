package prot3ct.workit.views.list_tasks;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import prot3ct.workit.R;
import prot3ct.workit.views.list_tasks.base.ListTasksContract;
import prot3ct.workit.views.navigation.DrawerUtil;

public class ListTasksActivity extends AppCompatActivity {
    private ListTasksContract.Presenter presenter;
    private ListTasksFragment listJobsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
        Toolbar toolbar = this.findViewById(R.id.id_drawer_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Browse Tasks");
        DrawerUtil drawer = new DrawerUtil(this, toolbar);
        drawer.getDrawer();

        listJobsFragment = (ListTasksFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (listJobsFragment == null) {
            listJobsFragment = ListTasksFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, listJobsFragment)
                    .commit();
        }

        this.presenter = new ListTasksPresenter(listJobsFragment, this);
        listJobsFragment.setPresenter(this.presenter);
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
                listJobsFragment.filterTask(newText);
                return true;
            }
        });
        return true;
    }
}
