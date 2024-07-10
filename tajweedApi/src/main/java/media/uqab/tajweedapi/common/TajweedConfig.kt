package media.uqab.tajweedapi.common

data class TajweedConfig(
    val colorConfig: TajweedColor,
    val parserConfig: ParserConfig
)

val DefaultTajweedConfig: TajweedConfig
    get() = TajweedConfig(
        colorConfig = DefaultColor,
        parserConfig = DefaultParserConfig
    )