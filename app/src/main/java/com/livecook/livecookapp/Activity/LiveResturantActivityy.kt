package com.livecook.livecookapp.Activity

import android.Manifest
import android.content.Intent
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.livecook.livecookapp.Api.MyApplication
import com.livecook.livecookapp.MainActivity
import com.livecook.livecookapp.Model.Constants
import com.livecook.livecookapp.Model.Constants.rtmp_path_cooker
import com.livecook.livecookapp.R
import com.squareup.picasso.Picasso
import com.takusemba.rtmppublisher.Publisher
import com.takusemba.rtmppublisher.PublisherListener
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject

class LiveResturantActivityy :AppCompatActivity(), PublisherListener {

    private lateinit var publisher: Publisher
    private lateinit var glView: GLSurfaceView
    private lateinit var container: RelativeLayout
    private lateinit var publishButton: Button
    private lateinit var cameraButton: ImageView
    private lateinit var backbuuton: ImageView

    private lateinit var label: TextView
    private  lateinit var toolbar: Toolbar

    private val rtmp_path_resturant = "rtmp://167.86.71.40:1995/livecook/tayeh-10"
    private val handler = Handler()
    private var thread: Thread? = null
    private var isCounting = false
    private var  ressturant_id_profile_publish=0
    private lateinit var cook_name_tv: TextView

