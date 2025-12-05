import { GymOwner } from '../gym-owner/gym-owner.model';

export interface GymCenter {
    centerId?: number;
    centerName: string;
    city: string;
    contactNumber: string;
    ownerId?: number;
    owner?: GymOwner;
    isActive?: boolean;
    isApproved?: boolean;
}
