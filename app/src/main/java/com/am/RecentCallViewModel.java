package com.am;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecentCallViewModel extends AndroidViewModel {
    public RecentCallRepository recentCallRepository;
    public LiveData<List<RecentCall>> listLiveData;

    public RecentCallViewModel(@NonNull Application application) {
        super(application);
        recentCallRepository = new RecentCallRepository(application);
        listLiveData = recentCallRepository.getAllCalls();
    }

    public void insert(RecentCall recentCall) {
        recentCallRepository.insert(recentCall);
    }

    public void update(RecentCall recentCall) {
        recentCallRepository.update(recentCall);
    }

    public void delete(RecentCall recentCall) {
        recentCallRepository.delete(recentCall);
    }

    public LiveData<List<RecentCall>> getListLiveData() {
        return listLiveData;
    }


}
