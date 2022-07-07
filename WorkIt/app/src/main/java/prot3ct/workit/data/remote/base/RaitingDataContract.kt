package prot3ct.workit.data.remote.base

import io.reactivex.Observable

interface RaitingDataContract {
    fun createRaiting(
        value: Int,
        description: String,
        receiverUserId: Int,
        taskId: Int,
        receiverUserRoleId: Int
    ): Observable<Boolean>
}