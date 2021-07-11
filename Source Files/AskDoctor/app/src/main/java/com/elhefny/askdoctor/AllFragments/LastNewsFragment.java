package com.elhefny.askdoctor.AllFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elhefny.askdoctor.LastNews.Article;
import com.elhefny.askdoctor.LastNews.LastNewsAdapter;
import com.elhefny.askdoctor.LastNews.NewNews;
import com.elhefny.askdoctor.LastNews.NewsClinet;
import com.elhefny.askdoctor.LastNews.NewsInterface;
import com.elhefny.askdoctor.NetworkUtil;
import com.elhefny.askdoctor.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LastNewsFragment extends Fragment {
    RecyclerView rv;
    LastNewsAdapter adapter;
    ArrayList<Article> articles;

    public LastNewsFragment() {
        // Required empty public constructor
    }

    public static LastNewsFragment newInstance() {
        LastNewsFragment fragment = new LastNewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.fragment_last_news_rv);
        articles = new ArrayList<>();
        gettingData();
        adapter = new LastNewsAdapter(getContext(), articles);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
    }

    private void gettingData() {
        if (!NetworkUtil.getConnectivityStatusString(getContext()).equals(getString(R.string.no_internet_connection))) {
            Wait_Fragment wait_fragment = Wait_Fragment.newInstance(getString(R.string.getting_data));
            wait_fragment.show(getFragmentManager(), null);
            NewsInterface api = NewsClinet.getServices();
            api.getNews("us", "health", NewsClinet.myKey).enqueue(new Callback<NewNews>() {
                @Override
                public void onResponse(Call<NewNews> call, Response<NewNews> response) {
                    if (response.isSuccessful()) {
                        articles.addAll(response.body().getArticles());
                        adapter.notifyDataSetChanged();
                        wait_fragment.dismiss();
                    } else {
                        Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        wait_fragment.dismiss();
                        Error_Fragment error_fragment = Error_Fragment.newInstance(getString(R.string.error_occurred));
                        error_fragment.show(getFragmentManager(), null);
                    }
                }

                @Override
                public void onFailure(Call<NewNews> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    wait_fragment.dismiss();
                    Error_Fragment error_fragment = Error_Fragment.newInstance("Error Occurred");
                    error_fragment.show(getFragmentManager(), null);
                }
            });
        } else {
            Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
}