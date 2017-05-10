package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.business.ModificationTrackerService;
import com.beehyv.nmsreporting.business.RoleService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dto.UserDto;
import com.beehyv.nmsreporting.enums.AccountStatus;
import com.beehyv.nmsreporting.model.Role;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

//    @RequestMapping(value={"/list/{locationId}"})
//    public @ResponseBody List<User> getUsersByLocation(@PathVariable("locationId") Integer locationId) {
//        return userService.findAllActiveUsersByLocation(locationId);
//    }

    @RequestMapping(value={"/myUserList"})
    public @ResponseBody List<User> getMyUsers() {
        return userService.findMyUsers(userService.getCurrentUser());
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
    @RequestMapping(value={"/tableList"})
    public @ResponseBody List<UserDto> getTableList() {
        List<UserDto> tabDto = new ArrayList<>();
        List<User> tabUsers = userService.findMyUsers(userService.getCurrentUser());
        String[] levels = {"National", "State", "District", "Block"};
        for(User user: tabUsers){
            UserDto user1 = new UserDto();
            user1.setId(user.getUserId());
            user1.setName(user.getFullName());
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmailId());
            user1.setPhoneNumber(user.getPhoneNumber());
            user1.setAccessLevel(user.getAccessLevel());
            try {
                user1.setState(user.getStateId().getStateName());
            } catch(NullPointerException e){
                user1.setState("");
            }
            try {
                user1.setDistrict(user.getDistrictId().getDistrictName());
            } catch(NullPointerException e){
                user1.setDistrict("");
            }
            try {
                user1.setBlock(user.getBlockId().getBlockName());
            } catch(NullPointerException e){
                user1.setBlock("");
            }
            user1.setAccessType(user.getRoleId().getRoleDescription());
            user1.setCreatedBy(true);
            tabDto.add(user1);

        }
        return tabDto;
    }

    @RequestMapping(value={"/user/{userId}"})
    public @ResponseBody User getUserById(@PathVariable("userId") Integer userId) {
        return userService.findUserByUserId(userId);
    }

    @RequestMapping(value={"/dto/{userId}"})
    public @ResponseBody UserDto getUserDto(@PathVariable("userId") Integer userId) {
        User user = userService.findUserByUserId(userId);
        String[] levels = {"National", "State", "District", "Block"};
        UserDto user1 = new UserDto();
        user1.setId(user.getUserId());
        user1.setName(user.getFullName());
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmailId());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setAccessLevel(user.getAccessLevel());
        try {
            user1.setState(user.getStateId().getStateName());
        } catch(NullPointerException e){
            user1.setState("");
        }
        try {
            user1.setDistrict(user.getDistrictId().getDistrictName());
        } catch(NullPointerException e){
            user1.setDistrict("");
        }
        try {
            user1.setBlock(user.getBlockId().getBlockName());
        } catch(NullPointerException e){
            user1.setBlock("");
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

    @RequestMapping(value={"/userNameAvailable/{username}"})
    public @ResponseBody Boolean userNameAvailable(@PathVariable("username") String username) {
        System.out.println(username);
        return userService.findUserByUsername(username) != null;
    }

    @RequestMapping(value = {"/createFromDto"}, method = RequestMethod.POST)
    public String createFromDto(@RequestBody String userDto) {
        userDto = userDto.replace("+", " ").replace("%40", "@").replace("string%3A", "");
        String[] attrs = userDto.split("&");

        HashMap<String, String> userMap = new HashMap<>();
        for(String attr : attrs){
            String[] arr = attr.split("=");
            userMap.put(arr[0], arr[1]);
        }

        if(userService.findUserByUsername(userMap.get("username")) != null){
            System.out.println("user exists");
            return "redirect:http://127.0.0.1:4040/index";
        }

        User newUser = new User();
        newUser.setFullName(userMap.get("name"));
        newUser.setUsername(userMap.get("username"));
        newUser.setPhoneNumber(userMap.get("phoneNumber"));
        newUser.setEmailId(userMap.get("email"));
        newUser.setAccessLevel(userMap.get("accessLevel").toUpperCase());
        newUser.setPassword(userMap.get("username"));
        try{
            newUser.setStateId(locationService.findStateById(Integer.parseInt(userMap.get("state"))));
        }catch (Exception e){
            newUser.setStateId(null);
        }

        try{
            newUser.setDistrictId(locationService.findDistrictById(Integer.parseInt(userMap.get("district"))));
        }catch (Exception e){
            newUser.setDistrictId(null);
        }

        try{
            newUser.setBlockId(locationService.findBlockById(Integer.parseInt(userMap.get("block"))));
        }catch (Exception e){
            newUser.setDistrictId(null);
        }
        newUser.setRoleId(roleService.findRoleByRoleId(Integer.parseInt(userMap.get("accessType"))));
        newUser.setAccountStatus(AccountStatus.PENDING.getAccountStatus());
        newUser.setCreatedByUser(userService.getCurrentUser());
        newUser.setCreationDate(new java.util.Date());

        userService.createNewUser(newUser);

        return "redirect:http://127.0.0.1:4040/index";
    }

    @RequestMapping(value = {"/update-user"}, method = RequestMethod.POST)
    public String updateExistingUser(@RequestBody String userDto) {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = null;
//        try {
//            node = mapper.readTree(userDtoString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        UserDto userDto = mapper.convertValue(node.get("user"), UserDto.class);
        userDto = userDto.replace("+", " ").replace("%40", "@").replace("string%3A", "");
        String[] attrs = userDto.split("&");
        HashMap<String, String> userMap = new HashMap<>();
        for(String attr : attrs){
            String[] arr = attr.split("=");
            userMap.put(arr[0], arr[1]);
        }
//
//        for(String key : userMap.keySet()){
//            System.out.println(key + " : " + userMap.get(key));
//        }


        User user = userService.findUserByUserId(Integer.parseInt(userMap.get("id")));

        user.setFullName(userMap.get("name"));
        user.setPhoneNumber(userMap.get("phoneNumber"));
        user.setEmailId(userMap.get("email"));
        user.setAccessLevel(userMap.get("accessLevel"));
        user.setRoleId(roleService.findRoleByRoleId(Integer.parseInt(userMap.get("accessType"))));

        try{
            user.setStateId(locationService.findStateById(Integer.parseInt(userMap.get("state"))));
        }catch (Exception e){
            user.setStateId(null);
        }

        try{
            user.setDistrictId(locationService.findDistrictById(Integer.parseInt(userMap.get("district"))));
        }catch (Exception e){
            user.setDistrictId(null);
        }

        try{
            user.setBlockId(locationService.findBlockById(Integer.parseInt(userMap.get("block"))));
        }catch (Exception e){
            user.setDistrictId(null);
        }
        userService.updateExistingUser(user);

        for(String key : userMap.keySet()){
            System.out.println(key + " : " + userMap.get(key));
        }

        return "redirect:http://127.0.0.1:4040/index";

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
/*
    @CrossOrigin
    @RequestMapping(value = {"/update-user-2"}, method = RequestMethod.POST)
    public String updateExistingUser2(@RequestBody UserDto userDto) {
        System.out.println(userDto.getName() + "**********************");
        User user = userService.findUserByUserId(userDto.getId());
        user.setFullName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmailId(userDto.getEmail());
        user.setAccessLevel(userDto.getAccessLevel());
        user.setRoleId(roleService.findRoleByRoleId(Integer.parseInt(userDto.getAccessType())));

        try{
            user.setStateId(locationService.findStateById(Integer.parseInt(userDto.getState())));
        }catch (Exception e){
            user.setStateId(null);
        }

        try{
            user.setDistrictId(locationService.findDistrictById(Integer.parseInt(userDto.getDistrict())));
        }catch (Exception e){
            user.setDistrictId(null);
        }

        try{
            user.setBlockId(locationService.findBlockById(Integer.parseInt(userDto.getBlock())));
        }catch (Exception e){
            user.setDistrictId(null);
        }

        userService.updateExistingUser(user);

        return "redirect:/home";
    }*/

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
