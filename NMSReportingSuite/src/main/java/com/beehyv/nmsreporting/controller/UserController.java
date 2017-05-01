package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.ModificationTrackerService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dto.UserDto;
import com.beehyv.nmsreporting.model.Location;
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
import java.util.ArrayList;
import java.util.Collections;
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

    @RequestMapping(value={"/currentUser"})
    public @ResponseBody User getCurrentUser() {
        return userService.getCurrentUser();
    }

    //To be changed
    @RequestMapping(value={"/tableList/{locationId}"})
    public @ResponseBody List<User> getTableList(@PathVariable("locationId") Integer locationId) {
        List<UserDto> tabDto = new ArrayList<>();
        List<Location> tabLocation;
        List<User> tabUsers = userService.findAllActiveUsersByLocation(locationId);
        String[] levels = {"National", "State", "District", "Block"};
        for(User user: tabUsers){
            UserDto user1 = new UserDto();
            user1.setId(user.getUserId());
            user1.setName(user.getFullName());
            user1.setEmail(user.getEmailId());
            user1.setPhoneNumber(user.getPhoneNumber());
            int x = 0;
            tabLocation = new ArrayList<>();
            Location loc = user.getLocationId();
            while(loc.getReferenceId() != null){
                tabLocation.add(loc);
                loc = loc.getReferenceId();
                x++;
            }
            user1.setAccessLevel(levels[x]);
            Collections.reverse(tabLocation);
            if(x>=1){
                user1.setState(tabLocation.get(0).getLocation());
            }
            if(x>=2){
                user1.setDistrict(tabLocation.get(1).getLocation());
            }
            if(x>=3){
                user1.setBlock(tabLocation.get(2).getLocation());
            }
            user1.setAccessType(user.getRoleId().getRoleDescription());
            user1.setCreatedBy(true);
//            user1.setUsername(user.getUsername());
            tabDto.add(user1);

        }
        return tabUsers;
    }

//    @RequestMapping(value={"/accessLevel/{userId}"})
//    public @ResponseBody Integer getUserAccessLevel(@PathVariable("userId") Integer userId) {
//        User usr = userService.findUserByUserId(userId);
//
//        return x;
//    }

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
