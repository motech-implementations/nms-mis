package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.*;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.dao.SubcenterDao;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.enums.ModificationType;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.beehyv.nmsreporting.utils.Global.retrieveDocuments;
import static com.beehyv.nmsreporting.utils.ServiceFunctions.StReplace;

/**
 * Created by beehyv on 15/3/17.
 */
@Controller
@RequestMapping("/nms/user")
public class UserController {
    @Autowired
    private UserService userService;

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
    private SubcenterDao subcenterDao;

    @Autowired
    private MAPerformanceService maPerformanceService;


    @Autowired
    private AggregateKilkariReportsService aggregateKilkariReportsService;

    @Autowired
    private BreadCrumbService breadCrumbService;

    private final Date bigBang = new Date(0);
    private final String documents = retrieveDocuments();
    private final String reports = documents+"Reports/";
    private Calendar c =Calendar.getInstance();
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

    @RequestMapping(value={"/profile"})
    public @ResponseBody UserDto profile() {
        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
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

    @RequestMapping(value={"/isLoggedIn"})
    public @ResponseBody Boolean isLoggedIn() {
        return userService.getCurrentUser() != null;
    }

    @RequestMapping(value={"/isAdminLoggedIn"})
    public @ResponseBody Boolean isAdminLoggedIn() {
        User currentUser = userService.getCurrentUser();
        if(currentUser == null || currentUser.getRoleName().equals(AccessType.USER.getAccessType())){
            return false;
        }
        return true;
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
    }

    @RequestMapping(value={"/user/{userId}"})
    public @ResponseBody User getUserById(@PathVariable("userId") Integer userId) {
        return userService.findUserByUserId(userId);
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
    @ResponseBody public Map<Integer, String> createNewUser(@RequestBody User user) {

        user = locationService.SetLocations(user);
        Map<Integer,String> map = userService.createNewUser(user);
        if(map.get(0).equals("User Created")){
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.CREATE.getModificationType());
            modification.setModifiedUserId(userService.findUserByUsername(user.getUsername()).getUserId());
            modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
            modificationTrackerService.saveModification(modification);
        }
        return map;
    }

    @RequestMapping(value = {"/updateUser"}, method = RequestMethod.POST)
    @ResponseBody public Map updateExistingUser(@RequestBody User user) {

        user = locationService.SetLocations(user);
        User oldUser = userService.findUserByUserId(user.getUserId());
        Map<Integer,String> map = userService.updateExistingUser(user);
        if(map.get(0).equals("User Updated")){
            userService.TrackModifications(oldUser, user);
        }
        return map;
    }

    @RequestMapping(value = {"/updateContacts"}, method = RequestMethod.POST)
    @ResponseBody public Map updateContacts(@RequestBody ContactInfo contactInfo) {
        User user=userService.findUserByUserId(contactInfo.getUserId());
        Map<Integer, String> map=userService.updateContacts(contactInfo);
        if(map.get(0).equals("Contacts Updated")){
            TrackContactInfoModifications(user, contactInfo);
        }
        return map;
    }

    private void TrackContactInfoModifications(User oldUser, ContactInfo contactInfo) {
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

    @RequestMapping(value = {"/resetPassword"}, method = RequestMethod.POST)
    @ResponseBody public Map resetPassword(@RequestBody PasswordDto passwordDto){

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
    }


    @RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.POST)
    @ResponseBody
    public Map forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){

        User user=userService.findUserByUsername(forgotPasswordDto.getUsername());
        Map<Integer, String >map=  userService.forgotPasswordCredentialChecker(forgotPasswordDto);
        if(map.get(0).equals("Password changed successfully")){
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.UPDATE.getModificationType());
            modification.setModifiedUserId(user.getUserId());
            modification.setModifiedField("password");
            modification.setModifiedByUserId(user.getUserId());
            modificationTrackerService.saveModification(modification);
        }
        return map;
    }



    @RequestMapping(value = {"/deleteUser/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Map deleteExistingUser(@PathVariable("id") Integer id) {

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

        MessageMatrixResponseDto messageMatrixResponseDto = new MessageMatrixResponseDto();

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
            return aggregateKilkariReportsService.getMessageMatrixReport(reportRequest, currentUser);
        }

        if(reportRequest.getReportType().equals(ReportType.kilkariRepeatListenerMonthWise.getReportType())) {
            AggregateKilkariRepeatListenerMonthWiseDto aggregateKilkariRepeatListenerMonthWiseDto = new AggregateKilkariRepeatListenerMonthWiseDto();
            aggregateKilkariRepeatListenerMonthWiseDto = aggregateKilkariReportsService.getKilkariRepeatListenerMonthWiseReport(reportRequest);
            return aggregateKilkariRepeatListenerMonthWiseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.kilkariCalls.getReportType())) {
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            List<KilkariCallReportDto> kilkariCallReportDtos = aggregateKilkariReportsService.getKilkariCallReport(reportRequest, currentUser);
            aggregateResponseDto.setTableData(kilkariCallReportDtos);
            return aggregateResponseDto;
        }

            if (reportRequest.getReportType().equals(ReportType.maPerformance.getReportType())) {
                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Calendar calendar = Calendar.getInstance();
                Date toDate = new Date();
                Date startDate = new Date(0);
                Calendar aCalendar = Calendar.getInstance();
                aCalendar.setTime(reportRequest.getFromDate());
                aCalendar.set(Calendar.MILLISECOND, 0);
                aCalendar.set(Calendar.SECOND, 0);
                aCalendar.set(Calendar.MINUTE, 0);
                aCalendar.set(Calendar.HOUR_OF_DAY, 0);


                aCalendar.add(Calendar.DATE, -1);
                Date fromDate = aCalendar.getTime();
                aCalendar.setTime(reportRequest.getToDate());
                aCalendar.set(Calendar.MILLISECOND, 0);
                aCalendar.set(Calendar.SECOND, 0);
                aCalendar.set(Calendar.MINUTE, 0);
                aCalendar.set(Calendar.HOUR_OF_DAY, 0);
                toDate = aCalendar.getTime();


                List<MAPerformanceDto> summaryDto = new ArrayList<>();
                List<AggregateCumulativeMA> cumulativesummaryReportStart = new ArrayList<>();
                List<AggregateCumulativeMA> cumulativesummaryReportEnd = new ArrayList<>();

                if (reportRequest.getStateId() == 0) {
                    cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", fromDate));
                    cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", toDate));
                } else {
                    if (reportRequest.getDistrictId() == 0) {
                        cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", fromDate));
                        cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", toDate));
                    } else {
                        if (reportRequest.getBlockId() == 0) {
                            cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", fromDate));
                            cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", toDate));

                        } else {
                            cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcenter", fromDate));
                            cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcenter", toDate));

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
                            if (locationType.equalsIgnoreCase("Subcenter")) {
                                summaryDto1.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId().intValue()).getSubcenterName());
                            }
                            if (locationType.equalsIgnoreCase("DifferenceState")) {
                                summaryDto1.setLocationName("No District Count");
                                summaryDto1.setLocationId((long) -1);
                            }
                            if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                                summaryDto1.setLocationName("No Block Count");
                                summaryDto1.setLocationId((long) -1);

                            }
                            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                                summaryDto1.setLocationName("No Subcenter Count");
                                summaryDto1.setLocationId((long) -1);

                            }
                            summaryDto1.setAshasFailed(maPerformanceService.getAshasFailed(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));
                            summaryDto1.setAshasAccessed(maPerformanceService.getAccessedCount(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));
