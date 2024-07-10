package media.uqab.tajweedapi.indopak

import media.uqab.tajweedapi.TajweedType

sealed class IndoPakTajweedType(
    /**
     * characters to be included or excluded from paining
     */
    val endOffset: Int
) : TajweedType

data object WazeebGunnah : IndoPakTajweedType(1)
data object Iqfaa : IndoPakTajweedType(-1)
data object Iqlab : IndoPakTajweedType(1)
data object IdgamWithGunnah : IndoPakTajweedType(1)
data object IdgamWithoutGunnah : IndoPakTajweedType(-3)
data object Qalqalah : IndoPakTajweedType(1)
data object QalqalahAtStop : IndoPakTajweedType(-1)