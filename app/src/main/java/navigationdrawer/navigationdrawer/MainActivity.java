package navigationdrawer.navigationdrawer;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import navigationdrawer.navigationdrawer.ui.home.HomeFragment;
import navigationdrawer.navigationdrawer.ui.play.PlayFragment;
import navigationdrawer.navigationdrawer.ui.search.SearchFragment;
import navigationdrawer.navigationdrawer.ui.top20.Top20Fragment;

public class MainActivity extends AppCompatActivity{
    private  DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;
    public static FragmentManager fragmentManager;
    Fragment homeFragment;
    Menu optionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//toolbar 이미지 가운데정렬999

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_top10, R.id.nav_search,
                R.id.nav_play)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
        setupDrawerContent(navigationView);

        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment , homeFragment).commit();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked1

        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_play:
                fragmentClass = PlayFragment.class;
                break;
            case R.id.nav_top10:
                fragmentClass = Top20Fragment.class;
                break;
            default:
                fragmentClass = SearchFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment1

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

        // Highlight the selected item has been done by NavigationView

        menuItem.setChecked(true);
        // Set action bar title

        setTitle(menuItem.getTitle());
        // Close the navigation drawer

        drawer.closeDrawers();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void playBtnClick(View view) {

        if (!PlayFragment.mp.isPlaying()) {
            // Stopping
            PlayFragment.mp.start();
            PlayFragment.playBtn.setImageResource(R.drawable.ic_pause);
        } else {
            // Playing
            PlayFragment.mp.pause();
            PlayFragment.playBtn.setImageResource(R.drawable.ic_play);
        }
    }
}
