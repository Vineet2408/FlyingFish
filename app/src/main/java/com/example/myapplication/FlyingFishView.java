package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View
{
    private Bitmap fish[]=new Bitmap[2];
    private int fishx=10;
    private int fishy;
    private int fishSpeed;

    private int canvaswidth, canvasHeight;

    private boolean touch=false;
    private int lifeCounter;

    private int yellowX,yellowY,yellowSpeed=16;
    private Paint yellowPaint=new Paint();

    private  int greenX,greenY,greenSpeed=20;
    private  Paint greenPaint=new Paint();

    private int redX,redY,redSpeed=16;
    private Paint redPaint=new Paint();

    private int score=0;

    private Bitmap backgroundImage;
    private Paint scorePaint =new Paint();
    private Bitmap life[]=new Bitmap[2];

    public FlyingFishView(Context context)
    {
        super(context);
        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        backgroundImage=BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishy=550;
        score=0;
        lifeCounter=3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvaswidth=canvas.getWidth();

        canvasHeight=canvas.getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishy = fish[0].getHeight();
        int maxFishy = canvasHeight - fish[0].getHeight()*3;
        fishy=fishy+fishSpeed;

        if (fishy < minFishy)
        { fishy = minFishy; }

        if (fishy > maxFishy)
        {  fishy = maxFishy; }

        fishSpeed=fishSpeed+2;

        if(touch)
        {
            canvas.drawBitmap(fish[1],fishx+10,fishy,null);
            touch=false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fishx+10,fishy,null);
        }

        yellowX=yellowX-yellowSpeed;
        if (hitBallChecker(yellowX,yellowY))
        {
            score=score+10;
            yellowX=yellowX-100;
        }

        greenX=greenX-greenSpeed;
        if(hitBallChecker(greenX,greenY))
        {
            score=score+20;
            greenX=greenX-100;
        }


        if(yellowX < 0)
        {
            yellowX=canvaswidth+21;
            yellowY=(int)Math.floor(Math.random() * (maxFishy-minFishy))+minFishy;
        }

        canvas.drawCircle(yellowX,yellowY,20,yellowPaint);

        if(greenX < 0)
        {
            greenX=canvaswidth+21;
            greenY=(int)Math.floor(Math.random() * (maxFishy-minFishy))+minFishy;
        }

        canvas.drawCircle(greenX,greenY,25,greenPaint);


        redX=redX-redSpeed;
        if (hitBallChecker(redX,redY))
        {
            redX=redX-120;
            lifeCounter--;
            if (lifeCounter==0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),GameOverAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(intent);
            }
        }

        if(redX < 0)
        {
            redX=canvaswidth+21;
            redY=(int)Math.floor(Math.random() * (maxFishy-minFishy))+minFishy;
        }

        canvas.drawCircle(redX,redY,28,redPaint);

        for (int i=0;i<3;i++)
        {
            int x= (int) (480+ life[0].getWidth() * 1.25 * i);
            int y=20;
            if(i<lifeCounter)
            {
                canvas.drawBitmap(life[0],x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);

            }
        }

        canvas.drawText("score : "+score,20,60, scorePaint);

    }

    public boolean hitBallChecker(int x,int y)
    {
        if (fishx < x && x< (fishx+ fish[0].getWidth()) && fishy <y && y < (fishy + fish[0].getHeight()))
        {
            return  true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction()== MotionEvent.ACTION_DOWN)
        {
            touch=true;
            fishSpeed=-22;
        }
        return true;
    }
}
