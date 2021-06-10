package com.example.weatherapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listcard.view.*
import org.json.JSONException
import java.text.ParseException

class detail : AppCompatActivity() {

    val CITY: String = "tegal,id"
    val API: String = "e1df8846613500313bb8181549d2deaa" // Use API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val card1 = findViewById<CardView>(R.id.card1)
        card1.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            startActivity(intent)
        }

        getDetail().execute()

    }

    inner class getDetail() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/forecast?cnt=3&units=metric&q=tegal&appid=e1df8846613500313bb8181549d2deaa").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                /*
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val tempMin = main.getString("temp_min")+"°C"
                val tempMax = main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val gust = wind.getString("gust")
                val cl = jsonObj.getJSONObject("clouds")
                val cloud = cl.getString("all")

                val weathercode = weather.getString("icon")


                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.location).text = address
                findViewById<TextView>(R.id.last_updated).text =  updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp

                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.humidity).text = humidity

                findViewById<TextView>(R.id.precipitation).text = windSpeed
                findViewById<TextView>(R.id.gust).text = gust

                findViewById<TextView>(R.id.min).text = tempMin
                findViewById<TextView>(R.id.max).text = tempMax

                val iconUrl = "http://openweathermap.org/img/w/$weathercode.png"
                //Picasso.get().load(iconUrl).into(R.id.weatherIcon)
                */

                val jsonObj = JSONObject(result)

                val tes1 = jsonObj.getJSONObject("city")
                val city = tes1.getString("name")

                val list = jsonObj.getJSONArray("list")
                val list1 = list.getJSONObject(0)
                val list2 = list.getJSONObject(1)
                val list3 = list.getJSONObject(2)

                val main1 = list1.getJSONObject("main")
                val main2 = list2.getJSONObject("main")
                val main3 = list3.getJSONObject("main")

                val wind1 = list1.getJSONObject("wind")
                val wind2 = list2.getJSONObject("wind")
                val wind3 = list3.getJSONObject("wind")

                val temp1 = main1.getString("temp")+"°C"
                val temp2 = main2.getString("temp")+"°C"
                val temp3 = main3.getString("temp")+"°C"

                val dt1 = list1.getString("dt_txt")
                val dt2 = list2.getString("dt_txt")
                val dt3 = list3.getString("dt_txt")

                val weather1 = list1.getJSONArray("weather").getJSONObject(0)
                val weather2 = list2.getJSONArray("weather").getJSONObject(0)
                val weather3 = list3.getJSONArray("weather").getJSONObject(0)

                val desc1 = weather1.getString("description")
                val desc2 = weather2.getString("description")
                val desc3 = weather3.getString("description")

                val min1 = main1.getString("temp_min")+"°C"
                val min2 = main2.getString("temp_min")+"°C"
                val min3 = main3.getString("temp_min")+"°C"

                val max1 = main1.getString("temp_max")+"°C"
                val max2 = main2.getString("temp_max")+"°C"
                val max3 = main3.getString("temp_max")+"°C"

                val windSpeed1 = wind1.getString("speed")
                val windSpeed2 = wind2.getString("speed")
                val windSpeed3 = wind3.getString("speed")

                val gust1 = wind1.getString("gust")
                val gust2 = wind2.getString("gust")
                val gust3 = wind3.getString("gust")

                val hum1 = main1.getString("humidity")
                val hum2 = main2.getString("humidity")
                val hum3 = main3.getString("humidity")

                val string: String? = intent.getStringExtra("keyString")


                findViewById<TextView>(R.id.location).text = city.capitalize()

                if (string == "1"){

                    findViewById<TextView>(R.id.status).text = desc1.capitalize()
                    findViewById<TextView>(R.id.temp).text = temp1

                    findViewById<TextView>(R.id.wind).text = windSpeed1
                    findViewById<TextView>(R.id.humidity).text = hum1

                    findViewById<TextView>(R.id.precipitation).text = hum1
                    findViewById<TextView>(R.id.gust).text = gust1

                    findViewById<TextView>(R.id.min).text = min1
                    findViewById<TextView>(R.id.max).text = max1
                }
                if (string == "2"){
                    findViewById<TextView>(R.id.status).text = desc2.capitalize()
                    findViewById<TextView>(R.id.temp).text = temp2

                    findViewById<TextView>(R.id.wind).text = windSpeed2
                    findViewById<TextView>(R.id.humidity).text = hum2

                    findViewById<TextView>(R.id.precipitation).text = hum2
                    findViewById<TextView>(R.id.gust).text = gust2

                    findViewById<TextView>(R.id.min).text = min2
                    findViewById<TextView>(R.id.max).text = max2
                }
                if (string == "3"){
                    findViewById<TextView>(R.id.status).text = desc3.capitalize()
                    findViewById<TextView>(R.id.temp).text = temp3

                    findViewById<TextView>(R.id.wind).text = windSpeed3
                    findViewById<TextView>(R.id.humidity).text = hum3

                    findViewById<TextView>(R.id.precipitation).text = hum3
                    findViewById<TextView>(R.id.gust).text = gust3

                    findViewById<TextView>(R.id.min).text = min3
                    findViewById<TextView>(R.id.max).text = max3
                }




                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }

}
