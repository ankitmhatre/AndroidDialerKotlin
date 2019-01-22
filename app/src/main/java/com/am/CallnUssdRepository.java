package com.am;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CallnUssdRepository {
    private RecentCallDao recentCallDao;
    private UssdDao ussdDao;
    private LiveData<List<RecentCall>> listLiveData;
    private LiveData<List<UssdItem>> ussdList;

    public CallnUssdRepository(Application application) {
        CallnUssdDatabase recentCallDatabase = CallnUssdDatabase.getInstance(application);
        recentCallDao = recentCallDatabase.RecentCallDao();
        ussdDao = recentCallDatabase.ussdDao();
        listLiveData = recentCallDao.getAll();
        ussdList = ussdDao.getAll();
    }

    public void insertUssd(UssdItem ussdItem) {
        new InsertUssdCodeTask(ussdDao).execute(ussdItem);
    }

    public void deleteUssd(UssdItem ussdItem) {
        new DeleteUssdTask(ussdDao).execute(ussdItem);
    }

    public LiveData<List<UssdItem>> getUssdList() {
        return ussdList;
    }


    public void insert(RecentCall recentCall) {
        new InsertRecentCallTask(recentCallDao).execute(recentCall);
    }

    public void update(RecentCall recentCall) {
//To be implemented
    }

    public void delete(RecentCall recentCall) {
        new DeleteSpecificTask(recentCallDao).execute(recentCall);
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

    private static class DeleteSpecificTask extends AsyncTask<RecentCall, Void, Void> {
        private RecentCallDao recentCallDao;

        public DeleteSpecificTask(RecentCallDao recentCallDao) {
            this.recentCallDao = recentCallDao;
        }

        @Override
        protected Void doInBackground(RecentCall... recentCalls) {
            recentCallDao.delete(recentCalls[0].getC_id().toString());
            return null;
        }
    }

    private static class InsertUssdCodeTask extends AsyncTask<UssdItem, Void, Void> {
        private UssdDao ussdDao;

        public InsertUssdCodeTask(UssdDao ussdDao) {
            this.ussdDao = ussdDao;
        }

        @Override
        protected Void doInBackground(UssdItem... ussdItems) {
            ussdDao.insert(ussdItems[0]);
            return null;
        }
    }


    private static class DeleteUssdTask extends AsyncTask<UssdItem, Void, Void> {
        private UssdDao ussdDao;

        public DeleteUssdTask(UssdDao ussdDao) {
            this.ussdDao = ussdDao;
        }

        @Override
        protected Void doInBackground(UssdItem... ussdItems) {
            ussdDao.delete(ussdItems[0].getUssd_id());
            return null;
        }
    }


}

