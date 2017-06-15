package com.tedu.employeemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.tedu.employeemanager.QuertActivity;
import com.tedu.employeemanager.R;
import com.tedu.employeemanager.entity.Employee;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class EmployeeAdapter extends BaseAdapter{
    private Context context;
    private List<Employee> list = new ArrayList<>();
    public EmployeeAdapter(Context context) {
        this.context = context;

    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Employee getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Hander hander = null;
        Employee employee = list.get(position);
        if (view == null) {
            view = View.inflate(context, R.layout.quert_parent, null);
            hander = new Hander();
            hander.text01 = (TextView) view.findViewById(R.id.tv_01_quert);
            hander.text02 = (TextView) view.findViewById(R.id.tv_02_quert);
            hander.text03 = (TextView) view.findViewById(R.id.tv_03_quert);
            hander.text04 = (TextView) view.findViewById(R.id.tv_04_quert);
            hander.text05 = (TextView) view.findViewById(R.id.tv_05_quert);
            view.setTag(hander);
        } else {

            hander = (Hander) view.getTag();
        }

        hander.text01.setText(""+employee.getId());
        hander.text02.setText(employee.getName());
        hander.text03.setText(""+employee.getAge());
        hander.text04.setText(""+employee.getSalary());
        String gender = "";
        if (employee.getGender().equals("m")) {
            gender = "男";
        } else {
            gender = "女";
        }
        hander.text05.setText(gender);
        return view;
    }
    class Hander{
        private TextView text01;
        private TextView text02;
        private TextView text03;
        private TextView text04;
        private TextView text05;
    }
    public void addEmployee(List<Employee> employee){
        if (list != null) {
            list.addAll(employee);
            notifyDataSetChanged();
        }
    }

    public void deleteEmployee(Employee employee) {
        list.remove(employee);
    }
}
