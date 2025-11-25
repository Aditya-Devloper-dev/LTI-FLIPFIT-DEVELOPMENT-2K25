package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.bookings.InvalidBookingException;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class FlipFitGymBookingServiceTest {

    @Mock
    private FlipFitGymBookingRepository bookingRepo;

    @Mock
    private FlipFitGymCustomerRepository customerRepo;

    @Mock
    private FlipFitGymSlotRepository slotRepo;

    @Mock
    private FlipFitGymCenterRepository centerRepo;

    @InjectMocks
    private FlipFitGymBookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookSlot_whenSlotDoesNotBelongToCenter_shouldThrowException() {
        // Arrange
        Long customerId = 1L;
        Long slotId = 100L;
        Long centerId = 10L;
        Long otherCenterId = 20L;

        GymCustomer customer = new GymCustomer();
        customer.setCustomerId(customerId);

        GymCenter center = new GymCenter();
        center.setCenterId(centerId);

        GymCenter otherCenter = new GymCenter();
        otherCenter.setCenterId(otherCenterId);

        GymSlot slot = new GymSlot();
        slot.setSlotId(slotId);
        slot.setCenter(otherCenter); // Slot belongs to a different center

        GymBooking booking = new GymBooking();
        booking.setCustomer(customer);
        booking.setSlot(slot);
        booking.setCenter(center);

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(slotRepo.findById(slotId)).thenReturn(Optional.of(slot));
        when(centerRepo.findById(centerId)).thenReturn(Optional.of(center));

        // Act & Assert
        Exception exception = assertThrows(InvalidBookingException.class, () -> {
            bookingService.bookSlot(booking);
        });

        assertTrue(exception.getMessage().contains("does not belong to center"));
    }
}
