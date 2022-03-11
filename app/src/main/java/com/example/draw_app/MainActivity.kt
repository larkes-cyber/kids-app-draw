package com.example.draw_app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
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

}