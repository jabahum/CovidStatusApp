package com.example.covidstatusapp.countrydetails.repositories;

import com.example.covidstatusapp.network.APIClient;
import com.example.covidstatusapp.network.APIinterface;

public class MyCountryRepository {
    private static MyCountryRepository myCountryRepository;
    private APIinterface apIinterface;

    public MyCountryRepository() {
        apIinterface = APIClient.getClient().create(APIinterface.class);
    }

    public static MyCountryRepository getInstance() {
        if (myCountryRepository == null) {
            myCountryRepository = new MyCountryRepository();
        }
        return myCountryRepository;
    }
}
