package com.example.covidstatusapp.countrydetails.repositories;

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
}
