
plugins {
    id("java")
    id("pmd")
    id("checkstyle")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("com.google.code.gson:gson:2.13.2")
        implementation ("com.google.code.gson:gson:2.10.1")

}

tasks.test {
    useJUnitPlatform()
}

checkstyle {
    toolVersion = "10.17.0"
    configFile = file("config/checkstyle/checkstyle.xml")
    isIgnoreFailures = true
    maxWarnings = 0
}

pmd {
    toolVersion = "6.55.0"
    ruleSetFiles = files("config/pmd/pmd.xml")
    ruleSets = listOf()
    isIgnoreFailures = true
}

