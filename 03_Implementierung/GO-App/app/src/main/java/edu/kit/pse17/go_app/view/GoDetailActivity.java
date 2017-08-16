package edu.kit.pse17.go_app.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.viewModel.GoViewModel;
import edu.kit.pse17.go_app.viewModel.GroupViewModel;

/**
 * die Activity ist zusammen mit der Layout File go_detail.xml Teil des Views, der dem user die Details eines Gos anzeigt.
 *
 * Die Activity ist hauptsächlich für die Darstellung von Informationen zuständig. Die einzige Datenmanipulation, die hier vorgenommen werden kann,
 * ist die Änderung des Teilnahmestatus des Users.
 */

public class GoDetailActivity extends BaseActivity {
    private TextView title;
    private TextView description;
    private TextView startTime;
    private TextView endTime;
    private TextView memberCount;
    private RecyclerView members;
    private int index;
    private GoDetailsFragment details_frag;
    private GoMapFragment map_frag;
    private static final String INDEX_INTENT_CODE = "index";
    /*
    * Knopf um den Teilnahmestatus in dem GO zu ändern.
    * */
    private FloatingActionButton changeStatus;

    private FloatingActionButton gone;
    private FloatingActionButton going;
    private FloatingActionButton not_going;


    /**
     * Viewmodel Instanz, in der die dargestellten Daten der Aktivität gespeichert werden, um sie bei
     * Konfigurationsänderungen zu erhalten.
     */
    private GoViewModel viewModel;

    private PagerAdapter pagerAdapter;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    /**
     * Lifecycle-Methode der Activity, die beim Erzeugen aufgreufen wird. Dem ContentView der App wird das richtige
     * XML Layout zugewiesen und die Informationen die das ViewModel bereitstellt den Layout_komponenten zur Darstellung
     * übergeben.
     *
     * Es die Livedata des ViewModels auf zum Beobachten registriert, um bei Änderungen die View updaten zu können.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_details);

        details_frag = new GoDetailsFragment();
        map_frag = new GoMapFragment();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // do nothing
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    //not the map so
                    unHideFab();
                } else if (position == 1) { // this is maps position
                    hideFab();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        changeStatus = (FloatingActionButton) findViewById(R.id.go_status_fab);
        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gone.getVisibility() == View.VISIBLE) {
                    hideGoMenuButtons();
                } else {
                    showGoMenuButtons();
                }
            }
        });
        gone = (FloatingActionButton) findViewById(R.id.go_gone_fab);
        going = (FloatingActionButton) findViewById(R.id.go_going_fab);
        not_going = (FloatingActionButton) findViewById(R.id.go_not_going_fab);
        hideGoMenuButtons();
        //details_frag.onCreateView(getLayoutInflater(),viewPager,null);

        title = (TextView) findViewById(R.id.go_name);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);

        index = getIntent().getIntExtra(INDEX_INTENT_CODE, -1);

        String uid = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE).getString("uid",null);
        if(uid == null){
            throw new NullPointerException();
        }
        viewModel = ViewModelProviders.of(this).get(GoViewModel.class);
        viewModel.init(index, uid, GroupViewModel.getCurrentViewModel());
        viewModel.getGo().observe(this, new Observer<Go>() {

            @Override
            public void onChanged(@Nullable Go go) {
                //update UI
                displayData(go);
            }
        });

    }

    /**
     * Weißt die Daten des Gos den entsprechenden Laoyut-Komponenten zu.
     *
     * @param go Das Go dessen Daten in der View angezeigt werden sollen
     */
    private void displayData(Go go) {
        //TODO: make XML elements display Go Daata
        title.setText(go.getName());
        startTime.setText(go.getStart());
        endTime.setText(go.getEnd());
    }
    private void hideGoMenuButtons(){
        gone.setVisibility(View.GONE);
        going.setVisibility(View.GONE);
        not_going.setVisibility(View.GONE);
    }
    private void showGoMenuButtons(){
        gone.setVisibility(View.VISIBLE);
        going.setVisibility(View.VISIBLE);
        not_going.setVisibility(View.VISIBLE);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if(position == 0){
                if(details_frag == null){
                    details_frag = new GoDetailsFragment();
                    details_frag.displayData(viewModel.getGo().getValue());
                }
                return details_frag;
            } else if (position == 1) {
                if(map_frag == null)
                    map_frag = new GoMapFragment();
                return map_frag;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) {
                return "Details";
            } else if (position == 1) {
                return "Map";
            }
            return null;
        }

    }
    /*
    * hide FloatingActionButton on MapView, so that it does not interfere with Google Maps UI underneath it
    * */
    private void hideFab(){
        changeStatus.setVisibility(View.GONE);
    }

    private void unHideFab(){
        changeStatus.setVisibility(View.VISIBLE);
    }
}
