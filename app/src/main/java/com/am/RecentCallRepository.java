package com.am;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecentCallRepository {
    private RecentCallDao recentCallDao;
    private LiveData<List<RecentCall>> listLiveData;

    public RecentCallRepository(Application application) {
        RecentCallDatabase recentCallDatabase = RecentCallDatabase.getInstance(application);
        recentCallDao = recentCallDatabase.RecentCallDao();
        listLiveData = recentCallDao.getAll();
    }

    public void insert(RecentCall recentCall) {
        new InsertRecentCallTask(recentCallDao).execute(recentCall);
    }

    public void update(RecentCall recentCall) {
//To be implemented
    }

    public void delete(RecentCall recentCall) {
        //to be implemented
    }

    public LiveData<List<RecentCall>> getAllCalls() {
        return listLiveData;
    }

    private static class InsertRecentCallTask extends AsyncTask<RecentCall, Void, Void> {
        private RecentCallDao recentCallDao;

        public InsertRecentCallTask(RecentCallDao recentCallDao) {
            this.recentCallDao = recentCallDao;
        }

        @Override
        protected Void doInBackground(RecentCall... recentCalls) {
            recentCallDao.insert(recentCalls[0]);
            return null;
        }
    }


}

