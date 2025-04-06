package com.cortech.yahapp.core.domain.usecase.chat

import com.cortech.yahapp.core.data.model.auth.EmployeeResponse
import com.cortech.yahapp.core.domain.model.auth.UserType
import com.cortech.yahapp.core.domain.repository.chat.HomeRepository
import javax.inject.Inject

class FindEmployeesUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(prompt: String, userType: UserType): Result<List<EmployeeResponse>> {
        if (!prompt.startsWith("/find")) {
            return Result.failure(Exception("Invalid command. Use /find to search for employees."))
        }
        
        val searchQuery = prompt.removePrefix("/find").trim()
        if (searchQuery.isEmpty()) {
            return Result.failure(Exception("Please provide search criteria after /find"))
        }
        
        if (userType == UserType.EMPLOYEE) {
            return Result.failure(Exception("Only HR personnel can search for employees"))
        }

        return repository.findEmployees(searchQuery, "HUMAN RESOURCE")
    }
}
