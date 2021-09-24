package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.*;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.dao.impl.MAPerformanceDaoImpl;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.enums.ModificationType;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.*;
import com.beehyv.nmsreporting.utils.LoginUser;
import com.beehyv.nmsreporting.utils.ServiceFunctions;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import javassist.bytecode.ByteArray;
import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.beehyv.nmsreporting.utils.CryptoService.decrypt;
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

        if (reportRequest.getReportType().equals(ReportType.maPerformance.getReportType())) {

            Date fromDate = dateAdder(reportRequest.getFromDate(),0);

            Date toDate = dateAdder(reportRequest.getToDate(),1);

                List<MAPerformanceDto> summaryDto = new ArrayList<>();
                List<AggregateCumulativeMA> cumulativesummaryReportStart = new ArrayList<>();
                List<AggregateCumulativeMA> cumulativesummaryReportEnd = new ArrayList<>();
                HashMap<Long,MAPerformanceCountsDto> performanceCounts = new HashMap<>();

            if (reportRequest.getStateId() == 0) {
                cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", fromDate,false));
                cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", toDate,false));
                performanceCounts = maPerformanceService.getMAPerformanceCounts(0,"State",fromDate,toDate);
            } else {
                if (reportRequest.getDistrictId() == 0) {
                    cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", fromDate,false));
                    cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", toDate,false));
                    performanceCounts = maPerformanceService.getMAPerformanceCounts(reportRequest.getStateId(),"District",fromDate,toDate);
                } else {
                    if (reportRequest.getBlockId() == 0) {
                        cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", fromDate,false));
                        cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", toDate,false));
                        performanceCounts = maPerformanceService.getMAPerformanceCounts(reportRequest.getDistrictId(),"Block",fromDate,toDate);
                    } else {
                        cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcentre", fromDate,false));
                        cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcentre", toDate,false));
                        performanceCounts = maPerformanceService.getMAPerformanceCounts(reportRequest.getBlockId(),"Subcentre",fromDate,toDate);
                    }
                }
            }

            for (int i = 0; i < cumulativesummaryReportEnd.size(); i++) {
                for (int j = 0; j < cumulativesummaryReportStart.size(); j++) {
                    if (cumulativesummaryReportEnd.get(i).getLocationId().equals(cumulativesummaryReportStart.get(j).getLocationId())) {
                        AggregateCumulativeMA a = cumulativesummaryReportEnd.get(i);
                        AggregateCumulativeMA b = cumulativesummaryReportStart.get(j);
                        MAPerformanceDto summaryDto1 = new MAPerformanceDto();
                        summaryDto1.setId(a.getId());
                        summaryDto1.setLocationId(a.getLocationId());
                        summaryDto1.setAshasCompleted(a.getAshasCompleted() - b.getAshasCompleted());
//                            summaryDto1.setAshasFailed(a.getAshasFailed() - b.getAshasFailed());
                        summaryDto1.setAshasStarted(a.getAshasStarted() - b.getAshasStarted());
                        summaryDto1.setLocationType(a.getLocationType());

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
                            MAPerformanceCountsDto MAperformanceCounts = performanceCounts.get(a.getLocationId());
                            summaryDto1.setAshasFailed(MAperformanceCounts.getAshasFailed());
                            summaryDto1.setAshasAccessed(MAperformanceCounts.getAccessedAtleastOnce());
                            summaryDto1.setAshasNotAccessed(MAperformanceCounts.getAccessedNotOnce());
                            summaryDto1.setAshasActivated(MAperformanceCounts.getAshasActivated());
                            summaryDto1.setAshasDeactivated(MAperformanceCounts.getAshasDeactivated());
                            summaryDto1.setAshasRefresherCourse(MAperformanceCounts.getAshasRefresherCourse());
                            summaryDto1.setAshasJoined(MAperformanceCounts.getAshasActivated()+MAperformanceCounts.getAshasDeactivated());
//                            summaryDto1.setAshasFailed(maPerformanceService.getAshasFailed(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));
//                            summaryDto1.setAshasAccessed(maPerformanceService.getAccessedCount(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));
//                            summaryDto1.setCompletedPercentage(a.getAshasCompleted()*100/a.getAshasStarted());
//                            summaryDto1.setFailedpercentage(a.getAshasFailed()*100/a.getAshasStarted());
//                            summaryDto1.setNotStartedpercentage(a.getAshasNotStarted()*100/a.getAshasRegistered());
//                            summaryDto1.setAshasNotAccessed(maPerformanceService.getNotAccessedcount(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));

                        if (summaryDto1.getAshasCompleted() + summaryDto1.getAshasFailed() + summaryDto1.getAshasStarted() + summaryDto1.getAshasAccessed() + summaryDto1.getAshasNotAccessed() != 0 &&!locationType.equalsIgnoreCase("DifferenceState")) {
                            summaryDto.add(summaryDto1);
                        }
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

            if (reportRequest.getStateId() == 0) {
                cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", fromDate,false));
                cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", toDate,false));
            } else {
                if (reportRequest.getDistrictId() == 0) {
                    cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", fromDate,false));
                    cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", toDate,false));
                } else {
                    if (reportRequest.getBlockId() == 0) {
                        cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", fromDate,false));
                        cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", toDate,false));
                    } else {
                        cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcentre", fromDate,false));
                        cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcentre", toDate,false));
                    }
                }


            }

                for (int i = 0; i < cumulativesummaryReportEnd.size(); i++) {

                for (int j = 0; j < cumulativesummaryReportStart.size(); j++) {
                    boolean showRow = true;
                    if (cumulativesummaryReportEnd.get(i).getLocationId().equals(cumulativesummaryReportStart.get(j).getLocationId())) {
                        AggregateCumulativeMA a = cumulativesummaryReportEnd.get(i);
                        AggregateCumulativeMA b = cumulativesummaryReportStart.get(j);
                        MASubscriberDto summaryDto1 = new MASubscriberDto();
                        summaryDto1.setId(a.getId());
                        summaryDto1.setLocationId(a.getLocationId());
                        summaryDto1.setAshasCompleted(a.getAshasCompleted() - b.getAshasCompleted());
                        summaryDto1.setAshasFailed(a.getAshasFailed() - b.getAshasFailed());
                        summaryDto1.setAshasRegistered(a.getAshasRegistered() - b.getAshasRegistered());
                        summaryDto1.setAshasNotStarted(a.getAshasNotStarted() - b.getAshasNotStarted());
                        summaryDto1.setAshasStarted(a.getAshasStarted() - b.getAshasStarted());

                        summaryDto1.setAshasRejected(a.getAshasRejected() - b.getAshasRejected());

                        summaryDto1.setLocationType(a.getLocationType());
                        summaryDto1.setRegisteredNotCompletedStart(b.getAshasRegistered() - b.getAshasCompleted());
                        summaryDto1.setRegisteredNotCompletedend(a.getAshasRegistered() - a.getAshasCompleted());
                        summaryDto1.setRecordsReceived((a.getAshasRegistered() + a.getAshasRejected()) - (b.getAshasRejected() + b.getAshasRegistered()));

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

                        if ((summaryDto1.getAshasCompleted() + summaryDto1.getAshasStarted() + summaryDto1.getAshasFailed() + summaryDto1.getAshasRejected()
                                + summaryDto1.getAshasRegistered() + summaryDto1.getRegisteredNotCompletedend()
                                + summaryDto1.getRegisteredNotCompletedStart() + summaryDto1.getRecordsReceived() + summaryDto1.getAshasNotStarted() != 0 &&!locationType.equalsIgnoreCase("DifferenceState"))&&showRow) {
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

                if (summaryDto1.getAshasRegistered()+summaryDto1.getAshasCompleted()+summaryDto1.getAshasNotStarted()+summaryDto1.getAshasStarted()
                        +summaryDto1.getAshasFailed() !=0 && a.getId() != 0 && !locationType.equalsIgnoreCase("DifferenceState")) {
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



    @RequestMapping(value = "/downloadReport", method = RequestMethod.GET,produces = "application/vnd.ms-excel")
    @ResponseBody
    public String getBulkDataImportCSV(HttpServletResponse response, @DefaultValue("") @QueryParam("fileName") String fileName,
                                       @DefaultValue("") @QueryParam("rootPath") String rootPath) throws ParseException, java.text.ParseException {
//        adminService.getBulkDataImportCSV();
        response.setContentType("APPLICATION/OCTECT-STREAM");
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(rootPath)) {
            fileName = "";
            rootPath = "";
            return "fail";
        }
        String reportName=fileName;
        String reportPath=reports+rootPath;
        try {
            ServletOutputStream out = response.getOutputStream();
            String filename = reportName;
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            FileInputStream fl = new FileInputStream(reportPath + reportName);
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
    public Object downloadAnmCertificate( @RequestParam Long  msisdn) throws ParseException, IOException {

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

        String reportPath = certificate + "/Asha" + "/"+ rootPath +"/";
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
            FileInputStream fl = new FileInputStream(reportPath + fileName);
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

}
