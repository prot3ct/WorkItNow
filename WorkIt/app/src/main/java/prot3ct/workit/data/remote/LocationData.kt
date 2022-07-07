package prot3ct.workit.data.remote;

import android.util.Log;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import prot3ct.workit.config.ApiConstants;
import prot3ct.workit.models.Location;
import prot3ct.workit.models.base.HttpResponseContract;
import prot3ct.workit.models.base.LocationContract;
import prot3ct.workit.utils.GsonParser;
import prot3ct.workit.utils.OkHttpRequester;

public class LocationData {
    private final OkHttpRequester httpRequester;
    private final ApiConstants apiConstants;
    private final GsonParser jsonParser;

    public LocationData() {
        this.jsonParser = new GsonParser();
        this.httpRequester = new OkHttpRequester();
        this.apiConstants = new ApiConstants();
    }

    public Observable<LocationContract> getLatLng(final String location) {
        return httpRequester
            .get(apiConstants.getLocationLatLngUrl(location))
            .map(new Function<HttpResponseContract, LocationContract>() {
                @Override
                public LocationContract apply(HttpResponseContract iHttpResponse) throws Exception {
                    if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                        throw new Error(iHttpResponse.getMessage());
                    }

                    String responseBody = iHttpResponse.getBody();

                    JSONObject jsonObj = new JSONObject(responseBody);
                    JSONObject locationJson = jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                    return jsonParser.fromJson(locationJson.toString(), Location.class);
                }
            });
    }

    public Observable<Boolean> checkIfLocationExists(final String location) {
        return httpRequester
                .get(apiConstants.getLocationLatLngUrl(location))
                .map(new Function<HttpResponseContract, Boolean>() {
                    @Override
                    public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody();

                        Object jsonObj = new JSONObject(responseBody).get("status");
                        if(jsonObj.toString().equals("ZERO_RESULTS")) {
                            return false;
                        }
                        return true;
                    }
                });
    }
}
