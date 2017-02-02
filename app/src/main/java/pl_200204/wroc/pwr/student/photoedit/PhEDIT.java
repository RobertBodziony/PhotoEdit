package pl_200204.wroc.pwr.student.photoedit;

import android.Manifest;
import android.app.*;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.*;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class PhEDIT extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private static int RESULT_LOAD_IMG = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 123;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final int PLEASE_WAIT_DIALOG = 1;
    ImageView scrollMenu1, scrollMenu2, scrollMenu3, scrollMenu4, scrollMenu5,
            scrollMenu6, scrollMenu7, scrollMenu8, scrollMenu9, scrollMenu10,
            scrollMenu11, scrollMenu12, scrollMenu13, scrollMenu14;
    SeekBar sizeSeekBar;
    String imgDecodableString, text="";
    Boolean drawT = false, drawR = false, brSelected = false, contSelected = false;
    BitmapProcess bitmapProcess;
    ImageView image;
    Bitmap bitmapSc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        image = (ImageView) findViewById(R.id.main_image_view);
        bitmapProcess = new BitmapProcess(((BitmapDrawable)image.getDrawable()).getBitmap());
        scrollMenu1 = (ImageView) findViewById(R.id.scrollMenu1);
        sizeSeekBar = (SeekBar) findViewById(R.id.sizeSeekBar);
        new SetIcons(this).execute(bitmapProcess.getResizedBitmap(((BitmapDrawable)scrollMenu1.getDrawable()).getBitmap(),((BitmapDrawable)scrollMenu1.getDrawable()).getBitmap().getWidth()/4,((BitmapDrawable)scrollMenu1.getDrawable()).getBitmap().getHeight()/4));
        scrollMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.doGreyscale(bitmap);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu2 = (ImageView) findViewById(R.id.scrollMenu2);
        scrollMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.createSepiaToningEffect(bitmap,60,1,0,0);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu3 = (ImageView) findViewById(R.id.scrollMenu3);
        scrollMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.createSepiaToningEffect(bitmap,60,0,1,0);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu4 = (ImageView) findViewById(R.id.scrollMenu4);
        scrollMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.createSepiaToningEffect(bitmap,60,0,0,1);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu5 = (ImageView) findViewById(R.id.scrollMenu5);
        scrollMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.doInvert(bitmap);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu6 = (ImageView) findViewById(R.id.scrollMenu6);
        scrollMenu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.doColorFilter(bitmap,0.9,0.5,0.5);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu7 = (ImageView) findViewById(R.id.scrollMenu7);
        scrollMenu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.doColorFilter(bitmap,0.5,0.9,0.5);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu8 = (ImageView) findViewById(R.id.scrollMenu8);
        scrollMenu8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.doColorFilter(bitmap,0.5,0.5,0.9);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu9 = (ImageView) findViewById(R.id.scrollMenu9);
        scrollMenu9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.doGamma(bitmap, 0.7, 0.6, 0.5);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu10 = (ImageView) findViewById(R.id.scrollMenu10);
        scrollMenu10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.decreaseColorDepth(bitmap,64);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu11 = (ImageView) findViewById(R.id.scrollMenu11);
        scrollMenu11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawT = true;
                drawR = false;
                contSelected = false;
                brSelected = false;
                final AlertDialog.Builder alert = new AlertDialog.Builder(PhEDIT.this);

                final EditText edittext = new EditText(PhEDIT.this);
                alert.setMessage("Type your text here:");
                alert.setTitle("Text tool");

                alert.setView(edittext);

                alert.setPositiveButton("Set Text", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        text = edittext.getText().toString();
                        Toast.makeText(getApplicationContext(),"You have set text: \'"+text+"\'. Click on the photo to place it there.",Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });

        scrollMenu12 = (ImageView) findViewById(R.id.scrollMenu12);
        scrollMenu12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawR = true;
                drawT = false;
                contSelected = false;
                brSelected = false;
            }
        });

        scrollMenu13 = (ImageView) findViewById(R.id.scrollMenu13);
        scrollMenu13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawR = false;
                drawT = false;
                contSelected = true;
                brSelected = false;
                bitmapProcess.setnVal(150);
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.doContrast(bitmap);
                image.setImageBitmap(bitmap);
            }
        });

        scrollMenu14 = (ImageView) findViewById(R.id.scrollMenu14);
        scrollMenu14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawR = false;
                drawT = false;
                contSelected = false;
                brSelected = true;
                sizeSeekBar.setProgress(50);
                bitmapProcess.setnVal(1);
                Bitmap bitmap = bitmapProcess.getScaled();
                bitmap = bitmapProcess.doBrightness(bitmap);
                image.setImageBitmap(bitmap);
            }
        });

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] viewCoords = new int[2];
                image.getLocationOnScreen(viewCoords);

                final int index = event.getActionIndex();
                final float[] coords = new float[] { event.getX(index), event.getY(index) };
                Matrix matrix = new Matrix();
                image.getImageMatrix().invert(matrix);
                matrix.postTranslate(image.getScrollX(), image.getScrollY());
                matrix.mapPoints(coords);
                Bitmap bitmap = bitmapProcess.getScaled();
                Log.e("X AND Y", coords[0]+" - "+coords[1]);
                if (drawT) {
                    bitmap = bitmapProcess.drawT(bitmap, (int) coords[0],(int) coords[1],text,50);
                    image.setImageBitmap(bitmap);
                } else if (drawR) {
                    bitmap = bitmapProcess.drawRectang(bitmap, coords[0],coords[1],50);
                    image.setImageBitmap(bitmap);
                }
                return true;
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekProgress = seekBar.getProgress();
                if(drawT || drawT){
                Toast.makeText(getApplicationContext(),"Size changed to : "+seekProgress+"px.",Toast.LENGTH_SHORT).show();
                if(seekProgress < 5){
                    seekProgress = 5;
                }
                bitmapProcess.setnSize(seekProgress);
                } else if (contSelected){
                    if(seekProgress < 33){
                        seekProgress = 50;
                    } else if(seekProgress >= 33 && seekProgress < 66){
                        seekProgress = 100;
                    } else if(seekProgress >= 66){
                        seekProgress = 200;
                    }
                    bitmapProcess.setnVal(seekProgress);
                    Bitmap bitmap = bitmapProcess.getScaled();
                    bitmap = bitmapProcess.doContrast(bitmap);
                    image.setImageBitmap(bitmap);
                } else if(brSelected){
                    if(seekProgress < 50){
                        seekProgress = seekProgress - 50;
                    }
                    bitmapProcess.setnVal(seekProgress);
                    Bitmap bitmap = bitmapProcess.getScaled();
                    bitmap = bitmapProcess.doBrightness(bitmap);
                    image.setImageBitmap(bitmap);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ph_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.saveFilter) {
            if (bitmapProcess.getEdited() == null){
                Log.e("SAVING THE IMAGE","SOMETHING WENT WRONG...");
            } else {
                bitmapProcess.setScaled(bitmapProcess.getEdited());
                image.setImageBitmap(bitmapProcess.getScaled());
                Toast.makeText(getBaseContext(),"Filter added to the photo.",Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadImagefromGallery(View view) {

        try {
            int permission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Sorry but something went wrong.", Toast.LENGTH_LONG).show();
        }

        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/DCIM/PhotoEDIT");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(this,"Your photo is saved in "+root+"/DCIM/PhotoEDIT",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                    bitmapProcess = new BitmapProcess(bitmap);
                    if(bitmap.getHeight() > 2000 || bitmap.getWidth() > 2000){
                        bitmapSc = bitmapProcess.getResizedBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);}
                    else {
                        bitmapSc = bitmap;
                    }

                    bitmapProcess.setScaled(bitmapSc);
                    image.setImageBitmap(bitmap);
                    //bitmap = bitmapProcess.getResizedBitmap(bitmap, 75,75);
                } else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG)
                    .show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_take_from_gallery) {
            this.loadImagefromGallery(findViewById(R.id.main_image_view));
        } else if (id == R.id.nav_take_a_photo) {
            Toast.makeText(getApplicationContext(),"This functionality is not available yet. Sorry :)",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_save) {
            ImageView img = (ImageView) findViewById(R.id.main_image_view);
            Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
            this.saveImage(bitmap);
        } else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(),"This functionality is not available yet. Sorry :)",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(getApplicationContext(),"This functionality is not available yet. Sorry :)",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Dialog onCreateDialog(int dialogId) {

        switch (dialogId) {
            case PLEASE_WAIT_DIALOG:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("Setting up the content. It can take some time");
                dialog.setMessage("Pleas wait....");
                dialog.setCancelable(true);
                return dialog;
            default:
                break;
        }

        return null;
    }


}

class SetIcons extends AsyncTask<Bitmap, Void, Void> {

    Activity activityPhEdit;
    Bitmap bitmap;
    BitmapProcess bitmapProcessForThread;
    ImageView menu1,menu2,menu3,menu4,menu5,menu6,menu7,menu8,menu9,menu10,menu11,menu12,menu13,menu14;


    public SetIcons(Activity activityPhEdit) {
        this.activityPhEdit = activityPhEdit;
        menu1 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu1);
        menu2 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu2);
        menu3 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu3);
        menu4 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu4);
        menu5 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu5);
        menu6 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu6);
        menu7 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu7);
        menu8 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu8);
        menu9 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu9);
        menu10 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu10);
        menu11 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu11);
        menu12 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu12);
        menu13 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu13);
        menu14 = (ImageView) activityPhEdit.findViewById(R.id.scrollMenu14);
    }

    @Override
    protected void onPreExecute() {
        activityPhEdit.showDialog(PhEDIT.PLEASE_WAIT_DIALOG);
    }

    @Override
    protected Void doInBackground(Bitmap... arg0) {

            bitmap = arg0[0];
            bitmapProcessForThread = new BitmapProcess(bitmap);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(bitmap != null) {
            menu1.setImageBitmap(bitmapProcessForThread.doGreyscale(bitmap));
            menu2.setImageBitmap(bitmapProcessForThread.createSepiaToningEffect(bitmap,60,1,0,0));
            menu3.setImageBitmap(bitmapProcessForThread.createSepiaToningEffect(bitmap,60,0,1,0));
            menu4.setImageBitmap(bitmapProcessForThread.createSepiaToningEffect(bitmap,60,0,0,1));
            menu5.setImageBitmap(bitmapProcessForThread.doInvert(bitmap));
            menu6.setImageBitmap(bitmapProcessForThread.doColorFilter(bitmap,0.9,0.5,0.5));
            menu7.setImageBitmap(bitmapProcessForThread.doColorFilter(bitmap,0.5,0.9,0.5));
            menu8.setImageBitmap(bitmapProcessForThread.doColorFilter(bitmap,0.5,0.5,0.9));
            menu9.setImageBitmap(bitmapProcessForThread.doGamma(bitmap, 0.7, 0.6, 0.5));
            menu10.setImageBitmap(bitmapProcessForThread.decreaseColorDepth(bitmap,64));
            bitmapProcessForThread.setnSize(150);
            menu11.setImageBitmap(bitmapProcessForThread.drawT(bitmap,(bitmap.getWidth()/2)-45,(bitmap.getHeight()/2)+45,"A",150));
            bitmapProcessForThread.setnSize(50);
            menu12.setImageBitmap(bitmapProcessForThread.drawRectang(bitmap,(bitmap.getWidth()/2),(bitmap.getHeight()/2),150));
            bitmapProcessForThread.setnVal(50);
            menu13.setImageBitmap(bitmapProcessForThread.doContrast(bitmap));
            menu14.setImageBitmap(bitmapProcessForThread.doBrightness(bitmap));

        }
        activityPhEdit.removeDialog(PhEDIT.PLEASE_WAIT_DIALOG);
        //else Toast.makeText(activityPhEdit,"Something went wrong.",Toast.LENGTH_SHORT).show();
    }

}
