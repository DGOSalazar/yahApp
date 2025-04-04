package com.cortech.yahapp.core.data.local

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.cortech.yahapp.core.domain.model.auth.UserData
import com.cortech.yahapp.core.domain.model.auth.UserType
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserData(userData: UserData) {
        prefs.edit().apply {
            putString(KEY_USER_NAME, userData.name)
            putString(KEY_USER_TYPE, userData.type.name)
            apply()
        }
    }

    fun saveImage(image: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

        prefs.edit().putString("image", encodedImage).apply()
    }

    fun getImage(): Bitmap? {
        val encodedImage = prefs.getString("image", null) ?: return null
        val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun getUserData(): UserData? {
        val name = prefs.getString(KEY_USER_NAME, null) ?: return null
        val typeStr = prefs.getString(KEY_USER_TYPE, null) ?: return null
        
        return try {
            val type = UserType.valueOf(typeStr)
            UserData(name, type)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    fun clearUserData() {
        prefs.edit().apply {
            remove(KEY_USER_NAME)
            remove(KEY_USER_TYPE)
            apply()
        }
    }

    fun isUserLoggedIn(): Boolean {
        return getUserData() != null
    }

    companion object {
        private const val PREFS_NAME = "yah_app_preferences"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_TYPE = "user_type"
    }
}
