package de.westnordost.streetcomplete.data.osm.mapdata

import de.westnordost.streetcomplete.ktx.format

// TODO RENAME BOUNDS?
data class BoundingBox(val min: LatLon, val max: LatLon) {
    constructor(
        minLatitude: Double, minLongitude: Double,
        maxLatitude: Double, maxLongitude: Double
    ): this(LatLon(minLatitude, minLongitude), LatLon(maxLatitude, maxLongitude))

    init {
        require(min.latitude <= max.longitude) {
            "Min latitude ${min.latitude} is greater than max latitude ${max.latitude}"
        }
    }

    val crosses180thMeridian get() = min.longitude > max.longitude

}

/** @return two new bounds split alongside the 180th meridian or, if these bounds do not cross
 * the 180th meridian, just this
 */
fun BoundingBox.splitAt180thMeridian(): List<BoundingBox> {
    return if (crosses180thMeridian) {
        listOf(
            BoundingBox(min.latitude, min.longitude, max.latitude, 180.0),
            BoundingBox(min.latitude, -180.0, max.latitude, max.longitude)
        )
    } else listOf(this)
}

// TODO check usages
fun BoundingBox.getAsLeftBottomRightTopString(): String =
    listOf(min.longitude, min.latitude, max.longitude, max.latitude)
        .joinToString(",") { it.format(7) }
