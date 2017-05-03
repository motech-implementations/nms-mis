package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.business.ModificationTrackerService;
import com.beehyv.nmsreporting.business.RoleService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dto.UserDto;
import com.beehyv.nmsreporting.model.*;
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
import java.util.HashMap;
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
    private RoleService roleService;

    @Autowired
    private LocationService locationService;

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

    @RequestMapping(value={"/roles"})
    public @ResponseBody List<Role> getRoles() {
        return roleService.getRoles();
    }

    @RequestMapping(value={"/currentUser"})
    public @ResponseBody User getCurrentUser() {
        return userService.getCurrentUser();
    }

    //To be changed
    @RequestMapping(value={"/tableList/{locationId}"})
    public @ResponseBody List<UserDto> getTableList(@PathVariable("locationId") Integer locationId) {
        List<UserDto> tabDto = new ArrayList<>();
        List<Location> tabLocation;
        List<User> tabUsers = userService.findAllActiveUsersByLocation(locationId);
        String[] levels = {"National", "State", "District", "Block"};
        for(User user: tabUsers){
            UserDto user1 = new UserDto();
            user1.setId(user.getUserId());
            user1.setName(user.getFullName());
            user1.setUsername(user.getUsername());
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
            tabDto.add(user1);

        }
        return tabDto;
    }

//    @RequestMapping(value={"/accessLevel/{userId}"})
//    public @ResponseBody Integer getUserAccessLevel(@PathVariable("userId") Integer userId) {
//        User usr = userService.findUserByUserId(userId);
//
//        return x;
//    }

    @RequestMapping(value={"/dto/{userId}"})
    public @ResponseBody UserDto getUserDto(@PathVariable("userId") Integer userId) {
        User user = userService.findUserByUserId(userId);
        List<Location> tabLocation;
        String[] levels = {"National", "State", "District", "Block"};
        UserDto user1 = new UserDto();
        user1.setId(user.getUserId());
        user1.setName(user.getFullName());
        user1.setUsername(user.getUsername());
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
        return user1;
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

    private String getAttr(String str){
        return str.split("=")[1];
    }

    @RequestMapping(value = {"/create-new"}, method = RequestMethod.POST)
    public void userDto(@RequestBody String user1) {

        User user = new User();
        System.out.println(user1);
        String[] attrs = user1.split("&");

        HashMap<String, String> userMap = new HashMap<>();
        for(String attr : attrs){
            String[] arr = attr.split("=");
            userMap.put(arr[0], arr[1]);
        }

        user.setUsername(userMap.get("username"));
        user.setFullName(userMap.get("name").replace("+", " "));
        user.setPhoneNumber(userMap.get("phoneNumber"));
        user.setEmailId(userMap.get("email").replace("%40", "@"));
        user.setAccountStatus("ACTIVE");
        user.setCreatedByUser(userService.getCurrentUser());
        user.setCreationDate(new java.util.Date());
        user.setRoleId(roleService.findRoleByRoleId(Integer.parseInt(userMap.get("accessType"))));
        user.setPassword(userMap.get("phoneNumber"));
        String locId = "";
        if(userMap.get("accessLevel").equalsIgnoreCase("block")) {
            locId = userMap.get("block");
        } else if(userMap.get("accessLevel").equalsIgnoreCase("district")) {
            locId = userMap.get("district");
        } else if(userMap.get("accessLevel").equalsIgnoreCase("state")) {
            locId = userMap.get("state");
        } else {
            locId = "1";
        }
        user.setLocationId(locationService.findLocationById(Integer.parseInt(locId)));

        userService.createNewUser(user);

        /*
        user.setUsername(user1.getUsername());
        user.setFullName(user1.getName());
        user.setPhoneNumber(user1.getPhoneNumber());
        user.setEmailId(user1.getEmail());
        user.setAccountStatus("ACTIVE");
        user.setCreatedByUser(userService.getCurrentUser());
        user.setCreationDate(new java.util.Date());
        user.setRoleId(roleService.findRoleByRoleDesc(user1.getAccessType()));
        user.setPassword(user1.getPhoneNumber());
        String locName = "";
        if(user1.getAccessLevel().equalsIgnoreCase("block")) {
            locName = user1.getBlock();
        } else if(user1.getAccessLevel().equalsIgnoreCase("district")) {
            locName = user1.getDistrict();
        } else if(user1.getAccessLevel().equalsIgnoreCase("state")) {
            locName = user1.getState();
        } else {
            locName = "national";
        }
        user.setLocationId(locationService.findLocationByName(locName));

        userService.createNewUser(user);*/
    }

    @RequestMapping(value = {"/update-user"}, method = RequestMethod.POST)
    public void updateExistingUser(@RequestBody String userDtoString) {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = null;
//        try {
//            node = mapper.readTree(userDtoString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        UserDto userDto = mapper.convertValue(node.get("user"), UserDto.class);

        System.out.println("asdfsadfsa     " + userDtoString);

        String[] attrs = userDtoString.split("&");

        HashMap<String, String> userMap = new HashMap<>();
        for(String attr : attrs){
            String[] arr = attr.split("=");
            userMap.put(arr[0], arr[1]);
        }

        User user = userService.findUserByUsername(userMap.get("username"));

        user.setFullName(userMap.get("name").replace("+", " "));
        user.setPhoneNumber(userMap.get("phoneNumber"));
        user.setEmailId(userMap.get("email").replace("%40", "@"));
        user.setAccountStatus("ACTIVE");
        user.setCreatedByUser(userService.getCurrentUser());
        user.setCreationDate(new java.util.Date());
        user.setRoleId(roleService.findRoleByRoleId(Integer.parseInt(userMap.get("accessType"))));
        user.setPassword(userMap.get("phoneNumber"));
        String locId = "";
        if(userMap.get("accessLevel").equalsIgnoreCase("block")) {
            locId = userMap.get("block");
        } else if(userMap.get("accessLevel").equalsIgnoreCase("district")) {
            locId = userMap.get("district");
        } else if(userMap.get("accessLevel").equalsIgnoreCase("state")) {
            locId = userMap.get("state");
        } else {
            locId = "1";
        }
        user.setLocationId(locationService.findLocationById(Integer.parseInt(locId)));

        userService.updateExistingUser(user);


//        userService.updateExistingUser(user);

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
