package com.airomo.sdk.sample;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.airomo.sdk.AiromoSDKConnector;
import com.airomo.sdk.common.Platforms;
import com.airomo.sdk.common.SoftwareMarkets;
import com.airomo.sdk.sample.common.TabListener;
import com.airomo.sdk.ui.fragment.AppExploreGridFragment;
import com.airomo.sdk.ui.fragment.ExploreGridFragment;
import com.airomo.sdk.ui.fragment.ExploreGridFragment.ExploreGridTileType;

/**
 * The main activity class host for SDK sample fragments.
 * AppExploreGridFragment instances inserter into this activity and assigned to different ActionBar tabs to demonstrate
 * various application exploring functionality including tile size, filtering and searching.
 * Activity uses Support V7 library to emulate ActionBar for old Android versions.
 * System ActionBar search widget used for applications search. 
 * 
 * @author Dennis Gubsky
 *
 */
public class MainActivity extends ActionBarActivity 
{
	// Fragment tags
	private static final String APP_EXPLORE_FRAGMENT_SMALL = "APP_EXPLORE_FRAGMENT_SMALL";
	private static final String APP_EXPLORE_FRAGMENT_MEDIUM = "APP_EXPLORE_FRAGMENT_MEDIUM";
	private static final String APP_EXPLORE_FRAGMENT_LARGE = "APP_EXPLORE_FRAGMENT_LARGE";
	
	// Instance state item for selected tab storing
	private static final String SELECTED_TAB = "SELECTED_TAB";
	
