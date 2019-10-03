

usage:
	@echo "Usage: make (build|run|clean|all)"

build:
	@echo "Building project..." 
	@javac HelloZJson.java
	@echo "Ok"
run:
	@java HelloZJson
clean:
	@echo "Cleaning project..." 
	@rm -f *.class
	@echo "Ok"
all:
        clean build run

