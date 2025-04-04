package com.cortech.yahapp.features.register.domain.usecase

import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.data.repository.WizelineRepository
import com.cortech.yahapp.core.domain.model.UserData
import com.cortech.yahapp.core.domain.model.UserType
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: WizelineRepository,
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(name: String, password: String): Result<Unit> {
        return repository.getUserByName(name).map { response ->
            if (response.password == password) {
                userPreferences.saveUserData(
                    UserData(
                        name = response.name,
                        type = UserType.EMPLOYEE
                    )
                )
            } else {
                throw Exception("Invalid password")
            }
        }
    }
}
