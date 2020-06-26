package com.example.covidstatusapp.repositories;

import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.covidstatusapp.common.Resource;
import com.example.covidstatusapp.network.APIClient;
import com.example.covidstatusapp.network.APIinterface;

public class GlobalRepository {
    private static GlobalRepository globalRepository;
    private APIinterface apIinterface;

    public GlobalRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static GlobalRepository getInstance() {
        if (globalRepository == null) {
            globalRepository = new GlobalRepository();
        }
        return globalRepository;
    }

/*
    public MediatorLiveData<Resource<MainModel>> getTopTracks() {

        data.setValue(Resource.loading((MainModel) null));

        final LiveData<Resource<MainModel>> source = LiveDataReactiveStreams.fromPublisher(
                apIinterface.getTopTracks()
                        .toFlowable()
                        .onErrorReturn(new Function<Throwable, MainModel>() {
                            @Override
                            public MainModel apply(Throwable throwable) throws Exception {
                                return new MainModel();
                            }
                        })
                        .map(new Function<MainModel, Resource<MainModel>>() {
                            @Override
                            public Resource<MainModel> apply(MainModel mainModel) throws Exception {
                                if (mainModel == null ) {
                                    return Resource.error("Error Couldn't Fetch Data", null);
                                }
                                return Resource.success(mainModel);
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
        );

        data.addSource(source, new Observer<Resource<MainModel>>() {
            @Override
            public void onChanged(Resource<MainModel> mainModelResource) {
                data.setValue(mainModelResource);
                data.removeSource(source);
            }
        });

        return data;
    }
*/
}
