package mg.jaava.data

import android.util.Patterns
import mg.jaava.auth.domain.PatternValidator

object EmailPatterValidator : PatternValidator {
    override fun matches(value: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(value).matches()
}