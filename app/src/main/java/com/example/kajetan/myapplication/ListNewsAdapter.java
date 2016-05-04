package com.example.kajetan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kajetan on 2016-04-24.
 */
public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.MyVH> {
    private List<News> newsList;
    private Context context;
    private FragmentNewsReciver frn;
    public ListNewsAdapter(Context context, List<News> newsList, FragmentNewsReciver frn) {
        super();
        this.context = context;
        this.newsList = newsList;
        this.frn = frn;
    }
    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View)(LayoutInflater.from(context))
                .inflate(R.layout.layout_one_news, parent, false);

        MyVH myVH = new MyVH(view);

        return myVH;
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {
        News news = newsList.get(position);
        holder.tvAuthor.setText(news.author);
        if (news.text.length() > 100)
            holder.tvText.setText(news.text.substring(0, 100));
        else
            holder.tvText.setText(news.text);

        holder.cv.setAnimation(AnimationUtils.loadAnimation(context,R.anim.showing_news));
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView tvText;
        public TextView tvAuthor;
        public MyVH(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvMy);
            tvText = (TextView) itemView.findViewById(R.id.recText);
            tvAuthor = (TextView) itemView.findViewById(R.id.recAuthor);

            onLongClickListener(cv);
        }

        private void onLongClickListener(View view)
        {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent intent =
                    Toast.makeText(context, "Open full description", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(context, NewsWithComments.class);
                    //intent = intent.putExtra("text", tvText.getText());
                    //intent = intent.putExtra("author", tvAuthor.getText());                    //
                    // context.sta
                    // rtActivity(intent);
                    try {
                        frn.send("NEWS", new News(
                                        "2016-04-5",
                                        tvText.getText().toString(),
                                        tvAuthor.getText().toString())
                        );
                    } catch (Exception e) {
                        Log.d("LOG", e.toString());
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_to_front_news));


                    return true;
                }
            });
        }
    }
}
