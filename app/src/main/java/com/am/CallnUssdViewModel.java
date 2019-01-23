package com.am;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CallnUssdViewModel extends AndroidViewModel {
    public CallnUssdRepository callnUssdRepository;
    public LiveData<List<RecentCall>> listLiveData;
    public LiveData<List<UssdItem>> ussdList;

    public CallnUssdViewModel(@NonNull Application application) {
        super(application);
        callnUssdRepository = new CallnUssdRepository(application);
        listLiveData = callnUssdRepository.getAllCalls();
        ussdList = callnUssdRepository.getUssdList();
    }

    public void insert(RecentCall recentCall) {
        callnUssdRepository.insert(recentCall);
    }

    public void update(RecentCall recentCall) {
        callnUssdRepository.update(recentCall);
    }

    public void delete(RecentCall recentCall) {
        callnUssdRepository.delete(recentCall);
    }

    public LiveData<List<RecentCall>> getListLiveData() {
        return listLiveData;
    }


    public void insertUssd(UssdItem UssdItem) {
        callnUssdRepository.insertUssd(UssdItem);
    }


    public void deleteUssd(UssdItem UssdItem) {
        callnUssdRepository.deleteUssd(UssdItem);
    }

    public LiveData<List<UssdItem>> getUssdList() {
        return ussdList;
    }
    
    
}
