plugins {
	java
	id("org.springframework.boot") version "3.4.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "rabbitmq.project"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.rabbitmq:amqp-client:5.22.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<JavaExec>("runSender"){
	group="rabbitmq.project"
	mainClass.set("rabbitmq.project.rabbitmq_project.Send")
	classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("runReceiver"){
	group="rabbitmq.project"
	mainClass.set("rabbitmq.project.rabbitmq_project.Recv")
	classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("runNewTask"){
	group="rabbitmq.project"
	mainClass.set("rabbitmq.project.rabbitmq_project.NewTask")
	classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("runWorker"){
	group="rabbitmq.project"
	mainClass.set("rabbitmq.project.rabbitmq_project.Worker")
	classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("runEmitLog"){
	group="rabbitmq.project"
	mainClass.set("rabbitmq.project.rabbitmq_project.EmitLog")
	classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("runReceiveLogs"){
	group="rabbitmq.project"
	mainClass.set("rabbitmq.project.rabbitmq_project.ReceiveLogs")
	classpath = sourceSets["main"].runtimeClasspath
}
