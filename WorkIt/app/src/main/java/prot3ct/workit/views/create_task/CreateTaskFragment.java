package prot3ct.workit.views.create_task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import prot3ct.workit.R;
import prot3ct.workit.utils.WorkItProgressDialog;
import prot3ct.workit.views.create_task.base.CreateTaskContract;
import prot3ct.workit.views.list_tasks.ListTasksActivity;
import prot3ct.workit.views.navigation.DrawerUtil;

public class CreateTaskFragment extends Fragment implements CreateTaskContract.View {
    private CreateTaskContract.Presenter presenter;
    private Context context;

    private TextInputLayout  titleLayout;
    private TextInputLayout  lengthLayout;
    private TextInputLayout  descriptionLayout;
    private TextInputLayout  cityLayout;
    private TextInputLayout  addressLayout;
    private TextInputLayout  rewardLayout;
    private TextView titleTextView;
    private TextView startDateTextView;
    private EditText lengthEditText;
    private TextView descriptionTextView;
    private TextView cityTextView;
    private TextView addressTextView;
    private TextView rewardTextView;
    private Toolbar toolbar;
    private Button saveTaskButton;
    private String startDateString;

    private Calendar date;

    private WorkItProgressDialog dialog;

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    public static CreateTaskFragment newInstance() {
        return new CreateTaskFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void setPresenter(CreateTaskContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        this.toolbar = view.findViewById(R.id.id_drawer_toolbar);
        this.dialog = new WorkItProgressDialog(context);

        this.titleLayout = view.findViewById(R.id.input_layout_title);
        this.lengthLayout = view.findViewById(R.id.input_layout_length);
        this.descriptionLayout = view.findViewById(R.id.input_layout_description);
        this.cityLayout = view.findViewById(R.id.input_layout_city);
        this.addressLayout = view.findViewById(R.id.input_layout_address);
        this.rewardLayout = view.findViewById(R.id.input_layout_reward);
        this.titleTextView = (TextView) view.findViewById(R.id.id_title_edit_text);
        this.startDateTextView = (TextView) view.findViewById(R.id.id_choose_start_date_text_view);
        this.lengthEditText = (EditText) view.findViewById(R.id.id_length_edit_text);
        this.descriptionTextView = (TextView) view.findViewById(R.id.id_description_edit_text);
        this.cityTextView = (TextView) view.findViewById(R.id.id_city_edit_text);
        this.addressTextView = (TextView) view.findViewById(R.id.id_address_edit_text);
        this.rewardTextView = (TextView) view.findViewById(R.id.id_reward_edit_text);
        this.saveTaskButton = (Button) view.findViewById(R.id.id_create_task_btn);
        DrawerUtil drawer = new DrawerUtil(this.getActivity(), this.toolbar);
        drawer.getDrawer();

        this.startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        this.saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleLayout.setError(null);
                addressLayout.setError(null);
                lengthLayout.setError(null);
                cityLayout.setError(null);
                rewardLayout.setError(null);

                validateTask(titleTextView.getText().toString(),
                        lengthEditText.getText().toString(),
                        descriptionTextView.getText().toString(),
                        cityTextView.getText().toString(),
                        addressTextView.getText().toString(),
                        rewardTextView.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void showListJobsActivity() {
        Intent intent = new Intent(this.context, ListTasksActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifyError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void createTask() {
        presenter.createTask(
                titleTextView.getText().toString(),
                startDateString,
                lengthEditText.getText().toString(),
                descriptionTextView.getText().toString(),
                cityTextView.getText().toString(),
                addressTextView.getText().toString(),
                rewardTextView.getText().toString()
        );
    }

    @Override
    public void notifyInvalidLocation() {
        cityLayout.setError("Invalid location");
        addressLayout.setError("Invalid location");
    }

    @Override
    public void notifySuccessful() {
        Toast.makeText(getContext(), "Task created successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialogforLoading() {
        this.dialog.showProgress("Creating task...");
    }

    @Override
    public void dismissDialog() {
        this.dialog.dismissProgress();
    }

    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        this.date = Calendar.getInstance();
        DatePickerDialog pickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            boolean first = true;

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (first) {
                    date.set(year, monthOfYear, dayOfMonth);
                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            date.set(Calendar.MINUTE, minute);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm");

                            startDateTextView.setText(getOrdinal(date.get(Calendar.DAY_OF_MONTH)) + " " +
                                    getMonthForInt(date.get(Calendar.MONTH)) + " at " +
                                    String.format("%02d:%02d", date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE)));
                            startDateString = dateFormat.format(date.getTime());
                            first = false;
                        }
                    }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();

                    first = false;
                }
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));

        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DAY_OF_MONTH, 8);
        pickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
        pickerDialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
        pickerDialog.show();
    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    private String getOrdinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];
        }
    }

    private void validateTask(String title, String length, String description,
                                 String city, String address, String reward ) {
        if (title.length() < 5 || title.length() > 30) {
            titleLayout.setError("Title must be bettwen 5 and 30 symbols long");
            return;
        }

        if(!isInteger(length)) {
            lengthLayout.setError("Invalid length number");
            return;
        }

        int lengthInt = Integer.parseInt(length);
        if (!(lengthInt == 1 || lengthInt == 2 || lengthInt == 3 || lengthInt == 4)) {
            lengthLayout.setError("Invalid length number");
            return;
        }

        if(!isInteger(reward)) {
            rewardLayout.setError("Invalid reward number");
            return;
        }

        presenter.checkIfLocationExists(city + ", " + address);
    }

    private boolean isInteger( String input )
    {
        try
        {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e)
        {
            return false;
        }
    }
}
