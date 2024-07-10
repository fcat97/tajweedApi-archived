package media.uqab.tajweedapi


data class Tajweed(
    val type: TajweedType,
    val word: String,
    val startIndex: Int,
    val endIndex: Int,
)