# Smart Rate Dialog 🚀

Smart Rate Dialog is a lightweight Android library designed to help you filter user feedback effectively before they leave a rating on the Google Play Store.

This library acts as a feedback interceptor:
- 😞 **Negative Feedback:** Redirects users to an email client so they can send their complaints or bug reports directly to you, keeping negative reviews off the Play Store.
- 😍 **Positive Feedback:** Redirects users straight to your app's Google Play Store page so they can easily leave a 5-star review.

## ✨ Features
* Ultra-easy implementation (just a single line of code).
* Smart routing using native Android `Intent`.
* Protects your app's public rating from unexpected bug complaints.
* Extremely lightweight with zero impact on your final APK size.

## 📦 Installation

Step 1: Add the **JitPack** repository to your root `settings.gradle.kts` file:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("[https://jitpack.io](https://jitpack.io)") // Add this line
    }
}


dependencies {
    implementation("com.github.harunarrosyid:smartratedialog:1.0.4")
}

import com.harunarrosyid.smartratedialog.SmartRate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Trigger the smart rating dialog
        SmartRate.show(
            context = this,
            appName = "Your App Name",       // App name displayed in the dialog title
            supportEmail = "your.email@domain.com", // Target email for user complaints
            packageName = packageName        // Automatically fetches current app's package name
        )
    }
}
