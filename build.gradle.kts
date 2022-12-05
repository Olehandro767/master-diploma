plugins {
	java
	id("org.springframework.boot") version "2.7.6"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.graalvm.buildtools.native") version "0.9.18"
}

group = "ua.edu.ontu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.auth0:java-jwt:4.2.1")
	implementation("org.telegram:telegrambots:6.1.0")
	implementation("com.sun.mail:javax.mail:1.6.2")
	implementation("io.github.cdimascio:dotenv-java:2.3.1")
	implementation("com.h2database:h2")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
