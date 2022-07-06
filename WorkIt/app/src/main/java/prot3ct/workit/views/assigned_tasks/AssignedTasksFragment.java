package prot3ct.workit.views.assigned_tasks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import prot3ct.workit.R;
import prot3ct.workit.view_models.AssignedTasksListViewModel;
import prot3ct.workit.views.assigned_tasks.base.AssignedTasksContract;

public class AssignedTasksFragment extends Fragment implements AssignedTasksContract.View {
    private AssignedTasksContract.Presenter presenter;
    private Context context;
    private AssignedTasksAdapter adapter;

    private RecyclerView recyclerTaskView;

    public AssignedTasksFragment() {
        // Required empty public constructor
    }

    public static AssignedTasksFragment newInstance() {
        return new AssignedTasksFragment();
    }

    @Override
    public void setPresenter(AssignedTasksContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assigned_tasks, container, false);

        this.recyclerTaskView = view.findViewById(R.id.id_list_tasks_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerTaskView.setLayoutManager(llm);

        presenter.getAssignedTasks();

        return view;
    }

    @Override
    public void filterTask(String query) {
        adapter.filter(query);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void notifySuccessful(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupTasksAdapter(final List<AssignedTasksListViewModel> tasks) {
        adapter = new AssignedTasksAdapter(tasks, context, presenter);
        recyclerTaskView.setAdapter(adapter);
    }
}
