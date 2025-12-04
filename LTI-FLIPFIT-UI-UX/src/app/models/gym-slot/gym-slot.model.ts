export interface GymSlot {
    slotId?: number;
    centerId: number;
    date: string;
    startTime: string;
    endTime: string;
    capacity: number;
    availableSeats: number;
    price: number;
    activity?: string;
    isActive?: boolean;
    isApproved?: boolean;
}
