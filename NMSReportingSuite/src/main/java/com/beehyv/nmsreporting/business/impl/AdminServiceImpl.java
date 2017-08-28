package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.Report;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.*;
import com.beehyv.nmsreporting.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.beehyv.nmsreporting.utils.ServiceFunctions.StReplace;

/**
 * Created by beehyv on 19/4/17.
 */

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private VillageDao villageDao;

    @Autowired
    private HealthFacilityDao healthFacilityDao;

    @Autowired
    private HealthSubFacilityDao healthSubFacilityDao;

    @Autowired
    private TalukaDao talukaDao;

    @Autowired
    private MACourseAttemptDao maCourseAttemptDao;

    @Autowired
    private KilkariSixWeeksNoAnswerDao kilkariSixWeeksNoAnswerDao;

    @Autowired
    private ChildImportRejectionDao childImportRejectionDao;

    @Autowired
    private MotherImportRejectionDao motherImportRejectionDao;

    @Autowired
    private FlwImportRejectionDao flwImportRejectionDao;

    @Autowired
    private KilkariLowUsageDao kilkariLowUsageDao;

    @Autowired
    private KilkariSelfDeactivatedDao kilkariSelfDeactivatedDao;

    @Autowired
    private FrontLineWorkersDao frontLineWorkersDao;

    @Autowired
    private AnonymousUsersDao anonymousUsersDao;

    @Autowired
    private CircleDao circleDao;

    @Autowired
    private ReportTypeDao reportTypeDao;


    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String documents = System.getProperty("user.home") +File.separator+ "Documents/";
    private final String reports = documents+"Reports/";
    private Calendar c =Calendar.getInstance();
    @Override
    public HashMap startBulkDataImport(User loggedInUser) {
        Pattern pattern;
        Matcher matcher;
        Map<Integer, String> errorCreatingUsers = new HashMap<Integer, String>();
        createPropertiesFileForFileLocation();
        String fileName = null;
        fileName = retrievePropertiesFromFileLocationProperties();
        if (fileName == null) {
            fileName = documents+"BulkImportDatacr7ms10.csv";
            System.out.println("fileLocationproperties not working");
        }
        XSSFRow row;
        BufferedReader fis = null;
        try {
            fis = new BufferedReader(new FileReader(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                String line = "";
                String cvsSplitBy = ",";
                try {
                    int lineNumber = 1;
                    if ((line = fis.readLine()) != null) {
                    }
                    while ((line = fis.readLine()) != null) {
                        lineNumber++;
                        // use comma as separator
                        String[] Line = line.split(cvsSplitBy);

                       /* List<Role> userRole = roleDao.findByRoleDescription(Line[7]);
                        boolean isLevel = AccessLevel.isLevel(Line[7]);
                        if (!(isLevel)) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the access level for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        boolean isType = AccessType.isType(Line[8]);
                        if (!(isType)) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        if (userRole == null || userRole.size() == 0) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role of user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        int userRoleId = userRole.get(0).getRoleId();
                        String UserRole = AccessType.getType(Line[8]);
                        AccessLevel accessLevel = AccessLevel.getLevel(Line[7]);

                        if(stateDao.findByName(Line[1]).isEmpty()) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify valid state name";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        if(districtDao.findByName(Line[2]).isEmpty()){
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role of user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }*/
                        User user = new User();
                       /* user.setFullName(Line[0]);
                        user.setStateId(stateDao.findByName(Line[1]).get(0).getStateId());
                        user.setDistrictId(districtDao.findByName(Line[2]).get(0).getDistrictId());
                        user.setBlockId(blockDao.findByName(Line[3]).get(0).getBlockId());
                        user.setPhoneNumber(Line[4]);
                        user.setEmailId(Line[5]);
                        user.setUsername(Line[6]);
                        user.setAccessLevel(accessLevel.getAccessLevel());
                        user.setRoleId(userRoleId);*/
                        Role role;
                        State state;
                        String userName = Line[6];
                        if (userName.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the username for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        User UsernameExist = null;
                        UsernameExist = userDao.findByUserName(Line[6]);
                        if (UsernameExist == null) {
                            user.setUsername(Line[6]);
                        } else {
                            Integer rowNum = lineNumber;
                            String userNameError = "Username already exists.";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        int loggedUserRole = loggedInUser.getRoleId();
                        String loggedUserAccess = loggedInUser.getAccessLevel();
                        AccessLevel loggedUserAccessLevel = AccessLevel.getLevel(loggedUserAccess);
                        String userPhone = Line[4];
                        if (userPhone.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the phone number for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        String regexStr1 = "^[0-9]*$";
                        String regexStr2 = "^[0-9]{10}$";
                        if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please check the format of phone number for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        user.setPassword(passwordEncoder.encode(Line[4]));
                        user.setFullName(Line[0]);
                        user.setPhoneNumber(Line[4]);
                        user.setAccountStatus(AccountStatus.ACTIVE.getAccountStatus());
                        String userEmail = Line[5];
                        if (userEmail.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the Email for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                        pattern = Pattern.compile(EMAIL_PATTERN);
                        matcher = pattern.matcher(userEmail);
                        boolean validate = matcher.matches();
                        if (validate) {
                            user.setEmailId(Line[5]);
                        } else {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please enter the valid Email for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
/*                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = null;
                        try {
                            date = sdf1.parse(Line[7]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                        user.setCreationDate((sqlStartDate));*/
                        user.setCreatedByUser(loggedInUser);
                        user.setCreationDate(new Date());
                        List<Role> userRole = roleDao.findByRoleDescription(Line[8]);
                        String State = Line[1];
                        String District = Line[2];
                        String Block = Line[3];
                        boolean isLevel = AccessLevel.isLevel(Line[7]);
                        if (!(isLevel)) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the access level for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        boolean isType = AccessType.isType(Line[8]);
                        if (!(isType)) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        if (userRole == null || userRole.size() == 0) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role of user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        int userRoleId = userRole.get(0).getRoleId();
                        String UserRole = AccessType.getType(Line[8]);
                        AccessLevel accessLevel = AccessLevel.getLevel(Line[7]);
                        if (UserRole.equalsIgnoreCase("ADMIN")) {
                            if ((accessLevel == AccessLevel.NATIONAL) || (accessLevel == AccessLevel.STATE)) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else if (loggedUserAccessLevel == AccessLevel.DISTRICT) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else {
                                List<State> userStateList = stateDao.findByName(State);
                                List<District> userDistrictList = districtDao.findByName(District);
                                District userDistrict = null;
                                State userState = null;
                                if (userDistrictList.size() == 1) {

                                    userDistrict = userDistrictList.get(0);
                                    userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                } else {
                                    for (District district : userDistrictList) {
                                        State parent = stateDao.findByStateId(district.getStateOfDistrict());
                                        if ((userStateList != null) && (userStateList.size() != 0)) {
                                            if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                userDistrict = district;
                                                userState = parent;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (userDistrict == null) {
                                    Integer rowNum = lineNumber;
                                    String authorityError = "Please enter the valid district for this user.";
                                    errorCreatingUsers.put(rowNum, authorityError);
                                    continue;
                                } else {
                                    if (!loggedInUser.getStateId().equals(userState.getStateId())) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "You don't have authority to create this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        boolean isAdminAvailable = userDao.isAdminCreated(userDistrict);
                                        if (!(isAdminAvailable)) {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict.getDistrictId());
                                            user.setStateId(userState.getStateId());
                                        } else {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "Admin is available for this district.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                        if (UserRole.equalsIgnoreCase("USER")) {
                            if (loggedUserAccessLevel.ordinal() > accessLevel.ordinal()) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else {
                                if (accessLevel == AccessLevel.NATIONAL) {
                                    user.setAccessLevel(AccessLevel.NATIONAL.getAccessLevel());
                                } else if (accessLevel == AccessLevel.STATE) {
                                    user.setAccessLevel(accessLevel.getAccessLevel());
                                    List<State> userStateList = stateDao.findByName(State);
                                    if ((userStateList == null) || (userStateList.size() == 0)) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid State for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        if (loggedUserAccessLevel == AccessLevel.STATE) {
                                            if (!loggedInUser.getStateId().equals(userStateList.get(0).getStateId())) {
                                                Integer rowNum = lineNumber;
                                                String authorityError = "You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum, authorityError);
                                                continue;
                                            } else user.setStateId(userStateList.get(0).getStateId());
                                        } else user.setStateId(userStateList.get(0).getStateId());
                                    }
                                } else if (accessLevel == AccessLevel.DISTRICT) {
                                    List<State> userStateList = stateDao.findByName(State);
                                    List<District> userDistrictList = districtDao.findByName(District);
                                    District userDistrict = null;
                                    State userState = null;
                                    if (userDistrictList.size() == 1) {
                                        userDistrict = userDistrictList.get(0);
                                        userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                    } else {
                                        for (District district : userDistrictList) {
                                            State parent = stateDao.findByStateId(district.getStateOfDistrict());
                                            if ((userStateList != null) && (userStateList.size() != 0)) {
                                                if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                    userDistrict = district;
                                                    userState = parent;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (userDistrict == null) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid district for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        if (((loggedUserAccessLevel == AccessLevel.STATE) &&
                                                (!loggedInUser.getStateId().equals(userState.getStateId()))) ||
                                                ((loggedUserAccessLevel == AccessLevel.DISTRICT) &&
                                                        (!loggedInUser.getDistrictId().equals(userDistrict.getDistrictId())))) {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "You don't have authority to create this user.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        } else {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict.getDistrictId());
                                            user.setStateId(userState.getStateId());
                                        }
                                    }
                                } else {
                                    user.setAccessLevel(AccessLevel.BLOCK.getAccessLevel());
                                    List<State> userStateList = stateDao.findByName(State);
                                    List<District> userDistrictList = districtDao.findByName(District);
                                    List<Block> userBlockList = blockDao.findByName(Block);
                                    State userState = null;
                                    District userDistrict = null;
                                    Block userBlock = null;
                                    if (userBlockList.size() == 1) {
                                        userBlock = userBlockList.get(0);
                                        userDistrict = districtDao.findByDistrictId(userBlock.getDistrictOfBlock());
                                        userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                        user.setBlockId(userBlock.getBlockId());
                                        user.setStateId(userState.getStateId());
                                        user.setDistrictId(userDistrict.getDistrictId());
                                    } else if ((userBlockList.size() == 0) || userBlockList == null) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid Block for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                    } else {
                                        List<Block> commonDistrict = null;
                                        for (Block block : userBlockList) {
                                            District parent = districtDao.findByDistrictId(block.getDistrictOfBlock());
                                            if (userDistrictList.size() > 0) {
                                                for (District district : userDistrictList) {
                                                    if (parent.getDistrictId().equals(district.getDistrictId())) {
                                                        commonDistrict.add(block);
                                                    }
                                                }
                                            }
                                        }
                                        for (Block block : commonDistrict) {
                                            State parent = stateDao.findByStateId(block.getStateOfBlock());
                                            if (userState != null) {
                                                if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                    userBlock = block;
                                                    userDistrict = districtDao.findByDistrictId(userBlock.getDistrictOfBlock());
                                                    userState = stateDao.findByStateId(userBlock.getStateOfBlock());
                                                    break;
                                                }
                                            }
                                        }
                                        if (userBlock == null) {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "Please enter the valid location for this user.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        } else {
                                            if (((loggedUserAccessLevel == AccessLevel.STATE) &&
                                                    (!loggedInUser.getStateId().equals(userState.getStateId()))) ||
                                                    ((loggedUserAccessLevel == AccessLevel.DISTRICT) &&
                                                            (!loggedInUser.getDistrictId().equals(userDistrict.getDistrictId())))) {
                                                Integer rowNum = lineNumber;
                                                String authorityError = "You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum, authorityError);
                                                continue;
                                            } else {
                                                user.setBlockId(userBlock.getBlockId());
                                                user.setStateId(userState.getStateId());
                                                user.setDistrictId(userDistrict.getDistrictId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        user.setStateName(user.getStateId()==null ? "" : stateDao.findByStateId(user.getStateId()).getStateName());
                        user.setDistrictName(user.getDistrictId()==null? "" : districtDao.findByDistrictId(user.getDistrictId()).getDistrictName());
                        user.setBlockName(user.getBlockId()==null ? "" :  blockDao.findByblockId(user.getBlockId()).getBlockName());
                        user.setRoleId(userRoleId);
                        user.setRoleName(roleDao.findByRoleId(userRoleId).getRoleDescription());
                        userDao.saveUser(user);
                        ModificationTracker modification = new ModificationTracker();
                        modification.setModificationDate(new Date(System.currentTimeMillis()));
                        modification.setModificationType(ModificationType.CREATE.getModificationType());
                        modification.setModifiedUserId(userDao.findByUserName(user.getUsername()).getUserId());
                        modification.setModifiedByUserId(loggedInUser.getUserId());
                        modificationTrackerDao.saveModification(modification);

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return (HashMap) errorCreatingUsers;
    }

    public void getBulkDataImportCSV() {
        String NEW_LINE_SEPARATOR = "\n";

        //CSV file header
        String FILE_HEADER = "Full Name, State, District, Block, Phone number, Email ID, UserName, Access Level,Role";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(documents+"BulkImportData.csv");

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);
            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);
            //Write a new student object list to the CSV file
            System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createFiles(String reportType) {
        String serviceType=ReportType.getType(reportType).getServiceType();
        List<State> states = stateDao.getStatesByServiceType(serviceType);
        String rootPath = reports;
        File dir = new File(rootPath + reportType);
        if (!dir.exists())
            dir.mkdirs();
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + reportType + "/" + stateName;
            File dirState = new File(rootPathState);
            if (!dirState.exists())
                dirState.mkdirs();
            int stateId = state.getStateId();
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + "/" + districtName;
                File dirDistrict = new File(rootPathDistrict);
                if (!dirDistrict.exists())
                    dirDistrict.mkdirs();
                int districtId = district.getDistrictId();
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + "/" + blockName;
                    File dirBlock = new File(rootPathblock);
                    if (!dirBlock.exists())
                        dirBlock.mkdirs();
                }
            }
        }
    }

    @Override
    public void createFolders(String reportType) {

        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = reports;
        File dir = new File(rootPath + reportType);
        if (!dir.exists())
            dir.mkdirs();
        for (Circle circle : circleList) {
            String circleName = StReplace(circle.getCircleFullName());
            String rootPathCircle = rootPath + reportType + "/" + circleName;
            File dirCircle = new File(rootPathCircle);
            if (!dirCircle.exists())
                dirCircle.mkdirs();
        }
    }

    @Override
    public void createSpecificReport(ReportRequest reportRequest) {

        String rootPath = reports+reportRequest.getReportType()+ "/";
//        Date toDate=reportRequest.getToDate();
        Date startDate=new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);

//        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);

        Date fromDate = aCalendar.getTime();

        aCalendar.add(Calendar.MONTH, 1);

        Date toDate = aCalendar.getTime();
        int stateId=reportRequest.getStateId();
        int districtId=reportRequest.getDistrictId();
        int blockId=reportRequest.getBlockId();
        int circleId=reportRequest.getCircleId();
        if(reportRequest.getReportType().equals(ReportType.maCourse.getReportType())){

            if(stateId==0){
                List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(toDate);
                getCumulativeCourseCompletion(successFullcandidates, rootPath,AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<MACourseFirstCompletion> candidatesFromThisState = maCourseAttemptDao.getSuccessFulCompletionWithStateId(toDate,stateId);

                    getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<MACourseFirstCompletion> candidatesFromThisDistrict = maCourseAttemptDao.getSuccessFulCompletionWithDistrictId(toDate,districtId);

                        getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<MACourseFirstCompletion> candidatesFromThisBlock = maCourseAttemptDao.getSuccessFulCompletionWithBlockId(toDate,blockId);

                        getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.maInactive.getReportType())){

            if(stateId==0){
                List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers(toDate);
                getCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<FrontLineWorkers> candidatesFromThisState = frontLineWorkersDao.getInactiveFrontLineWorkersWithStateId(toDate,stateId);

                    getCumulativeInactiveUsers(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<FrontLineWorkers> candidatesFromThisDistrict = frontLineWorkersDao.getInactiveFrontLineWorkersWithDistrictId(toDate,districtId);

                        getCumulativeInactiveUsers(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<FrontLineWorkers> candidatesFromThisBlock = frontLineWorkersDao.getInactiveFrontLineWorkersWithBlockId(toDate,blockId);

                        getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){


            if(circleId==0){
                List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(fromDate,toDate);
                getCircleWiseAnonymousUsers(anonymousUsersList,  rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String circleName=StReplace(circleDao.getByCircleId(circleId).getCircleName());
                String circleFullName = StReplace(circleDao.getByCircleId(circleId).getCircleFullName());
                String rootPathCircle=rootPath+circleFullName+"/";
                List<AnonymousUsers> anonymousUsersListCircle = anonymousUsersDao.getAnonymousUsersCircle(fromDate,toDate,StReplace(circleDao.getByCircleId(circleId).getCircleName()));
                getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleFullName, toDate, reportRequest);
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.lowUsage.getReportType())){

            if(stateId==0){
                List<KilkariLowUsage> kilkariLowUsageList = kilkariLowUsageDao.getKilkariLowUsageUsers(getMonthYear(toDate));
                getKilkariLowUsage(kilkariLowUsageList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariLowUsage> candidatesFromThisState = kilkariLowUsageDao.getKilkariLowUsageUsersWithStateId(getMonthYear(toDate), stateId);

                    getKilkariLowUsage(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariLowUsage> candidatesFromThisDistrict = kilkariLowUsageDao.getKilkariLowUsageUsersWithDistrictId(getMonthYear(toDate), districtId);

                        getKilkariLowUsage(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariLowUsage> candidatesFromThisBlock = kilkariLowUsageDao.getKilkariLowUsageUsersWithBlockId(getMonthYear(toDate), blockId);

                        getKilkariLowUsage(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.sixWeeks.getReportType())){

            if(stateId==0){
                List<KilkariDeactivationOther> kilkariDeactivationOthers = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
                getKilkariSixWeekNoAnswer(kilkariDeactivationOthers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariDeactivationOther> candidatesFromThisState = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithStateId(fromDate,toDate,stateId);

                    getKilkariSixWeekNoAnswer(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariDeactivationOther> candidatesFromThisDistrict = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithDistrictId(fromDate,toDate,districtId);

                        getKilkariSixWeekNoAnswer(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariDeactivationOther> candidatesFromThisBlock = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithBlockId(fromDate,toDate,blockId);

                        getKilkariSixWeekNoAnswer(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

        else if(reportRequest.getReportType().equals(ReportType.lowListenership.getReportType())){

            if(stateId==0){
                List<KilkariDeactivationOther> kilkariDeactivationOthers = kilkariSixWeeksNoAnswerDao.getLowListenershipUsers(fromDate, toDate);
                getKilkariLowListenershipDeactivation(kilkariDeactivationOthers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariDeactivationOther> candidatesFromThisState = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithStateId(fromDate,toDate,stateId);

                    getKilkariLowListenershipDeactivation(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariDeactivationOther> candidatesFromThisDistrict = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithDistrictId(fromDate,toDate,districtId);

                        getKilkariLowListenershipDeactivation(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariDeactivationOther> candidatesFromThisBlock = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithBlockId(fromDate,toDate,blockId);

                        getKilkariLowListenershipDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

        else if(reportRequest.getReportType().equals(ReportType.selfDeactivated.getReportType())){

            if(stateId==0){
                List<KilkariSelfDeactivated> kilkariSelfDeactivatedList = kilkariSelfDeactivatedDao.getSelfDeactivatedUsers(fromDate, toDate);
                getKilkariSelfDeactivation(kilkariSelfDeactivatedList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariSelfDeactivated> candidatesFromThisState =kilkariSelfDeactivatedDao.getSelfDeactivatedUsersWithStateId(fromDate,toDate,stateId);

                    getKilkariSelfDeactivation(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariSelfDeactivated> candidatesFromThisDistrict = kilkariSelfDeactivatedDao.getSelfDeactivatedUsersWithDistrictId(fromDate,toDate,districtId);

                        getKilkariSelfDeactivation(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariSelfDeactivated> candidatesFromThisBlock = kilkariSelfDeactivatedDao.getSelfDeactivatedUsersWithBlockId(fromDate,toDate,blockId);

                        getKilkariSelfDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.motherRejected.getReportType())){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reportRequest.getFromDate());
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.DAY_OF_MONTH,1);
             toDate= calendar.getTime();
//            List<MotherImportRejection> motherImportRejections = motherImportRejectionDao.getAllRejectedMotherImportRecords(nextDay);

            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<MotherImportRejection> candidatesFromThisState = motherImportRejectionDao.getAllRejectedMotherImportRecordsWithStateId(toDate,stateId);

                getCumulativeRejectedMotherImports(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());;
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<MotherImportRejection> candidatesFromThisDistrict = motherImportRejectionDao.getAllRejectedMotherImportRecordsWithDistrictId(toDate,districtId);

                    getCumulativeRejectedMotherImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<MotherImportRejection> candidatesFromThisBlock = motherImportRejectionDao.getAllRejectedMotherImportRecordsWithBlockId(toDate,blockId);

                    getCumulativeRejectedMotherImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.childRejected.getReportType())){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reportRequest.getFromDate());
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            toDate=calendar.getTime();
//            List<ChildImportRejection> childImportRejections = childImportRejectionDao.getRejectedChildRecords(nextDay);

            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<ChildImportRejection> candidatesFromThisState = childImportRejectionDao.getRejectedChildRecordsWithStateId(toDate,stateId);

                getCumulativeRejectedChildImports(candidatesFromThisState,rootPathState, stateName, toDate,reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<ChildImportRejection> candidatesFromThisDistrict = childImportRejectionDao.getRejectedChildRecordsWithDistrictId(toDate,districtId);

                    getCumulativeRejectedChildImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<ChildImportRejection> candidatesFromThisBlock =childImportRejectionDao.getRejectedChildRecordsWithBlockId(toDate,blockId);

                    getCumulativeRejectedChildImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }

        }
        else if(reportRequest.getReportType().equals(ReportType.flwRejected.getReportType())){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reportRequest.getFromDate());
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            toDate=calendar.getTime();
//            List<FlwImportRejection> childImportRejections = flwImportRejectionDao.getAllRejectedFlwImportRecords(nextDay);

            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<FlwImportRejection> candidatesFromThisState = flwImportRejectionDao.getAllRejectedFlwImportRecordsWithStateId(toDate,stateId);

                getCumulativeRejectedFlwImports(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<FlwImportRejection> candidatesFromThisDistrict = flwImportRejectionDao.getAllRejectedFlwImportRecordsWithDistrictId(toDate,districtId);

                    getCumulativeRejectedFlwImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<FlwImportRejection> candidatesFromThisBlock = flwImportRejectionDao.getAllRejectedFlwImportRecordsWithBlockId(toDate,blockId);

                    getCumulativeRejectedFlwImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }

        }
    }


    private void getCumulativeRejectedChildImports(List<ChildImportRejection> rejectedChildImports, String rootPath,
                                                   String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Child Import Rejected Details ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "District Name",
                "PHC Id",
                "PHC Name",
                "Subcentre Id",
                "Taluka Id",
                "Taluka Name",
                "Health Block Name",
                "Subcentre Name",
                "Village Id",
                "Village Name",
                "Yr",
                "City Maholla",
                "GP Viilage",
                "Address",
                "Id No",
                "Name",
                "Mobile No",
                "Mother Name",
                "Mother Id",
                "Asha Phone Number",
                "BCG Dt",
                "OPV0 Dt",
                "HepatitisB1 Dt",
                "DPT1 Dt",
                "OPV1 Dt",
                "HepatitisB2 Dt",
                "Phone Number of Whom",
                "Whom Phone Number",
                "Birth Date",
                "Place Of Delivery",
                "Blood Group",
                "Caste",
                "Subcenter Name1",
                "ANM Name",
                "ANM Phone ",
                "Asha Phone",
                "Asha Name",
                "DPT2 Dt",
                "OPV2 Dt",
                "HepatitisB3 Dt",
                "DPT3 Dt",
                "OPV3 Dt",
                "HepatitisB4 Dt",
                "Measles Dt",
                "VitA Dose1 Dt",
                "MR Dt",
                "DPT Booster Dt",
                "OPV Booster Dt",
                "VitA Dose2 Dt",
                "VitA Dose3 Dt",
                "TT10 Dt",
                "JE Dt",
                "VitA Dose9 Dt",
                "DT5 Dt",
                "TT16Dt",
                "CLD Reg Date",
                "Asha ID",
                "Last Update Date",
                "VitA Dose6 Dt",
                "Remarks",
                "ANM Id",
                "Created By",
                "Updated By",
                "Measles2 Dt",
                "Weight Of Child",
                "Child Aadhar No",
                "Child EID",
                "Sex",
                "VitA Dose5 Dt",
                "VitA Dose7 Dt",
                "VitA Dose8 Dt",
                "Child EID Time",
                "Father Name",
                "Exec Date",
                "Accepted",
                "Rejection Reason",
                "Birth Certificate Number",
                "Entry Type",
                "Source",
                "Registration No",
                "MCTS Mother Id",
                "Action",
                "Creation Date",
                "Modification Date"

        });
        Integer counter = 2;
        if(rejectedChildImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (ChildImportRejection childRejection : rejectedChildImports) {
            empinfo.put((counter.toString()), new Object[]{
                    (childRejection.getDistrictName() == null) ? "No District Name": childRejection.getDistrictName(),
                    (childRejection.getPhcId() == null) ? "No PHC Id" : childRejection.getPhcId(),
                    (childRejection.getPhcName() == null) ? "No PHC Name" : childRejection.getPhcName(),
                    (childRejection.getSubcentreId() == null) ? "No Sub-centre Id" : childRejection.getSubcentreId(),
                    (childRejection.getTalukaId() == null) ? "No Health Taluka Id" : childRejection.getTalukaId(),
                    (childRejection.getTalukaName() == null) ? "No Taluka Name" : childRejection.getTalukaName(),
                    (childRejection.getHealthBlockName() == null) ? "No Health Block Name": childRejection.getHealthBlockName(),
                    (childRejection.getSubcentreName() == null) ? "No Sub Centre Name": childRejection.getSubcentreName(),
                    (childRejection.getVillageId() == null) ? "No Village Id": childRejection.getVillageId(),
                    (childRejection.getVillageName() == null) ? "No Village Name": childRejection.getVillageName(),
                    (childRejection.getYr() == null) ? "No Yr": childRejection.getYr(),
                    (childRejection.getCityMaholla() == null) ? "No City Maholla": childRejection.getCityMaholla(),
                    (childRejection.getgPVillage() == null) ? "No Gp Village": childRejection.getgPVillage(),
                    (childRejection.getAddress() == null) ? "No Address": childRejection.getAddress(),
                    (childRejection.getIdNo() == null) ? "No Id No": childRejection.getIdNo(),
                    (childRejection.getName() == null) ? "No Name": childRejection.getName(),
                    (childRejection.getMobileNo() == null) ? "No Mobile No": childRejection.getMobileNo(),
                    (childRejection.getMotherName() == null) ? "No Mother name": childRejection.getMotherName(),
                    (childRejection.getMotherId() == null) ? "No Mother Id": childRejection.getMotherId(),
                    (childRejection.getAshaPhone() == null) ? "No Asha Phone no": childRejection.getAshaPhone(),
                    (childRejection.getbCGDt() == null) ? "No  Bcg Dt": childRejection.getbCGDt(),
                    (childRejection.getoPV0Dt() == null) ? "No OpV0 Dt": childRejection.getoPV0Dt(),
                    (childRejection.getHepatitisB1Dt() == null) ? "No HepatitisB1 Dt": childRejection.getHepatitisB1Dt(),
                    (childRejection.getdPT1Dt() == null) ? "No DPT1 Dt": childRejection.getdPT1Dt(),
                    (childRejection.getoPV1Dt() == null) ? "No OPT1 Dt": childRejection.getoPV1Dt(),
                    (childRejection.getHepatitisB2Dt() == null) ? "No HepatitisB2 Dt": childRejection.getHepatitisB2Dt(),
                    (childRejection.getPhoneNumberWhom() == null) ? "No Phone Number of Whom": childRejection.getPhoneNumberWhom(),
                    (childRejection.getWhomPhoneNumber() == null) ? "No Whom Phone Number": childRejection.getWhomPhoneNumber(),
                    (childRejection.getBirthDate() == null) ? "No Birth Date": childRejection.getBirthDate(),
                    (childRejection.getPlaceOfDelivery() == null) ? "No Place of Delivery": childRejection.getPlaceOfDelivery(),
                    (childRejection.getBloodGroup() == null) ? "No Blood Group": childRejection.getBloodGroup(),
                    (childRejection.getCaste() == null) ? "No Caste": childRejection.getCaste(),
                    (childRejection.getSubcenterName1() == null) ? "No Sub Center Name1": childRejection.getSubcenterName1(),
                    (childRejection.getaNMName() == null) ? "No ANM Name": childRejection.getaNMName(),
                    (childRejection.getaNMPhone() == null) ? "No ANM phone": childRejection.getaNMPhone(),
                    (childRejection.getAshaPhone() == null) ? "No Asha Phone Number": childRejection.getAshaPhone(),
                    (childRejection.getAshaName() == null) ? "No Asha Name": childRejection.getAshaName(),
                    (childRejection.getdPT2Dt() == null) ? "No DPT2 Dt": childRejection.getdPT2Dt(),
                    (childRejection.getoPV2Dt() == null) ? "No OPV2 Dt": childRejection.getoPV2Dt(),
                    (childRejection.getHepatitisB3Dt() == null) ? "No HepatitisB3 Dt": childRejection.getHepatitisB3Dt(),
                    (childRejection.getdPT3Dt() == null) ? "No DPV3 Dt": childRejection.getdPT3Dt(),
                    (childRejection.getoPV3Dt() == null) ? "No OPV3 Dt": childRejection.getoPV3Dt(),
                    (childRejection.getHepatitisB4Dt() == null) ? "No HepatitisB4 Dt": childRejection.getHepatitisB4Dt(),
                    (childRejection.getMeaslesDt() == null) ? "No Measles Dt": childRejection.getMeaslesDt(),
                    (childRejection.getVitADose1Dt() == null) ? "No VitA Dose1 Dt": childRejection.getVitADose1Dt(),
                    (childRejection.getmRDt() == null) ? "No MR Dt": childRejection.getmRDt(),
                    (childRejection.getdPTBoosterDt() == null) ? "No DPV Booster Dt": childRejection.getdPTBoosterDt(),
                    (childRejection.getoPVBoosterDt() == null) ? "No OPV Booster Dt": childRejection.getoPVBoosterDt(),
                    (childRejection.getVitADose2Dt() == null) ? "No VitA Dose2 Dt": childRejection.getVitADose2Dt(),
                    (childRejection.getVitADose3Dt() == null) ? "No VitA Dose3 Dt": childRejection.getjEDt(),
                    (childRejection.gettT10Dt() == null) ? "No T10 Dt": childRejection.gettT10Dt(),
                    (childRejection.getjEDt() == null) ? "No JE Dt": childRejection.getjEDt(),
                    (childRejection.getVitADose9Dt() == null) ? "No VitA Dose9 Dt": childRejection.getVitADose9Dt(),
                    (childRejection.getdT5Dt() == null) ? "No Dt5 Dt": childRejection.getdT5Dt(),
                    (childRejection.gettT16Dt() == null) ? "No Tt16 Dt": childRejection.gettT16Dt(),
                    (childRejection.getcLDRegDATE() == null) ? "No Cld Reg Date": childRejection.getcLDRegDATE(),
                    (childRejection.getAshaID() == null) ? "No Asha Id": childRejection.getAshaID(),
                    (childRejection.getLastUpdateDate() == null) ? "No Last Updated Date": childRejection.getLastUpdateDate(),
                    (childRejection.getVitADose6Dt() == null) ? "No VitaDose6 Dt": childRejection.getVitADose6Dt(),
                    (childRejection.getRemarks() == null) ? "No Remarks": childRejection.getRemarks(),
                    (childRejection.getaNMID() == null) ? "No ANMID": childRejection.getaNMID(),
                    (childRejection.getCreatedBy() == null) ? "No Created By": childRejection.getCreatedBy(),
                    (childRejection.getUpdatedBy() == null) ? "No Updated by": childRejection.getUpdatedBy(),
                    (childRejection.getMeasles2Dt() == null) ? "No Measles2 Dt": childRejection.getMeasles2Dt(),
                    (childRejection.getWeightOfChild() == null) ? "No Weight of Child": childRejection.getWeightOfChild(),
                    (childRejection.getChildAadhaarNo() == null) ? "No Child Aadhaar No": childRejection.getChildAadhaarNo(),
                    (childRejection.getChildEID() == null) ? "No Child EID": childRejection.getChildEID(),
                    (childRejection.getSex() == null) ? "No Sex": childRejection.getSex(),
                    (childRejection.getVitADose5Dt() == null) ? "No VitA Dose5 Dt": childRejection.getVitADose5Dt(),
                    (childRejection.getVitADose7Dt() == null) ? "No VitA Dose7 Dt": childRejection.getVitADose7Dt(),
                    (childRejection.getVitADose8Dt() == null) ? "No VitA Dose8 Dt": childRejection.getVitADose8Dt(),
                    (childRejection.getChildEIDTime() == null) ? "No Child EID Time": childRejection.getChildEIDTime(),
                    (childRejection.getFatherName() == null) ? "No Father name": childRejection.getFatherName(),
                    (childRejection.getExecDate() == null) ? "No Exec Date": childRejection.getExecDate(),
                    (childRejection.getAccepted() == null) ? "No Accepted": childRejection.getAccepted(),
                    (childRejection.getRejectionReason() == null) ? "No Details": childRejection.getRejectionReason(),
                    (childRejection.getBirthCertificateNumber() == null) ? "No Birth Certificate Number": childRejection.getBirthCertificateNumber(),
                    (childRejection.getEntryType() == null) ? "No Entry Type": childRejection.getEntryType(),
                    (childRejection.getSource() == null) ? "No Source": childRejection.getSource(),
                    (childRejection.getRegistrationNo() == null) ? "No Registration No": childRejection.getRegistrationNo(),
                    (childRejection.getmCTSMotherIDNo() == null) ? "No MCTS Mother Id": childRejection.getmCTSMotherIDNo(),
                    (childRejection.getAction() == null) ? "No Action": childRejection.getAction(),
                    (childRejection.getCreationDate() == null) ? "No Creation Date": childRejection.getCreationDate(),
                    (childRejection.getModificationDate() == null) ? "No Modification Date": childRejection.getModificationDate(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid == 6 && rejectedChildImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:CF6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.childRejected.getReportType() + "_" + place + "_" + getDateMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getCumulativeRejectedMotherImports(List<MotherImportRejection> rejectedMotherImports, String rootPath,
                                                   String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Mother Import Rejected Details ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "District Name",
                "PHC Id",
                "PHC Name",
                "Subcentre Id",
                "Taluka Id",
                "Taluka Name",
                "Health Block Name",
                "Subcentre Name",
                "Village Id",
                "Village Name",
                "Yr",
                "GP Viilage",
                "Address",
                "Id No",
                "Name",
                "Husband Name",
                "Phone Number of Whom",
                "Whom Phone Number",
                "Birth Date",
                "JSY Beneficiary",
                "Caste",
                "Subcentre Name1",
                "ANM Name",
                "ANM Phone",
                "Asha Phone",
                "Asha Name",
                "Delivery Lnk Facility",
                "Facility Name",
                "LMP Date",
                "ANC1 Date",
                "ANC2 Date",
                "ANC3 Date",
                "ANC4 Date",
                "TT1 Date",
                "TT2 Date",
                "TT Booster Date",
                "IFA100 Given Date",
                "Anemia",
                "ANC Complication",
                "RTI STI",
                "Dly Date",
                "Dly Place Home Type",
                "Dly Place Public",
                "Dly Place Private",
                "Dly Type",
                "Dly Complication",
                "Discharge Date",
                "JSY Paid Date",
                "Abortion",
                "PNC Home Visit",
                "PNC Complication",
                "PPC Method",
                "PNC Checkup",
                "Outcome Nos",
                "Child1 Name",
                "Child1 Sex",
                "Child1 Wt",
                "Child1 Brestfeeding",
                "Child2 Name",
                "Child2 Sex",
                "Child2 Wt",
                "Child2 Brestfeeding",
                "Child3 Name",
                "Child3 Sex",
                "Child3 Wt",
                "Child3 Brestfeeding",
                "Child4 Name",
                "Child4 Sex",
                "Child4 Wt",
                "Child4 Brestfeeding",
                "Age",
                "MTHRREG DATE",
                "Last Update Date",
                "Remarks",
                "ANM ID",
                "ASHA ID",
                "Call Ans",
                "No Call Reason",
                "No Phone Reason",
                "Created By",
                "Updated By",
                "Aadhar No",
                "BPLAPL",
                "EID",
                "EID Time",
                "Entry Type",
                "Registration No",
                "Case No",
                "Mobile No",
                "Abortion Type",
                "Delivery Outcomes",
                "Exec Date",
                "Accepted",
                "Rejection Reason",
                "Source",
                "Action",
                "Creation Date",
                "Modification Date"
        });
        Integer counter = 2;
        if(rejectedMotherImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (MotherImportRejection motherRejection : rejectedMotherImports) {
            empinfo.put((counter.toString()), new Object[]{
                    (motherRejection.getDistrictName() == null) ? "No District Name": motherRejection.getDistrictName(),
                    (motherRejection.getPhcId() == null) ? "No PHC Id" : motherRejection.getPhcId(),
                    (motherRejection.getPhcName() == null) ? "No PHC Name" : motherRejection.getPhcName(),
                    (motherRejection.getSubcentreId() == null) ? "No Sub-centre Id" : motherRejection.getSubcentreId(),
                    (motherRejection.getTalukaId() == null) ? "No Health Taluka Id" : motherRejection.getTalukaId(),
                    (motherRejection.getTalukaName() == null) ? "No Taluka Name" : motherRejection.getTalukaName(),
                    (motherRejection.getHealthBlockName() == null) ? "No Health Block Name": motherRejection.getHealthBlockName(),
                    (motherRejection.getSubcentreName() == null) ? "No Sub Centre Name": motherRejection.getSubcentreName(),
                    (motherRejection.getVillageId() == null) ? "No Village Id": motherRejection.getVillageId(),
                    (motherRejection.getVillageName() == null) ? "No Village Name": motherRejection.getVillageName(),
                    (motherRejection.getYr() == null) ? "No Yr": motherRejection.getYr(),
                    (motherRejection.getgPVillage() == null) ? "No Gp Village": motherRejection.getgPVillage(),
                    (motherRejection.getAddress() == null) ? "No Address": motherRejection.getAddress(),
                    (motherRejection.getIdNo() == null) ? "No Id No": motherRejection.getIdNo(),
                    (motherRejection.getName() == null) ? "No Name": motherRejection.getName(),
                    (motherRejection.getHusbandName() == null) ? "No Husband Name": motherRejection.getHusbandName(),
                    (motherRejection.getPhoneNumberWhom() == null) ? "No Phone Number of Whom": motherRejection.getPhoneNumberWhom(),
                    (motherRejection.getWhomPhoneNumber() == null) ? "No Whom Phone Number": motherRejection.getWhomPhoneNumber(),
                    (motherRejection.getBirthDate() == null) ? "No Birth Date": motherRejection.getBirthDate(),
                    (motherRejection.getjSYBeneficiary() == null) ? "No Jsy Beneficiary": motherRejection.getjSYBeneficiary(),
                    (motherRejection.getCaste() == null) ? "No Caste": motherRejection.getCaste(),
                    (motherRejection.getSubcenterName1() == null) ? "No Sub Center Name1": motherRejection.getSubcenterName1(),
                    (motherRejection.getaNMName() == null) ? "No ANM Name": motherRejection.getaNMName(),
                    (motherRejection.getaNMPhone() == null) ? "No ANM phone": motherRejection.getaNMPhone(),
                    (motherRejection.getAshaPhone() == null) ? "No Asha Phone Number": motherRejection.getAshaPhone(),
                    (motherRejection.getAshaName() == null) ? "No Asha Name": motherRejection.getAshaName(),
                    (motherRejection.getDeliveryLnkFacility() == null) ? "No Delivery Link Facility": motherRejection.getDeliveryLnkFacility(),
                    (motherRejection.getFacilityName() == null) ? "No Facility Name": motherRejection.getFacilityName(),
                    (motherRejection.getLmpDate() == null) ? "No Lmp Date": motherRejection.getLmpDate(),
                    (motherRejection.getaNC1Date() == null) ? "No ANC1 Date": motherRejection.getaNC1Date(),
                    (motherRejection.getaNC2Date() == null) ? "No ANC2 Date": motherRejection.getaNC2Date(),
                    (motherRejection.getaNC3Date() == null) ? "No ANC3 Date": motherRejection.getaNC3Date(),
                    (motherRejection.getaNC4Date() == null) ? "No ANC4 Date": motherRejection.getaNC4Date(),
                    (motherRejection.gettT1Date() == null) ? "No TT1 Date": motherRejection.gettT1Date(),
                    (motherRejection.gettT2Date() == null) ? "No TT2 Date": motherRejection.gettT2Date(),
                    (motherRejection.gettTBoosterDate() == null) ? "No TT Booster Date": motherRejection.gettTBoosterDate(),
                    (motherRejection.getiFA100GivenDate() == null) ? "No IFA100 Given Date": motherRejection.getiFA100GivenDate(),
                    (motherRejection.getAnemia() == null) ? "No Anemia" : motherRejection.getAnemia(),
                    (motherRejection.getaNCComplication() == null) ? "No ANC Complication": motherRejection.getaNCComplication(),
                    (motherRejection.getrTISTI() == null) ? "No RTISTI": motherRejection.getrTISTI(),
                    (motherRejection.getDlyDate() == null) ? "No Dly Date": motherRejection.getDlyDate(),
                    (motherRejection.getDlyPlaceHomeType() == null) ? "No Dly Place Home type": motherRejection.getDlyPlaceHomeType(),
                    (motherRejection.getDlyPlacePublic() == null) ? "No Dly Place Public": motherRejection.getDlyPlacePublic(),
                    (motherRejection.getDlyPlacePrivate() == null) ? "No Dly Place Private": motherRejection.getDlyPlacePrivate(),
                    (motherRejection.getDlyType() == null) ? "No Dly Type": motherRejection.getDlyType(),
                    (motherRejection.getDlyComplication() == null) ? "No Dly Complication": motherRejection.getDlyComplication(),
                    (motherRejection.getDischargeDate() == null) ? "No Discharge Date": motherRejection.getDischargeDate(),
                    (motherRejection.getjSYPaidDate() == null) ? "No JSY Paid Date": motherRejection.getjSYPaidDate(),
                    (motherRejection.getAbortion() == null) ? "No Abortion": motherRejection.getAbortion(),
                    (motherRejection.getpNCHomeVisit() == null) ? "No PNC HOME Visit": motherRejection.getpNCHomeVisit(),
                    (motherRejection.getpNCComplication() == null) ? "No PNC Complication": motherRejection.getpNCComplication(),
                    (motherRejection.getpPCMethod() == null) ? "No PPC Method": motherRejection.getpPCMethod(),
                    (motherRejection.getpNCCheckup() == null) ? "No PNC Checkup": motherRejection.getpNCCheckup(),
                    (motherRejection.getOutcomeNos() == null) ? "No Outcomes": motherRejection.getOutcomeNos(),
                    (motherRejection.getChild1Name() == null) ? "No Child1 Name": motherRejection.getChild1Name(),
                    (motherRejection.getChild1Sex() == null) ? "No Child1 Sex": motherRejection.getChild1Sex(),
                    (motherRejection.getChild1Wt() == null) ? "No Child1 Weight": motherRejection.getChild1Wt(),
                    (motherRejection.getChild1Brestfeeding() == null) ? "No Child1 Brest Feeding": motherRejection.getChild1Brestfeeding(),
                    (motherRejection.getChild2Name() == null) ? "No Child2 Name": motherRejection.getChild2Name(),
                    (motherRejection.getChild2Sex() == null) ? "No Child2 Sex": motherRejection.getChild2Sex(),
                    (motherRejection.getChild2Wt() == null) ? "No Child2 Weight": motherRejection.getChild2Wt(),
                    (motherRejection.getChild2Brestfeeding() == null) ? "No Child2 Brest Feeding": motherRejection.getChild2Brestfeeding(),
                    (motherRejection.getChild3Name() == null) ? "No Child3 Name": motherRejection.getChild3Name(),
                    (motherRejection.getChild3Sex() == null) ? "No Child3 Sex": motherRejection.getChild3Sex(),
                    (motherRejection.getChild3Wt() == null) ? "No Child3 Weight": motherRejection.getChild3Wt(),
                    (motherRejection.getChild3Brestfeeding() == null) ? "No Child3 Brest Feeding": motherRejection.getChild3Brestfeeding(),
                    (motherRejection.getChild4Name() == null) ? "No Child4 Name": motherRejection.getChild4Name(),
                    (motherRejection.getChild4Sex() == null) ? "No Child4 Sex": motherRejection.getChild4Sex(),
                    (motherRejection.getChild4Wt() == null) ? "No Child4 Weight": motherRejection.getChild4Wt(),
                    (motherRejection.getChild4Brestfeeding() == null) ? "No Child4 Brest Feeding": motherRejection.getChild4Brestfeeding(),
                    (motherRejection.getAge() == null) ? "No Age": motherRejection.getAge(),
                    (motherRejection.getmTHRREGDATE() == null) ? "No MTHRREGDATE": motherRejection.getmTHRREGDATE(),
                    (motherRejection.getLastUpdateDate() == null) ? "No Last Updated Date": motherRejection.getLastUpdateDate(),
                    (motherRejection.getRemarks() == null) ? "No Remarks": motherRejection.getRemarks(),
                    (motherRejection.getaNMID() == null) ? "No ANMID": motherRejection.getaNMID(),
                    (motherRejection.getaSHAID() == null) ? "No AshaID": motherRejection.getaSHAID(),
                    (motherRejection.getCallAns() == null) ? "No Call Ans": motherRejection.getCallAns(),
                    (motherRejection.getNoCallReason() == null) ? "No Call Reason": motherRejection.getNoCallReason(),
                    (motherRejection.getNoPhoneReason() == null) ? "No Phone Reason": motherRejection.getNoPhoneReason(),
                    (motherRejection.getCreatedBy() == null) ? "No Created By": motherRejection.getCreatedBy(),
                    (motherRejection.getUpdatedBy() == null) ? "No Updated by": motherRejection.getUpdatedBy(),
                    (motherRejection.getAadharNo() == null) ? "No Aadhar No": motherRejection.getAadharNo(),
                    (motherRejection.getbPLAPL() == null) ? "No BPLAPL": motherRejection.getbPLAPL(),
                    (motherRejection.geteID() == null) ? "No EID": motherRejection.geteID(),
                    (motherRejection.geteIDTime() == null) ? "No EID Time": motherRejection.geteIDTime(),
                    (motherRejection.getEntryType() == null) ? "No Entry Type": motherRejection.getEntryType(),
                    (motherRejection.getRegistrationNo() == null) ? "No Registration No": motherRejection.getRegistrationNo(),
                    (motherRejection.getCaseNo() == null) ? "No Case No": motherRejection.getCaseNo(),
                    (motherRejection.getMobileNo() == null) ? "No Mobile No": motherRejection.getMobileNo(),
                    (motherRejection.getAbortionType() == null) ? "No Abortion Type": motherRejection.getAbortionType(),
                    (motherRejection.getDeliveryOutcomes() == null) ? "No Delivery Outcomes": motherRejection.getDeliveryOutcomes(),
                    (motherRejection.getExecDate() == null) ? "No Exec Date": motherRejection.getExecDate(),
                    (motherRejection.getAccepted() == null) ? "Not Accepted": motherRejection.getAccepted(),
                    (motherRejection.getRejectionReason() == null) ? "No Rejection Reason": motherRejection.getRejectionReason(),
                    (motherRejection.getSource() == null) ? "No Source": motherRejection.getSource(),
                    (motherRejection.getAction() == null) ? "No Action": motherRejection.getAction(),
                    (motherRejection.getCreationDate() == null) ? "No Creation Date": motherRejection.getCreationDate(),
                    (motherRejection.getModificationDate() == null) ? "No Modification Date": motherRejection.getModificationDate(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid == 6 && rejectedMotherImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:CT6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.motherRejected.getReportType() + "_" + place + "_" + getDateMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getCumulativeRejectedFlwImports(List<FlwImportRejection> rejectedChildImports, String rootPath,
                                                    String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Flw Import Rejected Details ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "District Name",
                "Taluka Id",
                "Taluka Name",
                "Health Block Name",
                "PHC Id",
                "PHC Name",
                "Subcentre Id",
                "Subcentre Name",
                "Village Id",
                "Village Name",
                "Flw Id",
                "MSIDN",
                "Gf Name",
                "Gf Status",
                "Exec Date",
                "Reg Date",
                "Sex",
                "Type",
                "SMS Reply",
                "Aadhar No",
                "Created On",
                "Updated On",
                "Bank Id",
                "Branch Name",
                "IFSC ID Code",
                "Bank Name",
                "Account Number",
                "Linked Aadhar Number",
                "Verified Date",
                "Verifier Name",
                "Verifier Id",
                "Call Answer",
                "Correct Phone Number",
                "Reason For No Call Answer",
                "Reason For No Phone",
                "Verifier Remarks",
                "Gf Address",
                "Husband Name",
                "Accepted",
               "Rejection Reason",
                "Source",
                "Action",
                "Creation Date",
                "Last Modification Date"

        });
        Integer counter = 2;
        if(rejectedChildImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (FlwImportRejection flwRejection : rejectedChildImports) {
            empinfo.put((counter.toString()), new Object[]{
                    (flwRejection.getDistrictName() == null) ? "No District Name": flwRejection.getDistrictName(),
                    (flwRejection.getTalukaId() == null) ? "No Health Taluka Id" : flwRejection.getTalukaId(),
                    (flwRejection.getTalukaName() == null) ? "No Taluka Name" : flwRejection.getTalukaName(),
                    (flwRejection.getHealthBlockName() == null) ? "No Health Block Name": flwRejection.getHealthBlockName(),
                    (flwRejection.getPhcId() == null) ? "No PHC Id" : flwRejection.getPhcId(),
                    (flwRejection.getPhcName() == null) ? "No PHC Name" : flwRejection.getPhcName(),
                    (flwRejection.getSubcentreId() == null) ? "No Sub Centre Id" : flwRejection.getSubcentreId(),
                    (flwRejection.getSubcentreName() == null) ? "No Sub-centre Id" : flwRejection.getSubcentreName(),
                    (flwRejection.getVillageId() == null) ? "No Village Id": flwRejection.getVillageId(),
                    (flwRejection.getVillageName() == null) ? "No Village Name": flwRejection.getVillageName(),
                    (flwRejection.getFlwId() == null) ? "No CFLW ID": flwRejection.getFlwId(),
                    (flwRejection.getMsisdn() == null) ? "No MSISDN": flwRejection.getMsisdn(),
                    (flwRejection.getGfName() == null) ? "No Gf Name": flwRejection.getGfName(),
                    (flwRejection.getGfStatus() == null) ? "No Gf Status": flwRejection.getGfStatus(),
                    (flwRejection.getExecDate() == null) ? "No Exec Date": flwRejection.getExecDate(),
                    (flwRejection.getRegDate() == null) ? "No Reg Date": flwRejection.getRegDate(),
                    (flwRejection.getSex() == null) ? "No Sex": flwRejection.getSex(),
                    (flwRejection.getType() == null) ? "No Type": flwRejection.getType(),
                    (flwRejection.getSmsReply() == null) ? "No SMS Reply": flwRejection.getSmsReply(),
                    (flwRejection.getAadharNo() == null) ? "No Aadhar No": flwRejection.getAadharNo(),
                    (flwRejection.getCreatedOn() == null) ? "No  Created On": flwRejection.getCreatedOn(),
                    (flwRejection.getUpdatedOn() == null) ? "No Updated On": flwRejection.getUpdatedOn(),
                    (flwRejection.getBankId() == null) ? "No Bank Id ": flwRejection.getBankId(),
                    (flwRejection.getBranchName() == null) ? "No Bank Name ": flwRejection.getBranchName(),
                    (flwRejection.getIfscIdCode() == null) ? "No IFSC Code ": flwRejection.getIfscIdCode(),
                    (flwRejection.getBankName() == null) ? "No Bank Name": flwRejection.getBankName(),
                    (flwRejection.getAccountNumber() == null) ? "No Account Nummber": flwRejection.getAccountNumber(),
                    (flwRejection.getAadharLinked() == null) ? "No Aadhar linked": flwRejection.getAadharLinked(),
                    (flwRejection.getVerifyDate() == null) ? "No Verified Date": flwRejection.getVerifyDate(),
                    (flwRejection.getVerifierName() == null) ? "No Verifier Name": flwRejection.getVerifierName(),
                    (flwRejection.getVerifierId() == null) ? "No Verifier Id": flwRejection.getVerifierId(),
                    (flwRejection.getCallAns() == null) ? "No Call Answer": flwRejection.getCallAns(),
                    (flwRejection.getPhoneNoCorrect() == null) ? "No Correct Phone number": flwRejection.getPhoneNoCorrect(),
                    (flwRejection.getNoCallReason() == null) ? "No Reason For No Call": flwRejection.getNoCallReason(),
                    (flwRejection.getNoPhoneReason() == null) ? "No Reason For No Phone": flwRejection.getNoPhoneReason(),
                    (flwRejection.getVerifierRemarks() == null) ? "No Verifier Remarks": flwRejection.getVerifierRemarks(),
                    (flwRejection.getGfAddress() == null) ? "No Gf Address": flwRejection.getGfAddress(),
                    (flwRejection.getHusbandName() == null) ? "No Husband Name": flwRejection.getHusbandName(),
                    (flwRejection.getAccepted() == null) ? "Not Accepted": flwRejection.getAccepted(),
                    (flwRejection.getRejectionReason() == null) ? "No Rejection Reason": flwRejection.getRejectionReason(),
                    (flwRejection.getSource() == null) ? "No Source": flwRejection.getSource(),
                    (flwRejection.getAction() == null) ? "No Action": flwRejection.getAction(),
                    (flwRejection.getCreationDate() == null) ? "No Creation Date": flwRejection.getCreationDate(),
                    (flwRejection.getModificationDate() == null) ? "No Modification Date": flwRejection.getModificationDate(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid == 6 && rejectedChildImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:AR6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.flwRejected.getReportType() + "_" + place + "_" + getDateMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCumulativeCourseCompletion(List<MACourseFirstCompletion> successfulCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        //Create blank workbook


        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " MA Course Completion Report ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Mobile Number",
                "State",
                "District",
                "Health Block",
                "Taluka",
                "Health Facility",
                "Health Sub Facility",
                "Village",
                "ASHA Name",
                "ASHA MCTS/RCH ID",
                "ASHA Creation Date",
                "ASHA Job Status",
                "First Completion Date",
                "SMS Sent Notification"
        });
        Integer counter = 2;
        if(successfulCandidates.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (MACourseFirstCompletion maCourseFirstCompletion : successfulCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    (maCourseFirstCompletion.getMsisdn() == null) ? "No Phone":maCourseFirstCompletion.getMsisdn(),
                    (maCourseFirstCompletion.getStateId() == null) ? "No State":stateDao.findByStateId(maCourseFirstCompletion.getStateId()).getStateName(),
                    (maCourseFirstCompletion.getDistrictId() == null) ? "No District":districtDao.findByDistrictId(maCourseFirstCompletion.getDistrictId()).getDistrictName(),
                    (maCourseFirstCompletion.getBlockId() == null) ? "No Block" : blockDao.findByblockId(maCourseFirstCompletion.getBlockId()).getBlockName(),
                    (maCourseFirstCompletion.getTalukaId() == null) ? "No Taluka" : talukaDao.findByTalukaId(maCourseFirstCompletion.getTalukaId()).getTalukaName(),
                    (maCourseFirstCompletion.getHealthFacilityId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(maCourseFirstCompletion.getHealthFacilityId()).getHealthFacilityName(),
                    (maCourseFirstCompletion.getHealthSubFacilityId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(maCourseFirstCompletion.getHealthSubFacilityId()).getHealthSubFacilityName(),
                    (maCourseFirstCompletion.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(maCourseFirstCompletion.getVillageId()).getVillageName(),
                    (maCourseFirstCompletion.getFullName() == null) ? "No Name":maCourseFirstCompletion.getFullName(),
                    (maCourseFirstCompletion.getExternalFlwId() == null) ? "No FLW_ID":maCourseFirstCompletion.getExternalFlwId(),
                    (maCourseFirstCompletion.getCreationDate() == null) ? "No Creation_date":maCourseFirstCompletion.getCreationDate(),
                    (maCourseFirstCompletion.getJobStatus() == null) ? "No Designation":maCourseFirstCompletion.getJobStatus(),
                    (maCourseFirstCompletion.getFirstCompletionDate() == null) ? "No Phone":maCourseFirstCompletion.getFirstCompletionDate(),
                    (maCourseFirstCompletion.getSentNotification() == null) ? "No Details": maCourseFirstCompletion.getSentNotification()
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid == 6 && successfulCandidates.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:N6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.maCourse.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCircleWiseAnonymousUsers(List<AnonymousUsers> anonymousUsersList, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Circle-wise Anonymous Users Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Circle Name",
                "Mobile Number",
                "Last Called Date"
        });
        Integer counter = 2;
        if(anonymousUsersList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (AnonymousUsers anonymousUser : anonymousUsersList) {
            empinfo.put((counter.toString()), new Object[]{
                    anonymousUser.getCircleName(),
                    anonymousUser.getMsisdn(),
                    anonymousUser.getLastCalledDate()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==6 && anonymousUsersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:C6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.maAnonymous.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCumulativeInactiveUsers(List<FrontLineWorkers> inactiveCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Cumulative Inactive Users Report "+place+"_"+getMonthYear(toDate));
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Mobile Number",
                "State",
                "District",
                "Health Block",
                "Taluka",
                "Health Facility",
                "Health Sub Facility",
                "Village",
                "ASHA Name",
                "ASHA MCTS/RCH ID",
                "ASHA Creation Date",
                "ASHA Job Status"
        });
        Integer counter = 2;
        if(inactiveCandidates.isEmpty()) {
            empinfo.put(counter.toString(),new Object[]{"No Records to display"});
        }
        for (FrontLineWorkers frontLineWorker : inactiveCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    (frontLineWorker.getMobileNumber() == null) ? "No Phone":frontLineWorker.getMobileNumber(),
                    (frontLineWorker.getState() == null) ? "No State":stateDao.findByStateId(frontLineWorker.getState()).getStateName(),
                    (frontLineWorker.getDistrict() == null) ? "No District":districtDao.findByDistrictId(frontLineWorker.getDistrict()).getDistrictName(),
                    (frontLineWorker.getBlock() == null) ? "No Block" : blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName(),
                    (frontLineWorker.getTaluka() == null) ? "No Taluka" : talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName(),
                    (frontLineWorker.getFacility() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName(),
                    (frontLineWorker.getSubfacility() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName(),
                    (frontLineWorker.getVillage() == null) ? "No Village" : villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName(),
                    (frontLineWorker.getFullName() == null) ? "No Name":frontLineWorker.getFullName(),
                    (frontLineWorker.getExternalFlwId() == null) ? "No FLW_ID":frontLineWorker.getExternalFlwId(),
                    (frontLineWorker.getCreationDate() == null) ? "No Creation_date":frontLineWorker.getCreationDate(),
                    (frontLineWorker.getJobStatus() == null) ? "No Designation":frontLineWorker.getJobStatus()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==6 && inactiveCandidates.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:L6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getKilkariSixWeekNoAnswer(List<KilkariDeactivationOther> kilkariSixWeeksNoAnswersList, String rootPath, String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Non-answering beneficiaries Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State",
                "District",
                "Health block",
                "Taluka",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Benificiary Name",
                "Mobile Number",
                "Age On Service In Weeks"});
        Integer counter = 2;
        if(kilkariSixWeeksNoAnswersList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (KilkariDeactivationOther kilkari : kilkariSixWeeksNoAnswersList) {
            empinfo.put((counter.toString()), new Object[]{
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getVillageId() == null) ? "No Taluka" : talukaDao.findByTalukaId(villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage()).getTalukaName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==6 && kilkariSixWeeksNoAnswersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:L6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.sixWeeks.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getKilkariLowListenershipDeactivation(List<KilkariDeactivationOther> lowListenershipList, String rootPath, String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Low Listenership deactivated beneficiaries Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State",
                "District",
                "Health block",
                "Taluka",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Benificiary Name",
                "Mobile Number",
                "Age On Service In Weeks"});
        Integer counter = 2;
        if(lowListenershipList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (KilkariDeactivationOther kilkari : lowListenershipList) {
            empinfo.put((counter.toString()), new Object[]{
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getVillageId() == null) ? "No Taluka" : talukaDao.findByTalukaId(villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage()).getTalukaName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==6 && lowListenershipList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:L6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.lowListenership.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getKilkariSelfDeactivation(List<KilkariSelfDeactivated> kilkariSelfDeactivatedList, String rootPath,
                                            String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari self-deactivators Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State",
                "District",
                "Health block",
                "Taluka",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Benificiary Name",
                "Mobile Number",
                "Age On Service In Weeks",
                "Date of activation",
                "Date when beneficiary self-deactivated",
                "Number of calls answered when subscribed to Kilkari"
        });
        Integer counter = 2;
        if(kilkariSelfDeactivatedList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
            empinfo.put((counter.toString()), new Object[]{
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getVillageId() == null) ? "No Taluka" : talukaDao.findByTalukaId(villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage()).getTalukaName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService(),
                    (kilkari.getPackActivationDate() == null) ? "No Activation_date" :kilkari.getPackActivationDate(),
                    (kilkari.getDeactivationDate() == null) ? "No Deactivation_date" :kilkari.getDeactivationDate(),
                    (kilkari.getCallsAnswered() == null) ? "No Call_Data" :kilkari.getCallsAnswered()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==6 && kilkariSelfDeactivatedList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:O6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.selfDeactivated.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getKilkariLowUsage(List<KilkariLowUsage> kilkariLowUsageList, String rootPath, String place, Date toDate,ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Low Usage beneficiaries Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State",
                "District",
                "Health block",
                "Taluka",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Benificiary Name",
                "Mobile Number",
                "Age On Service In Weeks"
        });
        Integer counter = 2;
        if(kilkariLowUsageList.isEmpty()) {
            empinfo.put(counter.toString(),new Object[]{"No Records to display"});
        }
        for (KilkariLowUsage kilkari : kilkariLowUsageList) {
            empinfo.put((counter.toString()), new Object[]{
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getVillageId() == null) ? "No Taluka" : talukaDao.findByTalukaId(villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage()).getTalukaName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService(),
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==6 && kilkariLowUsageList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:L6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.lowUsage.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHeadersForReportFiles(XSSFWorkbook workbook, ReportRequest reportRequest) {
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        XSSFRow row=spreadsheet.createRow(rowid++);
        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);//set it to bold
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align

        Cell cell1=row.createCell(0);
        Cell cell2=row.createCell(1);
        Cell cell3=row.createCell(7);
        Cell cell4=row.createCell(8);
        spreadsheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        spreadsheet.addMergedRegion(new CellRangeAddress(0,1,1,5));
        spreadsheet.addMergedRegion(new CellRangeAddress(0,1,7,7));
        spreadsheet.addMergedRegion(new CellRangeAddress(0,1,8,9));
        XSSFRow row1=spreadsheet.createRow(++rowid);
        Cell cell5=row1.createCell(0);
        Cell cell6=row1.createCell(1);
        Cell cell7=row1.createCell(4);
        Cell cell8=row1.createCell(5);
        Cell cell9=row1.createCell(8);
        Cell cell10=row1.createCell(9);
        cell1.setCellValue("Report:");
        cell2.setCellValue(ReportType.getType(reportRequest.getReportType()).getReportHeader());
        if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){
            String circleName;
            if(reportRequest.getCircleId()!=0) {
                circleName=circleDao.getByCircleId(reportRequest.getCircleId()).getCircleFullName();
            }else {
                circleName = "ALL";
            }
            cell3.setCellValue("Month:");
            cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
            cell5.setCellValue("Circle:");
            cell6.setCellValue(circleName);
        }else {
            if(reportRequest.getReportType().equals(ReportType.flwRejected.getReportType())||
                    reportRequest.getReportType().equals(ReportType.motherRejected.getReportType())||
                    reportRequest.getReportType().equals(ReportType.childRejected.getReportType())){
                cell3.setCellValue("Week:");
                cell4.setCellValue(getDateMonthYearName(reportRequest.getFromDate()));
            } else {
                cell3.setCellValue("Month:");
                cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
            }
            String stateName;
            if(reportRequest.getStateId() !=0){
                stateName=stateDao.findByStateId(reportRequest.getStateId()).getStateName();
            }else {
                stateName="ALL";
            }
            cell5.setCellValue("State:");
            cell6.setCellValue(stateName);
            String districtName;
            if(reportRequest.getDistrictId() !=0){
                districtName =districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName();
            }else {
                districtName="ALL";
            }
            cell7.setCellValue("District:");
            cell8.setCellValue(districtName);
            String blockName;
            if(reportRequest.getBlockId() !=0){
                blockName =blockDao.findByblockId(reportRequest.getBlockId()).getBlockName();
            }else {
                blockName ="ALL";
            }
            cell9.setCellValue("Block:");
            cell10.setCellValue(blockName);
        }
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);
        spreadsheet.addMergedRegion(new CellRangeAddress(2,2,0,0));
        spreadsheet.addMergedRegion(new CellRangeAddress(2,2,1,2));
        spreadsheet.addMergedRegion(new CellRangeAddress(2,2,5,6));
        spreadsheet.addMergedRegion(new CellRangeAddress(2,2,9,10));

    }
    @Override
    public void createChildImportRejectedFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.childRejected.getServiceType());
        String rootPath = reports +ReportType.childRejected.getReportType()+ "/";
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(toDate);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DAY_OF_MONTH,1);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.childRejected.getReportType());
        toDate=aCalendar.getTime();
        List<ChildImportRejection> rejectedChildImports = childImportRejectionDao.getRejectedChildRecords(toDate);

//        getCumulativeRejectedChildImports(rejectedChildImports, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<ChildImportRejection> candidatesFromThisState = new ArrayList<>();
            for (ChildImportRejection rejectedImport : rejectedChildImports) {
                if ((rejectedImport.getStateId()!=null)&&(rejectedImport.getStateId() == stateId)) {
                    candidatesFromThisState.add(rejectedImport);
                }
            }
            reportRequest.setStateId(stateId);
            getCumulativeRejectedChildImports(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<ChildImportRejection> candidatesFromThisDistrict = new ArrayList<>();
                for (ChildImportRejection rejectedImport : candidatesFromThisState) {
                    if ((rejectedImport.getDistrictId()!=null)&&(rejectedImport.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(rejectedImport);
                    }
                }
                reportRequest.setDistrictId(districtId);
                getCumulativeRejectedChildImports(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<ChildImportRejection> candidatesFromThisBlock = new ArrayList<>();
                    for (ChildImportRejection rejectedImport : candidatesFromThisDistrict) {
                        if ((rejectedImport.getHealthBlockId()!=null)&&(rejectedImport.getHealthBlockId() == blockId)) {
                            candidatesFromThisBlock.add(rejectedImport);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeRejectedChildImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }

    }

    @Override
    public void createMotherImportRejectedFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.motherRejected.getServiceType());
        String rootPath = reports +ReportType.motherRejected.getReportType()+ "/";
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(toDate);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DAY_OF_MONTH,1);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.motherRejected.getReportType());
        toDate=aCalendar.getTime();
        List<MotherImportRejection> rejectedMotherImports = motherImportRejectionDao.getAllRejectedMotherImportRecords(toDate);
//        getCumulativeRejectedMotherImports(rejectedMotherImports, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<MotherImportRejection> candidatesFromThisState = new ArrayList<>();
            for (MotherImportRejection rejectedImport : rejectedMotherImports) {
                if ((rejectedImport.getStateId()!=null)&&(rejectedImport.getStateId() == stateId)) {
                    candidatesFromThisState.add(rejectedImport);
                }
            }
            reportRequest.setStateId(stateId);
            getCumulativeRejectedMotherImports(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<MotherImportRejection> candidatesFromThisDistrict = new ArrayList<>();
                for (MotherImportRejection rejectedImport : candidatesFromThisState) {
                    if ((rejectedImport.getDistrictId()!=null)&&(rejectedImport.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(rejectedImport);
                    }
                }
                reportRequest.setDistrictId(districtId);
                getCumulativeRejectedMotherImports(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<MotherImportRejection> candidatesFromThisBlock = new ArrayList<>();
                    for (MotherImportRejection rejectedImport : candidatesFromThisDistrict) {
                        if ((rejectedImport.getHealthBlockId()!=null)&&(rejectedImport.getHealthBlockId() == blockId)) {
                            candidatesFromThisBlock.add(rejectedImport);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeRejectedMotherImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }

    }

    @Override
    public void createFlwImportRejectedFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.flwRejected.getServiceType());
        String rootPath = reports +ReportType.flwRejected.getReportType()+ "/";
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(toDate);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DAY_OF_MONTH,1);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        toDate=aCalendar.getTime();
        List<FlwImportRejection> rejectedFlwImports = flwImportRejectionDao.getAllRejectedFlwImportRecords(toDate);
        getCumulativeRejectedFlwImports(rejectedFlwImports, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<FlwImportRejection> candidatesFromThisState = new ArrayList<>();
            for (FlwImportRejection rejectedImport : rejectedFlwImports) {
                if ((rejectedImport.getStateId()!=null)&&(rejectedImport.getStateId() == stateId)) {
                    candidatesFromThisState.add(rejectedImport);
                }
            }
            reportRequest.setStateId(stateId);
            getCumulativeRejectedFlwImports(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<FlwImportRejection> candidatesFromThisDistrict = new ArrayList<>();
                for (FlwImportRejection rejectedImport : candidatesFromThisState) {
                    if ((rejectedImport.getDistrictId()!=null)&&(rejectedImport.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(rejectedImport);
                    }
                }
                reportRequest.setDistrictId(districtId);
                getCumulativeRejectedFlwImports(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<FlwImportRejection> candidatesFromThisBlock = new ArrayList<>();
                    for (FlwImportRejection rejectedImport : candidatesFromThisDistrict) {
                        if ((rejectedImport.getHealthBlockId()!=null)&&(rejectedImport.getHealthBlockId() == blockId)) {
                            candidatesFromThisBlock.add(rejectedImport);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeRejectedFlwImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }

    }

    @Override
    public void getCumulativeCourseCompletionFiles(Date toDate) {

        List<State> states = stateDao.getStatesByServiceType(ReportType.maCourse.getServiceType());
        String rootPath = reports+ReportType.maCourse.getReportType()+ "/";
        List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.maInactive.getReportType());
        getCumulativeCourseCompletion(successFullcandidates, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            int stateId = state.getStateId();
            List<MACourseFirstCompletion> candidatesFromThisState = new ArrayList<>();
            for (MACourseFirstCompletion asha : successFullcandidates) {
                if (asha.getStateId() == stateId) {
                    candidatesFromThisState.add(asha);
                }
            }
            reportRequest.setStateId(stateId);
            getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);

            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<MACourseFirstCompletion> candidatesFromThisDistrict = new ArrayList<>();
                for (MACourseFirstCompletion asha : candidatesFromThisState) {
                    if (asha.getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(asha);
                    }
                }
                reportRequest.setDistrictId(0);
                getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";
                    int blockId = block.getBlockId();
                    List<MACourseFirstCompletion> candidatesFromThisBlock = new ArrayList<>();
                    for (MACourseFirstCompletion asha : candidatesFromThisDistrict) {
                        if ((asha.getBlockId()!=null)&&(asha.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(asha);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate,reportRequest);
                }
            }
        }
    }

    @Override
    public void getCircleWiseAnonymousFiles(Date startDate, Date toDate) {
        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = reports+ReportType.maAnonymous.getReportType()+ "/";
        List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(startDate,toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.maAnonymous.getReportType());
        getCircleWiseAnonymousUsers(anonymousUsersList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (Circle circle : circleList) {
            String circleName = StReplace(circle.getCircleName());
            String circleFullName = StReplace(circle.getCircleFullName());
            String rootPathCircle=rootPath+circleFullName+"/";
            List<AnonymousUsers> anonymousUsersListCircle = new ArrayList<>();
            for(AnonymousUsers anonymousUser : anonymousUsersList){
                if(anonymousUser.getCircleName().equalsIgnoreCase(circleName)){
                    anonymousUsersListCircle.add(anonymousUser);
                }
            }
            getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleFullName, toDate, reportRequest);
        }
    }

    @Override
    public void getCumulativeInactiveFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.maInactive.getServiceType());
        String rootPath = reports+ReportType.maInactive.getReportType()+ "/";
        List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers(toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.maInactive.getReportType());
        getCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<FrontLineWorkers> candidatesFromThisState = new ArrayList<>();
            for (FrontLineWorkers asha : inactiveFrontLineWorkers) {
                if (asha.getState() == stateId) {
                    candidatesFromThisState.add(asha);
                }
            }
            reportRequest.setStateId(stateId);
            getCumulativeInactiveUsers(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState  + districtName+ "/";
                int districtId = district.getDistrictId();
                List<FrontLineWorkers> candidatesFromThisDistrict = new ArrayList<>();
                for (FrontLineWorkers asha : candidatesFromThisState) {
                    if (asha.getDistrict() == districtId) {
                        candidatesFromThisDistrict.add(asha);
                    }
                }
                reportRequest.setDistrictId(districtId);
                getCumulativeInactiveUsers(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict  + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<FrontLineWorkers> candidatesFromThisBlock = new ArrayList<>();
                    for (FrontLineWorkers asha : candidatesFromThisDistrict) {
                        if ((asha.getBlock()!=null)&&(asha.getBlock() == blockId)) {
                            candidatesFromThisBlock.add(asha);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    @Override
    public void getKilkariSixWeekNoAnswerFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.sixWeeks.getServiceType());
        String rootPath = reports +ReportType.sixWeeks.getReportType()+ "/";
        List<KilkariDeactivationOther> kilkariDeactivationOthers = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.sixWeeks.getReportType());
        getKilkariSixWeekNoAnswer(kilkariDeactivationOthers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate,reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariDeactivationOther> candidatesFromThisState = new ArrayList<>();
            for (KilkariDeactivationOther kilkari : kilkariDeactivationOthers) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            reportRequest.setStateId(stateId);
            getKilkariSixWeekNoAnswer(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariDeactivationOther> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariDeactivationOther kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                reportRequest.setDistrictId(districtId);
                getKilkariSixWeekNoAnswer(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<KilkariDeactivationOther> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariDeactivationOther kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getKilkariSixWeekNoAnswer(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    @Override
    public void getKilkariLowListenershipDeactivationFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.lowListenership.getServiceType());
        String rootPath = reports +ReportType.lowListenership.getReportType()+ "/";
        List<KilkariDeactivationOther> kilkariDeactivationOthers = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.lowListenership.getReportType());
        getKilkariLowListenershipDeactivation(kilkariDeactivationOthers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariDeactivationOther> candidatesFromThisState = new ArrayList<>();
            for (KilkariDeactivationOther kilkari : kilkariDeactivationOthers) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            reportRequest.setStateId(stateId);
            getKilkariLowListenershipDeactivation(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariDeactivationOther> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariDeactivationOther kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                reportRequest.setDistrictId(districtId);
                getKilkariLowListenershipDeactivation(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<KilkariDeactivationOther> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariDeactivationOther kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getKilkariLowListenershipDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    @Override
    public void getKilkariLowUsageFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.lowUsage.getServiceType());
        String rootPath = reports+ReportType.lowUsage.getReportType() + "/";
        List<KilkariLowUsage> kilkariLowUsageList = kilkariLowUsageDao.getKilkariLowUsageUsers(getMonthYear(toDate));
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.lowUsage.getReportType());
        getKilkariLowUsage(kilkariLowUsageList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate,reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariLowUsage> candidatesFromThisState = new ArrayList<>();
            for (KilkariLowUsage kilkari : kilkariLowUsageList) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            reportRequest.setStateId(stateId);
            getKilkariLowUsage(candidatesFromThisState, rootPathState, stateName, toDate,reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariLowUsage> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariLowUsage kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                reportRequest.setDistrictId(districtId);
                getKilkariLowUsage(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";
                    int blockId = block.getBlockId();
                    List<KilkariLowUsage> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariLowUsage kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getKilkariLowUsage(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    @Override
    public void getKilkariSelfDeactivationFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.selfDeactivated.getServiceType());
        String rootPath = reports+ReportType.selfDeactivated.getReportType() + "/";
        List<KilkariSelfDeactivated> kilkariSelfDeactivatedList = kilkariSelfDeactivatedDao.getSelfDeactivatedUsers(fromDate, toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.selfDeactivated.getReportType());
        getKilkariSelfDeactivation(kilkariSelfDeactivatedList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariSelfDeactivated> candidatesFromThisState = new ArrayList<>();
            for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            reportRequest.setStateId(stateId);
            getKilkariSelfDeactivation(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariSelfDeactivated> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariSelfDeactivated kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                reportRequest.setDistrictId(districtId);
                getKilkariSelfDeactivation(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate,reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";
                    int blockId = block.getBlockId();
                    List<KilkariSelfDeactivated> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariSelfDeactivated kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getKilkariSelfDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }


    private String retrievePropertiesFromFileLocationProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        String fileLocation = null;
        try {
            input = new FileInputStream("fileLocation.properties");
            // load a properties file
            prop.load(input);
            fileLocation = prop.getProperty("fileLocation");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileLocation;
    }

    private void createPropertiesFileForFileLocation() {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("fileLocation.properties");
            prop.setProperty("fileLocation", documents+"BulkImportDatacr7ms10.csv");
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    private String getMonthYear(Date toDate){
        c.setTime(toDate);
        c.add(Calendar.MONTH,-1);
        int month=c.get(Calendar.MONTH)+1;
        int year=(c.get(Calendar.YEAR))%100;
        String monthString;
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
        calendar.add( Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK)-1));
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


    private String getMonthYearName(Date toDate) {
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
//        c.add(Calendar.MONTH, -1);
        int month=c.get(Calendar.MONTH)+1;
//        String monthString = "";
        int year=(c.get(Calendar.YEAR))%100;
        String monthString = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
//        if(month<10){
//            monthString="0"+String.valueOf(month);
//        }
//        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+" "+yearString;
    }


    private String getDateMonthYearName(Date toDate) {
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
        String monthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        String yearString=String.valueOf(year);

        return dateString + " " + monthString+" "+yearString;
    }


}
