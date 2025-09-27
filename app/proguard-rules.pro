# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# ================================
# DEBUGGING & STACK TRACES
# ================================
# Keep line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep annotations for better debugging
-keepattributes *Annotation*

# ================================
# RETROFIT & GSON RULES
# ================================
# Retrofit does reflection on generic parameters and InnerClass requires enclosing class.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep inherited services.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# ================================
# GSON RULES
# ================================
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# ================================
# DATA MODELS - KEEP ALL MODELS
# ================================
# Keep all data classes used with Gson
-keep class com.br444n.dragonball.data.remote.models.** { *; }

# Keep data class constructors and fields
-keepclassmembers class com.br444n.dragonball.data.remote.models.** {
    <init>(...);
    <fields>;
}

# ================================
# ML KIT TRANSLATION RULES
# ================================
# Keep only essential ML Kit classes
-keep class com.google.mlkit.nl.translate.** { *; }
-keep class com.google.android.gms.tasks.** { *; }
-dontwarn com.google.android.gms.**
-dontwarn com.google.mlkit.**

# Keep translation service
-keep class com.br444n.dragonball.services.TranslationService { *; }

# ================================
# LOTTIE ANIMATION RULES
# ================================
# Keep Lottie Compose classes (use wildcard for safety)
-keep class com.airbnb.lottie.compose.** { *; }
-dontwarn com.airbnb.lottie.**

# ================================
# COIL IMAGE LOADING RULES
# ================================
# Keep Coil Compose classes (use wildcard for safety)
-keep class io.coil.compose.** { *; }
-dontwarn coil.**
-dontwarn io.coil.**

# ================================
# JETPACK COMPOSE RULES
# ================================
# Keep Compose compiler generated classes (essential for Compose to work)
-keep class **.*ComposableSingletons* { *; }
-keep class **.*LiveLiterals* { *; }

# Keep @Composable annotation and functions
-keepattributes *Annotation*
-keep @androidx.compose.runtime.Composable class * {
    <methods>;
}

# Keep essential Compose runtime classes (minimal set)
-keep class androidx.compose.runtime.Composer { *; }
-keep class androidx.compose.runtime.ComposerKt { *; }
-keep class androidx.compose.runtime.CompositionLocal { *; }

# Don't warn about Compose internals
-dontwarn androidx.compose.**

# ================================
# KOTLIN COROUTINES RULES
# ================================
# Keep only essential coroutines classes
-keep class kotlinx.coroutines.Dispatchers { *; }
-keep class kotlinx.coroutines.CoroutineScope { *; }
-keep class kotlinx.coroutines.flow.Flow { *; }
-keep class kotlinx.coroutines.flow.StateFlow { *; }
-keep class kotlinx.coroutines.flow.MutableStateFlow { *; }
-dontwarn kotlinx.coroutines.**

# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
-keepclassmembers class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}

# ================================
# NAVIGATION COMPONENT RULES
# ================================
# Keep Navigation Compose classes (use wildcard for safety)
-keep class androidx.navigation.compose.** { *; }
-keep class androidx.navigation.NavController { *; }
-dontwarn androidx.navigation.**

# ================================
# GENERAL ANDROID RULES
# ================================
# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ================================
# APPLICATION SPECIFIC RULES
# ================================
# Keep only essential application classes
-keep class com.br444n.dragonball.MainActivity { *; }

# Keep managers and services (specific classes)
-keep class com.br444n.dragonball.managers.language.** { *; }
-keep class com.br444n.dragonball.managers.theme.** { *; }
-keep class com.br444n.dragonball.services.TranslationService { *; }

# Keep navigation and UI components
-keep class com.br444n.dragonball.navigation.** { *; }
-keep class com.br444n.dragonball.ui.theme.** { *; }