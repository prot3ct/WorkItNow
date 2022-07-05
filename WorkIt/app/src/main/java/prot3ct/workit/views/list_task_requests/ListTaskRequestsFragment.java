package prot3ct.workit.views.list_task_requests;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import prot3ct.workit.R;
import prot3ct.workit.view_models.ProfileDetailsViewModel;
import prot3ct.workit.view_models.TaskRequestListViewModel;
import prot3ct.workit.views.my_tasks.MyTasksActivity;
import prot3ct.workit.views.navigation.DrawerUtil;
import prot3ct.workit.views.list_task_requests.base.ListTaskRequestContract;


public class ListTaskRequestsFragment extends Fragment implements ListTaskRequestContract.View {
    private ListTaskRequestContract.Presenter presenter;
    private Context context;
    private RecyclerView recyclerTaskRequestView;
    private Toolbar toolbar;

    private int taskId;


    public ListTaskRequestsFragment() {
        // Required empty public constructor
    }

    public static ListTaskRequestsFragment newInstance() {
        return new ListTaskRequestsFragment();
    }

    @Override
    public void setPresenter(ListTaskRequestContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_task_requests, container, false);
        this.toolbar = view.findViewById(R.id.id_drawer_toolbar);
        this.taskId = getActivity().getIntent().getIntExtra("taskId", 0);
        this.recyclerTaskRequestView = view.findViewById(R.id.id_list_task_requests_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerTaskRequestView.setLayoutManager(llm);

        DrawerUtil drawer = new DrawerUtil(this.getActivity(), this.toolbar);
        drawer.getDrawer();

        presenter.getTaskRequests(taskId);

        return view;
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
    public void showMyTasksActivty() {
        Intent intent = new Intent(this.context, MyTasksActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupTaskRequestsAdapter(List<TaskRequestListViewModel> users) {
        ListTaskRequestAdapter adapter = new ListTaskRequestAdapter(users, context, presenter);
        recyclerTaskRequestView.setAdapter(adapter);
    }
}
