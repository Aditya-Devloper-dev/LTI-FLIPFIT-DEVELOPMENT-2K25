export interface GymCenter {
    centerId?: number;
    centerName: string;
    city: string;
    contactNumber: string;
    ownerId?: number;
    owner?: any; // Using any to avoid creating GymOwner model for now, or create it if preferred
    isActive?: boolean;
    isApproved?: boolean;
}
