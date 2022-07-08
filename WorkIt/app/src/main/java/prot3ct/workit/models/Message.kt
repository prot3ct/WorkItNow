package prot3ct.workit.models

import kotlin.jvm.JvmOverloads
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.util.*

class Message @JvmOverloads constructor(
    private val id: String,
    private val user: User,
    private var text: String,
    private var createdAt: Date = Date()
) : IMessage, MessageContentType.Image,
    MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {
    private lateinit var image: Image
    lateinit var voice: Voice
    override fun getId(): String {
        return id
    }

    override fun getText(): String {
        return text
    }

    override fun getCreatedAt(): Date {
        return createdAt
    }

    override fun getUser(): User {
        return user
    }

    override fun getImageUrl(): String {
        return image.url
    }

    val status: String
        get() = "Sent"

    fun setText(text: String) {
        this.text = text
    }

    fun setCreatedAt(createdAt: Date) {
        this.createdAt = createdAt
    }

    fun setImage(image: Image) {
        this.image = image
    }

    class Image(val url: String)
    class Voice(val url: String, val duration: Int)
}