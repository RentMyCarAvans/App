package com.avans.rentmycar.utils


import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

fun hasBiometricCapability(context: Context): Int {
    val biometricManager = BiometricManager.from(context)
    return biometricManager.canAuthenticate()
}

fun isBiometricReady(context: Context) =
    hasBiometricCapability(context) == BiometricManager.BIOMETRIC_SUCCESS


fun setBiometricPromptInfo(
    title: String,
    subtitle: String,
    description: String,
    allowDeviceCredential: Boolean
): BiometricPrompt.PromptInfo {
    val builder = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setSubtitle(subtitle)
        .setDescription(description)

    // Use Device Credentials if allowed, otherwise show Cancel Button
    builder.apply {
        if (allowDeviceCredential) setDeviceCredentialAllowed(true)
        else setNegativeButtonText("Cancel")
    }

    return builder.build()
}

fun initBiometricPrompt(
    activity: AppCompatActivity,
    listener: BiometricAuthListener
): BiometricPrompt {
    // 1
    val executor = ContextCompat.getMainExecutor(activity)

    // 2
    val callback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            listener.onBiometricAuthenticateError(errorCode, errString.toString())
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Log.w(this.javaClass.simpleName, "Authentication failed for an unknown reason")
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            listener.onBiometricAuthenticateSuccess(result)
        }
    }

    // 3
    return BiometricPrompt(activity, executor, callback)
}

fun showBiometricPrompt(
    title: String = "Rent My Car Security",
    subtitle: String = "Enter biometric credentials to start the ride",
    description: String = "Input your PIN, Fingerprint or Face to ensure it's you!",
    activity: AppCompatActivity,
    listener: BiometricAuthListener,
    cryptoObject: BiometricPrompt.CryptoObject? = null,
    allowDeviceCredential: Boolean = false
) {
    // 1
    val promptInfo = setBiometricPromptInfo(
        title,
        subtitle,
        description,
        allowDeviceCredential
    )

    // 2
    val biometricPrompt = initBiometricPrompt(activity, listener)

    // 3
    biometricPrompt.apply {
        if (cryptoObject == null) authenticate(promptInfo)
        else authenticate(promptInfo, cryptoObject)
    }
}