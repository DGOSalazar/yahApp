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
            const val FILE_ERROR = "Error to select file: %s"
            const val WELCOME_MESSAGE = "Welcome to YahApp!, What can i help you with, %s?"
            const val FIND_COMMAND = "/find"
            const val GENERATE_RESPONSE_ERROR = "Failed to generate response"
            const val NO_VACANCIES_MESSAGE = "I'm sorry i can't found any vacancies that match with your criteria."
            const val SEARCH_POSITIONS_ERROR = "Failed to search for positions"
            const val ANALYZE_CV_ERROR = "Failed to analyze CV"
            const val CONTEXT_ERROR = "Context not available"
            const val UPLOAD_CV_ERROR = "I'm so sorry, i can't upload your summary, please try again later."
            const val UNEXPECTED_ERROR = "Error unexpected: %s"
            
            object CvUpload {
                const val SUCCESS_MESSAGE = "¡Done! Your summary is on the cloud, good luck!. 🎉"
                const val RECRUITER_ACCESS = "Now all the recruiters can see your CV. Is there anything else I can help you with?"
                const val HELP_OPTIONS_HEADER = "You can ask for:"
                const val HELP_CV = "- How to improve my CV"
                const val HELP_INTERVIEW = "- How to prepare for an interview"
                const val HELP_TRENDS = "- Working trends"
                const val HELP_OTHER = "Or whatever you want to ask me to found a job!"
            }
        }
    }
    const val GEMINI_MODEL_NAME = "gemini-2.0-flash"
    const val EMPTY_RESPONSE_ERROR = "Empty response from Gemini"
    
    object GeminiPrompt {
        const val SYSTEM_ROLE = "You are a specialized HR and professional development assistant. Your goal is to help two types of users:"
        
        object WorkerSupport {
            const val HEADER = "1. Workers and candidates:"
            const val IMPROVE_CV = "- Help them improve their CVs and professional profiles"
            const val SHOW_JOBS = "- Show them relevant job positions based on their experience"
            const val CAREER_GUIDANCE = "- Guide them in their professional development"
            const val APPLICATION_HELP = "- Assist them in the job application process"
            const val SAVE_CV = "- Allow them to save their CVs in the database"
        }
        
        object HrSupport {
            const val HEADER = "2. HR Personnel:"
            const val MANAGE_JOBS = "- Help them publish and manage job positions"
            const val FIND_CANDIDATES = "- Find candidates that match their positions"
            const val REVIEW_PROFILES = "- Review and evaluate candidate profiles"
            const val OPTIMIZE_DESCRIPTIONS = "- Optimize job descriptions"
        }
        
        object Guidelines {
            const val HEADER = "Guidelines:"
            const val BE_PROFESSIONAL = "- Be professional and empathetic"
            const val PRACTICAL_ADVICE = "- Give practical and actionable advice"
            const val POSITIVE_TONE = "- Maintain a positive and constructive tone"
            const val CONFIDENTIALITY = "- Respect information confidentiality"
            const val QUALITY_FOCUS = "- Prioritize quality over quantity in responses"
        }
    }
    
    object Api {
        const val USERS_ENDPOINT = "users/"
        const val USERS_QUERY_PARAM = "name"
        const val LOGO_TYPE = "logo_type"
        const val UPLOAD_SUMMARY = "upload"
        const val RECOMMEND_SUMMARY = "recommended"
    }
}
