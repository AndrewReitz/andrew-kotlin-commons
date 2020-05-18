plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  jcenter()
}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}

dependencies {
  val kotlinVersion = "1.3.72"
  implementation(kotlin("stdlib", kotlinVersion))
  implementation(kotlin("gradle-plugin", kotlinVersion))
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.10.1")
}
