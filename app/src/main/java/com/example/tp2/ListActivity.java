package com.example.tp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.tp2.adapter.StarAdapter;
import com.example.tp2.beans.Star;
import com.example.tp2.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    StarService service = StarService.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        stars = new ArrayList<>();

        init();
        recyclerView = findViewById(R.id.recycle_view);

        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }

    public void init(){
        service.create(new Star("kate bosworth",  "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Michelle_Rodriguez_at_the_New_York_Fashion_Week_crop.jpg/170px-Michelle_Rodriguez_at_the_New_York_Fashion_Week_crop.jpg", 3.5f));
        service.create(new Star("george clooney",  "https://flxt.tmsimg.com/assets/219616_v9_ba.jpg", 3));
        service.create(new Star("michelle rodriguez", "https://cfj.org/wp-content/uploads/2021/04/George-Clooney-scaled.jpg", 5));
        service.create(new Star("george clooney", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/84/Kate_Bosworth_%288078977611%29.jpg/220px-Kate_Bosworth_%288078977611%29.jpg", 1));
        service.create(new Star("louise bouroin",  "http://www.starsphotos.com/resize.php?id=1184", 5));
        service.create(new Star("louise bouroin", "https://cfj.org/wp-content/uploads/2021/04/George-Clooney-scaled.jpg", 1));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override

            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }

        });
        return true;
    }


}



