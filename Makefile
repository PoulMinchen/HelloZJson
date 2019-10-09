

usage:
	@echo "Usage: make (build|run|clean|all)"

build:
	@echo "Building project..." 
	@javac ZJsonTests.java
	@javac HelloZJson.java
	@javac ZJson.java
	@echo "Ok"
run:
	@java HelloZJson
clean:
	@echo "Cleaning project..." 
	@rm -f *.class
	@echo "Ok"



