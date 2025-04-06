package com.cortech.yahapp.core.domain.usecase.auth

import com.cortech.yahapp.core.data.local.UserPreferences
import com.cortech.yahapp.core.data.model.auth.UserRegistrationRequest
import com.cortech.yahapp.core.data.repository.AuthRepository
import com.cortech.yahapp.core.domain.model.auth.UserData
import com.cortech.yahapp.core.domain.model.auth.UserRegistration
import com.cortech.yahapp.core.domain.model.auth.UserType
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(user: UserRegistration): Result<Unit> {
        return repository.registerUser(
            UserRegistrationRequest(
                name = user.name,
                lastname = user.lastname,
                birthdate = user.birthdate,
                password = user.password,
                description = user.description,
                image = user.image
            )
        ).map { response ->
            userPreferences.saveUserData(
                UserData(
                    name = response.name,
                    type = if (user.isHumanResource) UserType.HUMAN_RESOURCE else UserType.EMPLOYEE
                )
            )
        }
    }
}
