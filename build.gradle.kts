plugins {
  kotlin("multiplatform") version "1.3.72"
  `maven-publish`
}

group = "cash.andrew.ktx"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

kotlin {
  jvm()
  js()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("stdlib-common"))
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation(kotlin("stdlib"))
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
      }
    }
    val jsMain by getting {
      dependencies {
        implementation(kotlin("stdlib-js"))
      }
    }
    val jsTest by getting {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
  }
}
