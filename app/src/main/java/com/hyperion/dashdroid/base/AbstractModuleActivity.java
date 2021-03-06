package com.hyperion.dashdroid.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyperion.dashdroid.R;
import com.hyperion.dashdroid.base.slidingmenu.SlidingMenuItem;
import com.hyperion.dashdroid.base.slidingmenu.SlidingMenuListAdapter;

import java.util.ArrayList;

/**
 * Created by infinity on 29-Apr-16.
 */
public abstract class AbstractModuleActivity extends AppCompatActivity {

	protected static SlidingMenuItem currentSelectedItem;
	protected final int MENU_SEARCH_BUTTON_ID = 0;
	protected final int MENU_REFRESH_BUTTON_ID = 2;
	protected DrawerLayout drawerLayout;
	protected ListView drawerList;
	protected ArrayList<SlidingMenuItem> slidingMenuItems;
	private Menu menu;
	private ActionBarDrawerToggle drawerToggle;
	private SlidingMenuListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		slidingMenuItems = new ArrayList<SlidingMenuItem>();

		setContentView(R.layout.base_module_activity);
		addSpecificContent();

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.list_slidermenu);
		drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				displayView(position);
			}
		});

		adapter = new SlidingMenuListAdapter(getApplicationContext(), slidingMenuItems);
		drawerList.setAdapter(adapter);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name) {

			@Override
			public void onDrawerClosed(View drawerView) {
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};

		drawerLayout.addDrawerListener(drawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		if(savedInstanceState == null) {
			for(int i = 0; i < slidingMenuItems.size(); i++) {
				if(slidingMenuItems.get(i).getType() == SlidingMenuItem.ItemType.ITEM) {
					displayView(i);
					i = slidingMenuItems.size();
				}
			}

		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onBackPressed() {
		if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	protected void displayView(int position) {

		if(slidingMenuItems.size() > position && slidingMenuItems.get(position).getType() ==
				SlidingMenuItem.ItemType.ITEM && slidingMenuItems.get(position).getFragment() != null) {

			FragmentManager fragmentManager = getFragmentManager();
			Fragment oldFragment = fragmentManager.findFragmentByTag(slidingMenuItems.get(position).getFragmentTag());

			if(oldFragment != null && oldFragment.isVisible()) {
				refresh();
				drawerLayout.closeDrawer(drawerList);
			} else {
				fragmentManager.beginTransaction().replace(R.id.frame_container, slidingMenuItems.
						get(position).getFragment(), slidingMenuItems.get(position).getFragmentTag()).commit();
				drawerList.setItemChecked(position, true);
				drawerList.setSelection(position);
				getSupportActionBar().setSubtitle(slidingMenuItems.get(position).getTitle());
				drawerLayout.closeDrawer(drawerList);

				currentSelectedItem = slidingMenuItems.get(position);
			}
		} else {
			Log.e(getClass().getSimpleName(), "Error in creating fragment");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		addOptionMenuItems(this.menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(drawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			switch(item.getItemId()) {

				case MENU_REFRESH_BUTTON_ID:
					refresh();
					break;
			}
		}

		return super.onOptionsItemSelected(item);
	}

	protected void refresh() {
		Log.d(getClass().getSimpleName(), "refresh() method called...");
	}

	public Menu getMenu() {
		return menu;
	}

	protected abstract void addSpecificContent();

	protected abstract void addOptionMenuItems(Menu menu);
}
