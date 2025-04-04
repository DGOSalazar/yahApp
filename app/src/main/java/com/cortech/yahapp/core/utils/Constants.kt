package com.cortech.yahapp.core.utils

object Constants {
    object PdfAnalysis {
        const val ERROR_ANALYZING = "Error analyzing PDF: %s"
        const val ERROR_READING = "Could not read PDF file"
        const val PROMPT = """You are an experienced HR professional and career advisor. Please analyze the following resume and provide specific recommendations for improvement. Focus on:
            1. Key technologies or certifications they should acquire to enhance their profile
            2. Suggestions to better highlight their achievements
            3. Modern skills that would complement their experience
            4. CV format and presentation improvements
            
            Keep your response concise and actionable. Use a professional yet encouraging tone. Provide the response in English.
            
            Resume content:"""
        const val PROMPT_CV_HEADER = "\nResume:"
    }
    
    const val UNKNOWN_ERROR = "Unknown error"
    const val UNKNOWN_FILE = "Unknown File"
    const val LOGIN_REQUIRED = "Please log in first"
    
    object Features {
        object Register {
            const val DATE_FORMAT = "%d-%02d-%02d"
            const val CALENDAR_EMOJI = "📅"
            const val USER_TYPE_LABEL = "User type:"
        }
        
        object Home {
            const val JOB_PUBLICATION_SUCCESS = "Job position published successfully!"
            const val JOB_PUBLICATION_DETAILS = "The candidates can found this position when they search:"
            const val SEND_NEW_VACANCY = "Do you want to send a new vacancy?"
            const val FILE_ERROR = "Error to select file: %s"
            const val PERMISSION_DENIED = "Permission to read files was denied. Cannot analyze PDF without permission."
            const val WELCOME_MESSAGE = "Welcome to YahApp!, What can i help you with, %s?"
            const val FIND_COMMAND = "/find"
            const val JOB_COMMAND = "/job"
            const val FIND_EMPLOYEES_ERROR = "Failed to find employees"
            const val GENERATE_RESPONSE_ERROR = "Failed to generate response"
            const val JOB_DETAILS_REQUIRED = "Please provide job details after /job"
            const val POST_JOB_ERROR = "Failed to post job position"
            const val JOB_DETAILS_PARSE_ERROR = "Error parsing job details: %s"
            const val NO_VACANCIES_MESSAGE = "I'm sorry i can't found any vacancies that match with your criteria."
            const val SEARCH_POSITIONS_ERROR = "Failed to search for positions"
            const val ANALYZE_CV_ERROR = "Failed to analyze CV"
            const val CONTEXT_ERROR = "Context not available"
            const val UPLOAD_CV_ERROR = "Failed to upload CV"
            const val UNEXPECTED_ERROR = "Error unexpected: %s"
            const val NO_EMPLOYEES_FOUND = "No employees found matching your criteria."
            
            object JobListing {
                const val VACANCIES_FOUND = "Hey I found something good for you! 🎯"
                const val POSITION_FORMAT = "%d. %s - %s"
                const val DESCRIPTION_PREFIX = "   📝 "
                const val SKILLS_HEADER = "   🔧 Required skills :"
                const val SKILL_FORMAT = "      - %s (%d years)"
                const val INTERVIEW_TYPE = "   🎯 Interview type: %s"
                const val MORE_DETAILS = "Do you want to know more about this position?"
                const val ONLY_HUMAN_RESOURCES = "Only human resources can post job positions"
            }
            
            object CvUpload {
                const val SUCCESS_MESSAGE = "¡Done! Your summary is on the cloud, good luck!. 🎉"
                const val RECRUITER_ACCESS = "Now all the recruiters can see your CV. Is there anything else I can help you with?"
                const val HELP_OPTIONS_HEADER = "You can ask for:"
                const val HELP_CV = "- How to improve my CV"
                const val HELP_INTERVIEW = "- How to prepare for an interview"
                const val HELP_TRENDS = "- Working trends"
                const val HELP_OTHER = "Or whatever you want to ask me to found a job!"
            }
            
            object EmployeeListing {
                const val EMPLOYEES_FOUND = "Found %d matching employees:"
                const val EMPLOYEE_FORMAT = "%d. %s %s"
                const val BIRTH_FORMAT = "   Birth: %s"
                const val DESCRIPTION_FORMAT = "   Description: %s"
                const val SKILLS_FORMAT = "   Skills: %s"
            }
        }
    }
    const val GEMINI_MODEL_NAME = "gemini-2.0-flash"
    const val EMPTY_RESPONSE_ERROR = "Empty response from Gemini"
    
    object Api {
        const val USERS_ENDPOINT = "users/"
        const val USERS_QUERY_PARAM = "name"
    }
    
    object Ui {
        const val CLIP_LABEL_CHAT = "Chat message"
        const val SELECT_AVATAR_TEXT = "Select your avatar:"
        const val AVATAR_DESCRIPTION = "Avatar option"
        const val WIZELINE_LOGO_DESCRIPTION = "Wizeline Logo"
        const val PROFILE_AVATAR_DESCRIPTION = "Profile Avatar"
        const val PDF_ICON_DESCRIPTION = "PDF Icon"
        const val PDF_ACTION_QUESTION = "¿Qué deseas hacer con este CV?"
        const val SKILLS_LABEL = "Skills"
        const val LOCATION_LABEL = "Location: "
        const val SALARY_LABEL = "Salary: "
        const val APP_TITLE_YOU = "You"
        const val APP_TITLE_ARE = "are"
        const val APP_TITLE_HIRED = "Hired!"
        const val APP_TITLE_SHORT = "YaH"
        const val TYPING_CURSOR = "▋"
        const val PREVIEW_MESSAGE_ID = "preview"
        const val PREVIEW_MESSAGE = "Sure, I can help you with that."
    }
    
    object Colors {
        const val MATERIAL_RED = 0xFFE91E63
        const val LIGHT_GRAY = 0xFFB0BEC5
    }
    
    object Animation {
        const val DEFAULT_TYPING_SPEED = 10L
    }
}
