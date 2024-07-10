package media.uqab.tajweedapi.indopak

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import media.uqab.tajweedapi.TajweedApi
import media.uqab.tajweedapi.common.DefaultColor
import media.uqab.tajweedapi.common.DefaultParserConfig
import media.uqab.tajweedapi.common.DefaultTajweedConfig
import media.uqab.tajweedapi.common.TajweedConfig

class IndoPakTajweedApi : TajweedApi {
    private val TAG = "TajweedApi"

    private var pattern = IndoPakTajweedPattern(DefaultParserConfig)

    private var patternQalqalah = pattern.getQalqalahInMiddlePattern().toRegex()
    private var patternQalqalahStop = pattern.getQalqalahInStopPattern().toRegex()
    private var patternIqfaa = pattern.getIqfaaPattern().toRegex()
    private var patternIqlab = pattern.getIqlabPattern().toRegex()
    private var patternIdgaanWG = pattern.getIdgaamWithGunnahPattern().toRegex()
    private var patternIdgaanWoG = pattern.getIdgaamWithOutGunnahPattern().toRegex()
    private var patternWazeebGunnah = pattern.getWazeebGunnah().toRegex()

    private var qalqalahColor = DefaultColor.qalqalahColor.let(Color::parseColor)
    private var iqfaaColor = DefaultColor.iqfaaColor.let(Color::parseColor)
    private var iqlabColor = DefaultColor.iqlabColor.let(Color::parseColor)
    private var idgamWithGunnahColor = DefaultColor.idgamWithGunnahColor.let(Color::parseColor)
    private var idgamWithOutGunnahColor =
        DefaultColor.idgamWithOutGunnahColor.let(Color::parseColor)
    private var wazeebGunnahColor = DefaultColor.wazeebGunnahColor.let(Color::parseColor)

    /**
     * configuration to be applied on the verse for coloring.
     */
    var config: TajweedConfig = DefaultTajweedConfig
        set(value) {
            with(value.colorConfig) {
                this@IndoPakTajweedApi.qalqalahColor = Color.parseColor(qalqalahColor)
                this@IndoPakTajweedApi.iqfaaColor = Color.parseColor(iqfaaColor)
                this@IndoPakTajweedApi.iqlabColor = Color.parseColor(iqlabColor)
                this@IndoPakTajweedApi.idgamWithGunnahColor = Color.parseColor(idgamWithGunnahColor)
                this@IndoPakTajweedApi.idgamWithOutGunnahColor =
                    Color.parseColor(idgamWithOutGunnahColor)
                this@IndoPakTajweedApi.wazeebGunnahColor = Color.parseColor(wazeebGunnahColor)
            }

            with(IndoPakTajweedPattern(value.parserConfig)) {
                this@IndoPakTajweedApi.patternQalqalah = getQalqalahInMiddlePattern().toRegex()
                this@IndoPakTajweedApi.patternQalqalahStop = getQalqalahInStopPattern().toRegex()
                this@IndoPakTajweedApi.patternIqfaa = getIqfaaPattern().toRegex()
                this@IndoPakTajweedApi.patternIqlab = getIqlabPattern().toRegex()
                this@IndoPakTajweedApi.patternIdgaanWG = getIdgaamWithGunnahPattern().toRegex()
                this@IndoPakTajweedApi.patternIdgaanWoG = getIdgaamWithOutGunnahPattern().toRegex()
                this@IndoPakTajweedApi.patternWazeebGunnah = getWazeebGunnah().toRegex()
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