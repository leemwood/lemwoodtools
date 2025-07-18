# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# ================================
# OPTIMIZATION SETTINGS
# ================================

# Enable aggressive optimization
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# ================================
# DEBUGGING & CRASH REPORTING
# ================================

# Keep line numbers for crash reports
-keepattributes SourceFile,LineNumberTable

# Hide original source file name for security
-renamesourcefileattribute SourceFile

# Keep stack trace information
-keepattributes Exceptions

# ================================
# ANDROID & ANDROIDX
# ================================

# Keep Compose related classes (more specific rules)
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.foundation.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep class androidx.compose.animation.** { *; }

# Keep Navigation Compose
-keep class androidx.navigation.** { *; }
-keepclassmembers class androidx.navigation.** { *; }

# Keep ViewModel and Lifecycle
-keep class androidx.lifecycle.** { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class * extends androidx.lifecycle.AndroidViewModel { *; }

# Keep Activity and Fragment
-keep class * extends androidx.activity.ComponentActivity { *; }
-keep class * extends androidx.fragment.app.Fragment { *; }

# ================================
# APPLICATION SPECIFIC
# ================================

# Keep application classes (more specific)
-keep class cn.lemwood.lemwoodtools.** { *; }

# Keep data classes and models
-keep class cn.lemwood.lemwoodtools.data.model.** { *; }
-keep class cn.lemwood.lemwoodtools.domain.model.** { *; }

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ================================
# KOTLIN & COROUTINES
# ================================

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# ================================
# SERIALIZATION
# ================================

# Kotlin serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keep,includedescriptorclasses class cn.lemwood.lemwoodtools.**$$serializer { *; }
-keepclassmembers class cn.lemwood.lemwoodtools.** {
    *** Companion;
}
-keepclasseswithmembers class cn.lemwood.lemwoodtools.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# ================================
# NETWORKING (if using Retrofit/OkHttp)
# ================================

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# ================================
# SECURITY & LOGGING
# ================================

# Remove all logging in release builds
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
    public static void checkNotNull(...);
    public static void checkParameterIsNotNull(...);
    public static void checkNotNullParameter(...);
    public static void checkExpressionValueIsNotNull(...);
    public static void checkNotNullExpressionValue(...);
    public static void checkReturnedValueIsNotNull(...);
    public static void checkFieldIsNotNull(...);
}

# ================================
# WEBVIEW (if used)
# ================================

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# ================================
# WARNINGS SUPPRESSION
# ================================

-dontwarn java.lang.invoke.StringConcatFactory