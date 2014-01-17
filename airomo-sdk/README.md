Airomo SDK for Android *(airomo-sdk)*. 
=======================================
Core functionality to access Airomo cloud services for Contextual App delivery. No UI fragment is included and expected to be developed by the client application developer. The data from the cloud is shared with the client app in JSON format.

Build instructions:
___________________

1. Import into Eclipse;
2. Right-click on *libs/airomo-sdk-lib.jar* and select **"Build Path | Add to Build path"**;
3. Right-click on *libs/airomo-sdk-lib.jar* and select **"Build Path | Configure Build Path"**;
4. On **"Order and Export"** tab make sure *airomo-sdk-lib.jar* and *gcm.jar* are checked;
5. Import *airomo-sdk* library into your project. *NOTE:* No need to do this import if you're going to use *airomo-sdk-ui* library;
6. Make sure your *project.properties* file contains *"manifestmerger.enabled=true"* command to import manifest settings from the library;
