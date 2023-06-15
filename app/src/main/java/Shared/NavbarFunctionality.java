package Shared;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
    private final Context context;
    private final String message = "Already on the tapped page";

    public NavbarFunctionality(Context context) {
        this.context = context;
    }

    public void Home_handler() {
        Backend2_Dashboard backend2_dashboard = (Backend2_Dashboard) context;

        FrameLayout home_1 = backend2_dashboard.findViewById(R.id.home);
        FrameLayout notification_1 = backend2_dashboard.findViewById(R.id.notification);
        FrameLayout profile_1 = backend2_dashboard.findViewById(R.id.profile);

        ImageView icon_1 = backend2_dashboard.findViewById(R.id.navbar_icon_home);
        TextView text_1 = backend2_dashboard.findViewById(R.id.navbar_text_home);

        color_selected(icon_1, text_1);

        home_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

        notification_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_handler(Backend6_Notification.class);
            }
        });

        profile_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_handler(Backend5_Profile.class);
            }
        });
    }

    public void Notification_handler(){
        Backend6_Notification backend6_notification = (Backend6_Notification) context;
        FrameLayout home_2 = backend6_notification.findViewById(R.id.home);
        FrameLayout notification_2 = backend6_notification.findViewById(R.id.notification);
        FrameLayout profile_2 = backend6_notification.findViewById(R.id.profile);
        ImageView notify = backend6_notification.findViewById(R.id.navbar_icon_notify);
        TextView text = backend6_notification.findViewById(R.id.navbar_text_notify);

        color_selected(notify, text);
        notification_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

        profile_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_handler(Backend5_Profile.class);
            }
        });

        home_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_handler(Backend2_Dashboard.class);
            }
        });
    }

    public void Profile_handler(){
        Backend5_Profile profile = (Backend5_Profile) context;
        FrameLayout home3 = profile.findViewById(R.id.home);
        FrameLayout notification3 = profile.findViewById(R.id.notification);
        FrameLayout profile3 = profile.findViewById(R.id.profile);
        ImageView image = profile.findViewById(R.id.navbar_icon_profile);
        TextView text = profile.findViewById(R.id.navbar_text_profile);

        color_selected(image,text);

        home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_handler(Backend2_Dashboard.class);
            }
        });

        notification3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_handler(Backend6_Notification.class);
            }
        });

        profile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void color_selected(ImageView image, TextView text) {
        image.setColorFilter(context.getResources().getColor(R.color.button_1));
        text.setTextColor(context.getResources().getColor(R.color.button_1));
    }

    private void activity_handler(Class<?> Destination_class) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, Destination_class);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }

}
