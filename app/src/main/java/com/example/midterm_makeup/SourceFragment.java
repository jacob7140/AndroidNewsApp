package com.example.midterm_makeup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SourceFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();

    private static final String ARG_PARAM1 = "param1";
    private final String TAG = "DATA";

    private NewsData.Category mCategory;

    public SourceFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SourceFragment newInstance(NewsData.Category category) {
        SourceFragment fragment = new SourceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = (NewsData.Category) getArguments().getSerializable(ARG_PARAM1);
        }
    }


    ArrayList<Source> sourceArrayList = new ArrayList<>();
    ListView listView;
    SourceAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(mCategory.getName() + " News Sources");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_source, container, false);
        listView = view.findViewById(R.id.sourceListView);

        String url = "https://newsapi.org/v2/sources?category=" + mCategory.getCategory() + "&apiKey=5a3fe1b53b794dd4b2d6b87fce205364";

        Request request = new Request.Builder().url(url).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: Client request failed...");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONArray array = json.getJSONArray("sources");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject sourceObject = array.getJSONObject(i);

                            String name = sourceObject.getString("name");

                            String description = sourceObject.getString("description");

                            String id = sourceObject.getString("id");

                            Source source = new Source(name, description, id);
                            sourceArrayList.add(source);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new SourceAdapter(getContext(), R.layout.source_adapter, sourceArrayList);
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        mListener.goToNews(sourceArrayList.get(position));
                                    }
                                });

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        return view;
    }

    class SourceAdapter extends ArrayAdapter<Source> {

        public SourceAdapter(@NonNull Context context, int resource, ArrayList<Source> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.source_adapter, parent, false);
            }

            Source source = getItem(position);
            TextView textViewName = convertView.findViewById(R.id.textViewSorceName);
            TextView textViewDescription = convertView.findViewById(R.id.textViewSourceDescription);

            textViewName.setText(source.getName());
            textViewDescription.setText(source.getDesc());


            return convertView;
        }
    }

    SourceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SourceListener) context;
    }

    interface SourceListener{
        void goToNews(Source source);
    }
}