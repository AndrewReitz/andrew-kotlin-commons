plugins {
  id("multiplatform-common")
}

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${versions.coroutines}")
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation(kotlin("test-junit"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.coroutines}")
      }
    }
    val jsMain by getting {
      dependencies {
        implementation(kotlin("test-js"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${versions.coroutines}")
      }
    }

    val nativeMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${versions.coroutines}")
      }
    }
  }
}
