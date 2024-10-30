package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.*;
import com.beehyv.nmsreporting.business.impl.AggregateKilkariReportsServiceImpl;
import com.beehyv.nmsreporting.business.impl.CertificateServiceImpl;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.enums.ModificationType;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.*;
import com.beehyv.nmsreporting.utils.ServiceFunctions;
import com.google.gson.Gson;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import static com.beehyv.nmsreporting.utils.Global.retrieveDocuments;
import static com.beehyv.nmsreporting.utils.ServiceFunctions.StReplace;
import static com.beehyv.nmsreporting.utils.ServiceFunctions.dateAdder;
import org.apache.pdfbox.pdmodel.PDDocument;
/**
 * Created by beehyv on 15/3/17.
 */
@Controller
@RequestMapping("/nms/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AggregateReportsService aggregateReportsService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ModificationTrackerService modificationTrackerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private AggregateCumulativeBeneficiaryDao aggregateCumulativeBeneficiaryDao;

    @Autowired
    private MAPerformanceService maPerformanceService;


    @Autowired
    private AggregateKilkariReportsService aggregateKilkariReportsService;

    @Autowired
    private BreadCrumbService breadCrumbService;

    @Autowired
    private StateServiceDao stateServiceDao;

    @Autowired
    private AshaEachBlockServiceDao ashaEachBlockServiceDao;

    @Autowired
    private HealthSubFacilityDao healthSubFacilityDao;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    BulkCertificateAuditDao bulkCertificateAuditDao;

    @Autowired
    private EtlNotificationService etlNotificationService;

    @Autowired
    private DownloadReportActivityService downloadReportActivityService;

    private final CacheManager cacheManager = CacheManager.create();
    private final Cache etlNotificationCache = cacheManager.getCache("etlNotificationCache");

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private ServiceFunctions serviceFunctions = new ServiceFunctions();
    private final String USER_AGENT = "Mozilla/5.0";
    private final Date bigBang = new Date(0);
    private final String documents = retrieveDocuments();
    private final String reports = documents+"Reports/";
    private final String certificate = documents+"Certificate/";
    private Calendar c =Calendar.getInstance();
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.POST)
    public @ResponseBody List<User> getAllUsers() {
        User currentUser = userService.getCurrentUser();
        if(currentUser.getUserId() != null) {
            return userService.findAllActiveUsers();
        } else
            return null;
    }

//    @RequestMapping(value={"/list/{locationId}"})
//    public @ResponseBody List<User> getUsersByLocation(@PathVariable("locationId") Integer locationId) {
//        return userService.findAllActiveUsersByLocation(locationId);
//    }

    @RequestMapping(value={"/myUserList"} , method = RequestMethod.POST)
    public @ResponseBody List<User> getMyUsers() {
        User currentUser = userService.getCurrentUser();
        if(currentUser.getUserId() != null){
            return userService.findMyUsers(userService.getCurrentUser());
        } else
            return null;

    }

    @RequestMapping(value={"/roles"} , method = RequestMethod.POST)
    public @ResponseBody List<Role> getRoles() {
        User currentUser = userService.getCurrentUser();
        if(currentUser.getUserId() != null&&((currentUser.getRoleName().equals("MASTER ADMIN"))||(currentUser.getRoleName().equals("ADMIN")))){
            return roleService.getRoles();
        } else
            return null;
    }

    @RequestMapping(value={"/currentUser"} , method = RequestMethod.POST)
    public @ResponseBody User getCurrentUser() {

        return userService.getCurrentUser();

    }

    @RequestMapping(value={"/profile"})
    public @ResponseBody UserDto profile() {
        User currentUser = userService.getCurrentUser();
        if(currentUser.getUserId() != null){
            UserDto user1 = new UserDto();
            user1.setId(currentUser.getUserId());
            user1.setName(currentUser.getFullName());
            user1.setUsername(currentUser.getUsername());
            user1.setEmail(currentUser.getEmailId());
            user1.setPhoneNumber(currentUser.getPhoneNumber());
            user1.setAccessLevel(currentUser.getAccessLevel());
            if(currentUser.getStateId() != null){
                user1.setState(locationService.findStateById(currentUser.getStateId()).getStateName());
            }
            else{
                user1.setState("");
            }
            if(currentUser.getDistrictId() != null) {
                user1.setDistrict(locationService.findDistrictById(currentUser.getDistrictId()).getDistrictName());
            }
            else{
                user1.setDistrict("");
            }
            if(currentUser.getBlockId() != null) {
                user1.setBlock(locationService.findBlockById(currentUser.getBlockId()).getBlockName());
            }
            else{
                user1.setBlock("");
            }
            user1.setAccessType(currentUser.getRoleName());
            user1.setCreatedBy(true);
            return user1;
        }
        return null;
    }

    @RequestMapping(value={"/isLoggedIn"} , method = RequestMethod.POST)
    public @ResponseBody Boolean isLoggedIn(
            HttpServletRequest request) {
         request.getSession(false);
//        request.getSession().getId();
//        if (userService.getCurrentUser() == null) {
//            isAdminLoggedIn();
//        }
        return userService.getCurrentUser() != null;
    }

    @RequestMapping(value={"/isAdminLoggedIn"} , method = RequestMethod.POST)
    public @ResponseBody Boolean isAdminLoggedIn() {
        User currentUser = userService.getCurrentUser();
        if(currentUser == null || currentUser.getRoleName().equals(AccessType.USER.getAccessType())){
            return false;
        }
        return true;
    }

    //To be changed
    @RequestMapping(value={"/tableList"} , method = RequestMethod.POST)
    public @ResponseBody List<UserDto> getTableList() {

        User currentUser = userService.getCurrentUser();
        if(currentUser.getUserId() != null){
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
                if(user.getStateId() == null){
                    user1.setState("");
                } else{
                    user1.setState(user.getStateName());
                }
                if(user.getDistrictId() == null){
                    user1.setDistrict("");
                } else{
                    user1.setDistrict(user.getDistrictName());
                }
                if(user.getBlockId() == null){
                    user1.setBlock("");
                } else{
                    user1.setBlock(user.getBlockName());
                }
                user1.setAccessType(user.getRoleName());
                int a;
                try{
                    a = user.getCreatedByUser().getUserId();
                } catch (NullPointerException e){
                    a = 0;
                }
                int b = getCurrentUser().getUserId();
                user1.setCreatedBy(a == b || getCurrentUser().getRoleId() == 1);
                tabDto.add(user1);

            }
            return tabDto;
        } else
            return null;



    }
//returning a user only if current user is the creator, this api is used only during edit user
    @RequestMapping(value={"/user/{userId}"})
    public @ResponseBody User getUserById(@PathVariable("userId") Integer userId) {
        if(getCurrentUser() != null){
            User user = userService.findUserByUserId(userId);
            if(getCurrentUser().getUserId().equals(user.getCreatedByUser().getUserId())) {
                return user;
            }
            return null;
        }
            return null;
    }

//    @RequestMapping(value={"/dto/{userId}"})
//    public @ResponseBody UserDto getUserDto(@PathVariable("userId") Integer userId) {
//        User user = userService.findUserByUserId(userId);
//        String[] levels = {"National", "State", "District", "Block"};
//        UserDto user1 = new UserDto();
//        user1.setId(user.getUserId());
//        user1.setName(user.getFullName());
//        user1.setUsername(user.getUsername());
//        user1.setEmail(user.getEmailId());
//        user1.setPhoneNumber(user.getPhoneNumber());
//        user1.setAccessLevel(user.getAccessLevel());
//        try {
//            user1.setState(locationService.findStateById(user.getStateId()).getStateName());
//        } catch(NullPointerException e){
//            user1.setState("");
//        }
//        try {
//            user1.setDistrict(locationService.findDistrictById(user.getDistrictId()).getDistrictName());
//        } catch(NullPointerException e){
//            user1.setDistrict("");
//        }
//        try {
//            user1.setBlock(locationService.findBlockById(user.getBlockId()).getBlockName());
//        } catch(NullPointerException e){
//            user1.setBlock("");
//        }
//        user1.setAccessType(user.getRoleId().getRoleId().toString());
//        user1.setCreatedBy(true);
//        return user1;
//    }

    @RequestMapping(value = {"/createUser"}, method = RequestMethod.POST)
    @ResponseBody public Map<Integer, String> createNewUser(@RequestBody User user) throws Exception {

        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
            user = locationService.SetLocations(user);
            Map<Integer,String> map = userService.createNewUser(user);
            if(map.get(0).equals("User Created")){
                String password = map.get(1);
                String email = user.getEmailId();
                byte[] encoded = Base64.encodeBase64((email + "||" + password + "||new").getBytes());
                String encrypted = new String(encoded);
                String url = "http://192.168.200.4:8080/NMSReportingSuite/nms/mail/sendPassword/" + encrypted;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                //add request header
                con.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());
                ModificationTracker modification = new ModificationTracker();
                modification.setModificationDate(new Date(System.currentTimeMillis()));
                modification.setModificationType(ModificationType.CREATE.getModificationType());
                modification.setModifiedUserId(userService.findUserByUsername(user.getUsername()).getUserId());
                modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
                modificationTrackerService.saveModification(modification);
            }
            return map;
        } else
            return null;

    }

    @RequestMapping(value = {"/updateUser"}, method = RequestMethod.POST)
    @ResponseBody public Map updateExistingUser(@RequestBody User user) {

        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
            user = locationService.SetLocations(user);
            User oldUser = userService.findUserByUserId(user.getUserId());
            Map<Integer,String> map = userService.updateExistingUser(user);
            if(map.get(0).equals("User Updated")){
                userService.TrackModifications(oldUser, user);
            }
            return map;
        } else
            return null;

    }

    @RequestMapping(value = {"/updateContacts"}, method = RequestMethod.POST)
    @ResponseBody public Map updateContacts(@RequestBody ContactInfo contactInfo) {

        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
            User user=userService.findUserByUserId(contactInfo.getUserId());
            Map<Integer, String> map=userService.updateContacts(contactInfo);
            if(map.get(0).equals("Contacts Updated")){
                TrackContactInfoModifications(user, contactInfo);
            }
            return map;
        } else
            return null;

    }

    private void TrackContactInfoModifications(User oldUser, ContactInfo contactInfo) {
        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
            if(!oldUser.getEmailId().equals(contactInfo.getEmail())) {
                ModificationTracker modification = new ModificationTracker();
                modification.setModificationDate(new Date(System.currentTimeMillis()));
                modification.setModificationType(ModificationType.UPDATE.getModificationType());
                modification.setModifiedField("email_id");
                modification.setPreviousValue(oldUser.getEmailId());
                modification.setNewValue(contactInfo.getEmail());
                modification.setModifiedUserId(oldUser.getUserId());
                modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
                modificationTrackerService.saveModification(modification);
            }
            if(!oldUser.getPhoneNumber().equals(contactInfo.getPhoneNumber())){
                ModificationTracker modification = new ModificationTracker();
                modification.setModificationDate(new Date(System.currentTimeMillis()));
                modification.setModificationType(ModificationType.UPDATE.getModificationType());
                modification.setModifiedField("phone_no");
                modification.setPreviousValue(oldUser.getPhoneNumber());
                modification.setNewValue(contactInfo.getPhoneNumber());
                modification.setModifiedUserId(oldUser.getUserId());
                modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
                modificationTrackerService.saveModification(modification);
            }
        }

    }

    @RequestMapping(value = {"/resetPassword"}, method = RequestMethod.POST)
    @ResponseBody public Map resetPassword(@RequestBody PasswordDto passwordDto) throws Exception {
        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
            User user=userService.findUserByUserId(passwordDto.getUserId());
            Map<Integer, String >map=  userService.changePassword(passwordDto);
            if(map.get(0).equals("Password changed successfully")){
                ModificationTracker modification = new ModificationTracker();
                modification.setModificationDate(new Date(System.currentTimeMillis()));
                modification.setModificationType(ModificationType.UPDATE.getModificationType());
                modification.setModifiedUserId(passwordDto.getUserId());
                modification.setModifiedField("password");
                modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
                modificationTrackerService.saveModification(modification);
            }
            return map;
        } else
            return null;
    }

    @RequestMapping(value = {"/etlNotification"}, method = RequestMethod.GET)
    @ResponseBody
    public List<String> etlNotification() throws Exception {
        User currentUser = userService.getCurrentUser();
        if(currentUser != null) {
            Element cacheElement = etlNotificationCache.get("etlNotification");
            if (cacheElement != null) {
                return (List<String>) cacheElement.getObjectValue();
            }

            List<String> noticeList = new ArrayList<>();
            List<Notice> notices = noticeDao.findNoticeForLast28Days();
            if (notices != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                for (Notice notice : notices) {
                    String etlNotification;
                    etlNotification = sdf.format(notice.getDate()) + "_" + notice.getMessage();
                    noticeList.add(etlNotification);
                }
            }
            etlNotificationCache.put(new Element("etlNotification", noticeList));
            return noticeList;
        } else
            return null;
    }

    @RequestMapping(value = {"/noticeScheduler"}, method  = RequestMethod.GET)
    public void noticeScheduler() throws Exception {
        etlNotificationService.scheduledNotification();
    }
