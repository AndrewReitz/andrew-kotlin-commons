rootProject.name = "andrew-kotlin-commons"

include(":commons-core", ":commons-coroutines", ":commons-test")

rootProject.children.forEach {
  it.buildFileName = "${it.name.replace("commons-", "")}.gradle.kts"
}
