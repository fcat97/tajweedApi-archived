package media.uqab.tajweedapi.indopak

import media.uqab.tajweedapi.TajweedPattern

open class IndoPakTajweedPattern : TajweedPattern {
    private val iqfaa = listOf(
        'ت',
        'ث',
        'ج',
        'د',
        'ذ',
        'ز',
        'س',
        'ش',
        'ص',
        'ض',
        'ط',
        'ظ',
        'ف',
        'ق',
        'ك',
        'ک'
    ) // U+06a9(alt kaaf, keheh)
    private val qalqalah = listOf('ق', 'ط', 'ب', 'ج', 'د')
    private val alif = 'ا' // U+0627
    private val meem = 'م' // U+0645
    private val nuun = 'ن' // U+0646
    private val baa = 'ب' // U+0628
    private val harqat = listOf('َ', 'ِ', 'ُ') // U+064e, U+0650, U+064f
    private val tanween = listOf('ً', 'ٍ', 'ٌ') // U+064b, U+064d, U+064c
    private val tashdeed = 'ّ' // U+0651
    private val maddah = 'ٓ' // U+0653
    private val small_high_maddah = 'ۤ' // U+06e4
    private val superscriptAlif = 'ٰ' //  U+0670 vertical fatha/খাড়া যবর
    private val subscriptAlif = 'ٖ' //  U+0656 vertical kasra/খাড়া জের
    private val invertedDamma = 'ٗ' // U+0657
    private val sakin = listOf('ۡ', 'ْ') // U+06e1, U+0652
    private val meem_isolated = listOf('ۢ', 'ۭ') // U+06e2, U+06ed
    private val harf_idgam_withGunnah = listOf(
        'ی', 'ى', 'و', 'م', 'ن',
    ) // U+06cc(farsi ya) U+064a, U+0648, U+0645, U+0646
    private val harf_idgam_withoutGunnah = listOf('ر', 'ل') // U+0631, U+0644
    private val stops = listOf(
        "مـ",
        "قلى",
        '\u06da', // small jeem
        '\u06dc', // small high seen
        '\u06d9', // small high lam-alef
        '\u066a', // arabic percent sign
        '\u0615' // small high tah
    )
    private val ignoreCharBetweenIdgamGunnah = listOf('\u06d6')


    /**
     * match a groupd with noon-sakin or tanween
     *
     * the tanween can have:
     * 1. any optional char at the beginning
     * 2. optional tashdeed followed
     * 3. and an optional alif at the end
     *
     * example: sura 2, verse 23
     */
    override fun getNuunSakin() = buildString {
        append('(')
        append(nuun)
        append('[')
        for (c in sakin) append(c)
        append(']')
        // tanween also included
        append('|')
        append("\\p{L}?") // any unicode letter
        append(tashdeed)
        append('?')
        append('[')
        for (c in tanween) append(c)
        append(']')
        append(alif)
        append('?')
        append(')')
        append(" ?")
    }

    private fun getHarqatPattern() = buildString {
        this.append('[')
        for (c in harqat) this.append(c)
        for (c in tanween) this.append(c)
        this.append(superscriptAlif)
        this.append(subscriptAlif)
        this.append(invertedDamma)
        this.append("]?")
    }

    override fun getQalqalahInMiddlePattern() = buildString {
        append("([")
        for (c in qalqalah) append(c)
        append(']')
        append('[')
        for (c in sakin) append(c)
        append("])")
    }

    /**
     * Any of Qalqah letters followed by an stop sign.
     * example sura 2, verse 44
     */
    override fun getQalqalahInStopPattern() = buildString {
        append("[")
        for (c in qalqalah) append(c)
        append(']')

        append(tashdeed)
        append('?')

        append('[')
        for (c in harqat) append(c)
        for (c in tanween) append(c)
        append("]")

        append('\u2009') // thin space
        append('?')

        // [] this can't be used since some stop sign contains multiple characters
        append('(')
        append(stops.joinToString("|"))
        append(')')
    }

    override fun getIqfaaPattern() = buildString {
        this.append(getNuunSakin())
        this.append('[')
        for (c in iqfaa) this.append(c)
        this.append(']')
        this.append(getHarqatPattern())
    }

    override fun getIqlabPattern() = buildString {
        this.append(getNuunSakin())
        this.append('[')
        for (c in meem_isolated) this.append(c)
        this.append(']')
        this.append("? ?")
        this.append(baa)
        this.append(getHarqatPattern())
    }

    override fun getIdgaamWithGunnahPattern() = buildString {
        this.append(getNuunSakin())

        // when idgam appears between two part of a sentence e.g. sura 2:7
        append('(')
        append('['); for (c in ignoreCharBetweenIdgamGunnah) append(c); append(']')
        append(") ?")

        this.append('[')
        for (c in harf_idgam_withGunnah) this.append(c)
        this.append(']')

        this.append(tashdeed)
        this.append('?')

        // here we don't use the getHarqatPattern()
        // since `U+06cc` sometime acts as extra harf which has no gunnah
        this.append('[')
        for (c in harqat) this.append(c)
        for (c in tanween) this.append(c)
        this.append(superscriptAlif)
        this.append(subscriptAlif)
        this.append(invertedDamma)
        this.append(']') // <--- here we don't add '?' i.e. it's must, not optional
    }

    override fun getIdgaamWithOutGunnahPattern() = buildString {
        this.append(getNuunSakin())
        this.append('[')
        for (c in harf_idgam_withoutGunnah) this.append(c)
        this.append(']')
        this.append(tashdeed)
        this.append('?')
        this.append(getHarqatPattern())
    }

    override fun getWazeebGunnah() = buildString {
        this.append('[')
        this.append(nuun)
        this.append(meem)
        this.append(']')
        this.append(tashdeed)
        this.append(getHarqatPattern())
        this.append(maddah)
        this.append('?')
//        this.append(getHarqatPattern())
    }
}