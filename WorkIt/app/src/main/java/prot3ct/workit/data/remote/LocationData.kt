package prot3ct.workit.data.remote

import io.reactivex.Observable
import prot3ct.workit.utils.OkHttpRequester
import prot3ct.workit.config.ApiConstants
import prot3ct.workit.utils.GsonParser
import prot3ct.workit.models.base.LocationContract
import org.json.JSONObject
import prot3ct.workit.models.Location
import java.lang.Error

class LocationData {
    private val httpRequester: OkHttpRequester = OkHttpRequester()

    private val apiConstants: ApiConstants = ApiConstants()

    private val jsonParser: GsonParser = GsonParser()

    fun getLatLng(location: String?): Observable<LocationContract> {
        return httpRequester[apiConstants.getLocationLatLngUrl(location!!)]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                val jsonObj = JSONObject(responseBody)
                val locationJson =
                    jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location")
                jsonParser.fromJson(locationJson.toString(), Location::class.java)
            }
    }

    fun checkIfLocationExists(location: String?): Observable<Boolean> {
        return httpRequester[apiConstants.getLocationLatLngUrl(location!!)]
            .map { iHttpResponse ->
                if (iHttpResponse.code == apiConstants.responseErrorCode()) {
                    throw Error(iHttpResponse.message)
                }
                val responseBody = iHttpResponse.body
                val jsonObj = JSONObject(responseBody)["status"]
                jsonObj.toString() != "ZERO_RESULTS"
            }
    }
}