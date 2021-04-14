package kr.devunt.grocerystore.service

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.InputStreamReader
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtService(
    @Value("classpath:private.key")
    private val resource: Resource
) {
    private val key =
        RSAKey.parseFromPEMEncodedObjects(InputStreamReader(resource.inputStream).use { it.readText() }).toRSAKey()
    private val signer = RSASSASigner(key)
    val publicKey = key.toRSAPublicKey()

    fun issue(audience: String) =
        SignedJWT(
            JWSHeader(JWSAlgorithm.RS256),
            JWTClaimsSet.Builder()
                .audience(audience)
                .issuer("grocery-store")
                .issueTime(Date())
                .expirationTime(Date.from(Instant.now().plus(Duration.ofHours(1))))
                .build(),
        ).apply {
            sign(signer)
        }.serialize()
}
