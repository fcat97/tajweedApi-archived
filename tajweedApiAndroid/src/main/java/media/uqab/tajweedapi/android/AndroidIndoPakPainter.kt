package media.uqab.tajweedapi.android

import android.graphics.Color
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import media.uqab.tajweedapi.Tajweed
import media.uqab.tajweedapi.TajweedColor
import media.uqab.tajweedapi.TajweedPainter
import media.uqab.tajweedapi.indopak.IdgamWithGunnah
import media.uqab.tajweedapi.indopak.IdgamWithoutGunnah
import media.uqab.tajweedapi.indopak.IndoPakTajweedApi
import media.uqab.tajweedapi.indopak.IndoPakTajweedType
import media.uqab.tajweedapi.indopak.IndopakTajweedColor
import media.uqab.tajweedapi.indopak.Iqfaa
import media.uqab.tajweedapi.indopak.Iqlab
import media.uqab.tajweedapi.indopak.Qalqalah
import media.uqab.tajweedapi.indopak.QalqalahAtStop
import media.uqab.tajweedapi.indopak.WazeebGunnah

open class AndroidIndoPakPainter : TajweedPainter {
    private fun getColor(type: IndoPakTajweedType, colors: IndopakTajweedColor): Int {
        return when (type) {
            WazeebGunnah -> colors.wazeebGunnahColor
            Iqfaa -> colors.iqfaaColor
            Iqlab -> colors.iqlabColor
            IdgamWithGunnah -> colors.idgamWithGunnahColor
            IdgamWithoutGunnah -> colors.idgamWithOutGunnahColor
            Qalqalah -> colors.qalqalahColor
            QalqalahAtStop -> colors.qalqalahColor
            else -> "#000000"
        }.let(Color::parseColor)
    }

    /**
     * @param verse must be an instance of [Spannable].
     * @param tajweed List of [Tajweed] parsed with [IndoPakTajweedApi]
     * @param colors [IndopakTajweedColor] specification
     */
    override fun <T> paint(verse: T, tajweed: List<Tajweed>, colors: TajweedColor): T {
        if (verse !is Spannable) return verse

        tajweed.forEach {
            val spans = verse.getSpans(it.startIndex, it.endIndex, ForegroundColorSpan::class.java)
            spans.forEach { span ->
                verse.removeSpan(span)
            }
            // Log.d(TAG, "$logTag: ${verse.subSequence(r.range.first, r.range.last)}")

            verse.setSpan(
                ForegroundColorSpan(
                    getColor(
                        it.type as IndoPakTajweedType,
                        colors as IndopakTajweedColor
                    )
                ),
                it.startIndex,
                it.endIndex + (it.type as IndoPakTajweedType).endOffset,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return verse
    }
}