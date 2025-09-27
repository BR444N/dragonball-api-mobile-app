# ================================
# SIMPLIFIED PROGUARD RULES
# ================================
# More conservative approach with fewer broad rules

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
-keep class com.br444n.dragonball.services.TranslationService { *; }
-keep class com.br444n.dragonball.managers.language.** { *; }
-keep class com.br444n.dragonball.managers.theme.** { *; }
-keep class com.br444n.dragonball.navigation.** { *; }

# ================================
# COMPOSE ESSENTIALS (MINIMAL)
# ================================
-keep class **.*ComposableSingletons* { *; }
-keep class **.*LiveLiterals* { *; }
-keepattributes *Annotation*

# ================================
# THIRD PARTY LIBRARIES (MINIMAL)
# ================================
# ML Kit (only translation)
-keep class com.google.mlkit.nl.translate.** { *; }
-dontwarn com.google.mlkit.**
-dontwarn com.google.android.gms.**

# Lottie (only what we use)
-dontwarn com.airbnb.lottie.**

# Coil (only what we use)
-dontwarn coil.**
-dontwarn io.coil.**

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