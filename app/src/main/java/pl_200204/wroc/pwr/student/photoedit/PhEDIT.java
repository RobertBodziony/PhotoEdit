package pl_200204.wroc.pwr.student.photoedit;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

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
            scrollMenu6, scrollMenu7, scrollMenu8, scrollMenu9, scrollMenu10;
    String imgDecodableString;
    BitmapProcess bitmapProcess;
    ImageView image;
    Bitmap bitmapSc;
    Fragment fr;

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
        new SetIcons(this).execute(bitmapProcess.getResizedBitmap(((BitmapDrawable)scrollMenu1.getDrawable()).getBitmap(),((BitmapDrawable)scrollMenu1.getDrawable()).getBitmap().getWidth()/4,((BitmapDrawable)scrollMenu1.getDrawable()).getBitmap().getHeight()/4));
        fr = new ListFragment();
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            /*ImageView img = (ImageView) findViewById(R.id.main_image_view);
            Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
            img.setImageBitmap(bitmap);
            */
            Toast.makeText(getApplicationContext(),"This functionality is not available yet. Sorry :)",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_add_filter) {
            /*
            ImageView img = (ImageView) findViewById(R.id.main_image_view);
            Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
            bitmap = bitmapProcess.getCropedBitmap(bitmap);
            img.setImageBitmap(bitmap);
            */
            Toast.makeText(getApplicationContext(),"This functionality is not available yet. Sorry :)",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_edit_photo) {
            Toast.makeText(getApplicationContext(),"This functionality is not available yet. Sorry :)",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_save) {
            Toast.makeText(getApplicationContext(),"This functionality is not available yet. Sorry :)",Toast.LENGTH_SHORT).show();
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
    ImageView menu1,menu2,menu3,menu4,menu5,menu6,menu7,menu8,menu9,menu10;


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

    }

    @Override
    protected void onPreExecute() {
        activityPhEdit.showDialog(PhEDIT.PLEASE_WAIT_DIALOG);
    }

    @Override
    protected Void doInBackground(Bitmap... arg0) {

            bitmap = arg0[0];
            bitmapProcessForThread = new BitmapProcess(bitmap);
            //bitmap = bitmapProcessForThread.doGreyscale(bitmap);

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

        }
        activityPhEdit.removeDialog(PhEDIT.PLEASE_WAIT_DIALOG);
        //else Toast.makeText(activityPhEdit,"Something went wrong.",Toast.LENGTH_SHORT).show();
    }

}
