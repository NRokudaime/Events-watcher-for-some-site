package com.testapp.nrokudaime.testapp.Controllers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.testapp.nrokudaime.testapp.Adapters.RecordsAdapter;
import com.testapp.nrokudaime.testapp.ApiRequest.BaseApiRequest;
import com.testapp.nrokudaime.testapp.ApiRequest.Interfaces.RecordApiInterface;
import com.testapp.nrokudaime.testapp.DaoSupport.DaoEntities.DaoRecord;
import com.testapp.nrokudaime.testapp.DaoSupport.DaoMaster;
import com.testapp.nrokudaime.testapp.DaoSupport.DaoSession;
import com.testapp.nrokudaime.testapp.Models.Record;
import com.testapp.nrokudaime.testapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScrollingActivity extends AppCompatActivity implements Callback<ArrayList<Record>> {

    private RecyclerView mRecyclerView;
    private RecordsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int nextPage = 0;
    DaoRecord daoRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
        initRefreshLayout();
        initDB();
        populateRecords();
    }

    private void initRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getApiRequest(true);
            }
        });
    }

    private void initDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db, DaoMaster.SCHEMA_VERSION);
        DaoSession daoSession = daoMaster.newSession();
        daoRecord = daoSession.getDaoRecord();
    }

    private void populateRecords() {
        String startCollumn = DaoRecord.Properties.START_Date.columnName;
        String endCollumn = DaoRecord.Properties.END_Date.columnName;
        String orderBy = startCollumn + " ASC, " + endCollumn + " ASC";
        cursor = db.query(daoRecord.getTablename(), daoRecord.getAllColumns(), null, null, null, null, orderBy);
        ArrayList<Record> records = new ArrayList<Record>();
        if (cursor.moveToFirst()) {
                Date currentDate = new Date();
            do {
                Record record = daoRecord.readEntity(cursor, 0);
                if (record.getEnd_date_formated().after(currentDate))
                {
                    records.add(record);
                } else {
                    daoRecord.delete(record);
                }
            } while (cursor.moveToNext());
            mAdapter.setRecords(records);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void initRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.startRecyclerView);
        mAdapter = new RecordsAdapter(new ArrayList<Record>(), this);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(animator);
    }

    public void getApiRequest(boolean pullToRefresh) {
        swipeRefreshLayout.setRefreshing(true);
        Retrofit retrofit = BaseApiRequest.retrofit;
        RecordApiInterface recordApiInterface = retrofit.create(RecordApiInterface.class);
        nextPage = pullToRefresh ? 1 : nextPage++;
        Call<ArrayList<Record>> call = recordApiInterface.getRecords(nextPage);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ArrayList<Record>> call, Response<ArrayList<Record>> response) {
        ArrayList<Record> records = response.body();
        if (mAdapter == null) {
            mAdapter = new RecordsAdapter(records, this);
        }
        for (Record record : records) {
            record.setStart_date_formated(getDateFromJson(record.getStart_date_not_formated()));
            record.setEnd_date_formated(getDateFromJson(record.getEnd_date_not_formated()));
            String[] args = new String[] {String.valueOf(record.getId())};
            cursor = db.query(daoRecord.getTablename(), daoRecord.getAllColumns(), "_id = ?", args, null, null, null);
            if (cursor.moveToNext()) {
                if (!daoRecord.readKey(cursor, 0).equals(record.getId())) {
                    daoRecord.insert(record);
                }
                else {
                    daoRecord.update(record);
                }
            } else {
                daoRecord.insert(record);
            }
        }
        if (nextPage == 1) {
            mAdapter.setRecords(records);
        } else {
            List<Record> newRecords = mAdapter.getRecords();
            newRecords.addAll(records);
            mAdapter.setRecords(newRecords);
        }
        swipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    private Date getDateFromJson(String notFormattedDate) {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = df.parse(notFormattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public void onFailure(Call<ArrayList<Record>> call, Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
