package com.example.paigeplander.maps_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Date;

// Displays a list of comments for a particular landmark
public class CommentFeedActivity extends AppCompatActivity {

    private static final String TAG = CommentFeedActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Comment> mComments = new ArrayList<Comment>();

    // UI elements
    EditText commentInputBox;
    RelativeLayout layout;
    Button sendButton;

    /* TODO: right now this recycler view is using hard coded comments.
     * You'll need to add functionality for pulling and posting comments from Firebase
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_feed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: replace this with the name of the landmark the user chose
        String landmarkName = "test landmark";

        // sets the app bar's title
        setTitle(landmarkName + ": Posts");

        // hook up UI elements
        layout = (RelativeLayout) findViewById(R.id.comment_layout);
        commentInputBox = (EditText) layout.findViewById(R.id.comment_input_edit_text);
        sendButton = (Button) layout.findViewById(R.id.send_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.comment_recycler);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickForSendButton();

        makeTestComments();
        setAdapterAndUpdateData();
    }

    private void makeTestComments() {
        String randomString = "hello world hello world ";
        Comment newComment = new Comment(randomString, "test_user1", new Date());
        Comment hourAgoComment = new Comment(randomString + randomString, "test_user2", new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        Comment overHourComment = new Comment(randomString, "test_user3", new Date(System.currentTimeMillis() - (2* 60 * 60 * 1000)));
        Comment dayAgoComment = new Comment(randomString, "test_user4", new Date(System.currentTimeMillis() - (25* 60 * 60 * 1000)));
        Comment daysAgoComment = new Comment(randomString + randomString + randomString, "test_user5", new Date(System.currentTimeMillis() - (48* 60 * 60 * 1000)));
        mComments.add(newComment); mComments.add(hourAgoComment); mComments.add(overHourComment); mComments.add(dayAgoComment); mComments.add(daysAgoComment);
        mComments.add(newComment); mComments.add(hourAgoComment); mComments.add(overHourComment); mComments.add(dayAgoComment); mComments.add(daysAgoComment);
    }

    private void setOnClickForSendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = commentInputBox.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    // don't do anything if nothing was added
                    commentInputBox.requestFocus();
                }

                else {
                    // clear edit text, post comment
                    commentInputBox.setText("");
                    postNewComment(comment);
                }
            }
        });
    }

    private void setAdapterAndUpdateData() {
        mAdapter = new CommentAdapter(this, mComments);
        mRecyclerView.setAdapter(mAdapter);
        // scroll to the last comment
        mRecyclerView.smoothScrollToPosition(mComments.size()-1);
    }

    private void postNewComment(String commentText) {
        Comment newComment = new Comment(commentText, "one-sixty", new Date());
        mComments.add(newComment);
        setAdapterAndUpdateData();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
