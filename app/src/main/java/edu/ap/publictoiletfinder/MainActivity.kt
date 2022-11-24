package edu.ap.publictoiletfinder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import codebeautify.Attributes
import codebeautify.JsonParseModel
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import okhttp3.*
import okhttp3.internal.wait
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val executorService: ExecutorService = Executors.newFixedThreadPool(4)
        super.onCreate(savedInstanceState)

        val parseModel = getToiletList(executorService)

        setContentView(R.layout.activity_main)
    }

    private fun jsonParseDataFromString(json: String): Attributes {
        val klaxon = Klaxon();
        val result = klaxon.parse<Attributes>(json)!!
        return result
    }
    fun getToiletList(executorService: ExecutorService): List<Attributes> {
        var toiletList: MutableList<Attributes> = mutableListOf()
        var parseModel: JsonParseModel
        val klaxon = Klaxon();
        val parser: Parser = Parser.default()

        executorService.execute {
            val url =
                URL("https://geodata.antwerpen.be/arcgissql/rest/services/P_Portal/portal_publiek1/MapServer/8/query?where=1%3D1&outFields=ID,STRAAT,HUISNUMMER,DOELGROEP,LUIERTAFEL,LAT,LONG,POSTCODE&returnGeometry=false&outSR=4326&f=json")
            var client: OkHttpClient = OkHttpClient()
            val request: Request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        val json = response.body!!.string()

                        println(json)

                        val jsonObject = JsonParseModel.fromJson(json)

                        //parseModel = JsonParseModel.fromJson(json)!!

                        //parseModel.features.forEach { feature -> toiletList.add(jsonParseDataFromString(feature.attributes.toString())) }
                    }
                }
            })
        }
        return toiletList
    }
}
