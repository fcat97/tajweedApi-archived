package media.uqab.tajweedapi.indopak

import media.uqab.tajweedapi.TajweedColor

data class IndopakTajweedColor(
    val qalqalahColor: String,
    val iqfaaColor: String,
    val iqlabColor: String,
    val idgamWithGunnahColor: String,
    val idgamWithOutGunnahColor: String,
    val wazeebGunnahColor: String,
): TajweedColor

val DefaultIndopakColor: IndopakTajweedColor
    get() = IndopakTajweedColor(
        qalqalahColor = "#00aa00",
        iqfaaColor = "#FF0000",
        iqlabColor = "#0000FF",
        idgamWithGunnahColor = "#FF00FF",
        idgamWithOutGunnahColor = "#d3d3d3",
        wazeebGunnahColor = "#F07624"
    )