//                        summaryDto1.setCompletedPercentage(a.getAshasCompleted()*100/a.getAshasStarted());
//                        summaryDto1.setFailedpercentage(a.getAshasFailed()*100/a.getAshasStarted());
//                        summaryDto1.setNotStartedpercentage(a.getAshasNotStarted()*100/a.getAshasRegistered());
                            summaryDto1.setAshasNotAccessed(maPerformanceService.getNotAccessedcount(a.getLocationId().intValue(), a.getLocationType(), fromDate, toDate));

                            if (summaryDto1.getAshasCompleted() + summaryDto1.getAshasFailed() + summaryDto1.getAshasStarted() + summaryDto1.getAshasAccessed() + summaryDto1.getAshasNotAccessed() != 0) {
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
                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Calendar calendar = Calendar.getInstance();
                Date toDate = new Date();
                Date startDate = new Date(0);
                Calendar aCalendar = Calendar.getInstance();
                aCalendar.setTime(reportRequest.getFromDate());
                aCalendar.set(Calendar.MILLISECOND, 0);
                aCalendar.set(Calendar.SECOND, 0);
                aCalendar.set(Calendar.MINUTE, 0);
                aCalendar.set(Calendar.HOUR_OF_DAY, 0);

//          aCalendar.add(Calendar.MONTH, -1);
                aCalendar.add(Calendar.DATE, -1);
                Date fromDate = aCalendar.getTime();


                aCalendar.setTime(reportRequest.getToDate());
                aCalendar.set(Calendar.MILLISECOND, 0);
                aCalendar.set(Calendar.SECOND, 0);
                aCalendar.set(Calendar.MINUTE, 0);
                aCalendar.set(Calendar.HOUR_OF_DAY, 0);
                toDate = aCalendar.getTime();


                List<MASubscriberDto> summaryDto = new ArrayList<>();
                List<AggregateCumulativeMA> cumulativesummaryReportStart = new ArrayList<>();
                List<AggregateCumulativeMA> cumulativesummaryReportEnd = new ArrayList<>();

                if (reportRequest.getStateId() == 0) {
                    cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", fromDate));
                    cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", toDate));
                } else {
                    if (reportRequest.getDistrictId() == 0) {
                        cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", fromDate));
                        cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", toDate));
                    } else {
                        if (reportRequest.getBlockId() == 0) {
                            cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", fromDate));
                            cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", toDate));
                        } else {
                            cumulativesummaryReportStart.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcenter", fromDate));
                            cumulativesummaryReportEnd.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcenter", toDate));
                        }
                    }


                }

                for (int i = 0; i < cumulativesummaryReportEnd.size(); i++) {
                    boolean notAvailable = true;
                    for (int j = 0; j < cumulativesummaryReportStart.size(); j++) {

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
                            summaryDto1.setRegisteredNotCompletedStart(b.getAshasRegistered() - b.getAshasCompleted() - b.getAshasFailed());
                            summaryDto1.setRegisteredNotCompletedend(a.getAshasRegistered() - a.getAshasCompleted() - a.getAshasFailed());
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
                            if (locationType.equalsIgnoreCase("Subcenter")) {
                                summaryDto1.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId().intValue()).getSubcenterName());
                            }
                            if (locationType.equalsIgnoreCase("DifferenceState")) {
                                summaryDto1.setLocationName("No District Count");
                                summaryDto1.setLocationId((long) -1);

                            }
                            if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                                summaryDto1.setLocationName("No Block Count");
                                summaryDto1.setLocationId((long) -1);

                            }
                            if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                                summaryDto1.setLocationName("No Subcenter Count");
                                summaryDto1.setLocationId((long) -1);

                            }
                            notAvailable = false;
                            if (summaryDto1.getAshasCompleted() + summaryDto1.getAshasStarted() + summaryDto1.getAshasFailed() + summaryDto1.getAshasRejected()
                                    + summaryDto1.getAshasRegistered() + summaryDto1.getRegisteredNotCompletedend()
                                    + summaryDto1.getRegisteredNotCompletedStart() + summaryDto1.getRecordsReceived() + summaryDto1.getAshasNotStarted() != 0) {
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
//            List<Map<String,String>> summaryReport = new ArrayList<>();
                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Calendar calendar = Calendar.getInstance();
                Date toDate = new Date();
                Date startDate = new Date(0);
                Calendar aCalendar = Calendar.getInstance();
                aCalendar.setTime(reportRequest.getToDate());
                aCalendar.set(Calendar.MILLISECOND, 0);
                aCalendar.set(Calendar.SECOND, 0);
                aCalendar.set(Calendar.MINUTE, 0);
                aCalendar.set(Calendar.HOUR_OF_DAY, 0);

//        aCalendar.add(Calendar.MONTH, -1);

                toDate = aCalendar.getTime();
                List<AggregateCumulativeMADto> summaryDto = new ArrayList<>();
                List<AggregateCumulativeMA> cumulativesummaryReport = new ArrayList<>();

                if (reportRequest.getStateId() == 0) {
                    cumulativesummaryReport.addAll(aggregateReportsService.getCumulativeSummaryMAReport(0, "State", toDate));
                } else {
                    if (reportRequest.getDistrictId() == 0) {
                        cumulativesummaryReport.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getStateId(), "District", toDate));
                    } else {
                        if (reportRequest.getBlockId() == 0) {
                            cumulativesummaryReport.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getDistrictId(), "Block", toDate));
                        } else {
                            cumulativesummaryReport.addAll(aggregateReportsService.getCumulativeSummaryMAReport(reportRequest.getBlockId(), "Subcenter", toDate));
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
                    summaryDto1.setCompletedPercentage((float) (a.getAshasStarted() == 0 ? 0 : (a.getAshasCompleted() * 10000 / a.getAshasStarted())) / 100);
                    summaryDto1.setFailedpercentage((float) (a.getAshasStarted() == 0 ? 0 : (a.getAshasFailed() * 10000 / a.getAshasStarted())) / 100);
                    summaryDto1.setNotStartedpercentage((float) (a.getAshasRegistered() == 0 ? 0 : (a.getAshasNotStarted() * 10000 / a.getAshasRegistered())) / 100);
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
                    if (locationType.equalsIgnoreCase("Subcenter")) {
                        summaryDto1.setLocationName(subcenterDao.findBySubcenterId(a.getLocationId().intValue()).getSubcenterName());
                    }
                    if (locationType.equalsIgnoreCase("DifferenceState")) {
                        summaryDto1.setLocationName("No District");
                        summaryDto1.setLocationId((long) -1);

                    }
                    if (locationType.equalsIgnoreCase("DifferenceDistrict")) {
                        summaryDto1.setLocationName("No Block");
                        summaryDto1.setLocationId((long) -1);

                    }
                    if (locationType.equalsIgnoreCase("DifferenceBlock")) {
                        summaryDto1.setLocationName("No Subcenter");
                        summaryDto1.setLocationId((long) -1);

                    }

                    if (a.getId() != 0) {
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
            if (reportRequest.getReportType().equals(ReportType.flwRejected.getReportType()) ||
                    reportRequest.getReportType().equals(ReportType.motherRejected.getReportType()) ||
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

    @RequestMapping(value={"/reportsMenu"})
    public @ResponseBody List<Map<String, Object>> getReportsMenu() {
        User currentUser = userService.getCurrentUser();
        Map<String, Object> maMenu = new HashMap<>();
        maMenu.put("name", "Mobile Academy Reports");
        maMenu.put("icon", "images/drop-down-3.png");

        List<Report> maList = new ArrayList<>();
        maList.add(new Report(
                ReportType.maCourse.getReportName(),
                ReportType.maCourse.getReportType(),
                "images/drop-down-3.png",
                ReportType.maCourse.getServiceType())
        );
        maList.add(new Report(
                ReportType.maAnonymous.getReportName(),
                ReportType.maAnonymous.getReportType(),
                "images/drop-down-3.png",
                ReportType.maAnonymous.getServiceType())
        );
        maList.add(new Report(
                ReportType.maInactive.getReportName(),
                ReportType.maInactive.getReportType(),
                "images/drop-down-3.png",
                ReportType.maInactive.getServiceType())
        );
        maList.add(new Report(
                ReportType.flwRejected.getReportName(),
                ReportType.flwRejected.getReportType(),
                "images/drop-down-3.png",
                ReportType.flwRejected.getServiceType())
        );
        maList.add(new Report(
                ReportType.maPerformance.getReportName(),
                ReportType.maPerformance.getReportType(),
                "images/drop-down-2.png",
                ReportType.maPerformance.getServiceType())
        );
        maList.add(new Report(
                ReportType.maSubscriber.getReportName(),
                ReportType.maSubscriber.getReportType(),
                "images/drop-down-2.png",
                ReportType.maSubscriber.getServiceType())
        );
        maList.add(new Report(
                ReportType.maCumulative.getReportName(),
                ReportType.maCumulative.getReportType(),
                "images/drop-down-2.png",
                ReportType.maCumulative.getServiceType())
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
                "images/drop-down-3.png",
                ReportType.sixWeeks.getServiceType())
        );
        kList.add(new Report(
                ReportType.lowListenership.getReportName(),
                ReportType.lowListenership.getReportType(),
                "images/drop-down-3.png",
                ReportType.lowListenership.getServiceType())
        );
        kList.add(new Report(
                ReportType.lowUsage.getReportName(),
                ReportType.lowUsage.getReportType(),
                "images/drop-down-3.png",
                ReportType.lowUsage.getServiceType())
        );
        kList.add(new Report(
                ReportType.selfDeactivated.getReportName(),
                ReportType.selfDeactivated.getReportType(),
                "images/drop-down-3.png",
                ReportType.selfDeactivated.getServiceType())
        );
        kList.add(new Report(
                ReportType.motherRejected.getReportName(),
                ReportType.motherRejected.getReportType(),
                "images/drop-down-3.png",
                ReportType.motherRejected.getServiceType())
        );
        kList.add(new Report(
                ReportType.childRejected.getReportName(),
                ReportType.childRejected.getReportType(),
                "images/drop-down-3.png",
                ReportType.childRejected.getServiceType())
        );
        kList.add(new Report(
                ReportType.kilkariCumulative.getReportName(),
                ReportType.kilkariCumulative.getReportType(),
                "images/drop-down-2.png",
                ReportType.kilkariCumulative.getServiceType())
        );
        kList.add(new Report(
                ReportType.beneficiaryCompletion.getReportName(),
                ReportType.beneficiaryCompletion.getReportType(),
                "images/drop-down-2.png",
                ReportType.beneficiaryCompletion.getServiceType())
        );
        kList.add(new Report(
                ReportType.usage.getReportName(),
                ReportType.usage.getReportType(),
                "images/drop-down-2.png",
                ReportType.usage.getServiceType())
        );
        kList.add(new Report(
                ReportType.kilkariCalls.getReportName(),
                ReportType.kilkariCalls.getReportType(),
                "images/drop-down-2.png",
                ReportType.kilkariCalls.getServiceType())
        );

        kList.add(new Report(
                ReportType.messageMatrix.getReportName(),
                ReportType.messageMatrix.getReportType(),
                "images/drop-down-2.png",
                ReportType.messageMatrix.getServiceType())
        );
        kList.add(new Report(
                ReportType.listeningMatrix.getReportName(),
                ReportType.listeningMatrix.getReportType(),
                "images/drop-down-2.png",
                ReportType.listeningMatrix.getServiceType())
        );
        kList.add(new Report(
                ReportType.kilkariSubscriber.getReportName(),
                ReportType.kilkariSubscriber.getReportType(),
                "images/drop-down-2.png",
                ReportType.kilkariSubscriber.getServiceType())
        );
        kList.add(new Report(
                ReportType.kilkariMessageListenership.getReportName(),
                ReportType.kilkariMessageListenership.getReportType(),
                "images/drop-down-2.png",
                ReportType.kilkariMessageListenership.getServiceType())
        );
        kList.add(new Report(
                ReportType.kilkariThematicContent.getReportName(),
                ReportType.kilkariThematicContent.getReportType(),
                "images/drop-down-2.png",
                ReportType.kilkariThematicContent.getServiceType())
        );
        kList.add(new Report(
                ReportType.kilkariRepeatListenerMonthWise.getReportName(),
                ReportType.kilkariRepeatListenerMonthWise.getReportType(),
                "images/drop-down-2.png",
                ReportType.kilkariRepeatListenerMonthWise.getServiceType())
        );
        kList.add(new Report(
                ReportType.beneficiary.getReportName(),
                ReportType.beneficiary.getReportType(),
                "images/drop-down-2.png",
                ReportType.beneficiary.getServiceType())
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
            if(state.getServiceType().equals("M") || state.getServiceType().equals("ALL")){
                l.add(maMenu);
            }
            if(state.getServiceType().equals("K") || state.getServiceType().equals("ALL")){
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

}
