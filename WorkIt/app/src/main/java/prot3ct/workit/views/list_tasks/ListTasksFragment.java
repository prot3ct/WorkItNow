package prot3ct.workit.views.list_tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import prot3ct.workit.R;
import prot3ct.workit.utils.EndlessRecyclerViewScrollListener;
import prot3ct.workit.view_models.AvailableTasksListViewModel;
import prot3ct.workit.views.create_task.CreateTaskActivity;
import prot3ct.workit.views.list_tasks.base.ListTasksContract;
import prot3ct.workit.views.login.LoginActivity;

public class ListTasksFragment extends Fragment implements ListTasksContract.View {
    private ListTasksContract.Presenter presenter;
    private Context context;

    private ListTasksAdapter adapter;
    private FloatingActionButton createTaskButton;
    private RecyclerView recyclerTaskView;
    private ProgressBar endProgressBar;
    private boolean searchMode = false;
    private String searchQuery;
    EndlessRecyclerViewScrollListener  scrollListener;

    final List<AvailableTasksListViewModel> allTasks = new ArrayList<AvailableTasksListViewModel>();

    public ListTasksFragment() {
        // Required empty public constructor
    }

    public static ListTasksFragment newInstance() {
        return new ListTasksFragment();
    }

    @Override
    public void setPresenter(ListTasksContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_tasks, container, false);

        this.endProgressBar = view.findViewById(R.id.list_tasks_end_progress_bar);
        this.createTaskButton = view.findViewById(R.id.id_create_task_button);
        this.recyclerTaskView = view.findViewById(R.id.id_list_tasks_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerTaskView.setLayoutManager(llm);
        adapter = new ListTasksAdapter(allTasks, context);
        recyclerTaskView.setAdapter(adapter);


        scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                showEndProgressBar();
                if (searchMode) {
                    presenter.getSearchedAvailableTasks(page, searchQuery);
                }
                else {
                    presenter.getAllTasks(page);
                }
            }
        };

        recyclerTaskView.addOnScrollListener(scrollListener);

        this.createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateJobActivity();
            }
        });

        presenter.getAllTasks(1);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void showEndProgressBar() {
        endProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEndProgressBar() {
        endProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void notifyError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
    }

    @Override
    public void showCreateJobActivity() {
        Intent intent = new Intent(this.context, CreateTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginActivity() {
        Intent intent = new Intent(this.context, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifySuccessful(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateTasks(final List<AvailableTasksListViewModel> tasks) {
        int curSize = adapter.getItemCount();
        allTasks.addAll(tasks);
        adapter.notifyDataSetChanged();
        hideEndProgressBar();
    }

    @Override
    public void filterTask(String query) {
        searchQuery = query;
        if (!searchMode && query.length() > 0) {
            searchMode = true;
            showEndProgressBar();
            allTasks.clear();
            adapter.notifyDataSetChanged();
            scrollListener.resetState();
            presenter.getSearchedAvailableTasks(1, query);
        }
        if (query.length() == 0) {
            searchMode = false;
            showEndProgressBar();
            allTasks.clear();
            adapter.notifyDataSetChanged();
            scrollListener.resetState();
            presenter.getAllTasks(1);
        }
    }
}
