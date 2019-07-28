package outreachtraders.com.phase1challenge_android;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyRecycleViewHolder extends RecyclerView.ViewHolder {
    TextView txt_title,txt_comment;
    public MyRecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_comment=(TextView)itemView.findViewById(R.id.txt_content);
        txt_title=(TextView)itemView.findViewById(R.id.txt_title);
    }
}
