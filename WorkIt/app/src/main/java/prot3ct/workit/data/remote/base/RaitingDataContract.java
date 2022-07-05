package prot3ct.workit.data.remote.base;

import io.reactivex.Observable;

public interface RaitingDataContract {
    Observable<Boolean> createRaiting(int value, String description, int receiverUserId, int taskId, int receiverUserRoleId);
}
