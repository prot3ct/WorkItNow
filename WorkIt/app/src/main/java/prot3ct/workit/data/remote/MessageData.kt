package prot3ct.workit.data.remote;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import prot3ct.workit.config.ApiConstants;
import prot3ct.workit.data.local.UserSession;
import prot3ct.workit.models.base.HttpResponseContract;
import prot3ct.workit.utils.GsonParser;
import prot3ct.workit.utils.OkHttpRequester;
import prot3ct.workit.view_models.AvailableTasksListViewModel;
import prot3ct.workit.view_models.ListMessagesViewModel;

public class MessageData {
    private final OkHttpRequester httpRequester;
    private final ApiConstants apiConstants;
    private final GsonParser jsonParser;
    private final UserSession userSession;
    private Map<String, String> headers;

    public MessageData(Context context) {
        this.jsonParser = new GsonParser();
        this.httpRequester = new OkHttpRequester();
        this.apiConstants = new ApiConstants();
        this.userSession = new UserSession(context);
        headers = new HashMap<>();
        headers.put("authToken", userSession.getId() + ":" + userSession.getAccessToken());
    }

    public Observable<Boolean> createMessage(String text, int authorId, int dialogId, String createdAt) {
        Map<String, String> messageDetails = new HashMap<>();
        messageDetails.put("text", text);
        messageDetails.put("authorId", authorId+"");
        messageDetails.put("dialogId", dialogId+"");
        messageDetails.put("createdAt", createdAt);

        return httpRequester
                .post(apiConstants.createMessage(dialogId), messageDetails, headers)
                .map(new Function<HttpResponseContract, Boolean>() {
                    @Override
                    public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode() || iHttpResponse.getCode() == apiConstants.reponseServerErrorCode()) {
                            throw new Error(iHttpResponse.getBody());
                        }

                        return true;
                    }
                });
    }

    public Observable<List<ListMessagesViewModel>> getMessages(int dialogId) {
        return httpRequester
                .get(apiConstants.getMessages(dialogId), headers)
                .map(new Function<HttpResponseContract, List<ListMessagesViewModel>>() {
                    @Override
                    public List<ListMessagesViewModel> apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody();
                        return jsonParser.fromJson(responseBody, new TypeToken<List<ListMessagesViewModel>>(){}.getType());
                    }
                });
    }
}
