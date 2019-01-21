package com.am;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {RecentCall.class}, version = 1)
public abstract class RecentCallDatabase extends RoomDatabase {

    public static RecentCallDatabase instance;

    public abstract RecentCallDao RecentCallDao();

    public static synchronized RecentCallDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RecentCallDatabase.class, "recentcall_database")
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

        private PopulateDbAsyncTask(RecentCallDatabase db) {
            recentCallDao = db.RecentCallDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            recentCallDao.insert(new RecentCall(null, "1:59pm", true, "2:15pm", "+9197683105446"));
            recentCallDao.insert(new RecentCall(null, "1:59pm", false, "2:15pm", "+8767565654456"));
            recentCallDao.insert(new RecentCall(null, "1:59pm", true, "2:15pm", "+9197683105446"));



            return null;
        }
    }
}