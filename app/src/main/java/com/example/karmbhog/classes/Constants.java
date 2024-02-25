package com.example.karmbhog.classes;

public class Constants {
    public static final String KEY_COMPANY_PREFERENCE_NAME = "LoginCompany";
    public static final String KEY_KITCHEN_MANAGER_PREFERENCE_NAME = "LoginKitchenMngr";
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_IS_COMPANY_LOGGED_IN = "isCompanyLoggedIn";
    public static final String KEY_IS_KITCHEN_MNGR_LOGGED_IN = "isKitchenMngrLoggedIn";

    //root collections -----------------------------------------------------------------------------
    public static final String KEY_COMPANY_COL = "Company";
    public static final String KEY_KITCHEN_MANAGER_COL = "KitchenManager";
    public static final String KEY_DONORS_COL = "Donors";

    //sub collections
    public static final String KEY_COMPANY_WORK_COL = "CompanyWork";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEES_COL = "HungerEmployees";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEES_REPORT_COL = "EmployeeReport";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEES_FEEDBACK_COL = "EmployeeFeedback";
    public static final String KEY_KITCHEN_MANAGER_EVENTS_COL = "Events";


    //company documents' fields --------------------------------------------------------------------
    public static final String KEY_COMPANY_EMAIL = "companyEmail";
    public static final String KEY_COMPANY_NAME = "companyName";
    public static final String KEY_COMPANY_PWD = "companyPassword";
    public static final String KEY_COMPANY_CITY = "companyCity";


    //company sub collection "CompanyWork" documents' fields
    public static final String KEY_COMPANY_WORK_NAME = "workName";
    public static final String KEY_COMPANY_WORK_NEEDED_WORKERS = "workersNeeded";
    public static final String KEY_COMPANY_WORK_AVAILABLE_WORKERS = "workersAvailable";


    //kitchen manager documents' fields ------------------------------------------------------------
    public static final String KEY_KITCHEN_MANAGER_EMAIL = "managerEmail";
    public static final String KEY_KITCHEN_MANAGER_CITY = "managerCity";
    public static final String KEY_KITCHEN_MANAGER_NAME = "managerName";
    public static final String KEY_KITCHEN_MANAGER_PWD = "managerPassword";


    //kitchen manager sub collection "HungerEmployees" documents' fields
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEE_USER_NAME = "userName";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEE_NAME = "employeeName";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEE_AGE = "employeeAge";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEE_MOB_NO = "employeeMobNo";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEE_ADDRESS = "employeeAddress";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEE_WORK_NAME = "employeeWorkName";
    public static final String KEY_KITCHEN_MANAGER_EMPLOYEE_ASSIGNED_COMPANY = "employeeAssignedCompany";
}
