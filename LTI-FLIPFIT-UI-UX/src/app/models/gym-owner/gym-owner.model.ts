
export interface GymOwner {
    ownerId: number;
    user: any; // Using any for User model for now
    businessName: string;
    gstNumber: string;
    panNumber: string;
    isApproved: boolean;
}
