package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.*;
import com.beehyv.nmsreporting.model.*;
import com.google.common.base.Strings;
import com.opencsv.CSVReader;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.beehyv.nmsreporting.utils.Global.retrieveDocuments;
import static com.beehyv.nmsreporting.utils.ServiceFunctions.StReplace;
import static com.beehyv.nmsreporting.utils.Constants.header_base64;
import static java.lang.Integer.parseInt;
//import static java.util.Objects.isNull;

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

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final String documents = retrieveDocuments();
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
                    int userRecordNumber = 1;
                    if ((line = fis.readLine()) != null) {
                    }
                    while ((line = fis.readLine()) != null) {
                        userRecordNumber++;
                        // use comma as separator
                        String[] Line = line.split(cvsSplitBy);
                        if (Line.length != 9) {
                            String userNameError = "Please fill in the required fields";
                            errorCreatingUsers.put(userRecordNumber, userNameError);
                            continue;
                        }
                        User user = new User();
                        String fullNameInput = Line[0];//
                        String stateInput = Line[1];
                        String districtInput = Line[2];
                        String blockInput = Line[3];
                        String contactNoInput = Line[4];//
                        String emailIdInput = Line[5];//
                        String userNameInput = Line[6];//
                        String accessLevelInput = Line[7];
                        String roleInput = Line[8];


                        // fullname validation

                        if (!Strings.isNullOrEmpty(fullNameInput)) {
                            fullNameInput=fullNameInput.trim().replaceAll("\\s+", " ");
                         }

                        if (Strings.isNullOrEmpty(fullNameInput)) {
                            errorCreatingUsers.put(userRecordNumber, "Full Name cannot be empty");
                            continue;
                        } else if(Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE).matcher(fullNameInput).find() == true) {
                            errorCreatingUsers.put(userRecordNumber, "Only alphabets and spaces are allowed in Full Name");
                            continue;
                        }
                        else if (fullNameInput.length() < 3) {
                            errorCreatingUsers.put(userRecordNumber, "Full name is too short");
                            continue;
                        }
                        user.setFullName(fullNameInput);

                        // email id validation

                        if (!Strings.isNullOrEmpty(emailIdInput)) {
                            emailIdInput=emailIdInput.trim();
                        }
                        if (Strings.isNullOrEmpty(emailIdInput)) {
                            errorCreatingUsers.put(userRecordNumber, "Email Id cannot be empty");
                            continue;
                        }

                        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                        pattern = Pattern.compile(EMAIL_PATTERN);
                        matcher = pattern.matcher(emailIdInput);
                        boolean validate = matcher.matches();
                        if (!validate) {
                            errorCreatingUsers.put(userRecordNumber, "Email Id is not valid");
                            continue;
                        }
                        user.setEmailId(emailIdInput);

                        // contact no. validation
                        if (!Strings.isNullOrEmpty(contactNoInput)) {
                            contactNoInput=contactNoInput.trim();
                        }

                        if (Strings.isNullOrEmpty(contactNoInput)) {
                            errorCreatingUsers.put(userRecordNumber, "Contact no. cannot be empty");
                            continue;
                        }
                        String regexStr1 = "^[0-9]*$";
                        String regexStr2 = "^[0-9]{10}$";
                        if (!(contactNoInput.matches(regexStr1)) || !(contactNoInput.matches(regexStr2))) {
                            errorCreatingUsers.put(userRecordNumber, "Contact no. is not valid");
                            continue;
                        }
                        user.setPhoneNumber(contactNoInput);

                        // access level validation
                        if (!Strings.isNullOrEmpty(accessLevelInput)) {
                            accessLevelInput=accessLevelInput.trim();
                        }

                        if (Strings.isNullOrEmpty(accessLevelInput)) {
                            errorCreatingUsers.put(userRecordNumber, "Access level cannot be empty");
                            continue;
                        }
                        if (!AccessLevel.isLevel(accessLevelInput)) {
                            errorCreatingUsers.put(userRecordNumber, "Access level is invalid");
                            continue;
                        }

                        // access type (role) validation
                        if (!Strings.isNullOrEmpty(roleInput)) {
                            roleInput=roleInput.trim();
                        }

                        if (Strings.isNullOrEmpty(roleInput)) {
                            errorCreatingUsers.put(userRecordNumber, "Role cannot be empty");
                            continue;
                        }
                        if (!(AccessType.isType(roleInput))) {
                            errorCreatingUsers.put(userRecordNumber, "Role is invalid");
                            continue;
                        }

                        List<Role> userRoles = roleDao.findByRoleDescription(roleInput);

                        if (userRoles == null || userRoles.size() == 0) {
                            errorCreatingUsers.put(userRecordNumber, "Role is invalid");
                            continue;
                        }
                        int userRoleId = userRoles.get(0).getRoleId();

                        user.setRoleId(userRoleId);
                        user.setRoleName(roleDao.findByRoleId(userRoleId).getRoleDescription());


                        // username validation
                        if (!Strings.isNullOrEmpty(userNameInput)) {
                            userNameInput=userNameInput.trim();
                        }

                        if (Strings.isNullOrEmpty(userNameInput)) {
                            errorCreatingUsers.put(userRecordNumber, "Username cannot be empty");
                            continue;
                        } else if (Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE).matcher(userNameInput).find() == true) {
                            errorCreatingUsers.put(userRecordNumber, "Only alphabets and numbers (no spaces) are allowed in UserName");
                            continue;
                        } else if (userNameInput.length() < 5) {
                            errorCreatingUsers.put(userRecordNumber, "Username is too short");
                            continue;
                        }
