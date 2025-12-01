package com.lti.flipfit.utils;

/**
 * Author :
 * Version : 1.0
 * Description : Helper class for creating role-specific user entities (Admin, Customer, Owner).
 */

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

@Component
public class UserRoleHelper {

    @Autowired
    private FlipFitGymAdminRepository adminRepo;

    @Autowired
    private FlipFitGymCustomerRepository customerRepo;

    @Autowired
    private FlipFitGymOwnerRepository ownerRepo;

    /**
     * @methodname - createUserRoleEntity
     * @description - Creates and saves the specific role entity (GymAdmin,
     *              GymCustomer, GymOwner) based on the user's role.
     * @param - user The user for whom the role entity is to be created.
     */
    public void createUserRoleEntity(User user) {
        if (RoleType.ADMIN.name().equals(user.getRole().getRoleId())) {
            GymAdmin admin = new GymAdmin();
            admin.setUser(user);
            adminRepo.save(admin);
        } else if (RoleType.CUSTOMER.name().equals(user.getRole().getRoleId())) {
            GymCustomer customer = new GymCustomer();
            customer.setUser(user);
            customerRepo.save(customer);
        } else if (RoleType.OWNER.name().equals(user.getRole().getRoleId())) {
            GymOwner owner = new GymOwner();
            owner.setUser(user);
            ownerRepo.save(owner);
        }
    }
}
