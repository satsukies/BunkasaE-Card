package net.ddns.satsukies.bunkasae_card.reader;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import net.ddns.satsukies.bunkasae_card.DrawerAdapter;
import net.ddns.satsukies.bunkasae_card.R;
import net.ddns.satsukies.bunkasae_card.card.CardActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ReaderActivity extends AppCompatActivity {

    /**
     * Drawer用Variable
     */
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle drawer_toggle;

    LinearLayoutManager layout_manager;
    RecyclerView recycler_view;
    DrawerAdapter drawer_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CardReaderFragment fragment = new CardReaderFragment();
            transaction.replace(R.id.sample_content_fragment_reader, fragment);
            transaction.commit();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        /**
         * 以下Drawer
         */
        // ドロワーの開け閉めをActionBarDrawerToggleで監視
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_toggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.app_name, R.string.app_name);
        drawer_layout.setDrawerListener(drawer_toggle);

        // ドロワー開くボタン(三本線)を表示
        drawer_toggle.setDrawerIndicatorEnabled(true);

        /**
         * RecyclerView
         */
        recycler_view = (RecyclerView) findViewById(R.id.drawer_view);

        // RecyclerView内のItemサイズが固定の場合に設定すると、パフォーマンス最適化
        recycler_view.setHasFixedSize(true);

        // レイアウトの選択
        layout_manager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layout_manager);

        // XML
        TypedArray drawerMenuList = getResources().obtainTypedArray(R.array.drawer_menu_list);
        int menuLength = drawerMenuList.length();

        // RecyclerView.Adapter に渡すデータ
        final ArrayList<HashMap<String, Object>> drawerMenuArr = new ArrayList<>();

        for (int i = 0; i < menuLength; i++) {
            TypedArray itemArr = getResources().obtainTypedArray(drawerMenuList.getResourceId(i, 0));
            int itemLength = itemArr.length();
            HashMap<String, Object> content = new HashMap<>();
            drawerMenuArr.add(content);
            for (int j = 0; j < itemLength; j++) {
                TypedArray contentArr = getResources().obtainTypedArray(itemArr.getResourceId(j, 0));

                // key-value
                if (contentArr.getString(0).contains("icon")) {
                    content.put(contentArr.getString(0), contentArr.getDrawable(1));
                } else {
                    content.put(contentArr.getString(0), contentArr.getString(1));
                }
                contentArr.recycle();
            }
            itemArr.recycle();
        }

        // XMLを読込んで表示する
        drawer_adapter = new DrawerAdapter(drawerMenuArr, getApplicationContext());

        recycler_view.setAdapter(drawer_adapter);

        // GestureDetectorを使って、onSingleTapUpを検知
        final GestureDetector mGestureDetector = new GestureDetector(getApplicationContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

        /**
         * Drawer内のタッチイベントの定義部分
         */
        recycler_view.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
                if (mGestureDetector.onTouchEvent(e)) {

                    // onSingleTapUpの時に、タッチしているViewを取得
                    View childView = view.findChildViewUnder(e.getX(), e.getY());
                    int position = recycler_view.getChildPosition(childView);

                    // タッチしているViewのデータを取得
                    HashMap<String, Object> data = drawerMenuArr.get(position);

                    String menu_label = data.get("text").toString();

                    // Menu アイテムのみ
                    if (menu_label.equals("About")) {
//                        startActivity(new Intent(getApplicationContext(), JUMP.class));
                        drawer_layout.closeDrawers();
                        return true;
                    }

                    if (menu_label.equals("Get Your Card ID")) {
                        startActivity(new Intent(getApplicationContext(), CardActivity.class));
                        return true;
                    }

                    if (menu_label.equals("Card Mode")) {
                        startActivity(new Intent(getApplicationContext(), CardActivity.class));
                        return true;
                    }

                    if (menu_label.equals("Reader Mode")) {
                        drawer_layout.closeDrawers();
                        return true;
                    }

                    if (menu_label.equals("How to Use")) {
//                        startActivity(new Intent(getApplicationContext(), JUMP.class));
                        drawer_layout.closeDrawers();
                    }
                }
                return false;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }
        });

//        menuIcon = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {
//            @Override
//            public int getToolbarViewId() {
//                return R.id.toolbar;
//            }
//        };
    }
}
