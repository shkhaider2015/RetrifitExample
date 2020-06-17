package com.example.retrifitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        getPosts();
//          getComments();
        createPost();
    }

    private void getPosts()
    {
        int userId = 4;
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(userId);
        //Complex Query
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(userId, "id", "desc");

        //in case we dont use params
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(userId, null, null);

        // For Query Map
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");
        Call<List<Post>> callMap = jsonPlaceHolderApi.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful())
                {
                    textViewResult.setText("Code : " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                for (Post post : posts)
                {
                    String content = "";
                    content += "ID : " + post.getId() + "\n Title : " + post.getTitle() + "\n Body : " + post.getText() + "\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void getComments()
    {
        int postId = 3;
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(postId);

        Call<List<Comment>> callURL = jsonPlaceHolderApi.getComments("posts/2/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful())
                {
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment: comments)
                {
                    String content = "";
                    content += "Post ID : " + comment.getPostId() +
                            "\n" + "ID : " + comment.getId() +
                            "\n" + "Name : " + comment.getName() +
                            "\n" + "Email : " + comment.getEmail() +
                            "\n" + "Text : " + comment.getText() + "\n\n";
                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void createPost()
    {
        Post post = new Post(33, "New Title", "New Message Text");

        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response)
            {
                if (!response.isSuccessful())
                {
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postResponse = response.body();

                    String content = "";
                    content += "Code : " + response.code() +
                            "\nID : " + postResponse.getId() +
                            "\n Title : " + postResponse.getTitle() +
                            "\n Body : " + postResponse.getText() +
                            "\n\n";
                    textViewResult.append(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });
    }
}