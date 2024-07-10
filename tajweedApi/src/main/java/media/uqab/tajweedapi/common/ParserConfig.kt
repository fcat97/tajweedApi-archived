package media.uqab.tajweedapi.common

data class ParserConfig(
    /**
     * Char to ignore if found inside any matching tajweed rule.
     */
    val ignoreChars: ArrayList<Char> = arrayListOf(),
)

val DefaultParserConfig: ParserConfig
    get() = ParserConfig()