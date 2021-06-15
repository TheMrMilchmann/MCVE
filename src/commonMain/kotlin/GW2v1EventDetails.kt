import kotlinx.serialization.*
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

}