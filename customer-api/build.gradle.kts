import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.5.0"
	kotlin("jvm") version "1.5.0"
	kotlin("plugin.spring") version "1.5.0"
	kotlin("kapt") version "1.5.0"
}

group = "com.pagaleve"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.mapstruct:mapstruct:1.4.2.Final")
	kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")
	implementation("software.amazon.awssdk:dynamodb:2.16.61")
	implementation("io.github.boostchicken:spring-data-dynamodb:5.2.5")
	implementation("com.amazonaws:aws-java-sdk:1.11.1017")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.11.0")
	testImplementation("org.mockito:mockito-inline:3.9.0")
	testImplementation("org.testcontainers:testcontainers:1.14.1")
	testImplementation("org.testcontainers:junit-jupiter:1.14.1")
}

dependencyManagement {
	imports {
		mavenBom("software.amazon.awssdk:bom:2.16.60")
	}
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

noArg {
	annotation("com.pagaleve.eheinen.annotations.NoArgConstructor")
	invokeInitializers = true
}
