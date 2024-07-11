package media.uqab.tajweedapi

/**
 * Platform independent painter interface
 * to apply color on text.
 */
interface TajweedPainter {
    /**
     * paint [TajweedColor] onto matching [Tajweed].
     *
     * @param verse the verse onto which paint should be applied. For example, on android it can be Spannable String
     * @param tajweed list of matching [Tajweed].
     * @param colors Colors to be applied for each [TajweedType]
     */
    fun <T> paint(verse: T, tajweed: List<Tajweed>, colors: TajweedColor): T
}