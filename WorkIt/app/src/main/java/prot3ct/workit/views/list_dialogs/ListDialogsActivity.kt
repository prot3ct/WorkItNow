package prot3ct.workit.views.list_dialogs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.list_dialogs.base.ListDialogsContract;

public class ListDialogsActivity extends AppCompatActivity {
    private ListDialogsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dialogs);

        ListDialogsFragment editTaskFragment =
                (ListDialogsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (editTaskFragment == null) {
            editTaskFragment = ListDialogsFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, editTaskFragment)
                    .commit();
        }

        this.presenter = new ListDialogsPresenter(editTaskFragment, this);
        editTaskFragment.setPresenter(this.presenter);
    }
}
