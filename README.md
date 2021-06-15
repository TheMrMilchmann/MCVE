I put  this together before I found the issue: https://github.com/Kotlin/kotlinx.serialization/issues/967.
Anyway, building the project fails with an rather obscure exception.

    ./gradlew build

```
org.jetbrains.kotlin.js.facade.exceptions.TranslationRuntimeException: Unexpected error occurred compiling the following fragment: 'import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class GW2v1EventDetails(
    val events: Map<String, EventDetails>
) {

    @Serializable
    data class EventDetails(
        val name: String,
        val level: Int,
        @SerialName("map_id")
        val mapID: Int,
        val flags: List<String>,
        val location: Location
    ) {

        @Suppress("ClassName")
        private object __JsonParametricSerializer_Location : JsonContentPolymorphicSerializer<Location>(Location::class) {
            override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out Location> {
                return when (element.jsonObject["type"]!!.jsonPrimitive.content) {
                    "cylinder" -> Location.Cylinder.serializer()
                    "poly" -> Location.Poly.serializer()
                    "sphere" -> Location.Sphere.serializer()
                    else -> TODO()
                }
            }
        }

        @Serializable(with = __JsonParametricSerializer_Location::class)
        sealed class Location {

            abstract val type: String

            @Suppress("ClassName")
            @Serializer(forClass = Cylinder::class)
            private object __CylinderGeneratedSerializer : KSerializer<Cylinder>

            @Suppress("ClassName")
            private object __CylinderSerializer : JsonTransformingSerializer<Cylinder>(__CylinderGeneratedSerializer) {
                override fun transformDeserialize(element: JsonElement): JsonElement =
                    JsonObject(element.jsonObject - "__virtualType")
            }

            @Serializable(with = __CylinderSerializer::class)
            data class Cylinder(
                override val type: String,
                val cylinder: Cylinder
            ) : Location() {

                @Serializable
                data class Cylinder(
                    val center: List<Double>,
                    val height: Double,
                    val radius: Double,
                    val rotation: Double
                )

            }

            @Suppress("ClassName")
            @Serializer(forClass = Poly::class)
            private object __PolyGeneratedSerializer : KSerializer<Poly>

            @Suppress("ClassName")
            private object __PolySerializer : JsonTransformingSerializer<Poly>(__PolyGeneratedSerializer) {
                override fun transformDeserialize(element: JsonElement): JsonElement =
                    JsonObject(element.jsonObject - "__virtualType")
            }

            @Serializable(with = __PolySerializer::class)
            data class Poly(
                override val type: String,
                val poly: Poly
            ) : Location() {

                @Serializable
                public data class Poly(
                    val center: List<Double>,
                    @SerialName("z_range")
                    val zRange: List<Double>,
                    val points: List<List<Double>>
                )

            }

            @Suppress("ClassName")
            @Serializer(forClass = Sphere::class)
            private object __SphereGeneratedSerializer : KSerializer<Sphere>

            @Suppress("ClassName")
            private object __SphereSerializer : JsonTransformingSerializer<Sphere>(__SphereGeneratedSerializer) {
                override fun transformDeserialize(element: JsonElement): JsonElement =
                    JsonObject(element.jsonObject - "__virtualType")
            }

            @Serializable(with = __SphereSerializer::class)
            data class Sphere(
                override val type: String,
                val sphere: Sphere
            ) : Location() {

                @Serializable
                data class Sphere(
                    val center: List<Double>,
                    val radius: Double,
                    val rotation: Double
                )

            }

        }

    }

}' at (1,1) in D:/TMM/MCVE/src/commonMain/kotlin/GW2v1EventDetails.kt
	at org.jetbrains.kotlin.js.translate.general.Translation.translateFile(Translation.java:385)
	at org.jetbrains.kotlin.js.translate.general.Translation.doGenerateAst(Translation.java:336)
	at org.jetbrains.kotlin.js.translate.general.Translation.generateAst(Translation.java:305)
	at org.jetbrains.kotlin.js.facade.K2JSTranslator.translate(K2JSTranslator.kt:163)
	at org.jetbrains.kotlin.js.facade.K2JSTranslator.translateUnits(K2JSTranslator.kt:108)
	at org.jetbrains.kotlin.js.facade.K2JSTranslator.translateUnits$default(K2JSTranslator.kt:87)
	at org.jetbrains.kotlin.js.facade.K2JSTranslator.translate(K2JSTranslator.kt:82)
	at org.jetbrains.kotlin.cli.js.K2JSCompiler.translate(K2JSCompiler.java:165)
	at org.jetbrains.kotlin.cli.js.K2JSCompiler.doExecute(K2JSCompiler.java:307)
	at org.jetbrains.kotlin.cli.js.K2JSCompiler.doExecute(K2JSCompiler.java:74)
	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:88)
	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:44)
	at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:98)
	at org.jetbrains.kotlin.incremental.IncrementalJsCompilerRunner.runCompiler(IncrementalJsCompilerRunner.kt:180)
	at org.jetbrains.kotlin.incremental.IncrementalJsCompilerRunner.runCompiler(IncrementalJsCompilerRunner.kt:77)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.compileIncrementally(IncrementalCompilerRunner.kt:303)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.compileImpl$rebuild(IncrementalCompilerRunner.kt:99)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.compileImpl(IncrementalCompilerRunner.kt:124)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.compile(IncrementalCompilerRunner.kt:74)
	at org.jetbrains.kotlin.incremental.IncrementalCompilerRunner.compile$default(IncrementalCompilerRunner.kt:65)
	at org.jetbrains.kotlin.daemon.CompileServiceImplBase.execJsIncrementalCompiler(CompileServiceImpl.kt:550)
	at org.jetbrains.kotlin.daemon.CompileServiceImplBase.access$execJsIncrementalCompiler(CompileServiceImpl.kt:96)
	at org.jetbrains.kotlin.daemon.CompileServiceImpl.compile(CompileServiceImpl.kt:1747)
	at jdk.internal.reflect.GeneratedMethodAccessor101.invoke(Unknown Source)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:567)
	at java.rmi/sun.rmi.server.UnicastServerRef.dispatch(UnicastServerRef.java:357)
	at java.rmi/sun.rmi.transport.Transport$1.run(Transport.java:200)
	at java.rmi/sun.rmi.transport.Transport$1.run(Transport.java:197)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:691)
	at java.rmi/sun.rmi.transport.Transport.serviceCall(Transport.java:196)
	at java.rmi/sun.rmi.transport.tcp.TCPTransport.handleMessages(TCPTransport.java:587)
	at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0(TCPTransport.java:828)
	at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.lambda$run$0(TCPTransport.java:705)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:391)
	at java.rmi/sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run(TCPTransport.java:704)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:630)
	at java.base/java.lang.Thread.run(Thread.java:831)
Caused by: java.lang.IllegalArgumentException
	at org.jetbrains.kotlinx.serialization.compiler.resolve.KSerializerDescriptorResolver.createLoadConstructorDescriptor(KSerializerDescriptorResolver.kt:293)
	at org.jetbrains.kotlinx.serialization.compiler.backend.js.SerializerJsTranslator$generateLoad$1.invoke(SerializerJsTranslator.kt:356)
	at org.jetbrains.kotlinx.serialization.compiler.backend.js.SerializerJsTranslator$generateLoad$1.invoke(SerializerJsTranslator.kt:233)
	at org.jetbrains.kotlinx.serialization.compiler.backend.js.JsCodegenUtilKt.buildFunction(JsCodegenUtil.kt:86)
	at org.jetbrains.kotlinx.serialization.compiler.backend.js.SerializerJsTranslator.generateFunction$kotlinx_serialization_compiler_plugin(SerializerJsTranslator.kt:41)
	at org.jetbrains.kotlinx.serialization.compiler.backend.js.SerializerJsTranslator.generateLoad(SerializerJsTranslator.kt:233)
	at org.jetbrains.kotlinx.serialization.compiler.backend.common.SerializerCodegen.generateLoadIfNeeded(SerializerCodegen.kt:116)
	at org.jetbrains.kotlinx.serialization.compiler.backend.common.SerializerCodegen.generate(SerializerCodegen.kt:38)
	at org.jetbrains.kotlinx.serialization.compiler.backend.js.SerializerJsTranslator$Companion.translate(SerializerJsTranslator.kt:379)
	at org.jetbrains.kotlinx.serialization.compiler.extensions.SerializationJsExtension.generateClassSyntheticParts(SerializationJsExtension.kt:30)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.generateClassSyntheticParts(ClassTranslator.kt:158)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.translate(ClassTranslator.kt:111)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.access$translate(ClassTranslator.kt:63)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator$Companion.translate(ClassTranslator.kt:611)
	at org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor.visitClassOrObject(AbstractDeclarationVisitor.kt:42)
	at org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor.visitClassOrObject(DeclarationBodyVisitor.kt:45)
	at org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor.visitClassOrObject(DeclarationBodyVisitor.kt:32)
	at org.jetbrains.kotlin.psi.KtVisitor.visitObjectDeclaration(KtVisitor.java:37)
	at org.jetbrains.kotlin.psi.KtObjectDeclaration.accept(KtObjectDeclaration.kt:63)
	at org.jetbrains.kotlin.js.translate.general.TranslatorVisitor.traverseContainer(TranslatorVisitor.java:43)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.translate(ClassTranslator.kt:92)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.access$translate(ClassTranslator.kt:63)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator$Companion.translate(ClassTranslator.kt:611)
	at org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor.visitClassOrObject(AbstractDeclarationVisitor.kt:42)
	at org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor.visitClassOrObject(DeclarationBodyVisitor.kt:45)
	at org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor.visitClassOrObject(DeclarationBodyVisitor.kt:32)
	at org.jetbrains.kotlin.psi.KtVisitor.visitClass(KtVisitor.java:33)
	at org.jetbrains.kotlin.psi.KtClass.accept(KtClass.kt:20)
	at org.jetbrains.kotlin.js.translate.general.TranslatorVisitor.traverseContainer(TranslatorVisitor.java:43)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.translate(ClassTranslator.kt:92)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.access$translate(ClassTranslator.kt:63)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator$Companion.translate(ClassTranslator.kt:611)
	at org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor.visitClassOrObject(AbstractDeclarationVisitor.kt:42)
	at org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor.visitClassOrObject(DeclarationBodyVisitor.kt:45)
	at org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor.visitClassOrObject(DeclarationBodyVisitor.kt:32)
	at org.jetbrains.kotlin.psi.KtVisitor.visitClass(KtVisitor.java:33)
	at org.jetbrains.kotlin.psi.KtClass.accept(KtClass.kt:20)
	at org.jetbrains.kotlin.js.translate.general.TranslatorVisitor.traverseContainer(TranslatorVisitor.java:43)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.translate(ClassTranslator.kt:92)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.access$translate(ClassTranslator.kt:63)
	at org.jetbrains.kotlin.js.translate.declaration.ClassTranslator$Companion.translate(ClassTranslator.kt:611)
	at org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor.visitClassOrObject(AbstractDeclarationVisitor.kt:42)
	at org.jetbrains.kotlin.js.translate.declaration.AbstractDeclarationVisitor.visitClassOrObject(AbstractDeclarationVisitor.kt:35)
	at org.jetbrains.kotlin.psi.KtVisitor.visitClass(KtVisitor.java:33)
	at org.jetbrains.kotlin.psi.KtClass.accept(KtClass.kt:20)
	at org.jetbrains.kotlin.js.translate.general.Translation.translateFile(Translation.java:377)
	... 38 more
```
