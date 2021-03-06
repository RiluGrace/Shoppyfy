package com.example.rilu_pc.shoppyfy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by babur on 16-01-2018.
 */

@Layout(R.layout.drawer_menu_item)
public class DrawerMenuItem
{
    public static final int DRAWER_MENU_ITEM_ARTICLES = 1;
    public static final int DRAWER_MENU_ITEM_NEW_ARTICLES = 2;
    public static final int DRAWER_MENU_ITEM_LOCATION_HISTROY = 3;
    public static final int DRAWER_MENU_ITEM_LOGIN_HISTORY = 4;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 5;

    private int mMenuPosition;
    private Context mContext;
    private DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    public DrawerMenuItem(Context context, int menuPosition) {
        mContext = context;
        mMenuPosition = menuPosition;
//        itemNameTxt = ((Activity)context).findViewById(R.id.itemNameTxt);
//        itemIcon = ((Activity)context).findViewById(R.id.itemIcon);
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_ARTICLES:
                itemNameTxt.setText("Articles");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.profile));
                break;
            case DRAWER_MENU_ITEM_NEW_ARTICLES:
                itemNameTxt.setText("New Articles");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.profile));
                break;
            case DRAWER_MENU_ITEM_LOCATION_HISTROY:
                itemNameTxt.setText("Location Histroy");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.profile));
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.profile));
                itemNameTxt.setText("Logout");
                break;
            case DRAWER_MENU_ITEM_LOGIN_HISTORY:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.profile));
                itemNameTxt.setText("Login History");
                break;
        }
    }

    @Click(R.id.menuitem)
    private void onMenuItemClick(){
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_ARTICLES:
                Intent i=new Intent(mContext,ArticleListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                if(mCallBack != null)mCallBack.onArticlesMenuSelected();
                break;
            case DRAWER_MENU_ITEM_NEW_ARTICLES:
                Intent a=new Intent(mContext,ArticleActivity.class);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(a);
                Toast.makeText(mContext, "New Articles", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onNewArticlesMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOCATION_HISTROY:
                Intent loc=new Intent(mContext,LocationDetailListActivity.class);
                loc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(loc);
                Toast.makeText(mContext, "Location Histroy", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onLocationHistroyMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                Intent la=new Intent(mContext,LoginActivity.class);
                la.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(la);
                Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onLogoutMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGIN_HISTORY:
                Intent l=new Intent(mContext, TimeDetailListActivity.class);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(l);
                Toast.makeText(mContext, "Login History", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onLoginHistroyMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack)

    {
        mCallBack = callBack;
    }


    public interface DrawerCallBack
    {

        void onArticlesMenuSelected();
        void onLogoutMenuSelected();
        void onLoginHistroyMenuSelected();
        void onNewArticlesMenuSelected();
        void onLocationHistroyMenuSelected();
    }
}