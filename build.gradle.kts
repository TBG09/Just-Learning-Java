plugins {
    java
}

group = "org.learn.java"
version = "4.2.2.0084-dev2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // JSON Parsing library (e.g., org.json)
    implementation("org.json:json:20211205")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "Learn.Core.Main"
    }

    // Include all dependencies in the JAR
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