	// Default filter values to be used for all fragments
	private Bundle defaultFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		handleIntent(getIntent(), savedInstanceState);				
	}
	
	// Handle Intent.ACTION_SEARCH action
	@Override
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent, null);
		super.onNewIntent(intent);
	}

	private boolean instanceStateSaved = false; 
	
	// Save selected tab ID for screen rotation handling
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putInt(SELECTED_TAB, getSupportActionBar().getSelectedTab().getPosition());
		instanceStateSaved = true;
		super.onSaveInstanceState(outState);
	}

	// Handle intent and create app explore tabs and fragments
	private void handleIntent(Intent intent, Bundle savedInstanceState) 
    {
    	// Initialize default filter
		defaultFilter = new Bundle();
		// Allow Android and iOS applications
		defaultFilter.putIntArray(AiromoSDKConnector.FILTER_PLATFORMS, new int [] { Platforms.Android.ordinal(), Platforms.iOS.ordinal() });
		// Allow Google Play and Amazon markets only
		defaultFilter.putIntArray(AiromoSDKConnector.FILTER_STORES, new int[] { SoftwareMarkets.GooglePlay.ordinal() });
		// Allow only 3 categories
		/*defaultFilter.putIntArray(AiromoSDKConnector.FILTER_CATEGORIES, new int[] { 1, 2, 3 } );
		// Allow only 3 tags
		defaultFilter.putStringArray(AiromoSDKConnector.FILTER_TAGS, new String[] { "games", "kids" } );*/
		// Allow only free applications
		// defaultFilter.putString(AiromoSDKConnector.FILTER_PRICE, AiromoSDKConnector.FILTER_PRICE_FREE );
		/*// Filter applications by one of query, meta-keywords or url
		defaultFilter.putString(AiromoSDKConnector.FILTER_QUERY, "skype" );
		//defaultFilter.putString(AiromoSDKConnector.FILTER_META_KEYWORDS, "voice,chat,free" );
		//defaultFilter.putString(AiromoSDKConnector.FILTER_URL, "www.skype.com" );*/
		
    	// If this is ACTION_SEARCH intent, then add search query to the default filter 
    	if (Intent.ACTION_SEARCH.equals(intent.getAction()))
    	{    		
    		defaultFilter.putString(AiromoSDKConnector.FILTER_QUERY, intent.getStringExtra(SearchManager.QUERY));
    	}
    	
    	// Create app explore fragments
    	createAppExploreFragments(savedInstanceState);    	
    }

	
	// Create system search widget
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    MenuInflater inflater = getMenuInflater();
	    try
	    {
		    inflater.inflate(R.menu.main, menu);
		    
		    MenuItem searchItem = menu.findItem(R.id.menu_search);
	        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		    if (searchView != null)
		    {
			    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			    searchView.setIconifiedByDefault(false); 
		    }
	    }
	    catch (Exception ex)
	    {
	    	return super.onCreateOptionsMenu(menu);
	    }	    
				
		return true;
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		// Make system "home" button operational 
        /*case android.R.id.home:
        	getSupportFragmentManager().popBackStack();
            return true;*/
        default:
   			return super.onOptionsItemSelected(item);
		}    
	}

	// Display fragments with applications explorer 
	public void createAppExploreFragments(Bundle savedInstanceState)
	{
		if (instanceStateSaved) return;
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    // Remove tabs if any
	    if (actionBar.getTabCount() > 0)
	    	actionBar.removeAllTabs();
		
	    Bundle bundle; 
	    Bundle filter;
	    Tab tab;
	    
	    // Create filter for this tab
	    filter = new Bundle();
	    if (defaultFilter != null)
	    	filter.putAll(defaultFilter);
	    filter.putIntArray(AiromoSDKConnector.FILTER_CATEGORIES, new int[] { 18 } );
	    filter.putStringArray(AiromoSDKConnector.FILTER_TAGS, new String [] { /*"sport",*/ "soccer" } );
	    
	    // Create App Explore fragment with SMALL tiles
	    bundle = ExploreGridFragment.makeArguments(
	    	DeveloperConstants.CLIENT_ID
	    	, DeveloperConstants.API_KEY
	    	, ExploreGridTileType.Small
	    	, filter /*filterBundle*/
	    	, true /*clearTop*/);
	    	    	    
	    tab = actionBar.newTab()
           .setText(R.string.tab_explore_small)
           .setTabListener(new TabListener<AppExploreGridFragment>(
        				   this
        				   , APP_EXPLORE_FRAGMENT_SMALL
        				   , AppExploreGridFragment.class
        				   , bundle
        				   , savedInstanceState != null ? getSupportFragmentManager().findFragmentByTag(APP_EXPLORE_FRAGMENT_SMALL) : null));	    
	    actionBar.addTab(tab);

	    // Create filter for this tab
	    filter = new Bundle();
	    if (defaultFilter != null)
	    	filter.putAll(defaultFilter);
	    filter.putIntArray(AiromoSDKConnector.FILTER_CATEGORIES, new int[] { 1, 2, 3 } );
	    
	    // Create App Explore fragment with MEDIUM tiles
	    bundle = ExploreGridFragment.makeArguments(
		    	DeveloperConstants.CLIENT_ID
		    	, DeveloperConstants.API_KEY
		    	, ExploreGridTileType.Medium
		    	, filter /*filterBundle*/
		    	, true /*clearTop*/);
	    tab = actionBar.newTab()
           .setText(R.string.tab_explore_medium)
           .setTabListener(new TabListener<AppExploreGridFragment>(
        				   this
        				   , APP_EXPLORE_FRAGMENT_MEDIUM
        				   , AppExploreGridFragment.class
        				   , bundle
        				   , savedInstanceState != null ? getSupportFragmentManager().findFragmentByTag(APP_EXPLORE_FRAGMENT_MEDIUM) : null));	    
	    actionBar.addTab(tab);
	    
	    // Create filter for this tab
	    filter = new Bundle();
	    if (defaultFilter != null)
	    	filter.putAll(defaultFilter);
	    
	    // Create App Explore fragment with LARGE tiles
	    bundle = ExploreGridFragment.makeArguments(
		    	DeveloperConstants.CLIENT_ID
		    	, DeveloperConstants.API_KEY
		    	, ExploreGridTileType.Large
		    	, filter /*filterBundle*/
		    	, true /*clearTop*/);
	    tab = actionBar.newTab()
           .setText(R.string.tab_explore_large)
           .setTabListener(new TabListener<AppExploreGridFragment>(
        				   this
        				   , APP_EXPLORE_FRAGMENT_LARGE
        				   , AppExploreGridFragment.class
        				   , bundle
        				   , savedInstanceState != null ? getSupportFragmentManager().findFragmentByTag(APP_EXPLORE_FRAGMENT_LARGE) : null));	    
	    actionBar.addTab(tab);
	    
	    // Switch tab if this is screen rotation
	    if (savedInstanceState != null)
	    {
	    	actionBar.getTabAt(savedInstanceState.getInt(SELECTED_TAB, 0)).select();
	    }
	}
}
