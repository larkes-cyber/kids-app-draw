package com.example.draw_app

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {

    private var mImageButtonCurrentPaint:ImageButton? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        val draw_view:DrawView = findViewById(R.id.viewDraw);
        val ib_brush:ImageButton = findViewById(R.id.ic_brush);
        val ll_paint_colors:LinearLayout = findViewById(R.id.ll_of_colors);
        val gallery_but:ImageButton = findViewById(R.id.ic_gallery);

        mImageButtonCurrentPaint = ll_paint_colors[1] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.pallet_active
            )
        )

        ib_brush.setOnClickListener {
            selectSize();
        }

        gallery_but.setOnClickListener {
            if(isPermissions()){
                //code
            }
            else{
                onRequestPermission();
            }
        }

    }

    private fun selectSize(){
        val draw_view:DrawView = findViewById(R.id.viewDraw);
        val brush_dilog = Dialog(this);

        brush_dilog.setContentView(R.layout.select_of_size);

        brush_dilog.setTitle("Brush size :")

        val small_dilog:ImageButton = brush_dilog.findViewById(R.id.ib_small_brush);
        val middle_dilog:ImageButton = brush_dilog.findViewById(R.id.ib_medium_brush);
        val large_dilog:ImageButton = brush_dilog.findViewById(R.id.ib_large_brush);
        brush_dilog.show()

        small_dilog.setOnClickListener (View.OnClickListener {
            draw_view.onChangeBrushSize(10.toFloat())
            brush_dilog.dismiss();

        })

        middle_dilog.setOnClickListener (View.OnClickListener {
            draw_view.onChangeBrushSize(20.toFloat())
            brush_dilog.dismiss();

        })

        large_dilog.setOnClickListener (View.OnClickListener {
            draw_view.onChangeBrushSize(30.toFloat())
            brush_dilog.dismiss();

        })

    }

    fun onSelectedColor(view:View){

        val draw_view:DrawView = findViewById(R.id.viewDraw);

        if(view !== mImageButtonCurrentPaint){

            val elem = view as ImageButton;
            val color = elem.tag.toString();

            draw_view.onChangeColor(color);

            elem!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_active));
            mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_normal));

            mImageButtonCurrentPaint = elem;


        }
    }

    fun onRequestPermission(){
        Toast.makeText(this, "You need to get permission to add background", Toast.LENGTH_LONG);

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE),PERMISSION_READ_DATA);

    }

    companion object{
        const val PERMISSION_READ_DATA = 1;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_READ_DATA){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this@MainActivity,
                    "You have permission",
                    Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this@MainActivity,
                    "Ooops you denied permission",
                    Toast.LENGTH_LONG).show();
            }
        }
    }

    fun isPermissions():Boolean{
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED;
    }

}