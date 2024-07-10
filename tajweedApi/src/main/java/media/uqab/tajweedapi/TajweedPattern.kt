package media.uqab.tajweedapi

internal interface TajweedPattern {
    fun getNuunSakin(): String
    fun getQalqalahInMiddlePattern(): String
    fun getQalqalahInStopPattern(): String
    fun getIqfaaPattern(): String
    fun getIqlabPattern(): String
    fun getIdgaamWithGunnahPattern(): String
    fun getIdgaamWithOutGunnahPattern(): String
    fun getWazeebGunnah(): String
}