package com.example.jack.http_01;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jack.http_01.bean.Person;
import com.example.jack.http_01.bean.SchoolInfo;

import java.util.List;


public class JsonAdapter extends BaseAdapter {

    private List<Person> list;
    private Context context;
    private LayoutInflater inflater;
    private Handler handler = new Handler();


    public JsonAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Person> data) {
        this.list = data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Person person = list.get(position);
        holder.name.setText(person.getName());
        holder.age.setText("" + person.getAge());

        List<SchoolInfo> school = person.getSchoolInfo();
        SchoolInfo schoolInfo1 = school.get(0);
        SchoolInfo schoolInfo2 = school.get(1);

        holder.school1.setText(schoolInfo1.getSchool_name());
        holder.school2.setText(schoolInfo2.getSchool_name());

        new HttpImage(holder.imageView, person.getUrl(), handler).start();
        return convertView;
    }

    class Holder {
        private ImageView imageView;
        private TextView name;
        private TextView age;
        private TextView school1;
        private TextView school2;

        public Holder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            age = (TextView) view.findViewById(R.id.age);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            school1 = (TextView) view.findViewById(R.id.school1);
            school2 = (TextView) view.findViewById(R.id.school2);
        }
    }
}
