package br.edu.ifpe.pdm.cardapiolanches.utils;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import java.util.ArrayList;

import br.edu.ifpe.pdm.cardapiolanches.R;

/**
 * Created by Richardson on 09/06/2015.
 */
public class SlideMenu {


    // nav drawer title


    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;
    private Activity activity;

    SlideMenu(){

    }
   public SlideMenu(Activity activity, DrawerLayout mDrawerLayout, ListView mDrawerList, CharSequence mDrawerTitle, CharSequence mTitle ){
    this.activity =activity;
   this.mDrawerLayout = mDrawerLayout;
        this.mDrawerList = mDrawerList;
        this.mDrawerTitle =  mDrawerTitle;
        this.mTitle = mTitle;

        this.mTitle = this.mDrawerTitle = activity.getTitle();

        // load slide menu items
        this.navMenuTitles = activity.getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
      //  navMenuIcons = activity.getResources().obtainTypedArray(R.array.nav_drawer_icons);

        this.mDrawerList = (ListView) activity.findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
        // What's hot, We  will add a counter here

        // Recycle the typed array
    //    navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(activity.getApplicationContext(),
                navDrawerItems);
       this.mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button

    }



}
