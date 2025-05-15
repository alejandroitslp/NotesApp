package com.peraz.notesapp.presentation.home

data class Notes(val id: Int, val title: String, val desc: String)


var _notesList = mutableListOf<Notes>(
    Notes(
        1,
        "Meeting Minutes",
        "Summary of the discussions and decisions made during the project kickoff meeting on October 26, 2023."
    ),
    Notes(
        2,
        "Bug Report",
        "Detailed description of a critical bug found in the user authentication module, affecting users with special characters in their passwords."
    ),
    Notes(
        3,
        "Feature Request",
        "Suggestion for adding a dark mode option to the mobile app, with user feedback and potential benefits outlined."
    ),
//    Notes(
//        4,
//        "User Guide Update",
//        "Instructions for using the new multi-select feature in the data grid, including screenshots and step-by-step directions."
//    ),
//    Notes(
//        5,
//        "Code Review",
//        "Comments on the code changes submitted by developer John Doe, highlighting areas for improvement and best practices."
//    ),
//    Notes(
//        6,
//        "Deployment Notes",
//        "Steps taken to deploy version 2.1.0 of the application, including database migration instructions and server restart commands."
//    ),
//    Notes(
//        7,
//        "Security Audit Findings",
//        "Report of the vulnerabilities discovered during the recent security audit, with severity levels and suggested fixes."
//    ),
//    Notes(
//        8,
//        "Performance Test Results",
//        "Metrics gathered from the latest load test, including average response time, peak throughput, and error rate."
//    ),
//    Notes(
//        9,
//        "Design Decision",
//        "Rationale behind the choice of using a card-based layout for the new dashboard, referencing usability research and design principles."
//    ),
//    Notes(
//        10,
//        "API Documentation",
//        "Explanation of the new /users/create endpoint, including request parameters, response format, and error codes."
//    ),
//    Notes(
//        11,
//        "Database Schema Changes",
//        "Details of the modifications made to the users table, such as adding a new 'status' column and constraints."
//    ),
//    Notes(
//        12,
//        "Project Timeline",
//        "Revised schedule for completing the project, highlighting key milestones and dependencies."
//    ),
//    Notes(
//        13,
//        "Risk Assessment",
//        "Analysis of potential risks to the project, with mitigation strategies for each identified risk."
//    ),
//    Notes(
//        14,
//        "Team Meeting Agenda",
//        "List of topics to be covered in the next team meeting, along with the desired outcomes for each item."
//    ),
//    Notes(
//        15,
//        "Client Feedback",
//        "Summary of the feedback received from the client after the demonstration of the beta version."
//    ),
//    Notes(
//        16,
//        "Action Items",
//        "List of tasks assigned to team members, including deadlines and priorities."
//    ),
//    Notes(
//        17,
//        "Research Findings",
//        "Results of a market research study, including statistics, key insights, and recommendations."
//    ),
//    Notes(
//        18,
//        "Training Materials",
//        "Documentation for training new employees on how to use the company's internal CRM system."
//    ),
//    Notes(
//        19,
//        "Technical Debt",
//        "List of code quality issues that need to be addressed in the future, with proposed refactoring strategies."
//    ),
//    Notes(
//        20,
//        "User Interview Notes",
//        "Transcript of an interview with a user, capturing their feedback, behaviors, and pain points."
//    ),
//    Notes(
//        21,
//        "Accessibility Considerations",
//        "Points to keep in mind when designing and developing to ensure compliance with accessibility standards."
//    ),
//    Notes(
//        22,
//        "Content Style Guide",
//        "Rules and guidelines for writing content for the application, focusing on tone, voice, and consistency."
//    ),
//    Notes(
//        23,
//        "Legal Compliance",
//        "Checklist of actions taken to ensure the project meets all relevant legal requirements."
//    ),
//    Notes(
//        24,
//        "Marketing Strategy",
//        "Plan for how to promote the new product, including target audience, key messages, and channels."
//    ),
//    Notes(
//        25,
//        "Resource Allocation",
//        "Details of how resources (budget, time, personnel) are being distributed across different project tasks."
//    ),
//    Notes(
//        26,
//        "Sprint Review",
//        "Feedback from the development team and stakeholders at the end of a sprint, focusing on what worked well and what needs improvement."
//    ),
//    Notes(
//        27,
//        "System Architecture",
//        "High level overview of the software system, including its components and how they interact"
//    ),
//    Notes(28,"Dependencies Update", "New version of the framework was integrated into the project."),
//    Notes(
//        29,
//        "Acceptance Criteria",
//        "List of tests that must pass for a user story to be considered as completed."
//    ),
//    Notes(
//        30,
//        "Deployment rollback",
//        "Process to revert the changes made during the most recent release."
//    ),
//    Notes(31,"Database Backup", "Process to create a backup of the system database."),
//    Notes(
//        32,
//        "Configuration setup",
//        "Setup process of the project, to be able to start working on it."
//    ),
//    Notes(
//        33,
//        "Refactoring task",
//        "List of things that must be changed on a component to improve its quality."
//    ),
//    Notes(34,"Documentation update", "Tasks done to update a given document."),
//    Notes(
//        35,
//        "User testing notes",
//        "Description of the user behaviour when interacting with the new app prototype."
//    ),
//    Notes(36,"Design choices", "Motives behind the current design choices."),
//    Notes(37,"Known bugs", "List of bugs known to exist in the system."),
//    Notes(38,"Release Notes", "List of changes done in the last system release."),
//    Notes(39,"Data model", "Description of the new data model."),
//    Notes(40,"API usage", "How to use the exposed endpoints."),
//    Notes(41,"Future tasks", "List of tasks that need to be done in the future."),
//    Notes(42,"Meeting Follow up", "Actions to be taken after the last meeting."),
//    Notes(43,"Out of scope", "List of things that are outside of the project scope."),
)

