package prot3ct.workit.data.remote;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Function;
import io.reactivex.Observable;
import prot3ct.workit.config.ApiConstants;
import prot3ct.workit.data.local.UserSession;
import prot3ct.workit.data.remote.base.RaitingDataContract;
import prot3ct.workit.models.base.HttpResponseContract;
import prot3ct.workit.utils.GsonParser;
import prot3ct.workit.utils.HashProvider;
import prot3ct.workit.utils.OkHttpRequester;

public class RaitingData implements RaitingDataContract {
    private final OkHttpRequester httpRequester;
    private final HashProvider hashProvider;
    private final ApiConstants apiConstants;
    private final GsonParser jsonParser;
    private final UserSession userSession;
    private Map<String, String> headers;

    public RaitingData(Context context) {
        this.jsonParser = new GsonParser();
        this.hashProvider = new HashProvider();
        this.httpRequester = new OkHttpRequester();
        this.apiConstants = new ApiConstants();
        this.userSession = new UserSession(context);
        headers = new HashMap<>();
        headers.put("authToken", userSession.getId() + ":" + userSession.getAccessToken());
    }

    @Override
    public Observable<Boolean> createRaiting(int value, String description, int receiverUserId, int taskId, int receiverUserRoleId) {
        Map<String, String> raiting = new HashMap<>();
        raiting.put("receiverUserId", Integer.toString(receiverUserId));
        raiting.put("taskId", Integer.toString(taskId));
        raiting.put("receiverUserRoleId", Integer.toString(receiverUserRoleId));
        raiting.put("description", description);
        raiting.put("value", value+"");

        return httpRequester
            .post(apiConstants.createRaitingUrl(), raiting, headers)
            .map(new Function<HttpResponseContract, Boolean>() {
                @Override
                public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                    if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                        throw new Error(iHttpResponse.getMessage());
                    }

                    return true;
                }
            });
    }
}
