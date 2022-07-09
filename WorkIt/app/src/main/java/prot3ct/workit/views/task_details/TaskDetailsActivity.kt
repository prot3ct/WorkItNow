package prot3ct.workit.views.task_details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import prot3ct.workit.R;
import prot3ct.workit.views.task_details.base.TaskDetailsContract;

public class TaskDetailsActivity extends AppCompatActivity {
    private TaskDetailsContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        TaskDetailsFragment jobDetailsFragment =
                (TaskDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (jobDetailsFragment == null) {
            jobDetailsFragment = TaskDetailsFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, jobDetailsFragment)
                    .commit();
        }

        this.presenter = new TaskDetailsPresenter(jobDetailsFragment, this);
        jobDetailsFragment.setPresenter(this.presenter);
    }
}
