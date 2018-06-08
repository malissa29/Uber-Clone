/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {

  public void redirectActivity( )
  {
    if (ParseUser.getCurrentUser().get("RiderorDriver").equals("Rider"))
    {
      Intent intent = new Intent(getApplicationContext(), RiderActivity.class);

      startActivity(intent);
    }
    else
    {
      Intent intent = new Intent(getApplicationContext(), viewRequestsActivity.class);

      startActivity(intent);
    }
  }

  public void getStarted (View view)
  {
    Switch userTypeSwitch = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      userTypeSwitch = (Switch) findViewById(R.id.userTypeSwitch);
    }
    Log.i("Switchvalue", String.valueOf(userTypeSwitch.isChecked()));

    String userType= "Rider";

    if(userTypeSwitch.isChecked())
    {
      userType = "Driver";
    }

    ParseUser.getCurrentUser().put("RiderorDriver", userType);

    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        redirectActivity();
      }
    });

    Log.i("iNFORMATION", "REDIRICTING" + userType);

    redirectActivity();


  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getSupportActionBar().hide();

    if (ParseUser.getCurrentUser() == null)
    {
      ParseAnonymousUtils.logIn(new LogInCallback() {

        @Override
        public void done(ParseUser user, ParseException e) {
         if (e==null)
         {
           Log.i("info", "anonymous login successful!");
         }
         else
         {
           Log.i("info", "anonymous login unsuccessful!");
         }
        }
      });
    }
    else
    {
      if (ParseUser.getCurrentUser().get("RiderorDriver") != null)
      {
        Log.i("iNFORMATION", "REDIRICTING" + ParseUser.getCurrentUser().get("RiderorDriver"));

        redirectActivity();
      }
    }

    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}