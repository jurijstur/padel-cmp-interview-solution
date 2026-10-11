package io.bifri.interview.cmp.padel.feature.tournament.domain.location

internal data class CityCoordinate(
    val latitude: Double,
    val longitude: Double,
)

internal fun cityCoordinate(location: String?, country: String?): CityCoordinate? {
    val locationKey = location?.foldCityKey()?.takeIf { it.isNotEmpty() } ?: return null
    val countryKey = country?.trim()?.uppercase()?.takeIf { it.isNotEmpty() } ?: return null
    return cities[locationKey to countryKey]
}

private fun String.foldCityKey(): String {
    val folded = trim().lowercase()
    return buildString {
        for (character in folded) {
            append(
                when (character) {
                    'á', 'à', 'â', 'ä', 'ã' -> 'a'
                    'é', 'è', 'ê', 'ë' -> 'e'
                    'í', 'ì', 'î', 'ï' -> 'i'
                    'ó', 'ò', 'ô', 'ö', 'õ' -> 'o'
                    'ú', 'ù', 'û', 'ü' -> 'u'
                    'ý', 'ÿ' -> 'y'
                    'ñ' -> 'n'
                    'ç' -> 'c'
                    'ș', 'ş' -> 's'
                    'ț', 'ţ' -> 't'
                    else -> character
                },
            )
        }
    }
}

private fun key(location: String, country: String) = location.foldCityKey() to country

private fun coordinate(latitude: Double, longitude: Double) = CityCoordinate(
    latitude = latitude,
    longitude = longitude,
)

// City centers copied once from OpenStreetMap. Keys follow the tournament location and country fields.
private val cities: Map<Pair<String, String>, CityCoordinate> = mapOf(
    key("Abidjan", "CI") to coordinate(latitude = 5.3204, longitude = -4.0161),
    key("Aguascalientes", "MX") to coordinate(latitude = 21.8805, longitude = -102.2967),
    key("Alcalá De Guadaira", "ES") to coordinate(latitude = 37.3398, longitude = -5.8408),
    key("Almaty", "KZ") to coordinate(latitude = 43.2364, longitude = 76.9457),
    key("Asunción", "PY") to coordinate(latitude = -25.2800, longitude = -57.6344),
    key("Ayia Napa", "CY") to coordinate(latitude = 34.9893, longitude = 33.9962),
    key("Bali", "ID") to coordinate(latitude = -8.2271, longitude = 115.1919),
    key("Bandol", "FR") to coordinate(latitude = 43.1358, longitude = 5.7524),
    key("Barcelona", "ES") to coordinate(latitude = 41.3826, longitude = 2.1771),
    key("Bari", "IT") to coordinate(latitude = 41.1258, longitude = 16.8620),
    // The tournament payload labels Belfast as IE.
    key("Belfast", "IE") to coordinate(latitude = 54.5964, longitude = -5.9302),
    key("Bordeaux", "FR") to coordinate(latitude = 44.8412, longitude = -0.5800),
    key("Brussels", "BE") to coordinate(latitude = 50.8467, longitude = 4.3525),
    key("Buenos Aires", "AR") to coordinate(latitude = -34.6096, longitude = -58.3888),
    key("Caltanisetta", "IT") to coordinate(latitude = 37.4903, longitude = 14.0633),
    key("Camboriu", "BR") to coordinate(latitude = -27.0075, longitude = -48.6139),
    key("Dublin", "IE") to coordinate(latitude = 53.3494, longitude = -6.2606),
    key("Dubai", "AE") to coordinate(latitude = 25.0743, longitude = 55.1886),
    key("Giulianova", "IT") to coordinate(latitude = 42.7536, longitude = 13.9668),
    key("Gran Canaria", "ES") to coordinate(latitude = 28.1289, longitude = -15.4349),
    key("Höganäs", "SE") to coordinate(latitude = 56.2009, longitude = 12.5516),
    // Jersey Island is stored as GB. The point is St Helier.
    key("Jersey Island", "GB") to coordinate(latitude = 49.1857, longitude = -2.1102),
    key("Koksijde", "BE") to coordinate(latitude = 51.1188, longitude = 2.6349),
    key("Kuala Lumpur", "MY") to coordinate(latitude = 3.1517, longitude = 101.6942),
    key("Leiria", "PT") to coordinate(latitude = 39.7438, longitude = -8.8071),
    key("London", "GB") to coordinate(latitude = 51.5074, longitude = -0.1278),
    key("Málaga", "ES") to coordinate(latitude = 36.7648, longitude = -4.4422),
    key("Mendoza", "AR") to coordinate(latitude = -32.8894, longitude = -68.8446),
    key("Miami", "US") to coordinate(latitude = 25.7742, longitude = -80.1936),
    key("Mumbai", "IN") to coordinate(latitude = 19.0550, longitude = 72.8692),
    key("Narbona", "FR") to coordinate(latitude = 43.1838, longitude = 3.0042),
    key("New Giza", "EG") to coordinate(latitude = 29.9768, longitude = 31.4033),
    key("Nola", "IT") to coordinate(latitude = 40.9211, longitude = 14.5327),
    key("Oeiras", "PT") to coordinate(latitude = 38.6926, longitude = -9.3123),
    key("Oporto", "PT") to coordinate(latitude = 41.1502, longitude = -8.6103),
    key("Palermo", "IT") to coordinate(latitude = 38.1112, longitude = 13.3524),
    key("Paredes", "PT") to coordinate(latitude = 41.2079, longitude = -8.3332),
    key("Parma", "IT") to coordinate(latitude = 44.6952, longitude = 10.0980),
    key("Pingyuan", "CN") to coordinate(latitude = 24.5698, longitude = 115.8867),
    key("Pretoria", "ZA") to coordinate(latitude = -25.7459, longitude = 28.1879),
    key("Rome", "IT") to coordinate(latitude = 41.8933, longitude = 12.4829),
    key("Shanghai", "CN") to coordinate(latitude = 31.2313, longitude = 121.4700),
    key("Teramo", "IT") to coordinate(latitude = 42.6581, longitude = 13.6980),
    key("Timisoara", "RO") to coordinate(latitude = 45.7538, longitude = 21.2257),
    key("Tirana", "AL") to coordinate(latitude = 41.3281, longitude = 19.8184),
    key("Valencia", "ES") to coordinate(latitude = 39.4697, longitude = -0.3763),
    key("Valladolid", "ES") to coordinate(latitude = 41.6200, longitude = -4.7260),
    key("Vila Real De Santo Antonio", "PT") to coordinate(latitude = 37.1946, longitude = -7.4156),
)
