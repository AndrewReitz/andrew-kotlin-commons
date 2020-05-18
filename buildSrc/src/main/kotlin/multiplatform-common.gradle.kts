@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.dokka")
  `maven-publish`
}

repositories {
  mavenCentral()

  jcenter()
}

kotlin {
  jvm()
  js()
  linuxX64()
  macosX64()
  mingwX86()

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

    val nativeMain = create("nativeMain") {
      dependsOn(commonMain)
    }

    val linuxX64Main by getting {
      dependsOn(nativeMain)
    }

    val macosX64Main by getting {
      dependsOn(nativeMain)
    }

    val mingwX86Main by getting {
      dependsOn(nativeMain)
    }
  }
}

tasks.withType<DokkaTask>().configureEach {
  outputDirectory = "$buildDir/dokka"
  outputFormat = "html"
}
