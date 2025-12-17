# MCVE

Run `./gradlew build`

See

```
> Task :lib-java:compileJava
CLASS --- com.example.lib.java.JavaClass
PACKAGE --- com.example.lib.java
MODULE --- com.example.lib.java
MODULE --- com.example.lib.java
```

and

```
> Task :lib-kotlin:kaptKotlin
CLASS --- com.example.lib.kotlin.JavaClass
PACKAGE --- com.example.lib.kotlin
MODULE --- unnamed module
```

See https://youtrack.jetbrains.com/issue/KT-82876
