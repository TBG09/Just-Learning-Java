plugins {
    java
}

group = "org.learn.java"
version = "3.9.4"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}



tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "Main" // Update this to the fully qualified name of your main class if needed
    }

}
