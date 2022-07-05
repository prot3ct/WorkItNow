package prot3ct.workit.views.completed_tasks;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stepstone.apprating.AppRatingDialog;

import java.util.Arrays;
import java.util.List;

import prot3ct.workit.R;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.view_models.CompletedTasksListViewModel;
import prot3ct.workit.views.completed_tasks.base.CompletedTasksContract;

public class CompletedTasksFragment extends Fragment implements CompletedTasksContract.View {
    private CompletedTasksContract.Presenter presenter;
    private Context context;
    private CompletedTasksAdapter adapter;

    private int loggedInUserId;
    private int selectedTaskId;
    private int userToBeRatedId;
    private int receiverUserRoleId;

    private FloatingActionButton createTaskButton;
    private WorkItProgressDialog dialog;

    private RecyclerView recyclerTaskView;

    public CompletedTasksFragment() {
        // Required empty public constructor
    }

    public static CompletedTasksFragment newInstance() {
        return new CompletedTasksFragment();
    }

    @Override
    public void setPresenter(CompletedTasksContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_tasks, container, false);

        this.dialog = new WorkItProgressDialog(context);
        this.recyclerTaskView = view.findViewById(R.id.id_list_tasks_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerTaskView.setLayoutManager(llm);
        presenter.getCompletedTasks();
        loggedInUserId = presenter.getLoggedInUserId();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void filterTask(String query) {
        adapter.filter(query);
    }

    @Override
    public void updateSelectedInfo(int taskId, int supervisorId, int taskerId) {
        selectedTaskId = taskId;
        if (loggedInUserId == supervisorId) {
            userToBeRatedId = taskerId;
            receiverUserRoleId = 3;
        }
        else {
            userToBeRatedId = supervisorId;
            receiverUserRoleId = 4;
        }
    }

    @Override
    public void postRaiting(int value, String description) {
        presenter.createRating(value, description, userToBeRatedId, selectedTaskId, receiverUserRoleId);
    }

    @Override
    public void notifyError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifySuccessful(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showDialogForLoading() {
        this.dialog.showProgress("Loading...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }

    @Override
    public void setupTasksAdapter(final List<CompletedTasksListViewModel> tasks) {
        adapter = new CompletedTasksAdapter(tasks, context, this);
        recyclerTaskView.setAdapter(adapter);
    }

    @Override
    public void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setDefaultRating(3)
                .setTitle("Rate user performance")
                .setStarColor(R.color.bg_login)
                .setTitleTextColor(R.color.md_black_1000)
                .setNumberOfStars(5)
                .setCommentBackgroundColor(R.color.md_blue_grey_50)
                .setHint("Please write short review")
                .setHintTextColor(R.color.md_black_1000)
                //.setWindowAnimation(R.style.MyDialogFadeAnimation)
                .create(getActivity())
                .show();
    }

    @Override
    public int getLoggedInUserId() {
        return this.loggedInUserId;
    }
}
