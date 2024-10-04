### Estrutura Sugerida para o `deployment.md`:

```markdown
# TaskManagerPlus Android App - Deployment Guide

## 1. Overview
This document provides step-by-step instructions for building, packaging, and deploying the TaskManagerPlus Android App. It covers both local deployment (for testing on a device or emulator) and production deployment (publishing to the Google Play Store).

## 2. Prerequisites
Before deploying the app, ensure the following are installed and configured:
- **Android Studio**: Download and install from [Android Developer Website](https://developer.android.com/studio).
- **Java Development Kit (JDK)**: Version 8 or higher.
- **Android SDK**: Installed through Android Studio.
- **Google Play Developer Account** (for production deployment): Sign up at [Google Play Console](https://play.google.com/console).

## 3. Local Deployment (Testing on a Device/Emulator)

### 3.1 Build the APK
To build the APK locally for testing:
1. Open **Android Studio**.
2. Open the project by navigating to `taskmanagerplus-android-app`.
3. Click on **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. Android Studio will generate the APK, which can be found in the `app/build/outputs/apk/` directory.

### 3.2 Run on an Emulator
To test the app on an Android emulator:
1. Open **Android Studio**.
2. Navigate to **Tools > AVD Manager**.
3. Create a new virtual device if you don’t have one.
4. Select an Android system image for the virtual device.
5. Click **Run** to launch the emulator.
6. Once the emulator is running, click **Run > Run 'app'** to install and launch the app on the emulator.

### 3.3 Run on a Physical Device
To test the app on a physical device:
1. Enable **Developer Mode** on your Android device by navigating to **Settings > About Phone > Tap Build Number 7 times**.
2. Enable **USB Debugging** in the Developer Options.
3. Connect the device to your computer via USB.
4. In Android Studio, select your device from the list in the **Run/Debug Configuration** dropdown.
5. Click **Run > Run 'app'** to install and launch the app on your device.

## 4. Production Deployment (Publishing to Google Play Store)

### 4.1 Create a Signed APK
Before publishing to the Play Store, you need to create a signed APK:
1. Open **Android Studio**.
2. Navigate to **Build > Generate Signed Bundle / APK**.
3. Select **APK** and click **Next**.
4. If you already have a key, use the existing one. Otherwise, create a new key:
   - Enter details such as the **Key store path**, **Password**, and **Alias**.
   - Choose **Release** as the build type.
5. Click **Finish** to generate the signed APK.

### 4.2 Set Up Google Play Console
1. Log in to your **Google Play Console** at [https://play.google.com/console](https://play.google.com/console).
2. Create a new app by clicking **Create App**.
3. Fill in the required information, such as the app’s name, category, and contact details.

### 4.3 Upload the APK
1. In the Google Play Console, navigate to **Release > Production**.
2. Click **Create New Release**.
3. Upload the signed APK you generated earlier.
4. Review the release details and click **Save**.

### 4.4 Fill Out App Information
1. Complete the **Store Listing** section with:
   - App name
   - Short description
   - Full description
   - Screenshots of the app
   - App icon (512x512 pixels)
2. Complete the **Content Rating** section by filling out the questionnaire.
3. Set the app’s **pricing and availability**.

### 4.5 Review and Publish
1. Review all sections to ensure everything is filled out correctly.
2. Once all steps are completed, click **Publish** to release the app on the Google Play Store.

## 5. Version Control and Release Notes
When publishing a new version of the app:
1. Update the version code and version name in the `build.gradle` file:
   ```gradle
   defaultConfig {
       versionCode 2
       versionName "1.1"
   }
   ```
2. Add release notes for the new version in the Google Play Console’s **Release** section.

## 6. Troubleshooting

### 6.1 APK Signing Issues
- Ensure that the **keystore path** and **password** are correct.
- If you lose the keystore, you will not be able to update the app. Keep a backup of the keystore in a safe place.

### 6.2 Play Store Upload Errors
- **Error: Target API Level**: Ensure that your app targets at least the latest required Android API level. Update `compileSdkVersion` and `targetSdkVersion` in `build.gradle` if necessary.
- **Error: Missing Information**: Check if all mandatory sections in the Google Play Console are completed (e.g., screenshots, content rating, etc.).

## 7. Conclusion
By following these steps, you can successfully build, test, and deploy the TaskManagerPlus Android App both locally and to the Google Play Store. Ensure that all required app information is filled out in the Google Play Console before publishing.
```

---

### Explanation of the Points:
1. **Overview**: A general overview of the deployment process, including local (for testing) and production (Google Play Store).
2. **Prerequisites**: The tools and prerequisites needed for deployment.
3. **Local Deployment**: Instructions for building and testing the app locally on an emulator or physical device.
4. **Production Deployment**: Detailed steps to publish the app on the Play Store, including creating a signed APK and how to set up the Google Play Console.
5. **Version Control and Release Notes**: How to manage app versions and add release notes.
6. **Troubleshooting**: Solutions for common issues that may arise during deployment.

### Next Steps:
- This document can be adjusted according to your specific deployment needs.
- We can proceed with adjustments or finalize the documents when you're ready!

This **Deployment Guide** will help ensure that the app distribution process is smooth and organized.

---
