package media.uqab.tajweedapi

import androidx.test.ext.junit.runners.AndroidJUnit4
import media.uqab.tajweedapi.indopak.IdgamWithGunnah
import media.uqab.tajweedapi.indopak.IndoPakTajweedApi
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun testIdgamWithGunnah() {
        val verse = "خَتَمَ ٱللَّهُ عَلَىٰ قُلُوبِهِمْ وَعَلَىٰ سَمْعِهِمْۖ وَعَلَىٰٓ أَبْصَٰرِهِمْ غِشَٰوَةٌۖ وَلَهُمْ عَذَابٌ عَظِيمٌ" // sura bakarah 2:7
        val api = IndoPakTajweedApi.getSingleton()
        val result = api.getTajweed(verse)
        result.forEach {
            println(it)
        }

        assertEquals(IdgamWithGunnah, result[0].type)
    }
}