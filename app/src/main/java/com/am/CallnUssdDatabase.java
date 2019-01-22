package com.am;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {RecentCall.class, UssdItem.class}, version = 1)
public abstract class CallnUssdDatabase extends RoomDatabase {

    public static CallnUssdDatabase instance;

    public abstract RecentCallDao RecentCallDao();
    public abstract UssdDao ussdDao();

    public static synchronized CallnUssdDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CallnUssdDatabase.class, "allbigdb")
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecentCallDao recentCallDao;

        private PopulateDbAsyncTask(CallnUssdDatabase db) {
            recentCallDao = db.RecentCallDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           /* recentCallDao.insert(new RecentCall(null, "1548131038456", true, "1548131038456", "+9197683105446"));
            recentCallDao.insert(new RecentCall(null, "1548131038456", false, "1548131038456", "+8767565654456"));
            recentCallDao.insert(new RecentCall(null, "1548131038456", true, "1548131038456", "+9197683105446"));
*/


            return null;
        }
    }
}