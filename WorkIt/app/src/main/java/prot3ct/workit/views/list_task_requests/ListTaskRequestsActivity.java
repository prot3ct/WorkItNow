package prot3ct.workit.views.list_task_requests;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.list_task_requests.base.ListTaskRequestContract;
import prot3ct.workit.views.navigation.DrawerUtil;

public class ListTaskRequestsActivity extends AppCompatActivity {
    private ListTaskRequestContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task_requests);

        ListTaskRequestsFragment listTaskRequestsFragment =
                (ListTaskRequestsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (listTaskRequestsFragment == null) {
            listTaskRequestsFragment = ListTaskRequestsFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, listTaskRequestsFragment)
                    .commit();
        }

        this.presenter = new ListTaskRequestsPresenter(listTaskRequestsFragment, this);
        listTaskRequestsFragment.setPresenter(this.presenter);
    }
}
