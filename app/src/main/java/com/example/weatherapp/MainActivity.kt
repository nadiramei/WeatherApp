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
import android.widget.ToggleButton

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listcard.view.*
import org.json.JSONException
import java.text.ParseException

class MainActivity : AppCompatActivity() {

    val CITY: String = "tegal,id"
    val API: String = "e1df8846613500313bb8181549d2deaa" // Use API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val card1 = findViewById<CardView>(R.id.card1)
        card1.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            intent.putExtra("keyString", "1")
            startActivity(intent)
        }
        val card2 = findViewById<CardView>(R.id.card2)
        card2.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            intent.putExtra("keyString", "2")
            startActivity(intent)
        }
        val card3 = findViewById<CardView>(R.id.card3)
        card3.setOnClickListener {
            val intent = Intent(this, detail::class.java)
            intent.putExtra("keyString", "3")
            startActivity(intent)
        }

        getWeather().execute()
        getForecast().execute()

    }

    inner class getWeather() : AsyncTask<String, Void, String>() {
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
                response = URL("https://api.openweathermap.org/data/2.5/weather?units=metric&q=tegal&appid=e1df8846613500313bb8181549d2deaa").readText(
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
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
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
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity

                findViewById<TextView>(R.id.precipitation).text = windSpeed
                findViewById<TextView>(R.id.gust).text = gust
                findViewById<TextView>(R.id.cloud).text = cloud

                val iconUrl = "http://openweathermap.org/img/w/$weathercode.png"
                //Picasso.get().load(iconUrl).into(R.id.weatherIcon)


                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }


    inner class getForecast() : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/forecast?cnt=3&units=metric&q=$CITY&appid=$API").readText(
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
                val jsonObj = JSONObject(result)

                val tes1 = jsonObj.getJSONObject("city")
                val tes = tes1.getString("timezone")

                val list = jsonObj.getJSONArray("list")
                val list1 = list.getJSONObject(0)
                val list2 = list.getJSONObject(1)
                val list3 = list.getJSONObject(2)

                val main1 = list1.getJSONObject("main")
                val main2 = list2.getJSONObject("main")
                val main3 = list3.getJSONObject("main")

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

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.date1).text = dt1
                findViewById<TextView>(R.id.condition1).text = desc1
                findViewById<TextView>(R.id.listtemp1).text = temp1

                findViewById<TextView>(R.id.date2).text = dt2
                findViewById<TextView>(R.id.condition2).text = desc2
                findViewById<TextView>(R.id.listtemp2).text = temp2

                findViewById<TextView>(R.id.date3).text = dt3
                findViewById<TextView>(R.id.condition3).text = desc3
                findViewById<TextView>(R.id.listtemp3).text = temp3

                //findViewById<CardView>(R.id.card1) as CardView




            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        }
    }
}
