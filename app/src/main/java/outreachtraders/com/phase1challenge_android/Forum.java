package outreachtraders.com.phase1challenge_android;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Forum extends AppCompatActivity {

    EditText edt_title, edt_content;
    Button btn_post;
    RecyclerView recyclerView;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post,MyRecycleViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        edt_content=(EditText)findViewById(R.id.edt_content);
        edt_title=(EditText)findViewById(R.id.edt_title);
        btn_post=(Button)findViewById(R.id.btn_post);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("EDMT_FIREBASE");

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postCommet();
            }
        });
        displayComment();
    }

    private void postCommet() {
        String title =edt_title.getText().toString();
        String content=edt_content.getText().toString();

        Post post=new Post(title,content);

        databaseReference.push() //Use this method to create unique id for comment
                .setValue(post);

        adapter.notifyDataSetChanged();

    }

    private void displayComment() {
         options=
                new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(databaseReference,Post.class)
                .build();

         adapter=
                new FirebaseRecyclerAdapter<Post, MyRecycleViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyRecycleViewHolder holder, int position, @NonNull Post model) {
                        holder.txt_title.setText(model.getTitle());
                        holder.txt_comment.setText(model.getContent());
                    }

                    @NonNull
                    @Override
                    public MyRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemView= LayoutInflater.from(getBaseContext()).inflate(R.layout.post_item,viewGroup,false);
                        return new MyRecycleViewHolder(itemView);
                    }
                };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
