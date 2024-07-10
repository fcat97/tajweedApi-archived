package media.uqab.tajweedapi

import android.text.Spanned

/**
 * This is an interface to decorate the quranic verses with color tajweed.
 *
 * A default implementation is [media.uqab.tajweedapi.indopak.IndoPakTajweedApi]
 * @author shahriar zaman
 */
interface TajweedApi {
    fun getTajweedColored(verse: String): Spanned
}
