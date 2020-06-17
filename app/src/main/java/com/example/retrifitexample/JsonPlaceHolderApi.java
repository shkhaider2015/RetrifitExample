package com.example.retrifitexample;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    // First Example
//    @GET("posts")
//    Call<List<Post>> getPosts();

    // Query url
//    @GET("posts")
//    Call<List<Post>> getPosts(@Query("userId") int userId);

    // Complex Query
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    //Query Map example
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);
//    @GET("posts/2/comments")
//    Call<List<Comment>> getComments();

    // dynamic changes
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postid);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("posts")
    Call<Post> createPost(@Body Post post);
}
