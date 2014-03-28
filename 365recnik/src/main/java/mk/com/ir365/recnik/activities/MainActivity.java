package mk.com.ir365.recnik.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import mk.com.ir365.recnik.R;
import mk.com.ir365.recnik.customtextview.TypeFacedTextView;
import mk.com.ir365.recnik.fragments.FromDialogFragment;
import mk.com.ir365.recnik.fragments.NavigationDrawerFragment;
import mk.com.ir365.recnik.fragments.ToDialogFragment;
import mk.com.ir365.recnik.fragments.TranslateFragment;
import mk.com.ir365.recnik.fund.RecnikConstant;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        TranslateFragment.OnTranslatingActionPerformed,
        FromDialogFragment.PrevediOdInterface,
        ToDialogFragment.PrevediVoInterface {


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Logo has to be setup here for 2.3.x and 2.2
        getSupportActionBar().setLogo(R.drawable.recnik_logo_icon);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        getSupportFragmentManager().beginTransaction().add(R.id.container, TranslateFragment.newInstance()).commit();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //
    @Override
    public void getTranslation(String jazik, String zbor) {

    }

    @Override
    public void prevediOdJazik(String jazik) {
        TypeFacedTextView tfvPrevediOd = (TypeFacedTextView) findViewById(R.id.tftv_language_from);
        TypeFacedTextView tvPrevediVo = (TypeFacedTextView) findViewById(R.id.tftv_language_to);
        ImageView arrow = (ImageView) findViewById(R.id.arrow);

        tfvPrevediOd.setText(jazik);

        switch (jazik) {
            case "English":
            case "Deutchs":
            case "Français":
            case "Shqip":
            case "Eλληνικά":
            case "Slovenščina":
            case "Pусский":
            case "Српски":
            case "Türkçe": {
                tvPrevediVo.setVisibility(View.VISIBLE);
                arrow.setVisibility(View.VISIBLE);
                tvPrevediVo.setText(RecnikConstant.makednoski[0]);
                break;
            }
            case "Лексикон на изрази и зборови":
            case "Правопис":
            case "ИТ поимник (Eng->Мак)": {
                tvPrevediVo.setVisibility(View.GONE);
                arrow.setVisibility(View.GONE);
                break;
            }
            case "Македонски":
                tvPrevediVo.setText("English");

        }
    }

    @Override
    public void prevediVoJazik(String jazik) {
        TypeFacedTextView tvPrevediVo = (TypeFacedTextView) findViewById(R.id.tftv_language_to);
        tvPrevediVo.setText(jazik);
    }
}
