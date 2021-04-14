import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "kr.devunt"
version = "0.0.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.4.32"

    kotlin("plugin.spring") version "1.4.32"
    kotlin("plugin.jpa") version "1.4.32"

    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

repositories {
    mavenCentral()
}

dependencies {
    fun spring(module: String) = "org.springframework.boot:spring-boot-$module"

    kotlin("stdlib-jdk8")
    kotlin("reflect")

    implementation(spring("starter-web"))
    implementation(spring("starter-security"))
    implementation(spring("starter-data-jpa"))
    developmentOnly(spring("devtools"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(spring("starter-test"))
    testImplementation("org.springframework.security:spring-security-test")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}