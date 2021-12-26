package com.vk.outflappybird;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "SettingActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    Button btnLogout;

    RelativeLayout settingLayout;
    GridView gridView;
    String [] names = {"Background 1","Background 2", "Background 3"};
    int[] images = {R.drawable.background, R.drawable.background2, R.drawable.background3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mAuth = FirebaseAuth.getInstance();

        btnLogout=findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        gridView = findViewById(R.id.gridView);
        CustomAdapter customAdapter=new CustomAdapter(names,images, this);
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                int selectedImage = images[i];

                startActivity(new Intent(SettingActivity.this, MainActivity.class).putExtra("image",selectedImage));
                settingLayout.setBackgroundResource(selectedImage);
            }

        });
    }

    public class  CustomAdapter extends BaseAdapter{
        private String [] imageName;
        private int [] imagePhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageName, int[] imagePhoto, Context context) {
            this.imageName = imageName;
            this.imagePhoto = imagePhoto;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return imagePhoto.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null){
                view = layoutInflater.inflate(R.layout.row_items, viewGroup, false);
            }
            TextView txtName=view.findViewById(R.id.txtName);
            ImageView imageView=view.findViewById(R.id.imageView);

            txtName.setText(imageName[i]);
            imageView.setImageResource(imagePhoto[i]);
            return view;

        }
    }

    private void signOut() {
        mAuth.signOut();
        Intent logoutIntent = new Intent(this, LoginActivity.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logoutIntent);
    }
}
