package prot3ct.workit.views.create_task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.create_task.base.CreateTaskContract;

public class CreateTaskActivity extends AppCompatActivity {
    private CreateTaskContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        CreateTaskFragment createJobFragment =
                (CreateTaskFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (createJobFragment == null) {
            createJobFragment = CreateTaskFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, createJobFragment)
                    .commit();
        }

        this.presenter = new CreateTaskPresenter(createJobFragment, this);
        createJobFragment.setPresenter(this.presenter);
    }
}
