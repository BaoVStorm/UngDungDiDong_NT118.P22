package com.example.truonghop6;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TruongHop7_EmployeeAdapter extends RecyclerView.Adapter<TruongHop7_EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee_bai7> employeeList;

    public TruongHop7_EmployeeAdapter(List<Employee_bai7> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.truonghop7_item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee_bai7 employee = employeeList.get(position);
        holder.tvFullName.setText(employee.getFullName() != null ? employee.getFullName() : "");

        if (employee.isManager()) {
            holder.ivManager.setVisibility(View.VISIBLE);
            holder.tvPosition.setVisibility(View.GONE);
        } else {
            holder.ivManager.setVisibility(View.GONE);
            holder.tvPosition.setVisibility(View.VISIBLE);
            holder.tvPosition.setText("Staff");
        }

        if (position % 2 == 0) {
            holder.llParent.setBackgroundResource(R.color.white);
        } else {
            holder.llParent.setBackgroundResource(R.color.light_blue);
        }
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvPosition;
        ImageView ivManager;
        LinearLayout llParent;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.item_employee_tv_fullname);
            tvPosition = itemView.findViewById(R.id.item_employee_tv_position);
            ivManager = itemView.findViewById(R.id.item_employee_iv_manager);
            llParent = itemView.findViewById(R.id.item_employee_ll_parent);
        }
    }
}


//   --------------- Employee_bai7
class Employee_bai7 {
    public String id, fullname;
    boolean isManager;

    Employee_bai7() {
        id = fullname = "";
        isManager = false;
    }

    Employee_bai7(String id, String fullname, boolean isManager) {
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



// note

/*
ArrayAdapter<Employee_bai7>
thành
RecyclerView.Adapter<TruongHop7_EmployeeAdapter.EmployeeViewHolder>

 */