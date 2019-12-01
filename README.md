```
// Preparation
javac --release 8 --source-path ./src/library/java -d ./build/library/classes ./src/library/java/com/example/library/Foo.java
javac --release 9 --source-path ./src/library/java9;./src/library/java -d ./build/library/classes9 ./src/library/java9/module-info.java ./src/library/java/com/example/library/Foo.java
jar --create -f ./build/library.jar -C ./build/library/classes com/example/library/Foo.class --release 9 -C ./build/library/classes9 module-info.class


// Demonstration

// This one works
javac --source-path ./src/consumer/java -d ./build/consumer/classes --module-path ./build/library.jar ./src/consumer/java/module-info.java

// This one does not
javac --release 9 --source-path ./src/consumer/java -d ./build/consumer/classes --module-path ./build/library.jar ./src/consumer/java/module-info.java
```