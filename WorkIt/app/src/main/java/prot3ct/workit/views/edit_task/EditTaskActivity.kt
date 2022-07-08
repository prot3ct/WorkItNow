package prot3ct.workit.views.edit_task;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.edit_task.base.EditTaskContract;

public class EditTaskActivity extends AppCompatActivity {
    private EditTaskContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        EditTaskFragment editTaskFragment =
                (EditTaskFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (editTaskFragment == null) {
            editTaskFragment = EditTaskFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, editTaskFragment)
                    .commit();
        }

        this.presenter = new EditTaskPresenter(editTaskFragment, this);
        editTaskFragment.setPresenter(this.presenter);
    }
}