//
//    @RequestMapping(value={"/getCaptcha"} , method = RequestMethod.GET)
//    public @ResponseBody String getCaptcha(
//            HttpServletRequest request) {
//
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        HttpSession sess = request.getSession();
//
//        // logic to generate captcha
//       String captchaCode = serviceFunctions.generateCaptcha();
//        sess.setAttribute("captcha",captchaCode);
//
//        String encoded = new String(Base64.encodeBase64((captchaCode).getBytes()));
//        return encoded.substring(0, encoded.length() - 1);
//    }
//    @RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.POST)
//    @ResponseBody
//    public Map forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto, HttpServletRequest request) throws Exception{
//
//        HttpSession session = request.getSession();
//        if(session.getAttribute("captcha").equals(forgotPasswordDto.getCaptcha())){
//            session.removeAttribute("captcha");
//            User user=userService.findUserByUsername(forgotPasswordDto.getUsername());
//            Map<Integer, String >map=  userService.forgotPasswordCredentialChecker(forgotPasswordDto);
//            if(map.get(0).equals("Password changed successfully")){
//                ModificationTracker modification = new ModificationTracker();
//                modification.setModificationDate(new Date(System.currentTimeMillis()));
//                modification.setModificationType(ModificationType.UPDATE.getModificationType());
//                modification.setModifiedUserId(user.getUserId());
//                modification.setModifiedField("password");
//                modification.setModifiedByUserId(user.getUserId());
//                modificationTrackerService.saveModification(modification);
//            }
//            return map;
//        }
//
//
//        return null;}

    @RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.POST)
    @ResponseBody
    public String forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();

//        String captcha = decrypt(new LoginUser(forgotPasswordDto.getCaptcha()));
        if (serviceFunctions.validateCaptcha(forgotPasswordDto.getCaptchaResponse()).equals("success")) {
//            if (captcha.equals(session.getAttribute("captcha"))) {
                String userName = forgotPasswordDto.getUsername();
                User user = userService.findUserByUsername(userName);

                if (user != null) {
                    String email = user.getEmailId();
                    String password = serviceFunctions.generatePassword();
                    byte[] encoded = Base64.encodeBase64((email + "||" + password + "||forgot").getBytes());
                    String encrypted = new String(encoded);
                    String url = "http://192.168.200.4:8080/NMSReportingSuite/nms/mail/sendPassword/" + encrypted;
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    // optional default is GET
                    con.setRequestMethod("GET");

                    //add request header
                    con.setRequestProperty("User-Agent", USER_AGENT);

                    int responseCode = con.getResponseCode();
                    System.out.println("\nSending 'GET' request to URL : " + url);
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //print result
                    System.out.println(response.toString());
                    if ((response.toString()).equals("success")) {
                        user.setPassword(passwordEncoder.encode(password));
                        user.setDefault(true);
                        user.setUnSuccessfulAttempts(0);
                        userService.updateUser(user);
                        ModificationTracker modification = new ModificationTracker();
                        modification.setModificationDate(new Date(System.currentTimeMillis()));
                        modification.setModificationType(ModificationType.UPDATE.getModificationType());
                        modification.setModifiedUserId(user.getUserId());
                        modification.setModifiedField("password");
                        modification.setModifiedByUserId(user.getUserId());
                        modificationTrackerService.saveModification(modification);
                        session.removeAttribute("captcha");
                        session.invalidate();
                    }

                    return "success";
                }
                return "invalid user";
        }
        return "invalid captcha";
    }




