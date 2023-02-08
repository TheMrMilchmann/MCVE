# https://github.com/LWJGL/lwjgl3/issues/856

Currently, the `requires org.lwjgl;` line in the module declaration is commented out.
Running the `run` task of the Gradle project now yields an error:

```
> Task :compileJava UP-TO-DATE
> Task :processResources NO-SOURCE
> Task :classes UP-TO-DATE
> Task :jar UP-TO-DATE

> Task :run FAILED
3 actionable tasks: 1 executed, 2 up-to-date
[LWJGL] Failed to load a library. Possible solutions:
	a) Add the directory that contains the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.
	b) Add the JAR that contains the shared library to the classpath.
[LWJGL] Enable debug mode with -Dorg.lwjgl.util.Debug=true for better diagnostics.
[LWJGL] Enable the SharedLibraryLoader debug mode with -Dorg.lwjgl.util.DebugLoader=true for better diagnostics.
Exception in thread "main" java.lang.UnsatisfiedLinkError: Failed to locate library: lwjgl.dll
	at org.lwjgl/org.lwjgl.system.Library.loadSystem(Library.java:147)
	at org.lwjgl/org.lwjgl.system.Library.loadSystem(Library.java:67)
	at org.lwjgl/org.lwjgl.system.Library.<clinit>(Library.java:50)
	at org.lwjgl/org.lwjgl.system.windows.WinBase.<clinit>(WinBase.java:26)
	at com.example/com.example.Main.main(Main.java:8)

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':run'.
> Process 'command 'C:\Program Files\Java\openjdk-17\bin\java.exe'' finished with non-zero exit value 1

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 195ms
```

The natives module is on the modulepath but cannot be found by LWJGL. (We can
verify this by executing the Gradle task again with the `--debug` option.)

The program only runs successful when uncommenting the above-mentioned line in
the module declaration.

```
> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes
> Task :jar

> Task :run
0

BUILD SUCCESSFUL in 338ms
3 actionable tasks: 3 executed
```