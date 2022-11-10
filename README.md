Although MontiCore generally supports left recursion (including mutual left recursion) when using interfaces, the generator seems to pass invalid input to Antlr for the snippet:

```
grammar Foo {

    interface Expression;
	
    // Note that this doesn't fail if the parenthesis are removed
    BinaryExpression implements Expression = (Expression) "+" Expression;

    LeafExpression implements Expression = "Bar";

}
```

```shell
$ gradlew generate

> Task :generate FAILED
[ERROR] 0xA1129 Error from Antlr subsystem: The following sets of rules are mutually left-recursive [expression] (see e.g. www.antlr.org)
```