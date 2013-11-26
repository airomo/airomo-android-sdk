Airomo SDK UI for Android *(airomo-sdk-ui)*. 
============================================

This library contains Airomo exploring UI fragments to be reused in your projects.

Build instructions:

1. Import into Eclipse;
2. Right-click on *libs/airomo-sdk-ui-lib.jar* and select **"Build Path | Add to Build path"**;
3. Right-click on *libs/airomo-sdk-ui-lib.jar* and select **"Build Path | Configure Build Path"**;
4. On **"Order and Export"** tab make sure *airomo-sdk-ui-lib.jar* is checked;
5. Import *airomo-sdk-ui* library into your project;
6. Make sure your *project.properties* file contains *"manifestmerger.enabled=true"* command to import manifest settings from the library;
7. Clone and import *Android-Universal-Image-Loader* library into your project from [this link](https://github.com/nostra13/Android-Universal-Image-Loader);
8. Right-click on *airomo-sdk-ui* project name and select **"Properties | Android"**. Make sure *airomo-sdk* and *Android-Universal-Image-Loader* libraries are correctly imported in *Library* group;
9. Make sure *proguard-project.txt* contains correct *"-include"* of *airomo-sdk/proguard-project.txt* file. By default it's assumed that *airomo-sdk* and *airomo-sdk-ui* are in the same folder so *proguard-project.txt* contains `-include ../airomo-sdk/proguard-project.txt` instruction;
10. Inherit your *Application* class from *com.airomo.sdk.ui.AiromoSDKUIApplication* and make sure you called *super.OnCreate* method to correctly initialize *Android-Universal-Image-Loader* library instance;

*Airomo SDK UI for Android* uses *Google Android Support library* to be compatible with older versions of Android. Please make sure you have *Google Android Support* library in your project exported into the target application. 

Using the library.

To insert applications exploring fragment into your UI you need to do the following:

1. Make sure your activity is inherited from *android.support.v4.app.FragmentActivity* or it's descendant class;
2. Create filtering bundle:
	`defaultFilter = new Bundle();
	// Allow Android applications only
	defaultFilter.putString(AiromoSDKConnector.FILTER_PLATFORM, Integer.toString(Platforms.Android.ordinal()));
	// Allow Google Play and Amazon markets only
	defaultFilter.putIntArray(AiromoSDKConnector.FILTER_STORE, new int[] { SoftwareMarkets.GooglePlay.ordinal(), SoftwareMarkets.Amazon.ordinal() });
	// Allow only 3 categories
	defaultFilter.putIntArray(AiromoSDKConnector.FILTER_CATEGORIES, new int[] { 1, 2, 3 } );
	// Allow only 2 tags
	defaultFilter.putStringArray(AiromoSDKConnector.FILTER_TAGS, new String[] { "games", "kids" } );
	// Allow only free applications
	defaultFilter.putString(AiromoSDKConnector.FILTER_PRICE, AiromoSDKConnector.FILTER_PRICE_FREE );
	// Filter applications by one of query, meta-keywords or url
	defaultFilter.putString(AiromoSDKConnector.FILTER_QUERY, "skype" );
	//defaultFilter.putString(AiromoSDKConnector.FILTER_META_KEYWORDS, "voice,chat,free" );
	//defaultFilter.putString(AiromoSDKConnector.FILTER_URL, "www.skype.com" );`

3. Create new instance of the *com.airomo.sdk.ui.fragment.AppExploreGridFragment* class:

	`AppExploreGridFragment fragment = AppExploreGridFragment.makeInstance(
		DeveloperConstants.CLIENT_ID	/* Your Developer ID to access Airomo API */
		, DeveloperConstants.API_KEY	/* Your Developer API KEY to access Airomo API */
		, com.airomo.sdk.ui.fragment.ExploreGridFragment.ExploreGridTileType.Small	/* Explore view tiles size Small | Meduim | Large */
		, defaultFilter			/* filterBundle for application filtering and searching */
		, true 				/* clearTop - let explore view to reduce memory usage removing apps from the top while scrolling */
	);`

4. Insert newly created fragment into your UI:

	`getSupportFragmentManager().beginTransaction().add(frag, R.id.explore_frag, "AIROMO_SDK_APP_EXPLORE_FRAG").commit();`

+ *AppExploreGridFragment* supports optimized screen rotation functionality and automatically changes number or columns by the screen width;
+ Images are loaded into the explore fragment by the *Android-Universal-Image-Loader* and cached on SD card. You can also use *Android-Universal-Image-Loader* library in your application. If you are don't like default settings of *Android-Universal-Image-Loader* then do not inherit from *com.airomo.sdk.ui.AiromoSDKUIApplication* and configure *Android-Universal-Image-Loader* instance in your application class;
+ *AppExploreGridFragment* catches all server exceptions and displays Error Dialog in case of error;


