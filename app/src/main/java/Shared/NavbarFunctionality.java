package Shared;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tca.Backend2_Dashboard;
import com.example.tca.Backend5_Profile;
import com.example.tca.Backend6_Notification;
import com.example.tca.R;

public class NavbarFunctionality {
    String message = "Already on the tapped page";
    Backend2_Dashboard backend2_dashboard = new Backend2_Dashboard();
    Backend6_Notification backend6_notification = new Backend6_Notification();
    Backend5_Profile backend5_profile = new Backend5_Profile();

    FrameLayout home_1 = backend2_dashboard.findViewById(R.id.home);
    FrameLayout notification_1 = backend2_dashboard.findViewById(R.id.notification);
    FrameLayout profile_1 = backend2_dashboard.findViewById(R.id.profile);

    ImageView icon_1 = backend2_dashboard.findViewById(R.id.navbar_icon_home);
    TextView text_1 = backend2_dashboard.findViewById(R.id.navbar_text_home);

    FrameLayout home_2 = backend6_notification.findViewById(R.id.home);

    public void Home_Button(boolean value){
        if(value){
            Context context = backend2_dashboard.getApplicationContext();
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            color_selected(icon_1,text_1);
            notification_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   context.startActivity(new Intent(context,Backend6_Notification.class));
                }
            });

            profile_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, Backend5_Profile.class));
                }
            });
        }
    }
    @SuppressLint("ResourceAsColor")
    public void color_selected(ImageView image , TextView text){
        image.setColorFilter(R.color.button_1);
        text.setTextColor(R.color.button_1);
    }

    public void activity_handler(Context context ){

    }
}
