package com.adrian.api;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        retrofit2.Call<List<Post>> call = apiService.getPosts();

        call.enqueue(new  retrofit2.Callback<List<Post>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();
                    if (posts != null && !posts.isEmpty()) {
                        displayPosts(posts);
                    } else {
                        txtResult.setText("No posts found");
                    }
                } else {
                    txtResult.setText("Failed to fetch posts");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Post>> call, Throwable t) {

                    Log.e("MainActivity", "Error: " + t.getMessage());
                    txtResult.setText("Error occurred");
                }
            });
            }


    private void displayPosts(List<Post> posts) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Post post : posts) {
            stringBuilder.append("Title: ").append(post.getTitle()).append("\n");
            stringBuilder.append("Body: ").append(post.getBody()).append("\n\n");
        }
        txtResult.setText(stringBuilder.toString());
    }
}
