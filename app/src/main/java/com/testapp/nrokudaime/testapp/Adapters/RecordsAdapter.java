package com.testapp.nrokudaime.testapp.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.testapp.nrokudaime.testapp.ApiRequest.BaseApiRequest;
import com.testapp.nrokudaime.testapp.Models.Record;
import com.testapp.nrokudaime.testapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {
    private List<Record> records;
    private Context context;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public RecordsAdapter(List<Record> records, Context context) {
        this.records = records;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Record record = records.get(i);
        viewHolder.text.setText(Html.fromHtml(record.getContent()));
        viewHolder.title.setText(record.getTitle());
        Glide.with(context)
                .load(BaseApiRequest.imagetBaseURL + record.getImage_url())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .crossFade()
                .into(viewHolder.image);
        if (record.getPlace().length() > 0) {
            String placeString = "<b>Место:</b> " + record.getPlace();
            viewHolder.place.setText(Html.fromHtml(placeString));
        }
        else viewHolder.place.setVisibility(View.GONE);
        if (record.getOrganizer().length() > 0){
            String orgString = "<b>Организатор:</b> " + record.getOrganizer();
            viewHolder.organisation.setText(Html.fromHtml(orgString));
        }
        else viewHolder.organisation.setVisibility(View.GONE);
        GradientDrawable drawable = (GradientDrawable)viewHolder.colorTag.getBackground();
        drawable.setColor(record.getColor());
        setDate(viewHolder, record);
    }

    private void setDate(ViewHolder viewHolder, Record record) {
        final SimpleDateFormat endDF = new SimpleDateFormat("MM.dd.yyyy");
        Date startDate = record.getStart_date_formated();
        Date endDate = record.getEnd_date_formated();
            String dateString = endDF.format(startDate) + " – " + endDF.format(endDate);
            viewHolder.date.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    @Override
    public RecordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_card_view, parent, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView text;
        private ImageView image;
        private TextView place;
        private TextView organisation;
        private View colorTag;
        private TextView date;
        public ViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            image = (ImageView) v.findViewById(R.id.image);
            text = (TextView) v.findViewById(R.id.descrition);
            text.setMovementMethod(LinkMovementMethod.getInstance());
            place = (TextView) v.findViewById(R.id.place_text);
            organisation = (TextView) v.findViewById(R.id.organisation_text);
            date = (TextView) v.findViewById(R.id.date_text);
            colorTag = (View) v.findViewById(R.id.tag_view);
        }
    }
}