//changed delete user to post, added a token verification
    @RequestMapping(value = {"/deleteUser"}, method = RequestMethod.POST)
    @ResponseBody
    public Map deleteExistingUser(HttpServletRequest request, @RequestBody Integer id) {
        User currentUser = userService.getCurrentUser();
        String token = "dhty" + currentUser.getUserId().toString() + "alkihkf";
        if(currentUser != null && request.getHeader("csrfToken").equals(token)){
            Map<Integer, String> map=userService.deleteExistingUser(id);
            if(map.get(0).equals("User deleted")) {
                ModificationTracker modification = new ModificationTracker();
                modification.setModificationDate(new Date(System.currentTimeMillis()));
                modification.setModificationType(ModificationType.DELETE.getModificationType());
                modification.setModifiedField("account_status");
                modification.setModifiedUserId(id);
                modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
                modificationTrackerService.saveModification(modification);
            }
            return map;
        } else
            return null;

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

    private int countLocationsWithHighCompletion(List<AggregateCumulativeMA> startData, List<AggregateCumulativeMA> endData) {
        int count = 0;
        Map<Long, AggregateCumulativeMA> startMap = new HashMap<>();

        for (AggregateCumulativeMA start : startData) {
            startMap.put(start.getLocationId(), start);
        }

        for (AggregateCumulativeMA end : endData) {
            AggregateCumulativeMA start = startMap.get(end.getLocationId());
            if (start != null) {
                int registered = end.getAshasRegistered() - start.getAshasRegistered();
                int completed = end.getAshasCompleted() - start.getAshasCompleted();

                if (registered > 0) {
                    double completionPercentage = ((double) completed / registered) * 100;
                    if (completionPercentage > 70) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private int countLocationsWithHighStarted(List<AggregateCumulativeMA> startData, List<AggregateCumulativeMA> endData) {
        int count = 0;
        Map<Long, AggregateCumulativeMA> startMap = new HashMap<>();

        for (AggregateCumulativeMA start : startData) {
            startMap.put(start.getLocationId(), start);
        }

        for (AggregateCumulativeMA end : endData) {
            AggregateCumulativeMA start = startMap.get(end.getLocationId());
            if (start != null) {
                int registered = end.getAshasRegistered() - start.getAshasRegistered();
                int started = end.getAshasStarted() - start.getAshasRegistered();

                if (registered > 0) {
                    double completionPercentage = ((double) started / registered) * 100;
                    if (completionPercentage > 70) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.POST/*,produces = "application/vnd.ms-excel"*/)
    @ResponseBody
    @Transactional
    public Object getReport(@RequestBody ReportRequest reportRequest/*,HttpServletResponse response*/) throws ParseException, java.text.ParseException {
        String reportPath = "";
        String reportName = "";
        String rootPath = "";
        String place = AccessLevel.NATIONAL.getAccessLevel();

        Map<String, String> m = new HashMap<>();
        User currentUser = userService.getCurrentUser();
        List<BreadCrumbDto> breadCrumbs = breadCrumbService.getBreadCrumbs(currentUser, reportRequest);
        AggregateResponseDto aggregateResponseDto = new AggregateResponseDto();



        AggregateKilkariReportsDto aggregateKilkariResponseDto = new AggregateKilkariReportsDto();
        if (currentUser.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel()) && !currentUser.getStateId().equals(reportRequest.getStateId())) {
            m.put("status", "fail");
            return m;
        }
        if (currentUser.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel()) && !currentUser.getDistrictId().equals(reportRequest.getDistrictId())) {
            m.put("status", "fail");
            return m;
        }
        if (currentUser.getAccessLevel().equals(AccessLevel.BLOCK.getAccessLevel()) && !currentUser.getBlockId().equals(reportRequest.getBlockId())) {
            m.put("status", "fail");
            return m;
        }
        if (reportRequest.getReportType().equals(ReportType.kilkariCumulative.getReportType())) {
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<AggregateCumulativeKilkariDto> aggregateCumulativeKilkariDtos = aggregateKilkariReportsService.getKilkariCumulativeSummary(reportRequest, currentUser);
            aggregateResponseDto.setTableData(aggregateCumulativeKilkariDtos);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.kilkariSubscriber.getReportType())) {

            aggregateKilkariResponseDto = aggregateKilkariReportsService.getKilkariSubscriberCountReport(reportRequest);
            aggregateKilkariResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateKilkariResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.kilkariSubscriberWithRegistrationDate.getReportType())){
            aggregateKilkariResponseDto = aggregateKilkariReportsService.getKilkariSubscriberCountReportBasedOnRegistrationDate(reportRequest);
            aggregateKilkariResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateKilkariResponseDto;
        }
        if (reportRequest.getReportType().equals(ReportType.beneficiary.getReportType())) {
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<KilkariAggregateBeneficiariesDto> kilkariAggregateBeneficiariesDtos = aggregateKilkariReportsService.getBeneficiaryReport(reportRequest, currentUser);
            aggregateResponseDto.setTableData(kilkariAggregateBeneficiariesDtos);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.usage.getReportType())) {
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<UsageDto> kilkariUsageDtos = aggregateKilkariReportsService.getUsageReport(reportRequest, currentUser);
            aggregateResponseDto.setTableData(kilkariUsageDtos);
            return aggregateResponseDto;
        }

        if(reportRequest.getReportType().equals(ReportType.kilkariHomePageReport.getReportType())){
            LOGGER.debug("inside this home api");
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<KilkariHomePageReportsDto> kilkariHomePageReportsDtos = aggregateKilkariReportsService.getkilkariHomePageReport(reportRequest);
            aggregateResponseDto.setTableData(kilkariHomePageReportsDtos);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.kilkariMessageListenership.getReportType())) {

            aggregateKilkariResponseDto = aggregateKilkariReportsService.getKilkariMessageListenershipReport(reportRequest);
            aggregateKilkariResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateKilkariResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.beneficiaryCompletion.getReportType())) {
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<AggCumulativeBeneficiaryComplDto> aggCumulativeBeneficiaryComplDtos = aggregateKilkariReportsService.getCumulativeBeneficiaryCompletion(reportRequest, currentUser);
            aggregateResponseDto.setTableData(aggCumulativeBeneficiaryComplDtos);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.listeningMatrix.getReportType())) {
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<ListeningMatrixDto> listeningMatrixDtos = aggregateKilkariReportsService.getListeningMatrixReport(reportRequest, currentUser);
            aggregateResponseDto.setTableData(listeningMatrixDtos);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.kilkariThematicContent.getReportType())) {
            aggregateKilkariResponseDto = aggregateKilkariReportsService.getKilkariThematicContentReport(reportRequest);
            aggregateKilkariResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateKilkariResponseDto;

        }

        if (reportRequest.getReportType().equals(ReportType.messageMatrix.getReportType())) {
            MessageMatrixResponseDto messageMatrixResponseDto = new MessageMatrixResponseDto();
            messageMatrixResponseDto = aggregateKilkariReportsService.getMessageMatrixReport(reportRequest, currentUser);
            messageMatrixResponseDto.setBreadCrumbData(breadCrumbs);
            return messageMatrixResponseDto;
        }

        if(reportRequest.getReportType().equals(ReportType.kilkariRepeatListenerMonthWise.getReportType())) {
            AggregateKilkariRepeatListenerMonthWiseDto aggregateKilkariRepeatListenerMonthWiseDto = new AggregateKilkariRepeatListenerMonthWiseDto();
            aggregateKilkariRepeatListenerMonthWiseDto = aggregateKilkariReportsService.getKilkariRepeatListenerMonthWiseReport(reportRequest);
            aggregateKilkariRepeatListenerMonthWiseDto.setBreadCrumbData(breadCrumbs);
            return aggregateKilkariRepeatListenerMonthWiseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.kilkariCalls.getReportType())) {
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<KilkariCallReportDto> kilkariCallReportDtos = aggregateKilkariReportsService.getKilkariCallReport(reportRequest, currentUser);
            aggregateResponseDto.setTableData(kilkariCallReportDtos);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.kilkariCallsWithBeneficiaries.getReportType())) {
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<KilkariCallReportWithBeneficiariesDto> kilkariCallReportWithBeneficiariesDtos = aggregateKilkariReportsService.getKilkariCallReportWithBeneficiaries(reportRequest, currentUser);
            aggregateResponseDto.setTableData(kilkariCallReportWithBeneficiariesDtos);
            return aggregateResponseDto;
        }

        if(reportRequest.getReportType().equals(ReportType.MAHomePageReport.getReportType())){

            Date fromDate = dateAdder(reportRequest.getFromDate(), 0);
            Date toDate = dateAdder(reportRequest.getToDate(), 1);
            List<LocationCountDto> locationCounts = new ArrayList<>();



            String locationType = null;
            Integer locationId = null;
            if (reportRequest.getStateId() == 0) {
                locationType = "State";
                locationId = 0;
            } else if (reportRequest.getDistrictId() == 0) {
                locationType = "District";
                locationId = reportRequest.getStateId();
            } else if (reportRequest.getBlockId() == 0) {
                locationType = "Block";
                locationId = reportRequest.getDistrictId();
            } else {
                locationType = "Subcentre";
                locationId = reportRequest.getBlockId();
            }



            if (locationType.equalsIgnoreCase("State")) {
                List<State> states = stateDao.getStatesByServiceType("MOBILE_ACADEMY");
                for (State state : states) {
                    Date serviceStartDate = stateServiceDao.getServiceStartDateForState(state.getStateId(), "MOBILE_ACADEMY");
                    if (serviceStartDate == null) {
                        continue;
                    }
                    List<AggregateCumulativeMA> districtDataEnd = aggregateReportsService.getCumulativeSummaryMAReport(state.getStateId(), "District", toDate, false);
                    List<AggregateCumulativeMA> districtDataStart = aggregateReportsService.getCumulativeSummaryMAReport(state.getStateId(), "District", fromDate, false);
                    int districtCountforCompletion = countLocationsWithHighCompletion(districtDataStart, districtDataEnd);
                    int districtCountforStarted = countLocationsWithHighStarted(districtDataStart, districtDataEnd);
                    LocationCountDto dto = new LocationCountDto();
                    dto.setLocationId((long) state.getStateId());
                    dto.setLocationName(state.getStateName());
                    dto.setCompletedCount(districtCountforCompletion);
                    dto.setStartedCount(districtCountforStarted);
                    dto.setLocationType(locationType);
                    locationCounts.add(dto);

                }
            } else if (locationType.equalsIgnoreCase("District")) {
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                for (District district : districts) {
                    List<AggregateCumulativeMA> blockDataEnd = aggregateReportsService.getCumulativeSummaryMAReport(district.getDistrictId(), "Block", toDate, false);
                    List<AggregateCumulativeMA> blockDataStart = aggregateReportsService.getCumulativeSummaryMAReport(district.getDistrictId(), "Block", fromDate, false);

                    int blockCountforCompletion = countLocationsWithHighCompletion(blockDataStart, blockDataEnd);
                    int blockCountforStarted = countLocationsWithHighStarted(blockDataStart, blockDataEnd);
                    LocationCountDto dto = new LocationCountDto();
                    dto.setLocationId((long) district.getDistrictId());
                    dto.setLocationName(district.getDistrictName());
                    dto.setCompletedCount(blockCountforCompletion);
                    dto.setStartedCount(blockCountforStarted);
                    dto.setLocationType(locationType);
                    locationCounts.add(dto);
                }
            } else if (locationType.equalsIgnoreCase("Block")) {
                List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                for (Block block : blocks) {
                    List<AggregateCumulativeMA> subcentreDataEnd = aggregateReportsService.getCumulativeSummaryMAReport(block.getBlockId(), "Subcentre", toDate, false);
                    List<AggregateCumulativeMA> subcentreDataStart = aggregateReportsService.getCumulativeSummaryMAReport(block.getBlockId(), "Subcentre", fromDate, false);

                    int subcentreCountforCompletion = countLocationsWithHighCompletion(subcentreDataStart, subcentreDataEnd);
                    int subcentreCountforStarted = countLocationsWithHighStarted(subcentreDataStart, subcentreDataEnd);
                    LocationCountDto dto = new LocationCountDto();
                    dto.setLocationId((long) block.getBlockId());
                    dto.setLocationName(block.getBlockName());
                    dto.setCompletedCount(subcentreCountforCompletion);
                    dto.setStartedCount(subcentreCountforStarted);
                    dto.setLocationType(locationType);
                    locationCounts.add(dto);            }
            }

            aggregateResponseDto.setTableData(locationCounts);
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);

            return aggregateResponseDto;

        }
        if (reportRequest.getReportType().equals(ReportType.maPerformance.getReportType())) {

            Date fromDate = dateAdder(reportRequest.getFromDate(), 0);
            Date toDate = dateAdder(reportRequest.getToDate(), 1);
            LOGGER.info("this is todateperformance: {}",toDate);

            List<MAPerformanceDto> summaryDto = new ArrayList<>();
            List<AggregateCumulativeMA> cumulativesummaryReportStart = new ArrayList<>();
            List<AggregateCumulativeMA> cumulativesummaryReportEnd = new ArrayList<>();
            Map<Long, MAPerformanceCountsDto> performanceCounts = new HashMap<>();

            String locationType;
            Integer locationId;

            if (reportRequest.getStateId() == 0) {
                locationType = "State";
                locationId = 0;
            } else if (reportRequest.getDistrictId() == 0) {
                locationType = "District";
                locationId = reportRequest.getStateId();
            } else if (reportRequest.getBlockId() == 0) {
                locationType = "Block";
                locationId = reportRequest.getDistrictId();
            } else {
                locationType = "Subcentre";
                locationId = reportRequest.getBlockId();
            }

            System.out.println("locationId is"+locationId);;
            try {
                LOGGER.info("this is fromdate and to date: {}, {}",fromDate,toDate);
                cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(locationId, locationType, fromDate, false));
                cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(locationId, locationType, toDate, false));
                performanceCounts = maPerformanceService.getMAPerformanceCounts(locationId, locationType, fromDate, toDate);
            } catch (NullPointerException e) {
                e.printStackTrace();

            }

            Map<Long, AggregateCumulativeMA> startMap =new HashMap<>();

            for(AggregateCumulativeMA aggregateCumulativeMAS : cumulativesummaryReportStart){
                startMap.put(aggregateCumulativeMAS.getLocationId(),aggregateCumulativeMAS);
            }


            for (AggregateCumulativeMA end : cumulativesummaryReportEnd) {
                AggregateCumulativeMA start = startMap.get(end.getLocationId());
                if (start != null) {
                    MAPerformanceDto summaryDto1 = new MAPerformanceDto();
                    summaryDto1.setId(end.getId());
                    summaryDto1.setLocationId(end.getLocationId());
                    summaryDto1.setLocationType(end.getLocationType());

                    switch (end.getLocationType().toLowerCase()) {
                        case "state":
                            summaryDto1.setLocationName(stateDao.findByStateId(end.getLocationId().intValue()).getStateName());
                            break;
                        case "district":
                            summaryDto1.setLocationName(districtDao.findByDistrictId(end.getLocationId().intValue()).getDistrictName());
                            break;
                        case "block":
                            summaryDto1.setLocationName(blockDao.findByblockId(end.getLocationId().intValue()).getBlockName());
                            break;
                        case "subcentre":
                            summaryDto1.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(end.getLocationId().intValue()).getHealthSubFacilityName());
                            summaryDto1.setLink(true);
                            break;
                        case "differencestate":
                            summaryDto1.setLocationName("No District Count");
                            summaryDto1.setLink(true);
                            summaryDto1.setLocationId((long) -1);
                            break;
                        case "differencedistrict":
                            summaryDto1.setLocationName("No Block Count");
                            summaryDto1.setLink(true);
                            summaryDto1.setLocationId((long) -1);
                            break;
                        case "differenceblock":
                            summaryDto1.setLocationName("No Subcenter Count");
                            summaryDto1.setLink(true);
                            summaryDto1.setLocationId((long) -1);
                            break;
                    }

                    try {
                        MAPerformanceCountsDto MAperformanceCounts = performanceCounts.get(end.getLocationId());
                        summaryDto1.setAshasActivated(end.getAshasRegistered() != null ? end.getAshasRegistered() : 0L);
                        if (MAperformanceCounts != null) {
                            summaryDto1.setAshasFailed(MAperformanceCounts.getAshasFailed() != null ? MAperformanceCounts.getAshasFailed() : 0);
                            int ashasStartedDifference = end.getAshasStarted() - start.getAshasStarted();
                            int ashasCompletedDifference = end.getAshasCompleted()-start.getAshasCompleted();
                            long ashaDeactivatedStartedCourseInBetweenCount = MAperformanceCounts.getAshaDeactivatedStartedCourseInBetweenCount() != null ? MAperformanceCounts.getAshaDeactivatedStartedCourseInBetweenCount() : 0L;
                            long ashaDeactivatedCompletedCourseInBetweenCount = MAperformanceCounts.getAshaDeactivatedCompletedCourseInBetweenCount() != null ? MAperformanceCounts.getAshaDeactivatedCompletedCourseInBetweenCount() : 0L;
                            summaryDto1.setAshasCompleted((int) (ashasCompletedDifference+ashaDeactivatedCompletedCourseInBetweenCount));
                            summaryDto1.setAshasStarted((int) (ashasStartedDifference + ashaDeactivatedStartedCourseInBetweenCount));
                            summaryDto1.setAshasAccessed(MAperformanceCounts.getAccessedAtleastOnce() != null ? MAperformanceCounts.getAccessedAtleastOnce() : 0L);
                            summaryDto1.setAshasNotAccessed(MAperformanceCounts.getAccessedNotOnce() != null ? MAperformanceCounts.getAccessedNotOnce() : 0L);
                            summaryDto1.setAshasDeactivated(MAperformanceCounts.getAshasDeactivatedInBetween() != null ? MAperformanceCounts.getAshasDeactivatedInBetween() : 0L);
                            summaryDto1.setAshasRefresherCourse(MAperformanceCounts.getAshasRefresherCourse() != null ? MAperformanceCounts.getAshasRefresherCourse() : 0L);
                            summaryDto1.setAshasCompletedInGivenTime(MAperformanceCounts.getAshasCompletedInGivenTime() != null ? MAperformanceCounts.getAshasCompletedInGivenTime() : 0L);
                            summaryDto1.setAshasJoined(MAperformanceCounts.getAshasActivatedInBetween() != null ? MAperformanceCounts.getAshasActivatedInBetween() : 0L);
                        }
                        else {
                            summaryDto1.setAshasFailed(0);
                            summaryDto1.setAshasAccessed(0L);
                            summaryDto1.setAshasNotAccessed(0L);
                            summaryDto1.setAshasDeactivated(0L);
                            summaryDto1.setAshasRefresherCourse(0L);
                            summaryDto1.setAshasCompletedInGivenTime(0L);
                            summaryDto1.setAshasJoined(0L);
                            summaryDto1.setAshasStarted(end.getAshasStarted() - start.getAshasStarted());
                            summaryDto1.setAshasCompleted(end.getAshasCompleted() - start.getAshasCompleted());


                        }

//                        int ashasCompleted = summaryDto1.getAshasCompleted() != null ? summaryDto1.getAshasCompleted() : 0;
//                        int ashasFailed = summaryDto1.getAshasFailed() != null ? summaryDto1.getAshasFailed() : 0;
//                        int ashasStarted = summaryDto1.getAshasStarted() != null ? summaryDto1.getAshasStarted() : 0;
//                        long ashasAccessed = summaryDto1.getAshasAccessed() != null ? summaryDto1.getAshasAccessed() : 0;
//                        long ashasNotAccessed = summaryDto1.getAshasNotAccessed() != null ? summaryDto1.getAshasNotAccessed() : 0;
//                        long ashasJoined = summaryDto1.getAshasJoined() != null ? summaryDto1.getAshasJoined() :0;


                            if (!end.getLocationType().equalsIgnoreCase("DifferenceState")) {
                                summaryDto.add(summaryDto1);
                            }

                    } catch (NullPointerException e) {

                        e.printStackTrace();
                    }
                }
            }

            aggregateResponseDto.setTableData(summaryDto);
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateResponseDto;
        }


        if (reportRequest.getReportType().equals(ReportType.maSubscriber.getReportType())) {

            Date fromDate = dateAdder(reportRequest.getFromDate(),0);
            Date toDate = dateAdder(reportRequest.getToDate(),1);


            List<MASubscriberDto> summaryDto = new ArrayList<>();
            List<AggregateCumulativeMA> cumulativesummaryReportStart = new ArrayList<>();
            List<AggregateCumulativeMA> cumulativesummaryReportEnd = new ArrayList<>();
            Map<Long, MAPerformanceCountsDto> performanceCounts = new HashMap<>();


            String locationTypes;
            Integer locationId;

            if(reportRequest.getStateId()==0){
                locationTypes = "State";
                locationId = 0;
            }else if (reportRequest.getDistrictId()==0){
                locationTypes = "District";
                locationId = reportRequest.getStateId();
            } else if (reportRequest.getBlockId()==0) {
                locationTypes = "Block";
                locationId = reportRequest.getDistrictId();
            }else {
                locationTypes = "Subcentre";
                locationId = reportRequest.getBlockId();
            }

            cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport((int) locationId, locationTypes, fromDate,false));
            cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport((int) locationId, locationTypes, toDate,false));

            performanceCounts = maPerformanceService.getMAPerformanceCounts(locationId, locationTypes, fromDate, toDate);

            for (int i = 0; i < cumulativesummaryReportEnd.size(); i++) {

                for (int j = 0; j < cumulativesummaryReportStart.size(); j++) {
                    boolean showRow = true;
                    if (cumulativesummaryReportEnd.get(i).getLocationId().equals(cumulativesummaryReportStart.get(j).getLocationId())) {
                        AggregateCumulativeMA a = cumulativesummaryReportEnd.get(i);
                        AggregateCumulativeMA b = cumulativesummaryReportStart.get(j);
                        MASubscriberDto summaryDto1 = new MASubscriberDto();
                        summaryDto1.setId(a.getId());
                        summaryDto1.setLocationId(a.getLocationId());
//                        summaryDto1.setAshasCompleted(a.getAshasCompleted() - b.getAshasCompleted());
                        summaryDto1.setAshasFailed(a.getAshasFailed() - b.getAshasFailed());
//                        summaryDto1.setAshasRegistered(a.getAshasRegistered() - b.getAshasRegistered());
                        summaryDto1.setAshasNotStarted(a.getAshasNotStarted() - b.getAshasNotStarted());
//                        summaryDto1.setAshasStarted(a.getAshasStarted() - b.getAshasStarted());

                        summaryDto1.setAshasRejected(a.getAshasRejected() - b.getAshasRejected());

                        summaryDto1.setLocationType(a.getLocationType());
                        summaryDto1.setRegisteredNotCompletedStart(b.getAshasRegistered() - b.getAshasCompleted());
                        summaryDto1.setRegisteredNotCompletedend(a.getAshasRegistered() - a.getAshasCompleted());
//                        summaryDto1.setRecordsReceived((a.getAshasRegistered() + a.getAshasRejected()) - (b.getAshasRejected() + b.getAshasRegistered()));

                        String locationType = a.getLocationType();
                        if (locationType.equalsIgnoreCase("State")) {
                            summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                        }
                        if (locationType.equalsIgnoreCase("District")) {
                            summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                        }
                        if (locationType.equalsIgnoreCase("Block")) {
                            summaryDto1.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
                        }
                        if (locationType.equalsIgnoreCase("Subcentre")) {
                            summaryDto1.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
                            summaryDto1.setLink(true);
                        }
                        if (locationType.equalsIgnoreCase("DifferenceState")) {
                            summaryDto1.setLocationName("No District Count");
                            summaryDto1.setLink(true);
                            summaryDto1.setLocationId((long) -1);

                        }
                        if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                            summaryDto1.setLocationName("No Block Count");
                            summaryDto1.setLink(true);
                            summaryDto1.setLocationId((long) -1);

                            }
                            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                                summaryDto1.setLocationName("No Subcenter Count");
                                summaryDto1.setLink(true);
                                summaryDto1.setLocationId((long) -1);

                            }
                            if(a.getLocationType().equalsIgnoreCase("State")&& !serviceStarted(a.getLocationId().intValue(),"State",toDate,fromDate,"MOBILE_ACADEMY"))
                            { showRow = false;}

                        try {
                            MAPerformanceCountsDto MAperformanceCounts = performanceCounts.get(cumulativesummaryReportEnd.get(i).getLocationId());
                            if (MAperformanceCounts != null) {
                                summaryDto1.setAshasFailed(MAperformanceCounts.getAshasFailed() != null ? MAperformanceCounts.getAshasFailed() : 0);
                                int ashasStartedDifference = a.getAshasStarted() - b.getAshasStarted();
                                int ashasCompletedDifference = a.getAshasCompleted() - b.getAshasCompleted();
                                long ashaDeactivatedStartedCourseInBetweenCount = MAperformanceCounts.getAshaDeactivatedStartedCourseInBetweenCount() != null ? MAperformanceCounts.getAshaDeactivatedStartedCourseInBetweenCount() : 0L;
                                long ashaDeactivatedCompletedCourseInBetweenCount = MAperformanceCounts.getAshaDeactivatedCompletedCourseInBetweenCount() != null ? MAperformanceCounts.getAshaDeactivatedCompletedCourseInBetweenCount() : 0L;
                                long totalashasDeactivated = MAperformanceCounts.getAshasDeactivatedInBetween() != null ? MAperformanceCounts.getAshasDeactivatedInBetween() : 0L;
                                LOGGER.debug("ashasStartedDifference: {}, ashasCompletedDifference: {}, totalashasDeactivated: {}, ashaDeactivatedStartedCourseInBetweenCount: {},ashaDeactivatedCompletedCourseInBetweenCount: {}",ashasStartedDifference,ashasCompletedDifference,totalashasDeactivated,ashaDeactivatedStartedCourseInBetweenCount,ashaDeactivatedCompletedCourseInBetweenCount);
                                summaryDto1.setAshasCompleted((int) (ashasCompletedDifference + ashaDeactivatedCompletedCourseInBetweenCount));
                                summaryDto1.setAshasStarted((int) (ashasStartedDifference + ashaDeactivatedStartedCourseInBetweenCount));
                                summaryDto1.setAshasRegistered((int) (a.getAshasRegistered() - b.getAshasRegistered() + totalashasDeactivated));
                                summaryDto1.setRecordsReceived((int) ((a.getAshasRegistered() + a.getAshasRejected()) - (b.getAshasRejected() + b.getAshasRegistered()) + totalashasDeactivated));
                                LOGGER.debug("this is recordes received value: {}",(a.getAshasRegistered() + a.getAshasRejected()) - (b.getAshasRejected() + b.getAshasRegistered()));

                            }
                            else {
                                summaryDto1.setAshasCompleted(a.getAshasCompleted() - b.getAshasCompleted());
                                summaryDto1.setAshasRegistered(a.getAshasRegistered() - b.getAshasRegistered());
                                summaryDto1.setAshasStarted(a.getAshasStarted() - b.getAshasStarted());
                                summaryDto1.setRecordsReceived((a.getAshasRegistered() + a.getAshasRejected()) - (b.getAshasRejected() + b.getAshasRegistered()));
                            }
                        } catch (NullPointerException e) {

                            e.printStackTrace();
                        }

                        int ashasCompleted = summaryDto1.getAshasCompleted();
                        int ashasStarted = summaryDto1.getAshasStarted();
                        int ashasFailed = summaryDto1.getAshasFailed();
                        int ashasRejected = summaryDto1.getAshasRejected();
                        int ashasRegistered = summaryDto1.getAshasRegistered();
                        int registeredNotCompletedEnd = summaryDto1.getRegisteredNotCompletedend();
                        int registeredNotCompletedStart = summaryDto1.getRegisteredNotCompletedStart();
                        int recordsReceived = summaryDto1.getRecordsReceived();
                        int ashasNotStarted = summaryDto1.getAshasNotStarted();


                        int totalSum = ashasCompleted + ashasStarted + ashasFailed + ashasRejected + ashasRegistered + registeredNotCompletedEnd + registeredNotCompletedStart + recordsReceived + ashasNotStarted;

                        int absSum = Math.abs(ashasCompleted) + Math.abs(ashasStarted) + Math.abs(ashasFailed) + Math.abs(ashasRejected) + Math.abs(ashasRegistered) + Math.abs(registeredNotCompletedEnd) + Math.abs(registeredNotCompletedStart) + Math.abs(recordsReceived) + Math.abs(ashasNotStarted);

                        if ((totalSum != 0 || absSum != 0) && !locationType.equalsIgnoreCase("DifferenceState") && showRow) {
                            summaryDto.add(summaryDto1);
                        }

                    }


                    }
                }

                aggregateResponseDto.setTableData(summaryDto);
                aggregateResponseDto.setBreadCrumbData(breadCrumbs);
                return aggregateResponseDto;


        }

            if (reportRequest.getReportType().equals(ReportType.maCumulative.getReportType())) {

                Date toDate = dateAdder(reportRequest.getToDate(),1);
                List<AggregateCumulativeMADto> summaryDto = new ArrayList<>();
                List<AggregateCumulativeMA> cumulativesummaryReport = new ArrayList<>();

                if (reportRequest.getStateId() == 0) {
                    cumulativesummaryReport.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", toDate,true));
                } else {
                    if (reportRequest.getDistrictId() == 0) {
                        cumulativesummaryReport.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", toDate,true));
                    } else {
                        if (reportRequest.getBlockId() == 0) {
                            cumulativesummaryReport.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", toDate,true));
                        } else {
                            cumulativesummaryReport.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcentre", toDate,true));
                        }
                    }


            }

            for (AggregateCumulativeMA a : cumulativesummaryReport) {
                AggregateCumulativeMADto summaryDto1 = new AggregateCumulativeMADto();
                summaryDto1.setId(a.getId());
                summaryDto1.setLocationId(a.getLocationId());
                summaryDto1.setAshasCompleted(a.getAshasCompleted());
                summaryDto1.setAshasFailed(a.getAshasFailed());
                summaryDto1.setAshasRegistered(a.getAshasRegistered());
                summaryDto1.setAshasNotStarted(a.getAshasNotStarted());
                summaryDto1.setAshasStarted(a.getAshasStarted());
                summaryDto1.setLocationType(a.getLocationType());
                summaryDto1.setCompletedPercentage((float) (a.getAshasRegistered() == 0 ? 0 : (Math.round((a.getAshasCompleted() * 10000.0f/ a.getAshasRegistered())))) / 100f);
                summaryDto1.setFailedpercentage((float) (a.getAshasRegistered() == 0 ? 0 : (Math.round((a.getAshasFailed() * 10000.0f / a.getAshasRegistered())))) / 100f);
                summaryDto1.setNotStartedpercentage((float) (a.getAshasRegistered() == 0 ? 0 : (Math.round((a.getAshasNotStarted() * 10000.0f / a.getAshasRegistered())))) / 100f);
                summaryDto1.setStartedPercentage((float) (a.getAshasRegistered() == 0 ? 0 : (Math.round((a.getAshasStarted() * 10000.0f / a.getAshasRegistered())))) / 100f);
                String locationType = a.getLocationType();
                if (locationType.equalsIgnoreCase("State")) {
                    summaryDto1.setLocationName(stateDao.findByStateId(a.getLocationId().intValue()).getStateName());
                }
                if (locationType.equalsIgnoreCase("District")) {
                    summaryDto1.setLocationName(districtDao.findByDistrictId(a.getLocationId().intValue()).getDistrictName());
                }
                if (locationType.equalsIgnoreCase("Block")) {
                    summaryDto1.setLocationName(blockDao.findByblockId(a.getLocationId().intValue()).getBlockName());
                }
                if (locationType.equalsIgnoreCase("Subcentre")) {
                    summaryDto1.setLocationName(healthSubFacilityDao.findByHealthSubFacilityId(a.getLocationId().intValue()).getHealthSubFacilityName());
                    summaryDto1.setLink(true);
                }
                if (locationType.equalsIgnoreCase("DifferenceState")) {
                    summaryDto1.setLocationName("No District");
                    summaryDto1.setLink(true);
                    summaryDto1.setLocationId((long) -1);

                }
                if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                    summaryDto1.setLocationName("No Block");
                    summaryDto1.setLink(true);
                    summaryDto1.setLocationId((long) -1);

                }
                if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                    summaryDto1.setLocationName("No Subcenter");
                    summaryDto1.setLink(true);
                    summaryDto1.setLocationId((long) -1);

                }

