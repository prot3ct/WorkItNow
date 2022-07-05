package prot3ct.workit.views.my_tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import prot3ct.workit.R;
import prot3ct.workit.view_models.MyTasksListViewModel;
import prot3ct.workit.views.create_task.CreateTaskActivity;
import prot3ct.workit.views.my_tasks.base.MyTasksContract;

public class MyTasksFragment extends Fragment implements MyTasksContract.View {
    private MyTasksContract.Presenter presenter;
    private Context context;
    private MyTasksAdapter adapter;

    private FloatingActionButton createTaskButton;
//    private Button logoutButton;
    private RecyclerView recyclerTaskView;

    public MyTasksFragment() {
        // Required empty public constructor
    }

    public static MyTasksFragment newInstance() {
        return new MyTasksFragment();
    }

    @Override
    public void setPresenter(MyTasksContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tasks, container, false);

        this.createTaskButton = view.findViewById(R.id.id_create_task_button);
        this.recyclerTaskView = view.findViewById(R.id.id_my_tasks_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerTaskView.setLayoutManager(llm);

        this.createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateJobActivity();
            }
        });

        presenter.getMyTasks();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void showCreateJobActivity() {
        Intent intent = new Intent(this.context, CreateTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifySuccessful(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void filterTask(String query) {
        adapter.filter(query);
    };

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupTasksAdapter(final List<MyTasksListViewModel> tasks) {
        adapter = new MyTasksAdapter(tasks, context, presenter);
        recyclerTaskView.setAdapter(adapter);
    }
}
