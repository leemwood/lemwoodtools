# Performance optimization rules for ProGuard/R8
# This file contains rules to optimize app performance while maintaining functionality

# Keep application class
-keep public class * extends android.app.Application

# Keep all classes that extend Activity, Service, BroadcastReceiver, ContentProvider
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep all View constructors
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Jetpack Compose specific rules
-keep class androidx.compose.** { *; }
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Keep data classes used with Compose
-keep @androidx.compose.runtime.Stable class * {
    *;
}
-keep @androidx.compose.runtime.Immutable class * {
    *;
}

# Keep ViewModel classes
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Keep classes with @Keep annotation
-keep @androidx.annotation.Keep class * {*;}
-keep @androidx.annotation.Keep interface * {*;}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

# Optimization settings
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Remove debug code
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    static void checkNotNullParameter(java.lang.Object, java.lang.String);
    static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
    static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    static void checkFieldIsNotNull(java.lang.Object, java.lang.String);
    static void checkFieldIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    static void checkNotNull(java.lang.Object);
    static void checkNotNull(java.lang.Object, java.lang.String);
    static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    static void throwUninitializedPropertyAccessException(java.lang.String);
}

# Keep line numbers for debugging crashes
-keepattributes SourceFile,LineNumberTable

# Keep generic signatures for reflection
-keepattributes Signature

# Keep annotations
-keepattributes *Annotation*

# Crashlytics (if used)
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# Gson/JSON serialization (if used)
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Retrofit (if used)
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# OkHttp (if used)
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Room Database (if used)
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Navigation Component
-keepnames class androidx.navigation.fragment.NavHostFragment

# WorkManager (if used)
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.InputMerger
-keep class androidx.work.impl.background.systemalarm.RescheduleReceiver