package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.ModificationTrackerService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.model.ModificationTracker;
import com.beehyv.nmsreporting.model.ModificationType;
import com.beehyv.nmsreporting.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Controller
@RequestMapping("/nms/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModificationTrackerService modificationTrackerService;

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers() {
        return userService.findAllActiveUsers();
    }

    @RequestMapping(value={"/list/{locationId}"})
    public @ResponseBody List<User> getUsersByLocation(@PathVariable("locationId") Integer locationId) {
        return userService.findAllActiveUsersByLocation(locationId);
    }

    @RequestMapping(value = {"/create-user"}, method = RequestMethod.POST)
    public void createNewUser(@RequestBody User user) {
        userService.createNewUser(user);
//        ModificationTracker modification = new ModificationTracker();
//        modification.setModificationDate(new Date(System.currentTimeMillis()));
//        modification.setModificationDescription("Account creation");
//        modification.setModificationType(ModificationType.CREATE.getModificationType());
//        modification.setModifiedUserId(user);
//        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
//        modificationTrackerService.saveModification(modification);
    }

    @RequestMapping(value = {"/update-user"}, method = RequestMethod.POST)
    public void updateExistingUser(@RequestBody String modificationDetails) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(modificationDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = mapper.convertValue(node.get("user"), User.class);
        userService.updateExistingUser(user);

//        String trackModification = mapper.convertValue(node.get("modification"), String.class);
//
//        ModificationTracker modification = new ModificationTracker();
//        modification.setModificationDate(new Date(System.currentTimeMillis()));
//        modification.setModificationType(ModificationType.UPDATE.getModificationType());
//        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
//        modification.setModifiedUserId(user);
//        modification.setModificationDescription(trackModification);
//        modificationTrackerService.saveModification(modification);
    }

    @RequestMapping(value = {"/delete-user"}, method = RequestMethod.POST)
    public void deleteExistingUser(@RequestBody User user) {
        userService.deleteExistingUser(user);
//        ModificationTracker modification = new ModificationTracker();
//        modification.setModificationDate(new Date(System.currentTimeMillis()));
//        modification.setModificationType(ModificationType.DELETE.getModificationType());
//        modification.setModificationDescription("Account deletion");
//        modification.setModifiedUserId(user);
//        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
