package asus.com.example.asus.cardview;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private asus.com.example.asus.cardview.AlbumsAdapter adapter;
    private List<Album> albumList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            PackageInfo info = getPackageManager().getPackageInfo("asus.com.example.asus.cardview", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new asus.com.example.asus.cardview.AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();


    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

    }

    /**
     * Adding few albums for testing
     */
    public void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.january,
                R.drawable.february,
                R.drawable.march,
                R.drawable.april,
                R.drawable.may,
                R.drawable.june,
                R.drawable.july,
                R.drawable.august,
                R.drawable.september,
                R.drawable.october,
                R.drawable.november,
                R.drawable.december};


        Album a = new Album("JANUARY", 31, covers[0]);
        albumList.add(a);

        a = new Album("FEBRUARY",28, covers[1]);
        albumList.add(a);

        a = new Album("MARCH", 31, covers[2]);
        albumList.add(a);

        a = new Album("APRIL", 30, covers[3]);
        albumList.add(a);

        a = new Album("MAY", 31, covers[4]);
        albumList.add(a);

        a = new Album("JUNE", 30, covers[5]);
        albumList.add(a);

        a = new Album("JULY", 31, covers[6]);
        albumList.add(a);

        a = new Album("AUGUST", 31, covers[7]);
        albumList.add(a);

        a = new Album("SEPTEMBER", 30, covers[8]);
        albumList.add(a);

        a = new Album("OCTOBER", 31, covers[9]);
        albumList.add(a);

        a = new Album("NOVEMBER", 30, covers[10]);
        albumList.add(a);

        a = new Album("DECEMBER", 31, covers[11]);
        albumList.add(a);


        adapter.notifyDataSetChanged();//adaptörü güncelliyor.

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    //////////////////////////Farklı inflater a geçiş

    public void mesajGonder(View view) {
        // düğmeye yanıt verecek bir şeyler

        Intent intent = new Intent(this, MesajGosterActivity.class);
        ImageView editText = (ImageView) findViewById(R.id.thumbnail);

        startActivity(intent);


    }

}