//                        user.setUsername(userNameInput);

                        User existingUser = userDao.findByUserName(userNameInput);
                        
                        if (existingUser != null) {
                            errorCreatingUsers.put(userRecordNumber, "Username already exists.");
                            continue;
                        }
                        user.setUsername(userNameInput);
                        
                        AccessLevel loggedUserAccessLevel = AccessLevel.getLevel(loggedInUser.getAccessLevel());

                        String userRoleInput = AccessType.getType(roleInput);
                        AccessLevel accessLevel = AccessLevel.getLevel(accessLevelInput);

                        if (!Strings.isNullOrEmpty(stateInput)) {
                            stateInput=stateInput.trim().replaceAll("\\s+", " ");
                        }

                        if (!Strings.isNullOrEmpty(districtInput)) {
                            districtInput=districtInput.trim().replaceAll("\\s+", " ");
                        }

                        if (!Strings.isNullOrEmpty(blockInput)) {
                            blockInput=blockInput.trim().replaceAll("\\s+", " ");
                        }


                        switch(accessLevel) {
                            case BLOCK:
                                if (Strings.isNullOrEmpty(stateInput)
                                        || Strings.isNullOrEmpty(districtInput)
                                        || Strings.isNullOrEmpty(blockInput)) {
                                    errorCreatingUsers.put(userRecordNumber, "State, district and block cannot be empty for BLOCK level users");
                                    continue;
                                }
                                break;
                            case DISTRICT:
                                if (Strings.isNullOrEmpty(stateInput)
                                        || Strings.isNullOrEmpty(districtInput)) {
                                    errorCreatingUsers.put(userRecordNumber, "State and district cannot be empty for DISTRICT level users");
                                    continue;
                                }
                                break;
                            case STATE:
                                if (Strings.isNullOrEmpty(stateInput)) {
                                    errorCreatingUsers.put(userRecordNumber, "State cannot be empty for STATE level users");
                                    continue;
                                }
                        }

                        List<State> userStateList = null;
                        List<District> userDistrictList = null;
                        List<Block> userBlockList = null;

                        if (accessLevel.equals(AccessLevel.STATE)) {
                            userStateList = stateDao.findByName(stateInput);
                        } else if (accessLevel.equals(AccessLevel.DISTRICT)) {
                            userStateList = stateDao.findByName(stateInput);
                            userDistrictList = districtDao.findByName(districtInput);

                        } else if (accessLevel.equals(AccessLevel.BLOCK)) {
                            userStateList = stateDao.findByName(stateInput);
                            userDistrictList = districtDao.findByName(districtInput);
                            userBlockList = blockDao.findByName(blockInput);
                        }

                        if (userRoleInput.equalsIgnoreCase("ADMIN")) {
                            if ((accessLevel == AccessLevel.NATIONAL) ) {
                                errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
                                continue;
                            } else if (loggedUserAccessLevel == AccessLevel.DISTRICT) {
                                errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
                                continue;
                            } else if (accessLevel == AccessLevel.BLOCK) {
                                errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
                                continue;
                            }  else if (accessLevel == AccessLevel.STATE && loggedUserAccessLevel != AccessLevel.NATIONAL) {
                                errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
                                continue;
                            }  else if (accessLevel == AccessLevel.STATE && loggedUserAccessLevel == AccessLevel.NATIONAL) {
                                if(userStateList == null || userStateList.size() == 0){
                                    errorCreatingUsers.put(userRecordNumber, "Provided state is invalid");
                                    continue;
                                }
                                State userState = userStateList.get(0);
                                boolean isAdminAvailable = userDao.isAdminCreated(userState);
                                if (!(isAdminAvailable)) {
                                    user.setAccessLevel(AccessLevel.STATE.getAccessLevel());
                                    user.setStateId(userState.getStateId());
                                } else {
                                    errorCreatingUsers.put(userRecordNumber, "Admin already exists for the provided state");
                                    continue;
                                }
                            }
                            else {
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
                                    errorCreatingUsers.put(userRecordNumber, "Provided district is invalid");
                                    continue;
                                } else if(userStateList == null || userStateList.size() == 0){
                                    errorCreatingUsers.put(userRecordNumber, "Provided state is invalid");
                                    continue;
                                } else {
                                    if ((loggedInUser.getStateId()!=null && !loggedInUser.getStateId().equals(userState.getStateId()))) {
                                        errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
                                        continue;
                                    } else {
                                        boolean isAdminAvailable = userDao.isAdminCreated(userDistrict);
                                        if (!(isAdminAvailable)) {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict.getDistrictId());
                                            user.setStateId(userState.getStateId());
                                        } else {
                                            errorCreatingUsers.put(userRecordNumber, "Admin already exists for the provided district");
                                            continue;
                                        }
                                    }
                                }
                            }
                        }

                        if (userRoleInput.equalsIgnoreCase("USER")) {
                            if (loggedUserAccessLevel.ordinal() > accessLevel.ordinal()) {
                                errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
                                continue;
                            } else {
                                if (accessLevel == AccessLevel.NATIONAL) {
                                    user.setAccessLevel(AccessLevel.NATIONAL.getAccessLevel());
                                } else if (accessLevel == AccessLevel.STATE) {
                                    user.setAccessLevel(accessLevel.getAccessLevel());
                                    if ((userStateList == null) || (userStateList.size() == 0)) {
                                        errorCreatingUsers.put(userRecordNumber, "Provided state is invalid");
                                        continue;
                                    } else {
                                        if (loggedUserAccessLevel == AccessLevel.STATE) {
                                            if (!loggedInUser.getStateId().equals(userStateList.get(0).getStateId())) {
                                                errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
                                                continue;
                                            } else user.setStateId(userStateList.get(0).getStateId());
                                        } else user.setStateId(userStateList.get(0).getStateId());
                                    }
                                } else if (accessLevel == AccessLevel.DISTRICT) {
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
                                        errorCreatingUsers.put(userRecordNumber, "Provided district is invalid");
                                        continue;
                                    }if ((userStateList == null) || (userStateList.size() == 0)) {
                                        errorCreatingUsers.put(userRecordNumber, "Provided state is invalid");
                                        continue;
                                    } else {
                                        if (((loggedUserAccessLevel == AccessLevel.STATE) &&
                                                (!loggedInUser.getStateId().equals(userState.getStateId()))) ||
                                                ((loggedUserAccessLevel == AccessLevel.DISTRICT) &&
                                                        (!loggedInUser.getDistrictId().equals(userDistrict.getDistrictId())))) {
                                            errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
                                            continue;
                                        } else {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict.getDistrictId());
                                            user.setStateId(userState.getStateId());
                                        }
                                    }
                                } else {
                                    user.setAccessLevel(AccessLevel.BLOCK.getAccessLevel());
                                    State userState = null;
                                    District userDistrict = null;
                                    Block userBlock = null;

                                    if ((userBlockList.size() == 0) || userBlockList == null) {
                                        errorCreatingUsers.put(userRecordNumber, "Provided block is invalid");
                                        continue;
                                    } else if (userDistrictList == null || userDistrictList.size() == 0) {
                                        errorCreatingUsers.put(userRecordNumber, "Provided district is invalid");
                                        continue;
                                    }if ((userStateList == null) || (userStateList.size() == 0)) {
                                        errorCreatingUsers.put(userRecordNumber, "Provided state is invalid");
                                        continue;
                                    } else if (userBlockList.size() == 1) {
                                        userBlock = userBlockList.get(0);
                                        userDistrict = districtDao.findByDistrictId(userBlock.getDistrictOfBlock());
                                        userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                        user.setBlockId(userBlock.getBlockId());
                                        user.setStateId(userState.getStateId());
                                        user.setDistrictId(userDistrict.getDistrictId());
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
                                            errorCreatingUsers.put(userRecordNumber, "Provided block is invalid");
                                            continue;
                                        } else {
                                            if (((loggedUserAccessLevel == AccessLevel.STATE) &&
                                                    (!loggedInUser.getStateId().equals(userState.getStateId()))) ||
                                                    ((loggedUserAccessLevel == AccessLevel.DISTRICT) &&
                                                            (!loggedInUser.getDistrictId().equals(userDistrict.getDistrictId())))) {
                                                errorCreatingUsers.put(userRecordNumber, "You don't have authority to create this user");
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

                        user.setPassword(passwordEncoder.encode(contactNoInput));
                        user.setAccountStatus(AccountStatus.ACTIVE.getAccountStatus());
                        user.setCreatedByUser(loggedInUser);
                        user.setCreationDate(new Date());
                        userDao.saveUser(user);
                        ModificationTracker modification = new ModificationTracker();
                        modification.setModificationDate(new Date(System.currentTimeMillis()));
                        modification.setModificationType(ModificationType.CREATE.getModificationType());
                        modification.setModifiedUserId(userDao.findByUserName(user.getUsername()).getUserId());
                        modification.setModifiedByUserId(loggedInUser.getUserId());
                        modificationTrackerDao.saveModification(modification);

                    }
                    if(userRecordNumber == 1){
                        errorCreatingUsers.put(1,"No records present in the uploaded file");
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
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(getMonthYear(toDate));
                getCumulativeCourseCompletion(successFullcandidates, rootPath,AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<MACourseFirstCompletion> candidatesFromThisState = maCourseAttemptDao.getSuccessFulCompletionWithStateId(getMonthYear(toDate),stateId);

                    getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<MACourseFirstCompletion> candidatesFromThisDistrict = maCourseAttemptDao.getSuccessFulCompletionWithDistrictId(getMonthYear(toDate),districtId);

                        getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<MACourseFirstCompletion> candidatesFromThisBlock = maCourseAttemptDao.getSuccessFulCompletionWithBlockId(getMonthYear(toDate),blockId);

                        getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.maInactive.getReportType())){
            reportRequest.setFromDate(toDate);
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

        else if(reportRequest.getReportType().equals(ReportType.maFreshActive.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<FrontLineWorkers> allCandidates = frontLineWorkersDao.getActiveFrontLineWorkers(toDate);
                getCumulativeActiveUsers(allCandidates, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<FrontLineWorkers> candidatesFromThisState = frontLineWorkersDao.getActiveFrontLineWorkersWithStateId(toDate,stateId);

                    getCumulativeActiveUsers(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<FrontLineWorkers> candidatesFromThisDistrict = frontLineWorkersDao.getActiveFrontLineWorkersWithDistrictId(toDate,districtId);

                        getCumulativeActiveUsers(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<FrontLineWorkers> candidatesFromThisBlock = frontLineWorkersDao.getActiveFrontLineWorkersWithBlockId(toDate,blockId);

                        getCumulativeActiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

        else if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){
            reportRequest.setFromDate(toDate);

            if(circleId==0){
                List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(getMonthYear(toDate));
                getCircleWiseAnonymousUsers(anonymousUsersList,  rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String circleName=StReplace(circleDao.getByCircleId(circleId).getCircleName());
                String circleFullName = StReplace(circleDao.getByCircleId(circleId).getCircleFullName());
                String rootPathCircle=rootPath+circleFullName+"/";
                List<AnonymousUsers> anonymousUsersListCircle = anonymousUsersDao.getAnonymousUsersCircle(getMonthYear(toDate), circleDao.getByCircleId(circleId).getCircleFullName());
                getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleFullName, toDate, reportRequest);
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.lowUsage.getReportType())){
            reportRequest.setFromDate(toDate);
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
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<KilkariManualDeactivations> kilkariManualDeactivations = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
                getKilkariSixWeekNoAnswer(kilkariManualDeactivations, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariManualDeactivations> candidatesFromThisState = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithStateId(fromDate,toDate,stateId);

                    getKilkariSixWeekNoAnswer(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariManualDeactivations> candidatesFromThisDistrict = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithDistrictId(fromDate,toDate,districtId);

                        getKilkariSixWeekNoAnswer(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariManualDeactivations> candidatesFromThisBlock = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithBlockId(fromDate,toDate,blockId);

                        getKilkariSixWeekNoAnswer(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

        else if(reportRequest.getReportType().equals(ReportType.lowListenership.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<KilkariManualDeactivations> kilkariManualDeactivations = kilkariSixWeeksNoAnswerDao.getLowListenershipUsers(fromDate, toDate);
                getKilkariLowListenershipDeactivation(kilkariManualDeactivations, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariManualDeactivations> candidatesFromThisState = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithStateId(fromDate,toDate,stateId);

                    getKilkariLowListenershipDeactivation(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariManualDeactivations> candidatesFromThisDistrict = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithDistrictId(fromDate,toDate,districtId);

                        getKilkariLowListenershipDeactivation(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariManualDeactivations> candidatesFromThisBlock = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithBlockId(fromDate,toDate,blockId);

                        getKilkariLowListenershipDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

        else if(reportRequest.getReportType().equals(ReportType.selfDeactivated.getReportType())){
            reportRequest.setFromDate(toDate);
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
            calendar.add(Calendar.DAY_OF_MONTH,-7);
            fromDate=calendar.getTime();
//            List<MotherImportRejection> motherImportRejections = motherImportRejectionDao.getAllRejectedMotherImportRecords(nextDay);
            reportRequest.setFromDate(toDate);
            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<MotherImportRejection> candidatesFromThisState =
                        motherImportRejectionDao.getAllRejectedMotherImportRecordsWithStateId(fromDate, toDate,stateId);

                getCumulativeRejectedMotherImports(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());;
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<MotherImportRejection> candidatesFromThisDistrict =
                            motherImportRejectionDao.getAllRejectedMotherImportRecordsWithDistrictId(fromDate, toDate,districtId);

                    getCumulativeRejectedMotherImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<MotherImportRejection> candidatesFromThisBlock =
                            motherImportRejectionDao.getAllRejectedMotherImportRecordsWithBlockId(fromDate, toDate,blockId);

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
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            fromDate=calendar.getTime();
//            List<ChildImportRejection> childImportRejections = childImportRejectionDao.getRejectedChildRecords(nextDay);
            reportRequest.setFromDate(toDate);
            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<ChildImportRejection> candidatesFromThisState =
                        childImportRejectionDao.getRejectedChildRecordsWithStateId(fromDate, toDate, stateId);

                getCumulativeRejectedChildImports(candidatesFromThisState,rootPathState, stateName, toDate,reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<ChildImportRejection> candidatesFromThisDistrict =
                            childImportRejectionDao.getRejectedChildRecordsWithDistrictId(fromDate, toDate, districtId);

                    getCumulativeRejectedChildImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<ChildImportRejection> candidatesFromThisBlock =
                            childImportRejectionDao.getRejectedChildRecordsWithBlockId(fromDate, toDate,blockId);

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
            toDate=calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            fromDate=calendar.getTime();

            reportRequest.setFromDate(fromDate);
            reportRequest.setToDate(toDate);

            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<FlwImportRejection> candidatesFromThisState =
                        flwImportRejectionDao.getAllRejectedFlwImportRecordsWithStateId(fromDate,toDate,stateId);

                getCumulativeRejectedFlwImports(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<FlwImportRejection> candidatesFromThisDistrict =
                            flwImportRejectionDao.getAllRejectedFlwImportRecordsWithDistrictId(fromDate, toDate,districtId);

                    getCumulativeRejectedFlwImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<FlwImportRejection> candidatesFromThisBlock =
                            flwImportRejectionDao.getAllRejectedFlwImportRecordsWithBlockId(fromDate, toDate,blockId);

                    getCumulativeRejectedFlwImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }

        }
    }

    @Override
    public void modifySpecificReport(ReportRequest reportRequest) {

        String rootPath = reports+reportRequest.getReportType()+ "/";
        Date startDate=new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);

        aCalendar.set(Calendar.DATE, 1);

        Date fromDate = aCalendar.getTime();

        aCalendar.add(Calendar.MONTH, 1);

        Date toDate = aCalendar.getTime();
        int stateId=reportRequest.getStateId();
        int districtId=reportRequest.getDistrictId();
        int blockId=reportRequest.getBlockId();
        if(reportRequest.getReportType().equals(ReportType.maCourse.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(getMonthYear(toDate));
                updateCumulativeCourseCompletion(successFullcandidates, rootPath,AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<MACourseFirstCompletion> candidatesFromThisState = maCourseAttemptDao.getSuccessFulCompletionWithStateId(getMonthYear(toDate),stateId);

                    updateCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<MACourseFirstCompletion> candidatesFromThisDistrict = maCourseAttemptDao.getSuccessFulCompletionWithDistrictId(getMonthYear(toDate),districtId);

                        updateCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<MACourseFirstCompletion> candidatesFromThisBlock = maCourseAttemptDao.getSuccessFulCompletionWithBlockId(getMonthYear(toDate),blockId);

                        updateCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.maInactive.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers(toDate);
                updateCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<FrontLineWorkers> candidatesFromThisState = frontLineWorkersDao.getInactiveFrontLineWorkersWithStateId(toDate,stateId);

                    updateCumulativeInactiveUsers(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<FrontLineWorkers> candidatesFromThisDistrict = frontLineWorkersDao.getInactiveFrontLineWorkersWithDistrictId(toDate,districtId);

                        updateCumulativeInactiveUsers(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<FrontLineWorkers> candidatesFromThisBlock = frontLineWorkersDao.getInactiveFrontLineWorkersWithBlockId(toDate,blockId);

                        updateCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

    }

    @Override
    public void updateCDRDetailsInDatabase() throws IOException {
        CSVReader reader = new CSVReader(new FileReader("emps.csv"), ',');

        // read line by line
        String[] record = null;

//        while ((record = reader.readNext()) != null) {
//            Employee emp = new Employee();
//            emp.setId(record[0]);
//            emp.setName(record[1]);
//            emp.setAge(record[2]);
//            emp.setCountry(record[3]);
//            emps.add(emp);
//        }
//
//        System.out.println(emps);

        reader.close();
    }

    private void getCumulativeRejectedChildImports(List<ChildImportRejection> rejectedChildImports, String rootPath,
                                                   String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Child Import Rejected Details ");
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "Name",
                "Child MCTS ID",
                "Child RCH ID",
                "Mobile Number",
                "State Name",
                "District Name",
                "Taluka Name",
                "Health Block",
                "Health Facility",
                "Health Sub-Facility",
                "Village Name",
                "Rejection Reason"
        });
        Integer counter = 2;
        if(rejectedChildImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (ChildImportRejection childRejection : rejectedChildImports) {
            empinfo.put((counter.toString()), new Object[]{
                    counter-1,
                    (childRejection.getName() == null) ? "No Name": childRejection.getName(),
                    (childRejection.getIdNo() == null) ? "No MCTS ID": childRejection.getIdNo(),
                    (childRejection.getRegistrationNo() == null) ? "No RCH ID": childRejection.getRegistrationNo(),
                    (childRejection.getMobileNo() == null) ? "No Mobile Number": childRejection.getMobileNo(),
                    (childRejection.getStateId() == null) ? "No State Name": stateDao.findByStateId(childRejection.getStateId()).getStateName(),
                    (childRejection.getDistrictName() == null) ? "No District Name": childRejection.getDistrictName(),
                    (childRejection.getTalukaName() == null) ? "No Taluka Name" : childRejection.getTalukaName(),
                    (childRejection.getHealthBlockName() == null) ? "No Health Block Name": childRejection.getHealthBlockName(),
                    (childRejection.getPhcName() == null) ? "No Health Facility" : childRejection.getPhcName(),
                    (childRejection.getSubcentreName() == null) ? "No Health Sub-Facility": childRejection.getSubcentreName(),
                    (childRejection.getVillageName() == null) ? "No Village Name": childRejection.getVillageName(),
                    (childRejection.getRejectionReason() == null) ? "No Rejection Reason": childRejection.getRejectionReason(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if(rowid!=8&&((cellid==3&&!obj.toString().equalsIgnoreCase("No MCTS ID"))
                        ||(cellid==4&&!obj.toString().equalsIgnoreCase("No RCH ID"))
                        ||(cellid==5&&!obj.toString().equalsIgnoreCase("No Mobile Number")))){
                cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1&&!rejectedChildImports.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                cell.setCellStyle(style);}
                else if(rowid == 9 && rejectedChildImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:M9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
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
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "Name",
                "Mother MCTS ID",
                "Mother RCH ID",
                "Mobile Number",
                "State Name",
                "District Name",
                "Taluka Name",
                "Health Block",
                "Health Facility",
                "Health Sub-Facility",
                "Village Name",
                "Rejection Reason"
        });
        Integer counter = 2;
        if(rejectedMotherImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (MotherImportRejection motherRejection : rejectedMotherImports) {
            empinfo.put((counter.toString()), new Object[]{
                    counter-1,
                    (motherRejection.getName() == null) ? "No Name": motherRejection.getName(),
                    (motherRejection.getIdNo() == null) ? "No MCTS ID": motherRejection.getIdNo(),
                    (motherRejection.getRegistrationNo() == null) ? "No RCH ID": motherRejection.getRegistrationNo(),
                    (motherRejection.getMobileNo() == null) ? "No Mobile Number": motherRejection.getMobileNo(),
                    (motherRejection.getStateId() == null) ? "No State Name": stateDao.findByStateId(motherRejection.getStateId()).getStateName(),
                    (motherRejection.getDistrictName() == null) ? "No District Name": motherRejection.getDistrictName(),
                    (motherRejection.getTalukaName() == null) ? "No Taluka Name" : motherRejection.getTalukaName(),
                    (motherRejection.getHealthBlockName() == null) ? "No Health Block": motherRejection.getHealthBlockName(),
                    (motherRejection.getPhcName() == null) ? "No Health Facility" : motherRejection.getPhcName(),
                    (motherRejection.getSubcentreName() == null) ? "No Health Sub-Facility": motherRejection.getSubcentreName(),
                    (motherRejection.getVillageName() == null) ? "No Village Name": motherRejection.getVillageName(),
                    (motherRejection.getRejectionReason() == null) ? "No Rejection Reason": motherRejection.getRejectionReason(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if(rowid!=8&&((cellid==3&&!obj.toString().equalsIgnoreCase("No MCTS ID"))
                        ||(cellid==4&&!obj.toString().equalsIgnoreCase("No RCH ID"))
                        ||(cellid==5&&!obj.toString().equalsIgnoreCase("No Mobile Number")))){
                    cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1 && !rejectedMotherImports.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    cell.setCellStyle(style);}
                else if(rowid == 9 && rejectedMotherImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:M9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
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
                "Asha Import Rejected Details");
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "ASHA Name",
                "ASHA Id",
                "ASHA Mobile Number",
                "State Name",
                "District Name",
                "Taluka Name",
                "Health Block",
                "Health Facility",
                "Health Sub-Facility",
                "Village Name",
                "ASHA Job Status",
                "Reason For Rejection"
        });
        Integer counter = 2;
        if(rejectedChildImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (FlwImportRejection flwRejection : rejectedChildImports) {
            empinfo.put((counter.toString()), new Object[]{
                    counter-1,
                    (flwRejection.getGfName() == null) ? "No ASHA Name": flwRejection.getGfName(),
                    (flwRejection.getFlwId() == null) ? "No ASHA ID": flwRejection.getFlwId(),
                    (flwRejection.getMsisdn() == null) ? "No ASHA Mobile Number": flwRejection.getMsisdn(),
                    (flwRejection.getStateId() == null) ? "No State Name": stateDao.findByStateId(flwRejection.getStateId()).getStateName(),
                    (flwRejection.getDistrictName() == null) ? "No District Name": flwRejection.getDistrictName(),
                    (flwRejection.getTalukaName() == null) ? "No Taluka Name" : flwRejection.getTalukaName(),
                    (flwRejection.getHealthBlockName() == null) ? "No Health Block": flwRejection.getHealthBlockName(),
                    (flwRejection.getPhcName() == null) ? "No Health Facility" : flwRejection.getPhcName(),
                    (flwRejection.getSubcentreName() == null) ? "No Health Sub-Facility" : flwRejection.getSubcentreName(),
                    (flwRejection.getVillageName() == null) ? "No Village Name": flwRejection.getVillageName(),
                    (flwRejection.getGfStatus() == null) ? "No ASHA Job Status": flwRejection.getGfStatus(),
                    (flwRejection.getRejectionReason() == null) ? "No Rejection Reason": flwRejection.getRejectionReason(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        List<String> comments= getHeaderComment().get("Asha Rejected Records");
        Integer index=0;
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if(rowid!=8&&((cellid==4&&!obj.toString().equalsIgnoreCase("No ASHA Mobile Number"))
                        ||(cellid==3&&!obj.toString().equalsIgnoreCase("No ASHA ID")))){
                cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1&& !rejectedChildImports.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    cell.setCellStyle(style);}
                else if(rowid == 9 && rejectedChildImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:M9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
                }
                if(key.equals("1")){
                    createComment(cell,row,comments,index,workbook,spreadsheet);
                    index++;
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            Calendar tempCalender = Calendar.getInstance();
            tempCalender.setTime(toDate);
            tempCalender.add(Calendar.MONTH, 1);
            Date reportMonthName = tempCalender.getTime();
            out = new FileOutputStream(new File(rootPath + ReportType.flwRejected.getReportType() + "_" + place + "_" + getMonthYear(reportMonthName) + ".xlsx"));
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
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "ASHA Name",
                "ASHA MCTS/RCH ID",
                "Mobile Number",
                "State",
                "District",
                "Taluka",
                "Health Block",
                "Health Facility",
                "Health Sub Facility",
                "Village",
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
                    counter-1,
                    (maCourseFirstCompletion.getFullName() == null) ? "No Name":maCourseFirstCompletion.getFullName(),
                    (maCourseFirstCompletion.getExternalFlwId() == null) ? "No FLW_ID":maCourseFirstCompletion.getExternalFlwId(),
                    (maCourseFirstCompletion.getMsisdn() == null) ? "No Phone":maCourseFirstCompletion.getMsisdn(),
                    (maCourseFirstCompletion.getStateId() == null) ? "No State":stateDao.findByStateId(maCourseFirstCompletion.getStateId()).getStateName(),
                    (maCourseFirstCompletion.getDistrictId() == null) ? "No District":districtDao.findByDistrictId(maCourseFirstCompletion.getDistrictId()).getDistrictName(),
                    (maCourseFirstCompletion.getTalukaId() == null) ? "No Taluka" : talukaDao.findByTalukaId(maCourseFirstCompletion.getTalukaId()).getTalukaName(),
                    (maCourseFirstCompletion.getBlockId() == null) ? "No Block" : blockDao.findByblockId(maCourseFirstCompletion.getBlockId()).getBlockName(),
                    (maCourseFirstCompletion.getHealthFacilityId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(maCourseFirstCompletion.getHealthFacilityId()).getHealthFacilityName(),
                    (maCourseFirstCompletion.getHealthSubFacilityId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(maCourseFirstCompletion.getHealthSubFacilityId()).getHealthSubFacilityName(),
                    (maCourseFirstCompletion.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(maCourseFirstCompletion.getVillageId()).getVillageName(),
                    (maCourseFirstCompletion.getCreationDate() == null) ? "No Creation_date":maCourseFirstCompletion.getCreationDate(),
                    (maCourseFirstCompletion.getJobStatus() == null) ? "No Designation":maCourseFirstCompletion.getJobStatus(),
                    (maCourseFirstCompletion.getFirstCompletionDate() == null) ? "No Phone":maCourseFirstCompletion.getFirstCompletionDate(),
                    (maCourseFirstCompletion.getSentNotification() == null) ? "No Details": maCourseFirstCompletion.getSentNotification()
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        List<String> comments= getHeaderComment().get("Course Completion");
        Integer index=0;
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if(rowid!=8&&((cellid==4&&!obj.toString().equalsIgnoreCase("No Phone"))
                        ||(cellid==3&&!obj.toString().equalsIgnoreCase("No FLW_ID")))){
                cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1&& !successfulCandidates.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    cell.setCellStyle(style);}
                else if(rowid == 9 && successfulCandidates.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:O9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
                }
                if(key.equals("1")&&comments.size()!=0 && comments.get(index)!=null) {
                    CreationHelper creationHelper = workbook.getCreationHelper();
                    Drawing drawing = spreadsheet.createDrawingPatriarch();
//            ClientAnchor clientAnchor = drawing.createAnchor(0, 0, 0, 0, 0, 7, 12, 17);
                    ClientAnchor anchor = creationHelper.createClientAnchor();
                    anchor.setCol1(cell.getColumnIndex());
                    anchor.setCol2(cell.getColumnIndex() + 3);
                    anchor.setRow1(row.getRowNum());
                    anchor.setRow2(row.getRowNum() + 1);
                    Comment comment = (Comment) drawing.createCellComment(anchor);
                    RichTextString richTextString = creationHelper.createRichTextString(comments.get(index));
                    comment.setString(richTextString);
                    cell.setCellComment(comment);
                    index++;
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

    private Map<String,List<String>> getHeaderComment() {
        Map<String,List<String>> map = new HashMap<String, List<String>>();

        map.put("Course Completion",Arrays.asList("S.No","ASHA Name","ASHA MCTS/RCH ID","Mobile Number","State","District","Taluka",
                "Health Block","Health Facility","Health Sub Facility","Village","Date when ASHA records came in to the Mobile Academy system for the first time",
                "ASHA’s Status as received from MCTS/RCH","The date when ASHA’s successfully completed the Mobile Academy course for the first time",
                "Refenrece ID generated and SMS sent from our system"));

        map.put("Anonymous User",Arrays.asList("S.No","Circle Name","Mobile Number","Last Called Date & Time"));
        map.put("Registered ASHAs not Started MA Course",Arrays.asList("S.No","ASHA Name","ASHA MCTS/RCH ID","Mobile Number",
                "State","District","Taluka","Health Block","Health Facility","Health Sub Facility","Village","This is the date when ASHA records came in to the Mobile Academy system for the first time",
                "ASHA’s Status as received from MCTS/RCH"));
        map.put("Registered Active ASHAs not completed MA Course",Arrays.asList("S.No","ASHA Name","ASHA MCTS/RCH ID","Mobile Number",
                "State","District","Taluka","Health Block","Health Facility","Health Sub Facility","Village","This is the date when ASHA has started the course for the first time",
                "ASHA’s Status as received from MCTS/RCH"));
        map.put("Asha Rejected Records",Arrays.asList("S.No","ASHA Name","ASHA Id","ASHA Mobile Number","State Name","District Name","Taluka Name","Health Block","Health Facility",
                        "Health Sub-Facility","Village Name","ASHA’s GF Status as received from MCTS/RCH","This gives why the ASHA record was rejected"));
        return map;
    }

    private void updateCumulativeCourseCompletion(List<MACourseFirstCompletion> successfulCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        if(successfulCandidates != null && ! successfulCandidates.isEmpty()) {
            int stateId = successfulCandidates.get(0).getStateId();
            try {
                boolean create = false;
                FileInputStream file = new FileInputStream(rootPath + ReportType.maCourse.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx");

                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Cell cell3, cell4, cell5, cell6, cell7, cell9 = null;

                for (Integer rowcount = 5; rowcount < successfulCandidates.size() + 5; rowcount++) {


                    //Retrieve the row and check for null
                    XSSFRow sheetrow = sheet.getRow(rowcount);
                    if (sheetrow == null) {
                        sheetrow = sheet.createRow(rowcount);
                    }
                    cell9 = sheetrow.getCell(9);
                    if (cell9 == null) {
                        workbook.removeSheetAt(0);
                        getCumulativeCourseCompletion(successfulCandidates,rootPath,place,toDate,reportRequest);
                        create = true;
                        break;
                    } else {
                        Long ext_flw_id = (long) parseInt(cell9.getStringCellValue());
                        MACourseFirstCompletion maCourseFirstCompletion = maCourseAttemptDao.getSuccessFulCompletionByExtrnalFlwId(getMonthYear(toDate), ext_flw_id, stateId);

                        if (maCourseFirstCompletion == null) {
                            continue;
                        }
                        //Update the value of cell
                        cell3 = sheetrow.getCell(3);
                        if (cell3.getStringCellValue().equalsIgnoreCase("No Block") && maCourseFirstCompletion.getBlockId() != null) {
                            cell3.setCellValue(blockDao.findByblockId(maCourseFirstCompletion.getBlockId()).getBlockName());
                        }
                        cell4 = sheetrow.getCell(4);
                        if (cell4.getStringCellValue().equalsIgnoreCase("No Taluka") && maCourseFirstCompletion.getTalukaId() != null) {
                            cell4.setCellValue(talukaDao.findByTalukaId(maCourseFirstCompletion.getTalukaId()).getTalukaName());
                        }
                        cell5 = sheetrow.getCell(5);
                        if (cell5.getStringCellValue().equalsIgnoreCase("No Health Facility") && maCourseFirstCompletion.getHealthFacilityId() != null) {
                            cell5.setCellValue(healthFacilityDao.findByHealthFacilityId(maCourseFirstCompletion.getHealthFacilityId()).getHealthFacilityName());
                        }
                        cell6 = sheetrow.getCell(6);
                        if (cell6.getStringCellValue().equalsIgnoreCase("No Health Subfacility") && maCourseFirstCompletion.getHealthSubFacilityId() != null) {
                            cell6.setCellValue(healthSubFacilityDao.findByHealthSubFacilityId(maCourseFirstCompletion.getHealthSubFacilityId()).getHealthSubFacilityName());
                        }
                        cell7 = sheetrow.getCell(7);
                        if (cell7.getStringCellValue().equalsIgnoreCase("No Village") && maCourseFirstCompletion.getVillageId() != null) {
                            cell7.setCellValue(villageDao.findByVillageId(maCourseFirstCompletion.getVillageId()).getVillageName());
                        }
                    }
                }
                if(!create) {
                    file.close();

                    FileOutputStream outFile = new FileOutputStream(new File(rootPath + ReportType.maCourse.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                    workbook.write(outFile);
                    outFile.close();
                    workbook = new XSSFWorkbook(new FileInputStream(rootPath + ReportType.maCourse.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                }

                } catch(FileNotFoundException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }

            }
        }


    private void getCircleWiseAnonymousUsers(List<AnonymousUsers> anonymousUsersList, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Circle-wise Anonymous Users Report");
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
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
                    counter-1,
                    anonymousUser.getCircleName(),
                    anonymousUser.getMsisdn(),
                    anonymousUser.getLastCalledDate()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        List<String> comments= getHeaderComment().get("Anonymous User");
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}

            Object[] objectArr = empinfo.get(key);

                Cell cell1=row.createCell(0);
                Cell cell2=row.createCell(1);
                Cell cell3=row.createCell(4);
                Cell cell4=row.createCell(7);
            if(key.equals("1")) {
                createComment(cell1, row, comments, 0, workbook, spreadsheet);
                createComment(cell2, row, comments, 1, workbook, spreadsheet);
                createComment(cell3, row, comments, 2, workbook, spreadsheet);
                createComment(cell4, row, comments, 3, workbook, spreadsheet);
            }
                if(rowid == 8){
                    CellRangeAddress range1 =new CellRangeAddress(rowid-1,rowid-1,0,0);
                    spreadsheet.addMergedRegion(range1);
                    CellRangeAddress range2 = new CellRangeAddress(rowid-1,rowid-1,1,3);
                    spreadsheet.addMergedRegion(range2);
                    CellRangeAddress range3 = new CellRangeAddress(rowid-1,rowid-1,4,6);
                    spreadsheet.addMergedRegion(range3);
                    CellRangeAddress range4 = new CellRangeAddress(rowid-1,rowid-1,7,9);
                    spreadsheet.addMergedRegion(range4);
//                    cleanBeforeMergeOnValidCells(spreadsheet,range1,style );
//                    cleanBeforeMergeOnValidCells(spreadsheet,range2,style );
//                    cleanBeforeMergeOnValidCells(spreadsheet,range3,style );
//                    cleanBeforeMergeOnValidCells(spreadsheet,range4,style );
                    cell1.setCellValue(objectArr[0].toString());
                    cell2.setCellValue(objectArr[1].toString());
                    cell3.setCellValue(objectArr[2].toString());
                    cell4.setCellValue(objectArr[3].toString());
                    cell1.setCellStyle(style);
                    cell2.setCellStyle(style);
                    cell3.setCellStyle(style);
                    cell4.setCellStyle(style);
                }

                if(rowid == 9 && anonymousUsersList.isEmpty()){
                    CellUtil.setAlignment(cell1, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:J9"));
                    cell1.setCellValue("No Records to display");
                }

                if(rowid != 8 && !anonymousUsersList.isEmpty()){

                    CellRangeAddress range1 =new CellRangeAddress(rowid-1,rowid-1,0,0);
                    spreadsheet.addMergedRegion(range1);
                    CellRangeAddress range2 = new CellRangeAddress(rowid-1,rowid-1,1,3);
                    spreadsheet.addMergedRegion(range2);
                    CellRangeAddress range3 = new CellRangeAddress(rowid-1,rowid-1,4,6);
                    spreadsheet.addMergedRegion(range3);
                    CellRangeAddress range4 = new CellRangeAddress(rowid-1,rowid-1,7,9);
                    spreadsheet.addMergedRegion(range4);
                    cell1.setCellValue(rowid - 8);
                    cell2.setCellValue(objectArr[1].toString());
                    cell3.setCellValue(objectArr[2].toString());
                    cell4.setCellValue(objectArr[3].toString());
                    cell1.setCellStyle(borderStyle);
                    cell2.setCellStyle(borderStyle);
                    cell3.setCellStyle(borderStyle);
                    cell4.setCellStyle(borderStyle);
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

    private void createComment(Cell cell1, XSSFRow row, List<String> comments, int i, XSSFWorkbook workbook, XSSFSheet spreadsheet) {
            CreationHelper creationHelper = workbook.getCreationHelper();
            Drawing drawing = spreadsheet.createDrawingPatriarch();
            ClientAnchor anchor = creationHelper.createClientAnchor();
            anchor.setCol1(cell1.getColumnIndex());
            anchor.setCol2(cell1.getColumnIndex() + 3);
            anchor.setRow1(row.getRowNum());
            anchor.setRow2(row.getRowNum() + 1);
            Comment comment = (Comment) drawing.createCellComment(anchor);
            RichTextString richTextString = creationHelper.createRichTextString(comments.get(i));
            comment.setString(richTextString);
            cell1.setCellComment(comment);
    }

    private void getCumulativeInactiveUsers(List<FrontLineWorkers> inactiveCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Registered ASHAs not Started MA Course Report "+place+"_"+getMonthYear(toDate));
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "ASHA Name",
                "ASHA MCTS/RCH ID",
                "Mobile Number",
                "State",
                "District",
                "Taluka",
                "Health Block",
                "Health Facility",
                "Health Sub Facility",
                "Village",
                "ASHA Creation Date",
        });
        Integer counter = 2;
        if(inactiveCandidates.isEmpty()) {
            empinfo.put(counter.toString(),new Object[]{"No Records to display"});
        }
        for (FrontLineWorkers frontLineWorker : inactiveCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    counter-1,
                    (frontLineWorker.getFullName() == null) ? "No Name":frontLineWorker.getFullName(),
                    (frontLineWorker.getExternalFlwId() == null) ? "No FLW_ID":frontLineWorker.getExternalFlwId(),
                    (frontLineWorker.getMobileNumber() == null) ? "No Phone":frontLineWorker.getMobileNumber(),
                    (frontLineWorker.getState() == null) ? "No State":stateDao.findByStateId(frontLineWorker.getState()).getStateName(),
                    (frontLineWorker.getDistrict() == null) ? "No District":districtDao.findByDistrictId(frontLineWorker.getDistrict()).getDistrictName(),
                    (frontLineWorker.getTaluka() == null) ? "No Taluka" : talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName(),
                    (frontLineWorker.getBlock() == null) ? "No Block" : blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName(),
                    (frontLineWorker.getFacility() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName(),
                    (frontLineWorker.getSubfacility() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName(),
                    (frontLineWorker.getVillage() == null) ? "No Village" : villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName(),
                    (frontLineWorker.getCreationDate() == null) ? "No Creation_date":frontLineWorker.getCreationDate(),
//                    (frontLineWorker.getJobStatus() == null) ? "No Designation":frontLineWorker.getJobStatus()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        List<String> comments= getHeaderComment().get("Registered ASHAs not Started MA Course");
        Integer index=0;
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {

                Cell cell = row.createCell(cellid++);

                if(rowid!=8&&((cellid==4&&!obj.toString().equalsIgnoreCase("No Phone"))
                        ||(cellid==3&&!obj.toString().equalsIgnoreCase("No FLW_ID")))){
                cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1&& !inactiveCandidates.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    cell.setCellStyle(style);}
                else if(rowid==9 && inactiveCandidates.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:M9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
                }

                if(key.equals("1")){
                    createComment(cell,row,comments,index,workbook,spreadsheet);
                    index++;
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

    private void getCumulativeActiveUsers(List<FrontLineWorkers> activeCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Registered Active ASHAs not Completed MA Course Report "+place+"_"+getMonthYear(toDate));
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "ASHA Name",
                "ASHA MCTS/RCH ID",
                "Mobile Number",
                "State",
                "District",
                "Taluka",
                "Health Block",
                "Health Facility",
                "Health Sub Facility",
                "Village",
                "ASHA Course Start Date",
                "ASHA Job Status",
        });
        Integer counter = 2;
        if(activeCandidates.isEmpty()) {
            empinfo.put(counter.toString(),new Object[]{"No Records to display"});
        }
        for (FrontLineWorkers frontLineWorker : activeCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    counter-1,
                    (frontLineWorker.getFullName() == null) ? "No Name":frontLineWorker.getFullName(),
                    (frontLineWorker.getExternalFlwId() == null) ? "No FLW_ID":frontLineWorker.getExternalFlwId(),
                    (frontLineWorker.getMobileNumber() == null) ? "No Phone":frontLineWorker.getMobileNumber(),
                    (frontLineWorker.getState() == null) ? "No State":stateDao.findByStateId(frontLineWorker.getState()).getStateName(),
                    (frontLineWorker.getDistrict() == null) ? "No District":districtDao.findByDistrictId(frontLineWorker.getDistrict()).getDistrictName(),
                    (frontLineWorker.getTaluka() == null) ? "No Taluka" : talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName(),
                    (frontLineWorker.getBlock() == null) ? "No Block" : blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName(),
                    (frontLineWorker.getFacility() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName(),
                    (frontLineWorker.getSubfacility() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName(),
                    (frontLineWorker.getVillage() == null) ? "No Village" : villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName(),
                    (frontLineWorker.getCreationDate() == null) ? "No Course_start_date":frontLineWorker.getCourseStartDate(),
                    (frontLineWorker.getJobStatus() == null) ? "Job status not defined":frontLineWorker.getJobStatus(),
//                    (frontLineWorker.getJobStatus() == null) ? "No Designation":frontLineWorker.getJobStatus()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        List<String> comments= getHeaderComment().get("Registered Active ASHAs not completed MA Course");
        Integer index=0;
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {

                Cell cell = row.createCell(cellid++);

                if(rowid!=8&&((cellid==4&&!obj.toString().equalsIgnoreCase("No Phone"))
                        ||(cellid==3&&!obj.toString().equalsIgnoreCase("No FLW_ID")))){
                    cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1&& !activeCandidates.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    cell.setCellStyle(style);}
                else if(rowid==9 && activeCandidates.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:M9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
                }

                if(key.equals("1")){
                    createComment(cell,row,comments,index,workbook,spreadsheet);
                    index++;
                }

            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.maFreshActive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
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


    private void updateCumulativeInactiveUsers(List<FrontLineWorkers> inactiveCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {

        if(! inactiveCandidates.isEmpty())
        {
        try {
            boolean create =false;

            FileInputStream file = new FileInputStream(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell3,cell4,cell5,cell6,cell7, cell9 = null;

            for (Integer rowcount = 5; ; rowcount++) {


                //Retrieve the row and check for null
                XSSFRow sheetrow = sheet.getRow(rowcount);
                if (sheetrow == null) {
                    break;
                }

                cell9 = sheetrow.getCell(9);
                if (cell9 == null) {
                    continue;

                } else {
                    String ext_flw_id = cell9.getStringCellValue();
                    FrontLineWorkers frontLineWorker = frontLineWorkersDao.getINactiveFrontLineWorkerByExternalFlwID(toDate, ext_flw_id);
                    //Update the value of cell
                    cell3 = sheetrow.getCell(3);
                    if (cell3.getStringCellValue().equalsIgnoreCase("No Block") && frontLineWorker.getBlock() != null) {
                        String temp =blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName();
                        cell3.setCellValue(temp);
                    }
                    cell4 = sheetrow.getCell(4);
                    if (cell4.getStringCellValue().equalsIgnoreCase("No Taluka") && frontLineWorker.getTaluka() != null) {
                        cell4.setCellValue(talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName());
                    }
                    cell5 = sheetrow.getCell(5);
                    if (cell5.getStringCellValue().equalsIgnoreCase("No Health Facility") && frontLineWorker.getFacility() != null) {
                        cell5.setCellValue(healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName());
                    }
                    cell6 = sheetrow.getCell(6);
                    if (cell6.getStringCellValue().equalsIgnoreCase("No Health Subfacility") && frontLineWorker.getSubfacility() != null) {
                        cell6.setCellValue(healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName());
                    }
                    cell7 = sheetrow.getCell(7);
                    if (cell7.getStringCellValue().equalsIgnoreCase("No Village") && frontLineWorker.getVillage() != null) {
                        cell7.setCellValue(villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName());
                    }
                }
            }
            if(!create) {
                file.close();

                FileOutputStream outFile = new FileOutputStream(new File(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                workbook.write(outFile);
                outFile.close();
                workbook = new XSSFWorkbook(new FileInputStream(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       }
    }

    private void updateCumulativeInactiveUsersInExcel(HashMap<String, FrontLineWorkers> frontLineWorkersHashMap, Integer stateId, String rootPath, String place, Date toDate) {

        if(stateId != null) {
            State state = stateDao.findByStateId(stateId);
            String stateName = state.getStateName();
            try {
                boolean create =false;

                FileInputStream file = new FileInputStream(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx");

                System.out.println("Accessed File " + rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx");

                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Cell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell0 = null;

                XSSFRow sheetrow1 = sheet.getRow(0);


                cell0 = sheetrow1.getCell(0);


                if(cell0 != null){

                    if(cell0.getStringCellValue().contains("Report:")) {

                        for (Integer rowcount = 5; ; rowcount++) {

                            //Retrieve the row and check for null
                            XSSFRow sheetrow = sheet.getRow(rowcount);
                            if (sheetrow == null) {
                                break;
                            }

                            cell1 = sheetrow.getCell(1);

                            cell9 = sheetrow.getCell(9);

                            if(cell9 != null){
                                cell9.setCellType(Cell.CELL_TYPE_STRING);

                            }
                            if (cell9 == null || cell9.getStringCellValue() == null) {
                                continue;

                            } else if (cell1.getStringCellValue().equalsIgnoreCase(stateName)) {
                                String ext_flw_id = cell9.getStringCellValue();
                                FrontLineWorkers frontLineWorker = frontLineWorkersHashMap.get(ext_flw_id);
                                //Update the value of cell

                                if (frontLineWorker == null) {
                                    continue;
                                }
                                cell3 = sheetrow.getCell(3);
                                if (frontLineWorker.getBlock() != null) {
                                    String temp = blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName();
                                    cell3.setCellValue(temp);
                                } else {
                                    cell3.setCellValue("No Block");
                                }

                                cell4 = sheetrow.getCell(4);
                                if (frontLineWorker.getTaluka() != null) {
                                    cell4.setCellValue(talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName());
                                } else {
                                    cell4.setCellValue("No Taluka");
                                }

                                cell5 = sheetrow.getCell(5);
                                if (frontLineWorker.getFacility() != null) {
                                    cell5.setCellValue(healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName());
                                } else {
                                    cell5.setCellValue("No Health Facility");
                                }

                                cell6 = sheetrow.getCell(6);
                                if (frontLineWorker.getSubfacility() != null) {
                                    cell6.setCellValue(healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName());
                                } else {
                                    cell6.setCellValue("No Health Subfacility");
                                }

                                cell7 = sheetrow.getCell(7);
                                if (frontLineWorker.getVillage() != null) {
                                    cell7.setCellValue(villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName());
                                } else {
                                    cell7.setCellValue("No Village");
                                }
                            }
                        }
                    } else if(cell0.getStringCellValue().contains("Mobile Number")) {

                        for (Integer rowcount = 1; ; rowcount++) {

                            //Retrieve the row and check for null
                            XSSFRow sheetrow = sheet.getRow(rowcount);
                            if (sheetrow == null) {
                                break;
                            }

                            cell1 = sheetrow.getCell(1);

                            cell9 = sheetrow.getCell(9);

                            if(cell9 != null){
                                cell9.setCellType(Cell.CELL_TYPE_STRING);

                            }
                            if (cell9 == null || cell9.getStringCellValue() == null) {
                                continue;

                            } else if (cell1.getStringCellValue().equalsIgnoreCase(stateName)) {
                                String ext_flw_id = cell9.getStringCellValue();
                                FrontLineWorkers frontLineWorker = frontLineWorkersHashMap.get(ext_flw_id);
                                //Update the value of cell

                                if (frontLineWorker == null) {
                                    continue;
                                }
                                cell3 = sheetrow.getCell(3);
                                if (frontLineWorker.getBlock() != null) {
                                    String temp = blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName();
                                    cell3.setCellValue(temp);
                                } else {
                                    cell3.setCellValue("No Block");
                                }

                                cell4 = sheetrow.getCell(4);
                                if (frontLineWorker.getTaluka() != null) {
                                    cell4.setCellValue(talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName());
                                } else {
                                    cell4.setCellValue("No Taluka");
                                }

                                cell5 = sheetrow.getCell(5);
                                if (frontLineWorker.getFacility() != null) {
                                    cell5.setCellValue(healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName());
                                } else {
                                    cell5.setCellValue("No Health Facility");
                                }

                                cell6 = sheetrow.getCell(6);
                                if (frontLineWorker.getSubfacility() != null) {
                                    cell6.setCellValue(healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName());
                                } else {
                                    cell6.setCellValue("No Health Subfacility");
                                }

                                cell7 = sheetrow.getCell(7);
                                if (frontLineWorker.getVillage() != null) {
                                    cell7.setCellValue(villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName());
                                } else {
                                    cell7.setCellValue("No Village");
                                }
                            }
                        }
                    }
                } else {

                        for (Integer rowcount = 8; ; rowcount++) {

                            //Retrieve the row and check for null
                            XSSFRow sheetrow = sheet.getRow(rowcount);
                            if (sheetrow == null) {
                                break;
                            }

                            cell4 = sheetrow.getCell(4);

                            cell2 = sheetrow.getCell(2);

                            if(cell2 != null){
                                cell2.setCellType(Cell.CELL_TYPE_STRING);
                            }

                            if (cell2 == null || cell2.getStringCellValue() == null) {
                                continue;

                            } else if (cell4.getStringCellValue().equalsIgnoreCase(stateName)) {
                                String ext_flw_id = cell2.getStringCellValue();
                                FrontLineWorkers frontLineWorker = frontLineWorkersHashMap.get(ext_flw_id);
                                //Update the value of cell

                                if (frontLineWorker == null) {
                                    continue;
                                }
                                cell7 = sheetrow.getCell(7);
                                if (frontLineWorker.getBlock() != null) {
                                    String temp = blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName();
                                    cell7.setCellValue(temp);
                                } else {
                                    cell7.setCellValue("No Block");
                                }

                                cell6 = sheetrow.getCell(6);
                                if (frontLineWorker.getTaluka() != null) {
                                    cell6.setCellValue(talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName());
                                } else {
                                    cell6.setCellValue("No Taluka");
                                }

                                cell8 = sheetrow.getCell(8);
                                if (frontLineWorker.getFacility() != null) {
                                    cell8.setCellValue(healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName());
                                } else {
                                    cell8.setCellValue("No Health Facility");
                                }

                                cell9 = sheetrow.getCell(9);
                                if (frontLineWorker.getSubfacility() != null) {
                                    cell9.setCellValue(healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName());
                                } else {
                                    cell9.setCellValue("No Health Subfacility");
                                }

                                cell10 = sheetrow.getCell(10);
                                if (frontLineWorker.getVillage() != null) {
                                    cell10.setCellValue(villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName());
                                } else {
                                    cell10.setCellValue("No Village");
                                }
                            }
                        }

                }




                if(!create) {
                    file.close();

                    FileOutputStream outFile = new FileOutputStream(new File(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                    workbook.write(outFile);
                    outFile.close();
                    workbook = new XSSFWorkbook(new FileInputStream(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getKilkariSixWeekNoAnswer(List<KilkariManualDeactivations> kilkariSixWeeksNoAnswersList, String rootPath, String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Non-answering beneficiaries Report");
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "Benificiary Name",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Mobile Number",
                "State",
                "District",
                "Taluka",
                "Health block",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Age On Service In Weeks"});
        Integer counter = 2;
        if(kilkariSixWeeksNoAnswersList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (KilkariManualDeactivations kilkari : kilkariSixWeeksNoAnswersList) {
            empinfo.put((counter.toString()), new Object[]{
                    counter-1,
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
//                    (kilkari.getVillageId() == null) ? "No Taluka" : talukaDao.findByTalukaId(villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage()).getTalukaName(),
                    (kilkari.getTalukaId() == null) ? "No Taluka" : talukaDao.findByTalukaId(kilkari.getTalukaId()).getTalukaName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
//                    (kilkari.getHsubcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility()).getHealthFacilityName(),
                    (kilkari.getHcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(kilkari.getHcenterId()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if(rowid!=8&&((cellid==3&&!obj.toString().equalsIgnoreCase("No MCTS Id"))
                        ||(cellid==4&&!obj.toString().equalsIgnoreCase("No RCH Id"))
                        ||(cellid==5&&!obj.toString().equalsIgnoreCase("No MSISDN")))){
                cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1&& !kilkariSixWeeksNoAnswersList.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    cell.setCellStyle(style);}
                else if(rowid==9 && kilkariSixWeeksNoAnswersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:M9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
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

    private void getKilkariLowListenershipDeactivation(List<KilkariManualDeactivations> lowListenershipList, String rootPath, String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Low Listenership deactivated beneficiaries Report");
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "Benificiary Name",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Mobile Number",
                "State",
                "District",
                "Taluka",
                "Health block",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Age On Service In Weeks"});
        Integer counter = 2;
        if(lowListenershipList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (KilkariManualDeactivations kilkari : lowListenershipList) {
            empinfo.put((counter.toString()), new Object[]{
                    counter-1,
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getTalukaId() == null) ? "No Taluka" : talukaDao.findByTalukaId(kilkari.getTalukaId()).getTalukaName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getHcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(kilkari.getHcenterId()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService(),
//                    checkEmptyOrNull(kilkari.getAgeOnService())

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if(rowid!=8&&((cellid==3&&!obj.toString().equalsIgnoreCase("No MCTS Id"))
                        ||(cellid==4&&!obj.toString().equalsIgnoreCase("No RCH Id"))
                        ||(cellid==5&&!obj.toString().equalsIgnoreCase("No MSISDN"))
                        ||(cellid==13&&!obj.toString().equalsIgnoreCase("No Age_Data")))){
                cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1 && !lowListenershipList.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    cell.setCellStyle(style);}
                else if(rowid==6 && lowListenershipList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:M9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
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

    private Boolean checkEmptyOrNull(String value) {
        return Strings.isNullOrEmpty(value) || "null".equalsIgnoreCase(value);
    }

    private void getKilkariSelfDeactivation(List<KilkariSelfDeactivated> kilkariSelfDeactivatedList, String rootPath,
                                            String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari self-deactivators Report");
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "Benificiary Name",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Mobile Number",
                "State",
                "District",
                "Taluka",
                "Health block",
                "Health Facility",
                "Health SubFacility",
                "Village",
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
                    counter-1,
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getTalukaId() == null) ? "No Taluka" : talukaDao.findByTalukaId(kilkari.getTalukaId()).getTalukaName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getHcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(kilkari.getHcenterId()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService(),
                    (kilkari.getPackActivationDate() == null) ? "No Activation_date" :kilkari.getPackActivationDate(),
                    (kilkari.getDeactivationDate() == null) ? "No Deactivation_date" :kilkari.getDeactivationDate(),
                    (kilkari.getCallsAnswered() == null) ? "No Call_Data" :kilkari.getCallsAnswered()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if(rowid!=8&&((cellid==3&&!obj.toString().equalsIgnoreCase("No MCTS Id"))
                        ||(cellid==4&&!obj.toString().equalsIgnoreCase("No RCH Id"))
                        ||(cellid==5&&!obj.toString().equalsIgnoreCase("No MSISDN")))){
                cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1&& !kilkariSelfDeactivatedList.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    row.setHeight((short)1100);
                    cell.setCellStyle(style);}
                else if(rowid==9 && kilkariSelfDeactivatedList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:P9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
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
        spreadsheet.protectSheet("123");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        CellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setWrapText(true);
        borderStyle.setLocked(false);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "S.No",
                "Benificiary Name",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Mobile Number",
                "State",
                "District",
                "Taluka",
                "Health block",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Age On Service In Weeks"
        });
        Integer counter = 2;
        if(kilkariLowUsageList.isEmpty()) {
            empinfo.put(counter.toString(),new Object[]{"No Records to display"});
        }
         logger.info("this is currenttime before the loop: {} ",new Date());
//        for (KilkariLowUsage kilkari : kilkariLowUsageList) {
//
//
//            empinfo.put((counter.toString()), new Object[]{
//                    counter-1,
//                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
//                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
//                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
//                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
//                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
//                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
//                    (kilkari.getTalukaId() == null) ? "No Taluka" : talukaDao.findByTalukaId(kilkari.getTalukaId()).getTalukaName(),
//                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
//                    (kilkari.getHcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(kilkari.getHcenterId()).getHealthFacilityName(),
//                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
//                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
//                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService(),
//            });
//            counter++;
//
//        }

         Set<Integer> stateIds = new HashSet<>();
         Set<Integer> districtIds = new HashSet<>();
         Set<Integer> talukaIds = new HashSet<>();
         Set<Integer> blockIds = new HashSet<>();
         Set<Integer> healthFacilityIds = new HashSet<>();
         Set<Integer> healthSubFacilityIds = new HashSet<>();
         Set<Integer> villageIds = new HashSet<>();


         for (KilkariLowUsage kilkari : kilkariLowUsageList) {
             if (kilkari.getStateId() != null) stateIds.add(kilkari.getStateId());
             if (kilkari.getDistrictId() != null) districtIds.add(kilkari.getDistrictId());
             if (kilkari.getTalukaId() != null) talukaIds.add(kilkari.getTalukaId());
             if (kilkari.getBlockId() != null) blockIds.add(kilkari.getBlockId());
             if (kilkari.getHcenterId() != null) healthFacilityIds.add(kilkari.getHcenterId());
             if (kilkari.getHsubcenterId() != null) healthSubFacilityIds.add(kilkari.getHsubcenterId());
             if (kilkari.getVillageId() != null) villageIds.add(kilkari.getVillageId());
         }


         logger.info("this is stateIds: {}, districtIds: {},talukaIds: {},blockIds: {},healthFacilityIds: {}",stateIds,districtIds,talukaIds,blockIds,healthFacilityIds);
         Map<Integer, String> stateMap = new HashMap<>();
         Map<Integer, String> districtMap = new HashMap<>();
         Map<Integer, String> talukaMap = new HashMap<>();
         Map<Integer, String> blockMap = new HashMap<>();
         Map<Integer, String> healthFacilityMap = new HashMap<>();
         Map<Integer, String> healthSubFacilityMap = new HashMap<>();
         Map<Integer, String> villageMap = new HashMap<>();

         if (stateIds != null && !stateIds.isEmpty()) {
             List<State> states = stateDao.findByIds(stateIds);
             logger.info("States: {}", states);

             for (State state : states) {
                 stateMap.put(state.getStateId(), state.getStateName());
             }
         }

         if (districtIds != null && !districtIds.isEmpty()) {
             List<District> districts = districtDao.findByIds(districtIds);
             logger.info("Districts: {}", districts);

             for (District district : districts) {
                 districtMap.put(district.getDistrictId(), district.getDistrictName());
             }
         }

         if (talukaIds != null && !talukaIds.isEmpty()) {
             List<Taluka> talukas = talukaDao.findByIds(talukaIds);
             logger.info("Talukas: {}", talukas);

             for (Taluka taluka : talukas) {
                 talukaMap.put(taluka.getTalukaId(), taluka.getTalukaName());
             }
         }

         if (blockIds != null && !blockIds.isEmpty()) {
             List<Block> blocks = blockDao.findByIds(blockIds);
             logger.info("Blocks: {}", blocks);

             for (Block block : blocks) {
                 blockMap.put(block.getBlockId(), block.getBlockName());
             }
         }

         if (healthFacilityIds != null && !healthFacilityIds.isEmpty()) {
             List<HealthFacility> healthFacilities = healthFacilityDao.findByIds(healthFacilityIds);
             logger.info("Health Facilities: {}", healthFacilities);

             for (HealthFacility healthFacility : healthFacilities) {
                 healthFacilityMap.put(healthFacility.getHealthFacilityId(), healthFacility.getHealthFacilityName());
             }
         }

         if (healthSubFacilityIds != null && !healthSubFacilityIds.isEmpty()) {
             List<HealthSubFacility> healthSubFacilities = healthSubFacilityDao.findByIds(healthSubFacilityIds);
             logger.info("Health Sub Facilities: {}", healthSubFacilities);

             for (HealthSubFacility healthSubFacility : healthSubFacilities) {
                 healthSubFacilityMap.put(healthSubFacility.getHealthSubFacilityId(), healthSubFacility.getHealthSubFacilityName());
             }
         }

         if (villageIds != null && !villageIds.isEmpty()) {
             List<Village> villages = villageDao.findByVillageIds(villageIds);
             logger.info("Villages: {}", villages);

             for (Village village : villages) {
                 villageMap.put(village.getVillageId(), village.getVillageName());
             }
         }




         logger.info("before kilkari loop");
         for (KilkariLowUsage kilkari : kilkariLowUsageList) {
             empinfo.put(String.valueOf(counter), new Object[]{
                     counter - 1,
                     (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                     (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                     (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                     (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                     (kilkari.getStateId() == null) ? "No State" : stateMap.get(kilkari.getStateId()),
                     (kilkari.getDistrictId() == null) ? "No District" : districtMap.get(kilkari.getDistrictId()),
                     (kilkari.getTalukaId() == null) ? "No Taluka" : talukaMap.get(kilkari.getTalukaId()),
                     (kilkari.getBlockId() == null) ? "No Block" : blockMap.get(kilkari.getBlockId()),
                     (kilkari.getHcenterId() == null) ? "No Health Facility" : healthFacilityMap.get(kilkari.getHcenterId()),
                     (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityMap.get(kilkari.getHsubcenterId()),
                     (kilkari.getVillageId() == null) ? "No Village" : villageMap.get(kilkari.getVillageId()),
                     (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService()
             });
             counter++;
         }

        logger.info("this is currenttime after the loop: {} ",new Date());
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            if(rowid==8){
                row.setHeight((short)1100);}
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if(rowid!=8&&((cellid==3&&!obj.toString().equalsIgnoreCase("No MCTS Id"))
                        ||(cellid==4&&!obj.toString().equalsIgnoreCase("No RCH Id"))
                        ||(cellid==5&&!obj.toString().equalsIgnoreCase("No MSISDN")))){
                    cell.setCellValue(obj.toString());}
                else{
                    cell.setCellValue(obj.toString());
                }
                if(rowid!=8&&cellid==1&& !kilkariLowUsageList.isEmpty()){
                    cell.setCellValue(rowid-8);
                }
                if(rowid==8){
                    cell.setCellStyle(style);}
                else if(rowid==9 && kilkariLowUsageList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:M9"));
                }
                else{
                    cell.setCellStyle(borderStyle);
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

    private void createHeadersForReportFiles(XSSFWorkbook workbook, ReportRequest reportRequest)  {
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.createRow(rowid++);


            String encodingPrefix = "base64,";
            String pngImageURL = header_base64;
            int contentStartIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] imageData = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStartIndex));//workbook.addPicture can use this byte array

            final int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);


            final CreationHelper helper = workbook.getCreationHelper();
            final Drawing drawing = spreadsheet.createDrawingPatriarch();

            final ClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );


            anchor.setCol1( 0 );
            anchor.setRow1( 0 );
            anchor.setRow2( 3 );
            anchor.setCol2( 12 );
            drawing.createPicture( anchor, pictureIndex );



        XSSFCellStyle style1 = workbook.createCellStyle();//Create style
        style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style1.setBorderBottom(CellStyle.BORDER_MEDIUM);

        spreadsheet.addMergedRegion(new CellRangeAddress(0,2,0,11));

        rowid = rowid+2;
        XSSFRow row=spreadsheet.createRow(rowid++);
        XSSFCellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);//set it to bold
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        Cell cell1=row.createCell(0);
        Cell cell2=row.createCell(1);
        Cell cell3=row.createCell(7);
        Cell cell4=row.createCell(8);
        CellRangeAddress range1 =new CellRangeAddress(3,4,0,0);
            cleanBeforeMergeOnValidCells(spreadsheet,range1,style );
            spreadsheet.addMergedRegion(range1);
        CellRangeAddress range2 = new CellRangeAddress(3,4,1,6);
            cleanBeforeMergeOnValidCells(spreadsheet,range2,style );
            spreadsheet.addMergedRegion(range2);
        CellRangeAddress range3 = new CellRangeAddress(3,4,7,7);
            cleanBeforeMergeOnValidCells(spreadsheet,range3,style );
            spreadsheet.addMergedRegion(range3);
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
            cell4.setCellValue(getMonthYear(reportRequest.getFromDate()));
            CellRangeAddress range4 = new CellRangeAddress(3,4,8,11);
            cleanBeforeMergeOnValidCells(spreadsheet,range4,style );
            spreadsheet.addMergedRegion(range4);
            cell5.setCellValue("Circle:");
            cell6.setCellValue(circleName);
        }else {
            if(reportRequest.getReportType().equals(ReportType.motherRejected.getReportType())||
                    reportRequest.getReportType().equals(ReportType.childRejected.getReportType())){
                cell3.setCellValue("Week:");
                cell4.setCellValue(getDateMonthYearName(reportRequest.getFromDate()));
                CellRangeAddress range4 = new CellRangeAddress(3,4,8,11);
                    cleanBeforeMergeOnValidCells(spreadsheet,range4,style );
                    spreadsheet.addMergedRegion(range4);
            }
            else if(reportRequest.getReportType().equals(ReportType.flwRejected.getReportType())){
                Calendar tempCalender = Calendar.getInstance();
                tempCalender.setTime(reportRequest.getToDate());
                tempCalender.add(Calendar.MONTH, 1);
                Date reportMonthName = tempCalender.getTime();
                cell3.setCellValue("Month:");
                cell4.setCellValue(getMonthYearName(reportMonthName));
                CellRangeAddress range4 = new CellRangeAddress(3,4,8,11);
                cleanBeforeMergeOnValidCells(spreadsheet,range4,style );
                spreadsheet.addMergedRegion(range4);
            }
            else {
                cell3.setCellValue("Month:");
                cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
                CellRangeAddress range4 = new CellRangeAddress(3,4,8,11);
                cleanBeforeMergeOnValidCells(spreadsheet,range4,style );
                spreadsheet.addMergedRegion(range4);
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
        if(!reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())) {
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);



        }
        XSSFRow row2=spreadsheet.createRow(++rowid);
        Cell cell11=row2.createCell(0);
        Cell cell12=row2.createCell(3);
        cell11.setCellValue("Report Generated on : ");
        cell12.setCellValue(getTodayDateMonthYear());
        cell11.setCellStyle(style);
        cell12.setCellStyle(style);
        CellRangeAddress range5 =new CellRangeAddress(5,5,1,3);
            cleanBeforeMergeOnValidCells(spreadsheet,range5,style );
            spreadsheet.addMergedRegion(range5);
        CellRangeAddress range8 = new CellRangeAddress(6, 6, 0, 2);
        cleanBeforeMergeOnValidCells(spreadsheet, range8, style);
        spreadsheet.addMergedRegion(range8);
        if(!reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())) {
            CellRangeAddress range6 = new CellRangeAddress(5, 5, 5, 7);
            cleanBeforeMergeOnValidCells(spreadsheet, range6, style);
            spreadsheet.addMergedRegion(range6);
            CellRangeAddress range7 = new CellRangeAddress(5, 5, 9, 11);
            cleanBeforeMergeOnValidCells(spreadsheet, range7, style);
            spreadsheet.addMergedRegion(range7);

        }

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
        toDate=aCalendar.getTime();
        aCalendar.add(Calendar.DAY_OF_MONTH, -7);
        Date fromDate=aCalendar.getTime();
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.childRejected.getReportType());
        List<ChildImportRejection> rejectedChildImports = childImportRejectionDao.getRejectedChildRecords(fromDate,toDate);

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
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
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
                reportRequest.setBlockId(0);
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
        toDate=aCalendar.getTime();
        aCalendar.add(Calendar.DAY_OF_MONTH, -7);
        Date fromDate=aCalendar.getTime();
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.motherRejected.getReportType());
        List<MotherImportRejection> rejectedMotherImports =
                motherImportRejectionDao.getAllRejectedMotherImportRecords(fromDate, toDate);
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
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
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
                reportRequest.setBlockId(0);
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

        toDate=aCalendar.getTime();
        aCalendar.set(Calendar.DAY_OF_MONTH, aCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date fromDate=aCalendar.getTime();
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(fromDate);
        reportRequest.setToDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.flwRejected.getReportType());

        List<FlwImportRejection> rejectedFlwImports = flwImportRejectionDao.getAllRejectedFlwImportRecords(fromDate, toDate);
//        getCumulativeRejectedFlwImports(rejectedFlwImports, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
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
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
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
                reportRequest.setBlockId(0);
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
        List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(getMonthYear(toDate));
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.maCourse.getReportType());
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
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
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
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
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
    public void modifyCumulativeCourseCompletionFiles(Date toDate, Integer stateIdRequest) {

        String rootPath = reports+ReportType.maCourse.getReportType()+ "/";
//        String rootPath = "/home/grameen/home/MA_Cumulative_Course_Completion/";
        List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(getMonthYear(toDate));
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.maCourse.getReportType());
        updateCumulativeCourseCompletion(successFullcandidates, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        State state = stateDao.findByStateId(stateIdRequest);
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
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            updateCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);

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
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                updateCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
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
                    updateCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate,reportRequest);
                }
            }

    }

    @Override
    public void getCircleWiseAnonymousFiles(Date startDate, Date toDate) {
        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = reports+ReportType.maAnonymous.getReportType()+ "/";
        List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(getMonthYear(toDate));
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setCircleId(0);
        reportRequest.setReportType(ReportType.maAnonymous.getReportType());
        getCircleWiseAnonymousUsers(anonymousUsersList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (Circle circle : circleList) {
            String circleName = StReplace(circle.getCircleName());
            String circleFullName = StReplace(circle.getCircleFullName());
            String circleFullNamewithSpace = circle.getCircleFullName();
            String rootPathCircle=rootPath+circleFullName+"/";
            List<AnonymousUsers> anonymousUsersListCircle = new ArrayList<>();
            for(AnonymousUsers anonymousUser : anonymousUsersList){
                if(anonymousUser.getCircleName().equalsIgnoreCase(circleFullNamewithSpace)){
                    anonymousUsersListCircle.add(anonymousUser);
                }
            }
            reportRequest.setCircleId(circle.getCircleId());
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
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
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
                reportRequest.setBlockId(0);
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
    public void modifyCumulativeInactiveFiles(Date toDate, Integer stateIdRequest) {
        //List<State> states = stateDao.getStatesByServiceType(ReportType.maInactive.getServiceType());
        String rootPath = reports+ReportType.maInactive.getReportType()+ "/";

        List<FrontLineWorkers> allFrontLineWorkers = frontLineWorkersDao.getAllFrontLineWorkers(toDate, stateIdRequest);

        HashMap<String, FrontLineWorkers> frontLineWorkersMap = new HashMap<>();

        for (FrontLineWorkers frontLineWorkers : allFrontLineWorkers) {
            frontLineWorkersMap.put(frontLineWorkers.getExternalFlwId(), frontLineWorkers);
        }
        State state = stateDao.findByStateId(stateIdRequest);

            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";

        System.out.println(" File name " + rootPath + ReportType.maInactive.getReportType() + "_" + stateName + "_" + getMonthYear(toDate) + ".xlsx");

        updateCumulativeInactiveUsersInExcel(frontLineWorkersMap, stateIdRequest, rootPathState, stateName, toDate);
            List<District> districts = districtDao.getDistrictsOfState(stateIdRequest);
            for (District district : districts) {


                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState  + districtName+ "/";
                int districtId = district.getDistrictId();

        System.out.println(" File name " + rootPath + ReportType.maInactive.getReportType() + "_" + districtName + "_" + getMonthYear(toDate) + ".xlsx");

                updateCumulativeInactiveUsersInExcel(frontLineWorkersMap, stateIdRequest, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {

                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict  + blockName+ "/";

         System.out.println(" File name " + rootPath + ReportType.maInactive.getReportType() + "_" + blockName + "_" + getMonthYear(toDate) + ".xlsx");

                    updateCumulativeInactiveUsersInExcel(frontLineWorkersMap, stateIdRequest, rootPathblock, blockName, toDate);
                }
            }

    }

    @Override
    public void getKilkariSixWeekNoAnswerFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.sixWeeks.getServiceType());
        String rootPath = reports +ReportType.sixWeeks.getReportType()+ "/";
        List<KilkariManualDeactivations> kilkariManualDeactivations = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.sixWeeks.getReportType());
        getKilkariSixWeekNoAnswer(kilkariManualDeactivations, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate,reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariManualDeactivations> candidatesFromThisState = new ArrayList<>();
            for (KilkariManualDeactivations kilkari : kilkariManualDeactivations) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getKilkariSixWeekNoAnswer(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariManualDeactivations> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariManualDeactivations kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getKilkariSixWeekNoAnswer(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<KilkariManualDeactivations> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariManualDeactivations kilkari : candidatesFromThisDistrict) {
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
        List<KilkariManualDeactivations> kilkariManualDeactivations = kilkariSixWeeksNoAnswerDao.getLowListenershipUsers(fromDate, toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.lowListenership.getReportType());
        getKilkariLowListenershipDeactivation(kilkariManualDeactivations, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariManualDeactivations> candidatesFromThisState = new ArrayList<>();
            for (KilkariManualDeactivations kilkari : kilkariManualDeactivations) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getKilkariLowListenershipDeactivation(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariManualDeactivations> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariManualDeactivations kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getKilkariLowListenershipDeactivation(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<KilkariManualDeactivations> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariManualDeactivations kilkari : candidatesFromThisDistrict) {
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
//        getKilkariLowUsage(kilkariLowUsageList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate,reportRequest);
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
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
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
                reportRequest.setBlockId(0);
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
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
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
                reportRequest.setBlockId(0);
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
        c.add(Calendar.MONTH, -1);
        int month=c.get(Calendar.MONTH);
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
    private String getTodayDateMonthYear() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
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

        return dateString + "-" + monthString+"-"+yearString;

    }
    /**
     * from date to todate in String format.
     * @param toDate
     * @return String containing fromdate to todate
     */
    private String getDateMonthYearName(Date toDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(toDate);
        int toDateValue =calendar.get(Calendar.DATE)-1;
        int toDateYear =(calendar.get(Calendar.YEAR))%100;
        String toDateString;
        if(toDateValue <10) {
            toDateString ="0"+String.valueOf(toDateValue);
        }
        else {
            toDateString =String.valueOf(toDateValue);
        }
        String toMonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        String toYearString =String.valueOf(toDateYear);
        calendar.add(Calendar.DAY_OF_MONTH, -6);

        int fromDateValue=calendar.get(Calendar.DATE)-1;
        String fromStringDate;
        if(fromDateValue <10) {
            fromStringDate ="0"+String.valueOf(fromDateValue);
        }
        else {
            fromStringDate =String.valueOf(fromDateValue);
        }
        String fromMonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        String fromYearString =String.valueOf((calendar.get(Calendar.YEAR))%100);

        return fromStringDate+"-"+fromMonthString+"-"+fromYearString+" to " +toDateString + "-" +toMonthString +"-"+ toYearString;
    }

    private void cleanBeforeMergeOnValidCells(XSSFSheet sheet,CellRangeAddress region, XSSFCellStyle cellStyle )
    {
        for(int rowNum =region.getFirstRow();rowNum<=region.getLastRow();rowNum++){
            XSSFRow row= sheet.getRow(rowNum);
            if(row==null){
                row = sheet.createRow(rowNum);
            }
            for(int colNum=region.getFirstColumn();colNum<=region.getLastColumn();colNum++){
                XSSFCell currentCell = row.getCell(colNum);
                if(currentCell==null){
                    currentCell = row.createCell(colNum);

                }

                currentCell.setCellStyle(cellStyle);

            }
        }


    }

}
