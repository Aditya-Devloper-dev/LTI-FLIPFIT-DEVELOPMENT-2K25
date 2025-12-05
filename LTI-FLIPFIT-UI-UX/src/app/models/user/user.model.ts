export interface UserRegistration {
    email: string;
    password: string;
    fullName: string;
    phoneNumber: string;
    role: string;
    businessName?: string;
    gstNumber?: string;
    panNumber?: string;
}

export interface UserLogin {
    email?: string;
    password?: string;
}

export interface LoginResponse {
    userId: number;
    email: string;
    roleId: number;
    roleName: string;
    loginStatus: string;
    customerId?: number;
    ownerId?: number;
    adminId?: number;
}