//                if (summaryDto1.getAshasRegistered()+summaryDto1.getAshasCompleted()+summaryDto1.getAshasNotStarted()+summaryDto1.getAshasStarted()
//                        +summaryDto1.getAshasFailed() !=0 && a.getId() != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
//                    summaryDto.add(summaryDto1);
//                }

                if (a.getId() != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
                    summaryDto.add(summaryDto1);
                }

                }
                aggregateResponseDto.setTableData(summaryDto);
                aggregateResponseDto.setBreadCrumbData(breadCrumbs);
                return aggregateResponseDto;

        }

        if (reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())) {
            if (!currentUser.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel()) && reportRequest.getCircleId() == 0) {
                m.put("status", "fail");
                return m;
            }

            if (reportRequest.getCircleId() != 0) {
                place = StReplace(locationService.findCircleById(reportRequest.getCircleId()).getCircleFullName());
                rootPath += place + "/";
            }
        } else {
            if (currentUser.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel()) && !currentUser.getStateId().equals(reportRequest.getStateId())) {
                m.put("status", "fail");
                return m;
            }
            if (currentUser.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel()) && !currentUser.getDistrictId().equals(reportRequest.getDistrictId())) {
                m.put("status", "fail");
                return m;
            }
            if (currentUser.getAccessLevel().equals(AccessLevel.BLOCK.getAccessLevel()) && !currentUser.getBlockId().equals(reportRequest.getBlockId())) {
                m.put("status", "fail");
                return m;
            }

                if (reportRequest.getStateId() != 0) {
                    place = StReplace(locationService.findStateById(reportRequest.getStateId()).getStateName());
                    rootPath += place + "/";
                }

            if (reportRequest.getDistrictId() != 0) {
                place = StReplace(locationService.findDistrictById(reportRequest.getDistrictId()).getDistrictName());
                rootPath += place + "/";
            }

            if (reportRequest.getBlockId() != 0) {
                place = StReplace(locationService.findBlockById(reportRequest.getBlockId()).getBlockName());
                rootPath += place + "/";
            }
        }
        String filename = reportRequest.getReportType() + "_" + place + "_" + getMonthYear(reportRequest.getFromDate()) + ".xlsx";
        if (reportRequest.getReportType().equals(ReportType.motherRejected.getReportType()) ||
                reportRequest.getReportType().equals(ReportType.childRejected.getReportType())) {
            filename = reportRequest.getReportType() + "_" + place + "_" + getDateMonthYear(reportRequest.getFromDate()) + ".xlsx";
        }
        reportPath = reports + reportRequest.getReportType() + "/" + rootPath;
        reportName = filename;

        File file = new File(reportPath + reportName);


        if (!(file.exists())) {
            adminService.createSpecificReport(reportRequest);
        }

        m.put("status", "success");
        m.put("file", reportName);
        m.put("path", reportRequest.getReportType() + "/" + rootPath);
        return m;
    }

    @RequestMapping(value = "/asha-count", method = RequestMethod.GET/*,produces = "application/vnd.ms-excel"*/)
    @Transactional
    public ResponseEntity<AshaPerformanceDto> getCountTillDate(){

        User currentUser = userService.getCurrentUser();
//        Date toDate = new Date();
        Date toDate = dateAdder(new Date(),-1);
        LOGGER.info("this is todate: {}",toDate);



        String locationType = null;
        Integer locationId = null;
        LOGGER.info("access level: {}",currentUser.getAccessLevel());
        LOGGER.info("access level 1: {}",AccessLevel.NATIONAL.getAccessLevel());
        if (currentUser.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            locationType = "State";
            locationId = 0;
        } else if (currentUser.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel())) {
            locationType = "District";
            locationId = currentUser.getStateId();
        } else if (currentUser.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel())) {
            locationType = "Block";
            locationId = currentUser.getDistrictId();
        } else {
            locationType = "Subcentre";
            locationId = currentUser.getBlockId();
        }
        List<AggregateCumulativeMA> cumulativesummaryReportEnd = new ArrayList<>();

        cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(locationId, locationType, toDate, false));


        LOGGER.info("this is cumulativeMAData: {}",cumulativesummaryReportEnd);

        Long ashaStarted = 0L;
        Long ashaCompleted = 0L;
        Long ashaRegistered = 0L;
        for(AggregateCumulativeMA end : cumulativesummaryReportEnd){

            LOGGER.info("this is asha started in loop: {}",end.getAshasStarted());
            LOGGER.info("this is asha completed in loop: {}",end.getAshasCompleted());
            ashaStarted += Long.valueOf(end.getAshasStarted());
            ashaCompleted += end.getAshasCompleted();
            ashaRegistered += Long.valueOf(end.getAshasRegistered());

        }


        return ResponseEntity.ok(new AshaPerformanceDto(ashaStarted,ashaCompleted,ashaRegistered));

    }

    @RequestMapping(value = "/cumulative-beneficiaries", method = RequestMethod.GET/*,produces = "application/vnd.ms-excel"*/)
    @Transactional
    public ResponseEntity<Long> getCumulativeBeneficiaries(){

        User currentUser = userService.getCurrentUser();
        Date toDate = new Date();

        String locationType = null;
        Long locationId = null;
//        ReportRequest reportRequest = new ReportRequest();
//        reportRequest.setPeriodType("MONTH");
//        reportRequest.setFromDate(toDate);
        LOGGER.info("access level: {}",currentUser.getAccessLevel());
        LOGGER.info("access level 1: {}",AccessLevel.NATIONAL.getAccessLevel());
        if (currentUser.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            locationType = "State";
            locationId = 0L; // National level
        } else if (currentUser.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel())) {
            locationType = "District";
            locationId = currentUser.getStateId().longValue();
        } else if (currentUser.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel())) {
            locationType = "Block";
            locationId = currentUser.getDistrictId().longValue();
        } else {
            locationType = "Subcentre";
            locationId = currentUser.getBlockId().longValue();
        }


        Long cumulativeJoinedSubscription = aggregateCumulativeBeneficiaryDao.getCumulativeJoinedSubscription(locationId, locationType, toDate);
        return ResponseEntity.ok(cumulativeJoinedSubscription);
