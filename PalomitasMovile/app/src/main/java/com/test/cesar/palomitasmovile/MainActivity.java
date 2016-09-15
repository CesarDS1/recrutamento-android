package com.test.cesar.palomitasmovile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.test.cesar.palomitasmovile.Utils.CustomRequestListener;
import com.test.cesar.palomitasmovile.Utils.NetworkManager;
import com.test.cesar.palomitasmovile.adapters.SeasonAdapter;
import com.test.cesar.palomitasmovile.models.Episode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private TextView ratingTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mRecyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ratingTv=(TextView)findViewById(R.id.rating_text);

        NetworkManager.getInstance().getRatingSeason( new CustomRequestListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                if (!result.isEmpty())
                {
                    ratingTv.setText(result);
                }
            }
            @Override
            public void onError(String string)
            {
                if (!string.isEmpty())
                {
                    ratingTv.setText("-");

                }
            }
        });
        NetworkManager.getInstance().getEpisodesFromSeason( new CustomRequestListener<ArrayList<Episode>>()
        {
            @Override
            public void onSuccess(ArrayList<Episode> listEpisodes)
            {
                hideLoading();
                if (listEpisodes.size()!=0)
                {
                    mAdapter = new SeasonAdapter(listEpisodes);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onError(String string)
            {
                hideLoading();
                if (!string.isEmpty())
                {
                    Toast toast = Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

public void hideLoading()
{
    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
}

}