    private lateinit var cookimage_image: CircleImageView


    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_resturant_activityy)
        glView = this.findViewById(R.id.surface_view)
        container = findViewById(R.id.container)
        publishButton = this.findViewById(R.id.toggle_publish)
        cameraButton = findViewById(R.id.toggle_camera)
        label = findViewById(R.id.live_label)
        cook_name_tv = findViewById(R.id.cook_name)
        cookimage_image = findViewById(R.id.cookimageprofile)
        backbuuton=findViewById(R.id.backbuuton)
        database = FirebaseDatabase.getInstance().reference.child("Live")

        backbuuton.setOnClickListener {
            // finish();
            startActivity(Intent(this@LiveResturantActivityy, MainActivity::class.java))

        }
        val intent = intent

        val rtmp_path_resturant = intent.getStringExtra(Constants.rtmp_path_resturant)
        val cook_name = intent.getStringExtra(Constants.cook_name_profile)
        val cookimage = intent.getStringExtra(Constants.cookimage_profile)
        val first_child=intent.getStringExtra(Constants.first_child)
        val second_child=intent.getStringExtra(Constants.second_child)
        ressturant_id_profile_publish=intent.getIntExtra(Constants.ressturant_id_profile_publish,-1)

        getResturantprofile()


        /* Toast.makeText(this, first_child, Toast.LENGTH_SHORT)
                 .apply { setGravity(Gravity.CENTER, 0, 0) }
                 .run { show() }

         Toast.makeText(this, second_child, Toast.LENGTH_SHORT)
                 .apply { setGravity(Gravity.CENTER, 0, 0) }
                 .run { show() }*/

        cook_name_tv.text = cook_name
        Picasso.with(this).load(cookimage)
                // .resize(100,100)
                .error(R.drawable.ellipse)

                .into(cookimage_image)


        //Toast.makeText(this,""+cook_name, Toast.LENGTH_SHORT)



        if (rtmp_path_resturant.isBlank()) {
            Toast.makeText(this, R.string.error_empty_url, Toast.LENGTH_SHORT)
                    .apply { setGravity(Gravity.CENTER, 0, 0) }
                    .run { show() }
        } else {
            publisher = Publisher.Builder(this)
                    .setGlView(glView)
                    .setUrl(rtmp_path_resturant)
                    .setSize(Publisher.Builder.DEFAULT_WIDTH, Publisher.Builder.DEFAULT_HEIGHT)
                    .setAudioBitrate(Publisher.Builder.DEFAULT_AUDIO_BITRATE)
                    .setVideoBitrate(Publisher.Builder.DEFAULT_VIDEO_BITRATE)
                    .setCameraMode(Publisher.Builder.DEFAULT_MODE)
                    .setListener(this)
                    .build()

            /*
              Toast.makeText(this, R.string.disconnected_publishing, Toast.LENGTH_SHORT)
                                        .apply { setGravity(Gravity.CENTER, 0, 0) }
                                        .run { show() }
             */

            publishButton.setOnClickListener {
                if (publisher.isPublishing) {
                    publisher.stopPublishing()

                    database.child(first_child).child(second_child).addValueEventListener(object : ValueEventListener

                    {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            p0.ref.child("status").setValue(false)
                        }

                    })


                } else {
                    publisher.startPublishing()

                  /*  database.child(first_child).child(second_child).addValueEventListener(object : ValueEventListener

                    {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {

                            p0.ref.child("status").setValue(true)
                        }

                    })*/


                }
            }

            cameraButton.setOnClickListener {
                publisher.switchCamera()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (rtmp_path_resturant.isNotBlank()) {
            updateControls()
        }
    }

    override fun onStarted() {
        /* Toast.makeText(this, R.string.started_publishing, Toast.LENGTH_SHORT)
                 .apply { setGravity(Gravity.CENTER, 0, 0) }
                 .run { show() }*/
        updateControls()
        startCounting()
    }

    override fun onStopped() {
        /*Toast.makeText(this, R.string.stopped_publishing, Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.CENTER, 0, 0) }
                .run { show() }*/
        updateControls()
        stopCounting()
    }

    override fun onDisconnected() {
        Toast.makeText(this, R.string.disconnected_publishing, Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.CENTER, 0, 0) }
                .run { show() }
        updateControls()
        stopCounting()
    }

    override fun onFailedToConnect() {
        Toast.makeText(this, R.string.failed_publishing, Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.CENTER, 0, 0) }
                .run { show() }
        updateControls()
        stopCounting()
    }

    private fun updateControls() {
        publishButton.text = getString(if (publisher.isPublishing) R.string.stop_publishing else R.string.start_publishing)
    }

    private fun startCounting() {
        isCounting = true
        label.text = getString(R.string.publishing_label, 0L.format(), 0L.format())
        label.visibility = View.VISIBLE
        val startedAt = System.currentTimeMillis()
        var updatedAt = System.currentTimeMillis()
        thread = Thread {
            while (isCounting) {
                if (System.currentTimeMillis() - updatedAt > 1000) {
                    updatedAt = System.currentTimeMillis()
                    handler.post {
                        val diff = System.currentTimeMillis() - startedAt
                        val second = diff / 1000 % 60
                        val min = diff / 1000 / 60
                        label.text = getString(R.string.publishing_label, min.format(), second.format())
                    }
                }
            }
        }
        thread?.start()
    }

    private fun stopCounting() {
        isCounting = false
        label.text = ""
        label.visibility = View.GONE
        thread?.interrupt()
    }

    private fun Long.format(): String {
        return String.format("%02d", this)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    fun getResturantprofile() {
         ressturant_id_profile_publish=intent.getIntExtra(Constants.ressturant_id_profile_publish,-1)


        //showDialog();

        val stringRequest = StringRequest(Request.Method.GET, "https://livecook.co/api/v1/restaurant/$ressturant_id_profile_publish/profile", Response.Listener { response ->
            Log.e("HZM", response)

            try {
                val task_respnse = JSONObject(response)
                val taskarray = task_respnse.getJSONObject("data")
                val name = taskarray.getString("name")
                val avatarURL = taskarray.getString("avatar_url")
                cook_name_tv.text = name


                    Picasso.with(this@LiveResturantActivityy).load(avatarURL)
                            // .resize(100,100)
                            .error(R.drawable.ellipse)

                            .into(cookimage_image)





            } catch (e1: JSONException) {
                e1.printStackTrace()

            }


            //  hideDialog();
        }, Response.ErrorListener { })

        MyApplication.getInstance().addToRequestQueue(stringRequest)

    }

    fun getCookerprofile() {

        //showDialog();

        val cook_id_profile_publish=intent.getIntExtra(Constants.cook_id_profile_publish,-1)
        val stringRequest = StringRequest(Request.Method.GET, "https://livecook.co/api/v1/cooker/$cook_id_profile_publish/profile", Response.Listener { response ->
            Log.e("HZM", response)

            try {
                val task_respnse = JSONObject(response)
                val taskarray = task_respnse.getJSONObject("data")
                val name = taskarray.getString("name")
                val avatarURL = taskarray.getString("avatar_url")
                cook_name_tv.text = name


                if (avatarURL.matches("".toRegex()) || !avatarURL.startsWith("http")) {////https://image.flaticon.com/icons/svg/1055/1055672.svg
                    Picasso.with(this@LiveResturantActivityy).load(avatarURL).error(R.drawable.ellipse)
                            // .resize(100,100)
                            .into(cookimage_image)
                } else {
                    Picasso.with(this@LiveResturantActivityy).load(avatarURL)
                            // .resize(100,100)
                            .error(R.drawable.ellipse)

                            .into(cookimage_image)


                }


            } catch (e1: JSONException) {
                e1.printStackTrace()

            }


            //  hideDialog();
        }, Response.ErrorListener { })

        MyApplication.getInstance().addToRequestQueue(stringRequest)

    }


}