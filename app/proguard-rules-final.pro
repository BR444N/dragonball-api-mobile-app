# ================================
# DRAGON BALL APP - PROGUARD RULES
# ================================
# Simplified and tested rules to avoid "Unresolved class name" errors

# ================================
# DEBUGGING & STACK TRACES
# ================================
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*

# ================================
# RETROFIT & GSON RULES (CRITICAL)
# ================================
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions*

-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Gson rules
-keepattributes Signature
-dontwarn sun.misc.**
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# ================================
# DATA MODELS (CRITICAL FOR API)
# ================================
-keep class com.br444n.dragonball.data.remote.models.** { *; }

# ================================
# ESSENTIAL APP CLASSES
# ================================
-keep class com.br444n.dragonball.MainActivity { *; }
-keep class com.br444n.dragonball.services.** { *; }
-keep class com.br444n.dragonball.managers.** { *; }
-keep class com.br444n.dragonball.navigation.** { *; }
-keep class com.br444n.dragonball.ui.theme.** { *; }

# ================================
# THIRD PARTY LIBRARIES
# ================================
# ML Kit Translation (safe wildcards)
-keep class com.google.mlkit.nl.translate.** { *; }
-keep class com.google.android.gms.tasks.** { *; }
-dontwarn com.google.mlkit.**
-dontwarn com.google.android.gms.**

# Lottie (safe wildcards)
-keep class com.airbnb.lottie.compose.** { *; }
-dontwarn com.airbnb.lottie.**

# Coil (safe wildcards)
-keep class io.coil.compose.** { *; }
-dontwarn coil.**
-dontwarn io.coil.**

# Navigation Compose (safe wildcards)
-keep class androidx.navigation.compose.** { *; }
-dontwarn androidx.navigation.**

# ================================
# COMPOSE ESSENTIALS
# ================================
-keep class **.*ComposableSingletons* { *; }
-keep class **.*LiveLiterals* { *; }
-dontwarn androidx.compose.**

# ================================
# COROUTINES ESSENTIALS
# ================================
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-dontwarn kotlinx.coroutines.**

# ================================
# STANDARD ANDROID RULES
# ================================
-keepclassmembers class * {
    native <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}