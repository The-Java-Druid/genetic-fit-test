# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Suppress warnings for missing java.beans classes not present in Android
-dontwarn java.beans.**
-keepattributes Signature

# Keep SnakeYAML classes to prevent issues during minification
-keep class org.yaml.snakeyaml.** { *; }
-dontwarn org.yaml.snakeyaml.**

# Keep the record classes, their fields, and all constructors/methods
-keep class com.geneticfittest.model.** { *; }
# Explicitly prevent R8 from optimizing or shifting constructor signatures
-keepclassmembers class com.geneticfittest.model.** {
    <init>(...);
}
# Keep the Record attribute metadata just in case your parsing checks it
-keepattributes Record

# Keep androidx.startup InitializationProvider and Initializer implementations loaded reflectively
-keep public class androidx.startup.InitializationProvider {
    public <init>();
}
-keep public class * implements androidx.startup.Initializer {
    public <init>();
}
# Prevent R8 / Proguard from obfuscating Room generated classes
-keep class * extends androidx.room.RoomDatabase { *; }
-keepclassmembers class * extends androidx.room.RoomDatabase {
    <init>(...);
}

# Keep WorkManager initialization components
-keep class androidx.work.impl.WorkDatabase_Impl { *; }
-keep class * extends androidx.work.Worker { *; }
-keep class * extends androidx.work.ListenableWorker { *; }
