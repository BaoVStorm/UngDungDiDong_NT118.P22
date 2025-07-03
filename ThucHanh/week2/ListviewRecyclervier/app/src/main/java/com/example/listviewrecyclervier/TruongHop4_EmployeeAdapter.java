package com.example.listviewrecyclervier;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import java.util.List;

public class TruongHop4_EmployeeAdapter extends ArrayAdapter<Employee_bai4> {
    private Activity context;

    public TruongHop4_EmployeeAdapter(Activity context, int layoutID, List<Employee_bai4> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.truonghop4_item_employee, parent, false);
        }

        // Get item
        Employee_bai4 employee = getItem(position);

        // Get views
        TextView tvFullName = convertView.findViewById(R.id.item_employee_tv_fullname);
        TextView tvPosition = convertView.findViewById(R.id.item_employee_tv_position);
        ImageView ivManager = convertView.findViewById(R.id.item_employee_iv_manager);
        LinearLayout llParent = convertView.findViewById(R.id.item_employee_ll_parent);

        // Set full name
        if (employee.getFullName() != null) {
            tvFullName.setText(employee.getFullName());
        } else {
            tvFullName.setText("");
        }

        // If this is a manager -> show icon manager. Otherwise, show Staff in tvPosition
        if (employee.isManager()) {
            ivManager.setVisibility(View.VISIBLE);
            tvPosition.setVisibility(View.GONE);
        } else {
            ivManager.setVisibility(View.GONE);
            tvPosition.setVisibility(View.VISIBLE);
            tvPosition.setText(context.getString(R.string.staff));
        }

        // Show different color backgrounds for alternating employees
        if (position % 2 == 0) {
            llParent.setBackgroundResource(R.color.white);
        } else {
            llParent.setBackgroundResource(R.color.light_blue);
        }

        return convertView;
    }
}


//   --------------- Employee_bai4
class Employee_bai4 {
    public String id, fullname;
    boolean isManager;

    Employee_bai4() {
        id = fullname = "";
        isManager = false;
    }

    Employee_bai4(String id, String fullname, boolean isManager) {
        this.id = id;
        this.fullname = fullname;
        this.isManager = isManager;
    }

    public String getFullName() {
        return fullname;
    }

    public boolean isManager() {
        return isManager;
    }
}
