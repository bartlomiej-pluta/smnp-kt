package error

import dsl.token.model.entity.TokenPosition

class InvalidSyntaxException(message: String?, val position: TokenPosition?) : Exception(message) {
    override val message: String?
        get() = super.message + if(position != null) " $position" else ""
}