//        Long ashaStarted = 0L;
//        Long ashaCompleted = 0L;
//        for(AggregateCumulativeMA end : cumulativeMAData){
//
//            ashaStarted += Long.valueOf(end.getAshasStarted());
//            ashaCompleted += end.getAshasCompleted();
//
//        }

    }



    @RequestMapping(value = "/downloadReport", method = RequestMethod.GET,produces = "application/vnd.ms-excel")
    @ResponseBody
    public String getBulkDataImportCSV(HttpServletResponse response, @DefaultValue("") @QueryParam("fileName") String fileName,
                                       @DefaultValue("") @QueryParam("rootPath") String rootPath) throws ParseException, java.text.ParseException {
//        adminService.getBulkDataImportCSV();

        User currentUser = userService.getCurrentUser();
        response.setContentType("APPLICATION/OCTECT-STREAM");
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(rootPath)) {
            fileName = "";
            rootPath = "";
            return "fail";
        }
        if (rootPath.contains("..")) {
            throw new IllegalArgumentException("Invalid rootPath");
        }
        String reportName=fileName;
        //String reportPath=reports+rootPath;
        String reportPath = Paths.get(reports, rootPath).normalize().toString();
        try {
            ServletOutputStream out = response.getOutputStream();
            String filename = reportName;
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            FileInputStream fl = new FileInputStream(reportPath +"/"+ reportName);
            int i;
            while ((i = fl.read()) != -1) {
                out.write(i);
            }
            fl.close();
            out.close();
            downloadReportActivityService.recordDownloadActivity(currentUser.getUsername(), reportName, currentUser.getUserId());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/generateAgg", method = RequestMethod.POST,produces = "application/vnd.ms-excel")
    @ResponseBody
    public String generateAggregates(@RequestBody AggregateExcelDto data, HttpServletResponse response) throws ParseException, java.text.ParseException {
        response.setContentType("APPLICATION/OCTECT-STREAM");

        String filename = data.getFileName()+".xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(
                "Sheet 1");
        aggregateReportsService.createSpecificAggreagateExcel(workbook,data);

        File dir = new File(reports+"/Aggregates");
        if (!dir.exists())
            dir.mkdirs();
        FileOutputStream out1 = null;
        try {
            out1 = new FileOutputStream(new File(reports+"/Aggregates/"+filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // workbook.write(response.getOutputStream());

        return "success";
    }

    @RequestMapping(value = "/downloadAgg", method = RequestMethod.GET,produces = "application/vnd.ms-excel")
    @ResponseBody
    public String downloadAggregates(HttpServletResponse response, @DefaultValue("") @QueryParam("fileName") String fileName) throws ParseException, java.text.ParseException {
//        adminService.getBulkDataImportCSV();
        response.setContentType("APPLICATION/OCTECT-STREAM");
        if (StringUtils.isEmpty(fileName)) {
            fileName = "";
            return "fail";
        }
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            FileInputStream fl = new FileInputStream(reports+"/Aggregates/"+fileName);
            int i;
            while ((i = fl.read()) != -1) {
                out.write(i);
            }
            fl.close();
            out.close();
            //workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File(reports + "/Aggregates/" + fileName);
            file.delete();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/downloadAggPdf", method = RequestMethod.POST,produces = "application/pdf")
    @ResponseBody
    public String downloadAggregatesPdf(@RequestBody AggregateExcelDto data, HttpServletResponse response) throws ParseException, java.text.ParseException {
        response.setContentType("application/pdf");

//Creating PDF document object
        PDDocument document = new PDDocument();


        //Saving the document
        try {
            aggregateReportsService.createSpecificAggreagatePdf(document,data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File dir = new File(reports+"/Aggregates");
        if (!dir.exists())
            dir.mkdirs();
        try {document.save(reports + "/Aggregates/"+data.getFileName()+".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Closing the document


        return "success";
    }

    @RequestMapping(value = "/downloadpdf", method = RequestMethod.GET,produces = "application/pdf")
    @ResponseBody
    public String downloadpdf1(HttpServletResponse response, @DefaultValue("") @QueryParam("fileName") String fileName) throws ParseException, java.text.ParseException {
//        adminService.getBulkDataImportCSV();
        response.setContentType("APPLICATION/OCTECT-STREAM");
        if (StringUtils.isEmpty(fileName)) {
            fileName = "";
            return "fail";
        }
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            FileInputStream fl = new FileInputStream(reports+"/Aggregates/"+fileName);
            int i;
            while ((i = fl.read()) != -1) {
                out.write(i);
            }
            fl.close();
            out.close();
            //workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File(reports + "/Aggregates/" + fileName);
            file.delete();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value={"/homePageMenu"} , method = RequestMethod.POST)
    public @ResponseBody List<Map<String, Object>> getHomePageMenu() {
        User currentUser = userService.getCurrentUser();
        Map<String, Object> maMenu = new HashMap<>();
        boolean showAggregateReports = true;
        boolean showLinelistingReports =true;
        maMenu.put("name", "Mobile Academy Reports");
        maMenu.put("icon", "images/drop-down-2.png");

        List<Report> maList = new ArrayList<>();

        maList.add(new Report(
                ReportType.MAHomePageReport.getReportName(),
                ReportType.MAHomePageReport.getReportType(),
                ReportType.MAHomePageReport.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.MAHomePageReport.getServiceType(),showLinelistingReports)
        );

        maMenu.put("service", maList.get(0).getService());
        maMenu.put("options", maList);

        Map<String, Object> kMenu = new HashMap<>();

        kMenu.put("name", "Kilkari Reports");
        kMenu.put("icon", "images/drop-down-2.png");

        List<Report> kList = new ArrayList<>();
        kList.add(new Report(
                ReportType.kilkariHomePageReport.getReportName(),
                ReportType.kilkariHomePageReport.getReportType(),
                ReportType.kilkariHomePageReport.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariHomePageReport.getServiceType(),showLinelistingReports)
        );

        kMenu.put("service", kList.get(0).getService());
        kMenu.put("options", kList);

        List<Map<String, Object>> l = new ArrayList<>();

        if(currentUser.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())){
            l.add(maMenu);
            l.add(kMenu);
        }
        else{
            State state = locationService.findStateById(currentUser.getStateId());
            if(("MOBILE_ACADEMY").equals(state.getServiceType()) || ("ALL").equals(state.getServiceType())){
                l.add(maMenu);
            }
            if(("KILKARI").equals(state.getServiceType()) || ("ALL").equals(state.getServiceType())){
                l.add(kMenu);
            }
        }
        return l;
    }


    @RequestMapping(value={"/reportsMenu"} , method = RequestMethod.POST)
    public @ResponseBody List<Map<String, Object>> getReportsMenu() {
        User currentUser = userService.getCurrentUser();
        Map<String, Object> maMenu = new HashMap<>();
        boolean showAggregateReports = true;
        boolean showLinelistingReports =true;
        maMenu.put("name", "Mobile Academy Reports");
        maMenu.put("icon", "images/drop-down-3.png");

        List<Report> maList = new ArrayList<>();
        maList.add(new Report(
                ReportType.maCourse.getReportName(),
                ReportType.maCourse.getReportType(),
                ReportType.maCourse.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.maCourse.getServiceType(),showLinelistingReports)
        );
        maList.add(new Report(
                ReportType.maAnonymous.getReportName(),
                ReportType.maAnonymous.getReportType(),
                ReportType.maAnonymous.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.maAnonymous.getServiceType(),showLinelistingReports)
        );
        maList.add(new Report(
                ReportType.maInactive.getReportName(),
                ReportType.maInactive.getReportType(),
                ReportType.maInactive.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.maInactive.getServiceType(),showLinelistingReports)
        );
        maList.add(new Report(
                ReportType.maFreshActive.getReportName(),
                ReportType.maFreshActive.getReportType(),
                ReportType.maFreshActive.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.maFreshActive.getServiceType(),showLinelistingReports)
        );
        maList.add(new Report(
                ReportType.flwRejected.getReportName(),
                ReportType.flwRejected.getReportType(),
                ReportType.flwRejected.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.flwRejected.getServiceType(),showLinelistingReports)
        );
        maList.add(new Report(
                ReportType.maPerformance.getReportName(),
                ReportType.maPerformance.getReportType(),
                ReportType.maPerformance.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.maPerformance.getServiceType(),showAggregateReports)
        );
        maList.add(new Report(
                ReportType.maSubscriber.getReportName(),
                ReportType.maSubscriber.getReportType(),
                ReportType.maSubscriber.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.maSubscriber.getServiceType(),showAggregateReports)
        );
        maList.add(new Report(
                ReportType.maCumulative.getReportName(),
                ReportType.maCumulative.getReportType(),
                ReportType.maCumulative.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.maCumulative.getServiceType(),showAggregateReports)
        );

        maMenu.put("service", maList.get(0).getService());
        maMenu.put("options", maList);

        Map<String, Object> kMenu = new HashMap<>();

        kMenu.put("name", "Kilkari Reports");
        kMenu.put("icon", "images/drop-down-3.png");

        List<Report> kList = new ArrayList<>();
        kList.add(new Report(
                ReportType.sixWeeks.getReportName(),
                ReportType.sixWeeks.getReportType(),
                ReportType.sixWeeks.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.sixWeeks.getServiceType(),showLinelistingReports)
        );
        kList.add(new Report(
                ReportType.lowListenership.getReportName(),
                ReportType.lowListenership.getReportType(),
                ReportType.lowListenership.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.lowListenership.getServiceType(),showLinelistingReports)
        );
        kList.add(new Report(
                ReportType.lowUsage.getReportName(),
                ReportType.lowUsage.getReportType(),
                ReportType.lowUsage.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.lowUsage.getServiceType(),showLinelistingReports)
        );
        kList.add(new Report(
                ReportType.selfDeactivated.getReportName(),
                ReportType.selfDeactivated.getReportType(),
                ReportType.selfDeactivated.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.selfDeactivated.getServiceType(),showLinelistingReports)
        );
        kList.add(new Report(
                ReportType.motherRejected.getReportName(),
                ReportType.motherRejected.getReportType(),
                ReportType.motherRejected.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.motherRejected.getServiceType(),showLinelistingReports)
        );
        kList.add(new Report(
                ReportType.childRejected.getReportName(),
                ReportType.childRejected.getReportType(),
                ReportType.childRejected.getSimpleName(),
                "images/drop-down-3.png",
                ReportType.childRejected.getServiceType(),showLinelistingReports)
        );
        kList.add(new Report(
                ReportType.kilkariCumulative.getReportName(),
                ReportType.kilkariCumulative.getReportType(),
                ReportType.kilkariCumulative.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariCumulative.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.beneficiaryCompletion.getReportName(),
                ReportType.beneficiaryCompletion.getReportType(),
                ReportType.beneficiaryCompletion.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.beneficiaryCompletion.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.usage.getReportName(),
                ReportType.usage.getReportType(),
                ReportType.usage.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.usage.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.kilkariCalls.getReportName(),
                ReportType.kilkariCalls.getReportType(),
                ReportType.kilkariCalls.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariCalls.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.kilkariCallsWithBeneficiaries.getReportName(),
                ReportType.kilkariCallsWithBeneficiaries.getReportType(),
                ReportType.kilkariCallsWithBeneficiaries.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariCallsWithBeneficiaries.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.messageMatrix.getReportName(),
                ReportType.messageMatrix.getReportType(),
                ReportType.messageMatrix.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.messageMatrix.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.listeningMatrix.getReportName(),
                ReportType.listeningMatrix.getReportType(),
                ReportType.listeningMatrix.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.listeningMatrix.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.kilkariThematicContent.getReportName(),
                ReportType.kilkariThematicContent.getReportType(),
                ReportType.kilkariThematicContent.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariThematicContent.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.kilkariRepeatListenerMonthWise.getReportName(),
                ReportType.kilkariRepeatListenerMonthWise.getReportType(),
                ReportType.kilkariRepeatListenerMonthWise.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariRepeatListenerMonthWise.getServiceType(),showAggregateReports)
        );

        kList.add(new Report(
                ReportType.kilkariSubscriber.getReportName(),
                ReportType.kilkariSubscriber.getReportType(),
                ReportType.kilkariSubscriber.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariSubscriber.getServiceType(),showAggregateReports)
        );

        kList.add(new Report(
                ReportType.kilkariSubscriberWithRegistrationDate.getReportName(),
                ReportType.kilkariSubscriberWithRegistrationDate.getReportType(),
                ReportType.kilkariSubscriberWithRegistrationDate.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariSubscriberWithRegistrationDate.getServiceType(),showAggregateReports)
        );
        kList.add(new Report(
                ReportType.kilkariMessageListenership.getReportName(),
                ReportType.kilkariMessageListenership.getReportType(),
                ReportType.kilkariMessageListenership.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.kilkariMessageListenership.getServiceType(),showAggregateReports)
        );

        kList.add(new Report(
                ReportType.beneficiary.getReportName(),
                ReportType.beneficiary.getReportType(),
                ReportType.beneficiary.getSimpleName(),
                "images/drop-down-2.png",
                ReportType.beneficiary.getServiceType(),showAggregateReports)
        );


        kMenu.put("service", kList.get(0).getService());
        kMenu.put("options", kList);

        List<Map<String, Object>> l = new ArrayList<>();

        if(currentUser.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())){
            l.add(maMenu);
            l.add(kMenu);
        }
        else{
            State state = locationService.findStateById(currentUser.getStateId());
            if(("MOBILE_ACADEMY").equals(state.getServiceType()) || ("ALL").equals(state.getServiceType())){
                l.add(maMenu);
            }
            if(("KILKARI").equals(state.getServiceType()) || ("ALL").equals(state.getServiceType())){
                l.add(kMenu);
            }
        }
        return l;
    }

    @RequestMapping(value = {"/createMaster"}, method = RequestMethod.GET)
    @ResponseBody String createNewUser() {
//
////        ModificationTracker modification = new ModificationTracker();
////        modification.setModificationDate(new Date(System.currentTimeMillis()));
////        modification.setModificationDescription("Account creation");
////        modification.setModificationType(ModificationType.CREATE.getModificationType());
////        modification.setModifiedUserId(user);
////        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
////        modificationTrackerService.saveModification(modification);
//
        return userService.createMaster();
    }
    private String getMonthYear(Date toDate) {
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
        int month=c.get(Calendar.MONTH)+1;
        String monthString = "";
        int year=(c.get(Calendar.YEAR))%100;
        //        String monthString = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+"_"+yearString;
    }

    private String getDateMonthYear(Date toDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(toDate);
        int date=calendar.get(Calendar.DATE);
        int month=calendar.get(Calendar.MONTH)+1;
        int year=(calendar.get(Calendar.YEAR))%100;
        String dateString;
        if(date<10) {
            dateString="0"+String.valueOf(date);
        }
        else dateString=String.valueOf(date);
        String monthString;
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return dateString + "_" + monthString+"_"+yearString;

    }

    private boolean serviceStarted(Integer locationId,String locationType, Date toDate, Date fromDate, String sreviceType){
        if(locationType.equalsIgnoreCase("State")){
            return !toDate.before(stateServiceDao.getServiceStartDateForState( locationId,sreviceType));
        }else{
            if(locationType.equalsIgnoreCase("District")){
                return !toDate.before(stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),sreviceType));
            }else{
                if(locationType.equalsIgnoreCase("Block")){
                    return !toDate.before(stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),sreviceType));
                }
            }
        }
        return true;
    }



    @RequestMapping(value={"asha/district/{districtId}/{year}"})
    public @ResponseBody List<AshaInEachBlock> downloadReport(@PathVariable("districtId") Integer districtId, @PathVariable("year") Integer year) {

        return ashaEachBlockServiceDao.setQuery(districtId, year);
    }

    @RequestMapping(value = "asha/certificate", method = RequestMethod.POST)
    @ResponseBody
    public Object generateCertificate( @RequestParam Long  msisdn) throws ParseException, IOException {

        Matcher matcher = Pattern.compile("\\d{10}").matcher(msisdn.toString());
        if(!matcher.matches()) {
            return "Invalid phone number";
        }

        User currentUser = userService.getCurrentUser();

        if(!currentUser.getAccountStatus().equalsIgnoreCase("ACTIVE")){
            return "User is deactivated";
        }
        // check weather certificate is generated or not

        return certificateService.createSpecificCertificate( msisdn, currentUser);

    }

    @RequestMapping(value = "/downloadCertificate", method = RequestMethod.GET,produces = "application/pdf")
    @ResponseBody
    public String getCertificate(HttpServletResponse response, @DefaultValue("") @QueryParam("fileName") String fileName,
                                 @DefaultValue("") @QueryParam("rootPath") String rootPath) throws ParseException {

        response.setContentType("application/pdf");
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(rootPath)) {
            return "fail";
        }

        User currentUser = userService.getCurrentUser();
        if( !currentUser.getAccountStatus().equalsIgnoreCase("ACTIVE") ){
            return "User is deactivated";
        }
        if (rootPath.contains("..") || fileName.contains("..")) {
            throw new IllegalArgumentException("Invalid rootPath");
        }

        String reportPath = certificate + "/Asha" + "/"+ rootPath +"/";
        String reportPath2 = Paths.get(reportPath).normalize().toString();
        String accessLevel = currentUser.getAccessLevel();

        String[] ids = rootPath.split("/");
        String authorization = "";
        if(accessLevel.equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            authorization = "Authorized";
        }
        else if( accessLevel.equalsIgnoreCase(AccessLevel.STATE.getAccessLevel()) &&
                currentUser.getStateId() == Integer.parseInt(ids[0]) ){
            authorization = "Authorized";
        }
        else if( accessLevel.equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel()) &&
                currentUser.getStateId() == Integer.parseInt(ids[0]) && currentUser.getDistrictId() == Integer.parseInt(ids[1]) ){
            authorization = "Authorized";
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.BLOCK.getAccessLevel()) &&
                currentUser.getStateId() == Integer.parseInt(ids[0]) && currentUser.getDistrictId() == Integer.parseInt(ids[1]) &&
                currentUser.getBlockId() == Integer.parseInt(ids[2])){
            authorization = "Authorized";
        }

        if( !authorization.equals("Authorized") ){
            return "Not Authorized";
        }

        try {
            ServletOutputStream out = response.getOutputStream();

            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            FileInputStream fl = new FileInputStream(reportPath2 + "/" + fileName);
            int i;
            while ((i = fl.read()) != -1) {
                out.write(i);
            }
            fl.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


    @RequestMapping(value = "asha/bulkcertificate", method = RequestMethod.POST)
    @ResponseBody
    public Object generateCertificateInBulk(@RequestBody CertificateRequest certificateRequest ) throws ParseException, IOException {

//        User currentUser = new User();
//        currentUser.setAccessLevel(AccessLevel.STATE.getAccessLevel());
//        currentUser.setStateId(32);
//        currentUser.setAccountStatus("ACTIVE");
//        currentUser.setDistrictId();
        Map<String, String> response = new HashMap<>();
        User currentUser = userService.getCurrentUser();
        if(!currentUser.getAccountStatus().equalsIgnoreCase("ACTIVE")){
            response.put("status","failed");
            response.put("message","User is deactivated");
            return response;
        }

        String accessLevel = currentUser.getAccessLevel();
        String formonth = getMonthYear(certificateRequest.getDate());
        String zipDir = formonth;
        String queryLevel = "";
        List<String> directories = new ArrayList<>();
        boolean isAuthenticated = false;

        Integer stateId = certificateRequest.getStateId();
        Integer districtId = certificateRequest.getDistrictId();
        Integer blockId = certificateRequest.getBlockId();
        HashMap<Integer, String> districtMap = new HashMap<>();
        HashMap<Integer, HashMap<Integer, String>> blockMap = new HashMap<>();

        String stateName = stateDao.findByStateId(stateId).getStateName();
        List<District> districts = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();


        if( accessLevel.equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel()) || (
                accessLevel.equalsIgnoreCase(AccessLevel.STATE.getAccessLevel()) &&
                        stateId.equals(currentUser.getStateId()) )
        ){
            districts = districtDao.getDistrictsOfState(stateId);
            districtMap = getDistrictMap(districts);
            for (District district : districts){
                blockMap.put(district.getDistrictId(), getBlockMap(blockDao.getBlocksOfDistrict(district.getDistrictId())));
            }

            isAuthenticated = true;
            zipDir = zipDir + "/" + stateId +"_" + stateName;
            directories.add(zipDir);
            if(districtId!=0){
                zipDir = zipDir + "/" + districtId + "_" + districtMap.get(districtId);
                directories.add(zipDir);
                if(blockId!=0){
                    zipDir = zipDir + "/" + blockId + "_" + blockMap.get(districtId).get(blockId);
                    directories.add(zipDir);
                    queryLevel = "BLOCK";
                }else {
                    queryLevel = "DISTRICT";
                }
            }
            else {
                queryLevel = "STATE";
            }
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel()) &&
                (stateId.equals(currentUser.getStateId())) &&
                (districtId.equals(currentUser.getDistrictId()))
        ){
            districts.add(districtDao.findByDistrictId(districtId));
            districtMap = getDistrictMap(districts);
            blockMap.put(districtId,getBlockMap(blockDao.getBlocksOfDistrict(districtId)));

            isAuthenticated = true;
            directories.add(zipDir + "/" + stateId +"_" + stateName);
            zipDir = zipDir + "/" + stateId + "_" + stateName + "/" + districtId + "_" + districtMap.get(districtId);
            directories.add(zipDir);
            if(blockId==0){
                queryLevel = "DISTRICT";
            }
            else {
                queryLevel = "BLOCK";
                zipDir = zipDir + "/" + blockId + "_" + blockMap.get(districtId).get(blockId);
                directories.add(zipDir);
            }
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.BLOCK.getAccessLevel())&&
                (stateId.equals(currentUser.getStateId())) &&
                (districtId.equals(currentUser.getDistrictId())) &&
                (blockId.equals(currentUser.getBlockId()))
        ){
            districts.add(districtDao.findByDistrictId(districtId));
            districtMap = getDistrictMap(districts);
            blocks.add(blockDao.findByblockId(blockId));
            blockMap.put(districtId, getBlockMap(blocks));
            zipDir = zipDir + "/" + stateId +"_" + stateName;
            directories.add(zipDir);
            zipDir = zipDir + "/" + districtId + "_" + districtMap.get(districtId);
            directories.add(zipDir);
            isAuthenticated = true;
            queryLevel = "BLOCK";
            zipDir = zipDir + "/" + blockId + "_" + blockMap.get(districtId).get(blockId);
            directories.add(zipDir);
        }

        if(!isAuthenticated){
            response.put("status","failed");
            response.put("message","You don't have enough permission");
            return response;
        }

        boolean auditable = !(getMonthYear(new Date()).equalsIgnoreCase(formonth));
        if(bulkCertificateAuditDao.findByFileDirectoryAndMonth(directories).isEmpty()) {
            response = certificateService.createCertificateInBulk(certificateRequest, formonth, auditable , queryLevel, zipDir, currentUser, stateName, districtMap, blockMap);
        }
        else {
            File fileDr = new File( documents+"Certificate/Asha/"+zipDir);
            if(!fileDr.exists()){
                response.put("status", "failed");
                response.put("message","NO Asha is available for the certificate with the above criteria");
            }
            else {
                response.put("status", "success");
                response.put("message", "certificate generated with the above criteria");
            }

            response.put("fileDir", zipDir);
        }
        return response;
    }


    @RequestMapping(value = "/certificate/bulkdownload", method = RequestMethod.GET, produces="application/zip")
    @ResponseBody
    public String getCertificateInBulk(HttpServletResponse response,
                                 @DefaultValue("") @QueryParam("zipDir") String zipDir) throws ParseException, IOException {
//
//        User currentUser = new User();
//        currentUser.setAccessLevel(AccessLevel.STATE.getAccessLevel());
//        currentUser.setStateId(32);
        User currentUser = userService.getCurrentUser();

        if (StringUtils.isEmpty(zipDir)) {
            return "fail";
        }

        if( !currentUser.getAccountStatus().equalsIgnoreCase("ACTIVE") ){
            return "User is deactivated";
        }

        String accessLevel = currentUser.getAccessLevel();
        String authString = "";
        String fileName = "";
        if(accessLevel.equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            authString = zipDir;
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
           authString = authString + currentUser.getStateId() + "_" + stateDao.findByStateId(currentUser.getStateId()).getStateName();
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())){
            authString = authString + currentUser.getStateId() + "_" + stateDao.findByStateId(currentUser.getStateId()).getStateName() + "/" +
                    currentUser.getDistrictId() + "_" + districtDao.findByDistrictId(currentUser.getDistrictId()).getDistrictName();
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.BLOCK.getAccessLevel())){
            authString = authString + currentUser.getStateId() + "_" + stateDao.findByStateId(currentUser.getStateId()).getStateName() + "/" +
                    currentUser.getDistrictId() + "_" + districtDao.findByDistrictId(currentUser.getDistrictId()).getDistrictName()
                    + "/" + currentUser.getBlockId() + "_" + blockDao.findByblockId(currentUser.getBlockId()).getBlockName();
        }
        else {
            return  "Not enough permission";
        }
        if(!zipDir.contains(authString)){
            return  "Not enough permission";
        }

        String sourceFile = documents +"Certificate/Asha/" + zipDir;

        response.setStatus(HttpServletResponse.SC_OK);
        String responseHeader ="attachment; filename=\"" +"Certificates_"+new Timestamp(System.currentTimeMillis())+".zip"+ "\"";
        response.addHeader("Content-Disposition", responseHeader);

