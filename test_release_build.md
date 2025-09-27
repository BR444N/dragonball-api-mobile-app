# 🧪 Plan de Testing para Release Build

## ✅ Problemas Resueltos:
- Eliminado `consumerProguardFiles` (no válido para apps)
- Corregidas clases inexistentes en ProGuard
- Simplificadas reglas para evitar warnings
- Usando `proguard-rules-final.pro` optimizado

## 1. Generar APK Release

```bash
# Limpiar proyecto
./gradlew clean

# Generar APK release con minificación
./gradlew assembleRelease

# El APK estará en: app/build/outputs/apk/release/app-release.apk
```

## 2. Verificar Tamaño del APK

```bash
# Ver tamaño antes y después
ls -lh app/build/outputs/apk/release/app-release.apk

# Analizar contenido del APK (opcional)
./gradlew analyzeReleaseBundle
```

## 3. Instalar y Probar Funcionalidades Críticas

### ✅ Lista de Verificación:

#### **API y Datos**
- [ ] Carga inicial de personajes
- [ ] Navegación a detalles de personaje
- [ ] Carga de transformaciones
- [ ] Carga de planetas
- [ ] Manejo de errores de red

#### **Traducción**
- [ ] Cambio de idioma en configuración
- [ ] Traducción de contenido (ES → EN)
- [ ] Traducción funciona sin crashes

#### **UI y Navegación**
- [ ] Navegación entre pantallas
- [ ] Animación de carga (Lottie spinner)
- [ ] Carga de imágenes de personajes
- [ ] Carga de imágenes de planetas

#### **Configuración**
- [ ] Cambio de tema (Light/Dark)
- [ ] Configuración de idioma
- [ ] Persistencia de configuraciones

#### **Performance**
- [ ] App inicia sin crashes
- [ ] Transiciones suaves
- [ ] Sin memory leaks evidentes
- [ ] Scroll fluido en listas

## 4. Comandos de Debugging

### Ver logs durante testing:
```bash
# Conectar dispositivo y ver logs
adb logcat | grep -E "(br444n|dragonball|ERROR|FATAL)"

# Ver crashes específicos
adb logcat | grep -E "AndroidRuntime|FATAL EXCEPTION"
```

### Verificar ProGuard mapping:
```bash
# El archivo de mapping estará en:
# app/build/outputs/mapping/release/mapping.txt
```

## 5. Problemas Comunes y Soluciones

### Si la app crashea al inicio:
```proguard
# Agregar a proguard-rules.pro:
-keep class com.br444n.dragonball.MainActivity { *; }
```

### Si falla la carga de personajes:
```proguard
# Verificar que los modelos estén protegidos:
-keep class com.br444n.dragonball.data.remote.models.** { *; }
```

### Si falla la traducción:
```proguard
# Agregar más reglas ML Kit:
-keep class com.google.mlkit.** { *; }
```

### Si no cargan las animaciones Lottie:
```proguard
# Verificar recursos:
-keep class com.airbnb.lottie.** { *; }
```

## 6. Métricas a Verificar

- **Tamaño APK**: Debería ser 15-30% más pequeño
- **Tiempo de inicio**: Debería ser igual o mejor
- **Uso de memoria**: Verificar con Android Studio Profiler
- **Funcionalidad**: 100% de features funcionando

## 7. Si Todo Funciona Bien

```bash
# Generar bundle para Play Store
./gradlew bundleRelease

# El bundle estará en: app/build/outputs/bundle/release/app-release.aab
```

## 8. Rollback si hay Problemas

Si encuentras problemas críticos:

```kotlin
// En app/build.gradle.kts, temporalmente:
buildTypes {
    release {
        isMinifyEnabled = false  // ← Cambiar a false
        isShrinkResources = false  // ← Cambiar a false
        // ...
    }
}
```

Luego ajustar las reglas ProGuard específicas y volver a probar.