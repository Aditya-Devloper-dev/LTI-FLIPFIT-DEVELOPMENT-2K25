package com.lti.flipfit.utils;

import com.lti.flipfit.constants.RoleType;
import com.lti.flipfit.entity.GymAdmin;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.User;
import com.lti.flipfit.repository.FlipFitGymAdminRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to handle login response construction and role-specific ID
 * retrieval.
 */
@Component
public class UserLoginHelper {

    @Autowired
    private FlipFitGymCustomerRepository customerRepo;

    @Autowired
    private FlipFitGymOwnerRepository ownerRepo;

    @Autowired
    private FlipFitGymAdminRepository adminRepo;

    /**
     * Builds the login response map containing user details and role-specific ID.
     *
     * @param user The authenticated user entity.
     * @return A map containing user details and login status.
     */
    public Map<String, Object> buildLoginResponse(User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("email", user.getEmail());
        response.put("roleId", user.getRole().getRoleId());
        response.put("roleName", user.getRole().getRoleName());
        response.put("loginStatus", "SUCCESS");

        String roleName = user.getRole().getRoleName();
        if (RoleType.CUSTOMER.name().equalsIgnoreCase(roleName)) {
            GymCustomer customer = customerRepo.findByUser_UserId(user.getUserId());
            if (customer != null) {
                response.put("customerId", customer.getCustomerId());
            }
        } else if (RoleType.OWNER.name().equalsIgnoreCase(roleName)
                || RoleType.OWNER.name().equalsIgnoreCase(roleName)) {
            GymOwner owner = ownerRepo.findByUser_UserId(user.getUserId());
            if (owner != null) {
                response.put("ownerId", owner.getOwnerId());
            }
        } else if (RoleType.ADMIN.name().equalsIgnoreCase(roleName)) {
            GymAdmin admin = adminRepo.findByUser_UserId(user.getUserId());
            if (admin != null) {
                response.put("adminId", admin.getAdminId());
            }
        }

        return response;
    }
}