//        sourceFile = retrieveDocuments()+zipDir;
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        File fileToZip = new File(sourceFile);

        CertificateServiceImpl.zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();

        return "success";
    }

    private HashMap<Integer, String> getDistrictMap(List<District> districts){
        HashMap<Integer, String> districtMap = new HashMap<>();
        for (District district : districts){
            districtMap.put(district.getDistrictId(),district.getDistrictName());
        }
        return districtMap;
    }

    private HashMap<Integer, String> getBlockMap(List<Block> blocks){
        HashMap<Integer, String> blockMap = new HashMap<>();
        for (Block block : blocks){
            blockMap.put(block.getBlockId(),block.getBlockName());
        }
        return blockMap;
    }

    @RequestMapping(value = "asha/bulkcertificateTillLastMonth/{stateId}", method = RequestMethod.GET)
    @ResponseBody
    public String generateTotalCertificateInBulkDistrictWise(@PathVariable Integer stateId ){

        Map<String, String> response = new HashMap<>();
        Gson gson = new Gson();
        String formonth = getMonthYear(new Date());

        HashMap<Integer, String> districtMap = new HashMap<>();
        HashMap<Integer, HashMap<Integer, String>> blockMap = new HashMap<>();
        State state = stateDao.findByStateId(stateId);
        List<District> districts;

            districts = districtDao.getDistrictsOfState(stateId);
            districtMap = getDistrictMap(districts);
            for (District district : districts){
                blockMap.put(district.getDistrictId(), getBlockMap(blockDao.getBlocksOfDistrict(district.getDistrictId())));
            }

            response = certificateService.createAllCertificateUptoCurrentMonthInBulk(formonth, state, districtMap, blockMap);

        return gson.toJson(response);
    }


}
