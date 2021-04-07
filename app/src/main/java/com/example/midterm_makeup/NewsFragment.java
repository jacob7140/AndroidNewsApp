package com.example.midterm_makeup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();

    private static final String ARG_PARAM1 = "param1";
    private Source mSource;
    private final String TAG = "DATA";

    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance(Source source) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, source);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSource = (Source) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    ArrayList<News> newsArrayList = new ArrayList<>();
    ListView listView;
    NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(mSource.getName());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        listView = view.findViewById(R.id.listViewNews);

        String url = "https://newsapi.org/v2/everything?sources=" + mSource.getId() + "&apiKey=5a3fe1b53b794dd4b2d6b87fce205364";

        Request request = new Request.Builder().url(url).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: Client Request failed...");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                if (response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONArray articlesArray = json.getJSONArray("articles");

                        for (int i = 0; i < articlesArray.length(); i++){
                            JSONObject object = articlesArray.getJSONObject(i);

                            String title = object.getString("title");
                            String description = object.getString("description");
                            String author = object.getString("author");
                            String pictureUrl = object.getString("urlToImage");
                            String url = object.getString("url");

                            String date = object.getString("publishedAt");
                            ZonedDateTime dateTime = ZonedDateTime.parse(date);
                            String res = dateTime.withZoneSameInstant(ZoneId.of("EST")).format(DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a"));

                            News news = new News(title, author, description, res, pictureUrl, url);
                            newsArrayList.add(news);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new NewsAdapter(getContext(), R.layout.news_adapter, newsArrayList);
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        mListener.goToBrowser(newsArrayList.get(position));

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

    class NewsAdapter extends ArrayAdapter<News> {

        public NewsAdapter(@NonNull Context context, int resource, ArrayList<News> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_adapter, parent, false);
            }

            News news = getItem(position);
            TextView textViewTitle = convertView.findViewById(R.id.textViewNewsTitle);
            TextView textViewDescription = convertView.findViewById(R.id.textViewNewsDescription);
            TextView textViewAuthor = convertView.findViewById(R.id.textViewNewsAuthor);
            TextView textViewPublishedAt = convertView.findViewById(R.id.textViewNewsTime);
            ImageView imageView = convertView.findViewById(R.id.imageView);

            textViewTitle.setText(news.getTitle());
            textViewDescription.setText(news.getDescription());
            textViewAuthor.setText(news.getAuthor());
            textViewPublishedAt.setText(news.getPublishedAt());

            Picasso.get().load(news.getPictureUrl()).into(imageView);


            return convertView;
        }
    }

    NewsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NewsListener) context;
    }

    interface NewsListener{
        void goToBrowser(News news);
    }
}