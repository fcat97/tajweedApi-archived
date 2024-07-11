package media.uqab.tajweedapi.indopak

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import media.uqab.tajweedapi.Tajweed
import media.uqab.tajweedapi.TajweedApi

class IndoPakTajweedApi : TajweedApi {
    private val TAG = "TajweedApi"

    private var pattern = IndoPakTajweedPattern()

    private var patternQalqalah = pattern.getQalqalahInMiddlePattern().toRegex()
    private var patternQalqalahStop = pattern.getQalqalahInStopPattern().toRegex()
    private var patternIqfaa = pattern.getIqfaaPattern().toRegex()
    private var patternIqlab = pattern.getIqlabPattern().toRegex()
    private var patternIdgaanWG = pattern.getIdgaamWithGunnahPattern().toRegex()
    private var patternIdgaanWoG = pattern.getIdgaamWithOutGunnahPattern().toRegex()
    private var patternWazeebGunnah = pattern.getWazeebGunnah().toRegex()

    private var qalqalahColor = DefaultIndopakColor.qalqalahColor.let(Color::parseColor)
    private var iqfaaColor = DefaultIndopakColor.iqfaaColor.let(Color::parseColor)
    private var iqlabColor = DefaultIndopakColor.iqlabColor.let(Color::parseColor)
    private var idgamWithGunnahColor = DefaultIndopakColor.idgamWithGunnahColor.let(Color::parseColor)
    private var idgamWithOutGunnahColor = DefaultIndopakColor.idgamWithOutGunnahColor.let(Color::parseColor)
    private var wazeebGunnahColor = DefaultIndopakColor.wazeebGunnahColor.let(Color::parseColor)

    /**
     * configuration to be applied on the verse for coloring.
     */
    @Deprecated("Platform specific, will be removed. use TajweedPainter instead.")
    var color: IndopakTajweedColor = DefaultIndopakColor
        set(value) {
            with(value) {
                this@IndoPakTajweedApi.qalqalahColor = Color.parseColor(qalqalahColor)
                this@IndoPakTajweedApi.iqfaaColor = Color.parseColor(iqfaaColor)
                this@IndoPakTajweedApi.iqlabColor = Color.parseColor(iqlabColor)
                this@IndoPakTajweedApi.idgamWithGunnahColor = Color.parseColor(idgamWithGunnahColor)
                this@IndoPakTajweedApi.idgamWithOutGunnahColor = Color.parseColor(idgamWithOutGunnahColor)
                this@IndoPakTajweedApi.wazeebGunnahColor = Color.parseColor(wazeebGunnahColor)
            }

            field = value
        }

    /**
     * This method only works for indoQuran format
     * IndoQuran is written slightly different than Saudi Quran
     * This format has less character than Saudi one
     *
     * @param verse Verse to decorate with color in IndoQuran Format
     * @return a spanned Object with Tazweed decoration.
     */
    @Deprecated(
        message = "This method is Platform dependent, will be dropped in future release",
        replaceWith = ReplaceWith(
            expression = "getTajweed(verse)",
        )
    )
    override fun getTajweedColored(verse: String): Spanned {
        val spannable = SpannableString(verse)

        //if (!verse.contains("صُمٌّۢ بُکۡم")) return spannable
        // Log.d(TAG, "getTajweedColored: ${getIqlabPattern()}")

        // TODO: this needs too much computation.. So make it fast
        applySpan(patternWazeebGunnah, verse, wazeebGunnahColor, spannable, logTag = "wazeebGunnah")
        applySpan(patternIqfaa, verse, iqfaaColor, spannable, endOffset = -1, logTag = "iqfaa")
        applySpan(patternIqlab, verse, iqlabColor, spannable, logTag = "iqlab")
        applySpan(patternIdgaanWG, verse, idgamWithGunnahColor, spannable, logTag = "idgam_wg")
        applySpan(
            patternIdgaanWoG,
            verse,
            idgamWithOutGunnahColor,
            spannable,
            endOffset = -3,
            logTag = "idgam_wog"
        )
        applySpan(patternQalqalah, verse, qalqalahColor, spannable, logTag = "qalqalah")
        applySpan(
            patternQalqalahStop,
            verse,
            qalqalahColor,
            spannable,
            endOffset = -1,
            logTag = "qalqalah_stop"
        )

        return spannable
    }

    override fun getTajweed(verse: String): List<Tajweed> {
        val tajweed = mutableListOf<Tajweed>()

        listOf(
            patternWazeebGunnah to WazeebGunnah,
            patternIqfaa to Iqfaa,
            patternIqlab to Iqlab,
            patternIdgaanWG to IdgamWithGunnah,
            patternIdgaanWoG to IdgamWithoutGunnah,
            patternQalqalah to Qalqalah,
            patternQalqalahStop to QalqalahAtStop
        ).forEach { (regex, type) ->
            regex.findAll(verse).map {
                Tajweed(type, it.value, it.range.first, it.range.last)
            }.let(tajweed::addAll)
        }

        return tajweed
    }

    private fun applySpan(
        regex: Regex,
        verse: String,
        color: Int,
        spannable: Spannable,
        endOffset: Int = 1,
        /*debug*/ logTag: String = "applySpan"
    ) {
        val range = regex.findAll(verse)
        for (r in range) {
            val spans =
                spannable.getSpans(r.range.first, r.range.last, ForegroundColorSpan::class.java)
            spans.forEach { spannable.removeSpan(it) }
//            Log.d(TAG, "$logTag: ${verse.subSequence(r.range.first, r.range.last)}")
            spannable.setSpan(
                ForegroundColorSpan(color),
                r.range.first, r.range.last + endOffset,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    // for debugging only
    private fun toUnicode(c: Char): String {
        return String.format("\\%04x", c.code)
    }

    companion object {
        private var singleton: IndoPakTajweedApi? = null

        /**
         * Get a singleton instance for [IndoPakTajweedApi].
         */
        @JvmStatic
        fun getSingleton(): IndoPakTajweedApi = synchronized(Unit) {
            if (singleton == null) {
                singleton = IndoPakTajweedApi()
            }

            singleton!!
        }
    }
}