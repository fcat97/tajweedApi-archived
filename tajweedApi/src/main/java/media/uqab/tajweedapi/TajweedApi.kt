package media.uqab.tajweedapi

import android.text.Spanned

/**
 * This is an interface to decorate the quranic verses with color tajweed.
 *
 * A default implementation is [media.uqab.tajweedapi.indopak.IndoPakTajweedApi]
 * @author shahriar zaman
 */
interface TajweedApi {
    @Deprecated(
        message = "This method is Platform dependent, will be dropped in future release",
        replaceWith = ReplaceWith(
            expression = "getTajweed(verse)",
        )
    )
    fun getTajweedColored(verse: String): Spanned
    fun getTajweed(verse: String): List<Tajweed>
}
