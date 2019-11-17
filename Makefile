CLASSPATH_ADD=src/main/java/
usage:
	@echo "make (build|start|test|clean|all|pkg)"
	@echo "make (mvnbuild|mvntest|mvnclean|mvnall|mvnpkg)"
all:
	@echo All commands using
	@make build
	@make start
build:
	@echo Build projects
	@javac src/main/java/EustroSoft/ZJson.java
	@javac -cp ${CLASSPATH_ADD} src/test/java/EustroSoft/ZJsonTest.java
pkg:
	@echo Make JAR package
	mkdir -p work
	cd src/main/java/; jar -c0f ../../../work/ZJson.jar EustroSoft/*.class
clean:
	@echo Clean project starts

test:
	@echo Test starts
	@java src/test/java/EustroSoft/ZJsonTest
start:
	@echo Start project
	@java src/main/java/EustroSoft/HelloZJson
mvnall:
	@echo All MAVEN commands using
	@make mvnclean
	@make mvnbuild
	@make mvntest
	@make mvnpkg 
mvnbuild:
	@echo MAVEN build projects
	@mvn compile
mvnpkg:
	@echo Make MAVEN packaging
	@mvn package
mvnclean:
	@echo MAVEN clean project starts
	@mvn clean
mvntest:
	@echo MAVEN test starts
	@mvn test
