package outreachtraders.com.phase1challenge_android;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forum extends AppCompatActivity {

    EditText full_name, phone, email, region, district, division, edt_title, edt_content;
    Button btn_post, btn_update, btn_delete;
    RecyclerView recyclerView;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post,MyRecycleViewHolder> adapter;

    Post selectedPost;
    String selectedKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        full_name=(EditText)findViewById(R.id.full_name);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        region=(EditText)findViewById(R.id.region);
        district=(EditText)findViewById(R.id.district);
        division=(EditText)findViewById(R.id.division);
        edt_content=(EditText)findViewById(R.id.edt_content);
        edt_title=(EditText)findViewById(R.id.edt_title);
        btn_post=(Button)findViewById(R.id.btn_post);
        btn_update=(Button)findViewById(R.id.btn_update);
        btn_delete=(Button)findViewById(R.id.btn_delete);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("EDMT_FIREBASE");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayComment();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
                division.setText("");
                district.setText("");
                region.setText("");
                email.setText("");
                phone.setText("");
                full_name.setText("");
                edt_content.setText("");
                edt_title.setText("");

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference
                        .child(selectedKey)
                        .setValue(new Post(full_name.getText().toString(),
                                phone.getText().toString(), email.getText().toString(), region.getText().toString(), district.getText().toString(),
                                division.getText().toString(), edt_title.getText().toString(),edt_content.getText().toString()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Forum.this, "Updated !", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Forum.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference
                        .child(selectedKey)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Forum.this, "Delete !", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Forum.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        displayComment();
    }

@Override
protected void onStop(){
        if (adapter !=null)
            adapter.stopListening();
        super.onStop();
}

    private void postComment() {
        String name=full_name.getText().toString();
        String pnumber=phone.getText().toString();
        String mail=email.getText().toString();
        String reg=region.getText().toString();
        String dist=district.getText().toString();
        String div=division.getText().toString();
        String title =edt_title.getText().toString();
        String content=edt_content.getText().toString();

        Post post=new Post(name, pnumber, mail, reg, dist, div, title,content);

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
                    protected void onBindViewHolder(@NonNull MyRecycleViewHolder holder, int position, @NonNull final Post model) {
                        holder.txt_division.setText(model.getTitle());
                        holder.txt_district.setText(model.getTitle());
                        holder.txt_region.setText(model.getTitle());
                        holder.txt_email.setText(model.getTitle());
                        holder.txt_phone.setText(model.getTitle());
                        holder.txt_full_name.setText(model.getTitle());
                        holder.txt_title.setText(model.getTitle());
                        holder.txt_comment.setText(model.getContent());

                        holder.setiItemClickListener(new IItemClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                selectedPost=model;
                                selectedKey=getSnapshots().getSnapshot(position).toString();
                                Log.d("Key Item",""+selectedKey);

                                //Bind data
                                edt_content.setText(model.getContent());
                                edt_title.setText(model.getTitle());


                            }
                        });
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
