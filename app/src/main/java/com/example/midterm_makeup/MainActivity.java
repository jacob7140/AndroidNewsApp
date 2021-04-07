package com.example.midterm_makeup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CategoriesFragment.CategoriesListener, SourceFragment.SourceListener,
        NewsFragment.NewsListener {
    private String CATEGORIES_FRAGMENT = "CATEGORIES_FRAGMENT";
    private String SOURCE_FRAGMENT = "SOURCE_FRAGMENT";
    private String NEWS_FRAGMENT = "NEWS_FRAGMENT";
    private String BROWSER_FRAGMENT = "BROWSER_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CategoriesFragment(), CATEGORIES_FRAGMENT).commit();

    }

    @Override
    public void goToSource(NewsData.Category category) {
        getSupportFragmentManager().beginTransaction().replace(R.id.rootView, SourceFragment.newInstance(category), SOURCE_FRAGMENT)
                .addToBackStack("CATEGORIES_FRAGMENT").commit();
    }

    @Override
    public void goToNews(Source source) {
        getSupportFragmentManager().beginTransaction().replace(R.id.rootView, NewsFragment.newInstance(source), NEWS_FRAGMENT).commit();
    }

    @Override
    public void goToBrowser(News news) {
        getSupportFragmentManager().beginTransaction().replace(R.id.rootView, BrowserFragment.newInstance(news), BROWSER_FRAGMENT)
                .addToBackStack("NEWS_FRAGMENT").commit();

    }
}