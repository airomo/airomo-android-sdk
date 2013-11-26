Airomo SDK Sample applications *(airomo-sdk-android-sample)*.
=============================================================

This application project contains sample code and configuration to demostrate key features of Airomo SDK library.

Build instructions:
___________________

1. Make sure *airomo-sdk* and *airomo-sdk-ui* libraries are already imported and configured;
2. Import application project into Eclipse;
3. Right-click in *airomo-sdk-android-sample* project name and select **"Properties | Android"**. Make sure *airomo-sdk-ui* and Google *android-support-v7-appcompat* libraries  are correctly imported into Library group. Import and configure *Google Support V7 Appcompat* library from *SDK* if necessary see [this link](http://developer.android.com/tools/support-library/setup.html) for reference;
4. Make sure *proguard-project.txt* contains correct *"-include"* of *airomo-sdk-ui/proguard-project.txt* file. By default it's assumed that *airomo-sdk*, *airomo-sdk-ui* and *airomo-sdk-android-sample* are in the same folder so *proguard-project.txt* contains `-include ../airomo-sdk-ui/proguard-project.txt` instruction;

The following classes are included into the sample project:
___________________________________________________________

1) *com.airomo.sdk.sample.MainActivity* class which demostrates *com.airomo.sdk.ui.fragment.AppExploreGridFragment* usage for exploaring, filtering and searching applications using *Apiromo API*.
2) *com.airomo.sdk.sample.AiromoSDKSampleApplication* which simply extends *com.airomo.sdk.ui.AiromoSDKUIApplication* to correctly initialize *Android-Universal-Image-Loader* library instance;
3) *com.airomo.sdk.sample.DeveloperConstants* contains developer keys to access *Airomo API*;
4) *com.airomo.sdk.sample.common.TabListener* helper class to insert tabs into the system *Action Bar*;

Sample application build
________________________

Airomo SDK sample application APK build available [here](https://github.com/airomo/airomo-android-sdk/blob/master/airomo-sdk-android-sample/release/airomo-sdk-android-sample.apk).



