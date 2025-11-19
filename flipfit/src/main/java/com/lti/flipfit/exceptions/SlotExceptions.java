package com.lti.flipfit.exceptions;

import org.springframework.http.HttpStatus;

public class SlotExceptions {
    /*
     * @Exception: SlotAlreadyExistsException
     * @Description: Thrown when a slot overlaps with an existing slot in the center.
     * @ErrorCode: SLOT_ALREADY_EXISTS
     * @HttpStatus: 409 CONFLICT
     */
    public static class SlotAlreadyExistsException extends BaseException {
        public SlotAlreadyExistsException(String message) {
            super(message, "SLOT_ALREADY_EXISTS", HttpStatus.CONFLICT);
        }
    }

    /*
     * @Exception: InvalidSlotTimeException
     * @Description: Thrown when endTime <= startTime during slot creation.
     * @ErrorCode: INVALID_SLOT_TIME
     * @HttpStatus: 400 BAD REQUEST
     */
    public static class InvalidSlotTimeException extends BaseException {
        public InvalidSlotTimeException(String message) {
            super(message, "INVALID_SLOT_TIME", HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * @Exception: CapacityInvalidException
     * @Description: Thrown when slot capacity is <= 0 or invalid.
     * @ErrorCode: INVALID_CAPACITY
     * @HttpStatus: 400 BAD REQUEST
     */
    public static class CapacityInvalidException extends BaseException {
        public CapacityInvalidException(String message) {
            super(message, "INVALID_CAPACITY", HttpStatus.BAD_REQUEST);
        }
    }
}
