package media.uqab.tajweedapi.common

data class TajweedColor(
    val qalqalahColor: String,
    val iqfaaColor: String,
    val iqlabColor: String,
    val idgamWithGunnahColor: String,
    val idgamWithOutGunnahColor: String,
    val wazeebGunnahColor: String,
)

val DefaultColor: TajweedColor
    get() = TajweedColor(
        qalqalahColor = "#00aa00",
        iqfaaColor = "#FF0000",
        iqlabColor = "#0000FF",
        idgamWithGunnahColor = "#FF00FF",
        idgamWithOutGunnahColor = "#d3d3d3",
        wazeebGunnahColor = "#F07624"
    )