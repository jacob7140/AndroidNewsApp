package com.example.midterm_makeup;

import android.content.Context;
import android.icu.text.Transliterator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment {



    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ListView listView;
    CategoriesAdapter adapter;
    ArrayList<NewsData.Category> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("News Source Categories");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        listView = view.findViewById(R.id.categoryListView);
        data = NewsData.categories;

        adapter = new CategoriesAdapter(getContext(), R.layout.categories_adapter, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.goToSource(data.get(position));
            }
        });

        return view;
    }

    class CategoriesAdapter extends ArrayAdapter<NewsData.Category> {

        public CategoriesAdapter(@NonNull Context context, int resource, ArrayList<NewsData.Category> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.categories_adapter, parent, false);
            }

            NewsData.Category category = getItem(position);
            TextView textViewCategories = convertView.findViewById(R.id.textViewCategories);

            textViewCategories.setText(category.getName());

            return convertView;
        }
    }

    CategoriesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CategoriesFragment.CategoriesListener) context;
    }

    interface CategoriesListener{
        void goToSource(NewsData.Category category);
    }
}