package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private LocationDao locationDao;

    @Autowired
    private KilkariSixWeeksNoAnswerDao kilkariSixWeeksNoAnswerDao;

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

    @Override
    public HashMap startBulkDataImport(User loggedInUser) {
        Pattern pattern;
        Matcher matcher;
        Map<Integer, String> errorCreatingUsers = new HashMap<Integer, String>();
        createPropertiesFileForFileLocation();
        String fjilename = null;
        fjilename = retrievePropertiesFromFileLocationProties();

        if (fjilename == null) {
            fjilename = System.getProperty("user.home")
                    + "/Documents/BulkImportDatacr7ms10.csv";
            System.out.println("fileLocationproperties not working");
        }
        XSSFRow row;
        BufferedReader fis = null;
        try {
            fis = new BufferedReader(new FileReader(fjilename));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                String line = "";
                String cvsSplitBy = ",";
                try {

                    int linNumber = 1;
                    if ((line = fis.readLine()) != null) {

                    }
                    while ((line = fis.readLine()) != null) {
                        linNumber++;

                        // use comma as separator
                        String[] Line = line.split(cvsSplitBy);

                        User user = new User();
                        Role role;
                        Location locaation;
                        State state;


                        String userName = Line[6];
                        if (userName == "") {
                            Integer rowNum = linNumber;
                            String userNameError = "Please specify the username for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        User UsernameExist = null;
                        UsernameExist = userDao.findByUserName(Line[6]);
                        if (UsernameExist == null) {
                            user.setUsername(Line[6]);
                        } else {
                            Integer rowNum = linNumber;
                            String userNameError = "Username already exists.";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }


                        int loggedUserRole = loggedInUser.getRoleId().getRoleId();

                        String loggedUserAccess = loggedInUser.getAccessLevel();
                        AccessLevel loggedUserAccessLevel = AccessLevel.getLevel(loggedUserAccess);
                        String userPhone = Line[4];
                        if (userPhone == "") {
                            Integer rowNum = linNumber;
                            String userNameError = "Please specify the phone number for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }

                        String regexStr1 = "^[0-9]*$";
                        String regexStr2 = "^[0-9]{10}$";
                        if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
                            Integer rowNum = linNumber;
                            String userNameError = "Please check the format of phone number for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        user.setPassword(Line[4]);


                        user.setFullName(Line[0]);


                        user.setPhoneNumber(Line[4]);

                        String userEmail = Line[5];
                        if (userEmail == "") {
                            Integer rowNum = linNumber;
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
                            Integer rowNum = linNumber;
                            String userNameError = "Please enter the valid Email for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = null;
                        try {
                            date = sdf1.parse(Line[7]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

                        user.setCreationDate((sqlStartDate));

                        /*user.setCreatedByUser(loggedInUser);*/

                        List<Role> userRole = roleDao.findByRoleDescription(Line[8]);

                        String State = Line[1];
                        String District = Line[2];
                        String Block = Line[3];


                        boolean isLevel = AccessLevel.isLevel(Line[8]);

                        if (!(isLevel)) {
                            Integer rowNum = linNumber;
                            String userNameError = "Please specify the access level for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }

                        boolean isType = AccessType.isType(Line[9]);

                        if (!(isType)) {
                            Integer rowNum = linNumber;
                            String userNameError = "Please specify the role for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }


                        if (userRole == null || userRole.size() == 0) {
                            Integer rowNum = linNumber;
                            String userNameError = "Please specify the role of user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }

                        int userRoleId = userRole.get(0).getRoleId();

                        String UserRole = AccessType.getType(Line[9]);

                        AccessLevel accessLevel = AccessLevel.getLevel(Line[8]);

                        if (UserRole.equalsIgnoreCase("ADMIN")) {
                            if ((accessLevel == AccessLevel.NATIONAL) || (accessLevel == AccessLevel.STATE)) {
                                Integer rowNum = linNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else if (loggedUserAccessLevel == AccessLevel.DISTRICT) {
                                Integer rowNum = linNumber;
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
                                    userState = userDistrict.getStateOfDistrict();
                                } else {
                                    for (District district : userDistrictList) {
                                        State parent = district.getStateOfDistrict();
                                        if ((userStateList != null) && (userStateList.size() != 0)) {
                                            if (parent.getStateId() == userStateList.get(0).getStateId()) {
                                                userDistrict = district;
                                                userState = parent;
                                                break;
                                            }
                                        }

                                    }
                                }
                                if (userDistrict == null) {
                                    Integer rowNum = linNumber;
                                    String authorityError = "Please enter the valid district for this user.";
                                    errorCreatingUsers.put(rowNum, authorityError);
                                    continue;
                                } else {
                                    if (loggedInUser.getStateId().getStateId() != userState.getStateId()) {
                                        Integer rowNum = linNumber;
                                        String authorityError = "You don't have authority to create this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        boolean isAdminAvailable = userDao.isAdminCreated(userDistrict);
                                        if (!(isAdminAvailable)) {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict);
                                            user.setStateId(userState);
                                        } else {
                                            Integer rowNum = linNumber;
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
                                Integer rowNum = linNumber;
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
                                        Integer rowNum = linNumber;
                                        String authorityError = "Please enter the valid State for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        if (loggedUserAccessLevel == AccessLevel.STATE) {
                                            if (loggedInUser.getStateId().getStateId() != userStateList.get(0).getStateId()) {
                                                Integer rowNum = linNumber;
                                                String authorityError = "You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum, authorityError);
                                                continue;
                                            } else user.setStateId(userStateList.get(0));
                                        } else user.setStateId(userStateList.get(0));
                                    }
                                } else if (accessLevel == AccessLevel.DISTRICT) {
                                    List<State> userStateList = stateDao.findByName(State);
                                    List<District> userDistrictList = districtDao.findByName(District);
                                    District userDistrict = null;
                                    State userState = null;
                                    if (userDistrictList.size() == 1) {
                                        userDistrict = userDistrictList.get(0);
                                        userState = userDistrict.getStateOfDistrict();
                                    } else {
                                        for (District district : userDistrictList) {
                                            State parent = district.getStateOfDistrict();
                                            if ((userStateList != null) && (userStateList.size() != 0)) {
                                                if (parent.getStateId() == userStateList.get(0).getStateId()) {
                                                    userDistrict = district;
                                                    userState = parent;
                                                    break;
                                                }
                                            }

                                        }
                                    }
                                    if (userDistrict == null) {
                                        Integer rowNum = linNumber;
                                        String authorityError = "Please enter the valid district for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        if (((loggedUserAccessLevel == AccessLevel.STATE) && (loggedInUser.getStateId().getStateId() != userState.getStateId())) || ((loggedUserAccessLevel == AccessLevel.DISTRICT) && (loggedInUser.getDistrictId().getDistrictId() != userDistrict.getDistrictId()))) {
                                            Integer rowNum = linNumber;
                                            String authorityError = "You don't have authority to create this user.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        } else {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict);
                                            user.setStateId(userState);

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
                                        userDistrict = userBlock.getDistrictOfBlock();
                                        userState = userDistrict.getStateOfDistrict();

                                    } else if ((userBlockList.size() == 0) || userBlockList == null) {
                                        Integer rowNum = linNumber;
                                        String authorityError = "Please enter the valid Block for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                    } else {
                                        List<Block> commonDistrict = null;
                                        for (Block block : userBlockList) {
                                            District parent = block.getDistrictOfBlock();
                                            if (userDistrictList.size() > 0) {
                                                for (District district : userDistrictList) {
                                                    if (parent.getDistrictId() == district.getDistrictId()) {
                                                        commonDistrict.add(block);
                                                    }
                                                }

                                            }
                                        }
                                        for (Block block : commonDistrict) {
                                            State parent = block.getStateOfBlock();
                                            if (userState != null) {
                                                if (parent.getStateId() == userStateList.get(0).getStateId()) {
                                                    userBlock = block;
                                                    userDistrict = userBlock.getDistrictOfBlock();
                                                    userState = userBlock.getStateOfBlock();
                                                    break;
                                                }

                                            }
                                        }
                                        if (userBlock == null) {
                                            Integer rowNum = linNumber;
                                            String authorityError = "Please enter the valid location for this user.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        } else {
                                            if (((loggedUserAccessLevel == AccessLevel.STATE) && (loggedInUser.getStateId().getStateId() != userState.getStateId())) || ((loggedUserAccessLevel == AccessLevel.DISTRICT) && (loggedInUser.getDistrictId().getDistrictId() != userDistrict.getDistrictId()))) {
                                                Integer rowNum = linNumber;
                                                String authorityError = "You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum, authorityError);
                                                continue;
                                            } else {
                                                user.setBlockId(userBlock);
                                                user.setStateId(userState);
                                                user.setDistrictId(userDistrict);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        userDao.saveUser(user);
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
        String FILE_HEADER = "Full Name, STATE, DISTRICT, BLOCK, Phone number, Email ID, UserName, Creation Date, Access Level,Role";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("BulkImportData.csv");

            //Write the CSV file header
            fileWriter.append(FILE_HEADER.toString());

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
    public void createFiles(String fileName) {
        List<State> states = stateDao.getAllStates();
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/Reports";
        File dir = new File(rootPath + "/" + fileName);
        if (!dir.exists())
            dir.mkdirs();
        for (State state : states) {
            String stateName = state.getStateName();
            String rootPathState = System.getProperty("user.home") + File.separator + "Documents/Reports/" + fileName + "/" + stateName;
            File dirState = new File(rootPathState);
            if (!dirState.exists())
                dirState.mkdirs();
            int stateId = state.getStateId();
            List<District> districts = stateDao.getChildLocations(stateId);

            for (District district : districts) {
                String districtName = district.getDistrictName();

                String rootPathDistrict = rootPathState + "/" + districtName;
                File dirDistrict = new File(rootPathDistrict);
                if (!dirDistrict.exists())
                    dirDistrict.mkdirs();
                int districtId = district.getDistrictId();
                List<Block> Blocks = districtDao.getBlocks(districtId);
                for (Block block : Blocks) {
                    String blockName = block.getBlockName();
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
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/Reports";
        File dir = new File(rootPath + "/" + reportType);
        if (!dir.exists())
            dir.mkdirs();
        for (Circle circle : circleList) {
            String circleName = circle.getCircleName();
            String rootPathCircle = rootPath + "/" + reportType + "/" + circleName;
            File dirCircle = new File(rootPathCircle);
            if (!dirCircle.exists())
                dirCircle.mkdirs();
        }


    }

    public void getCumulativeCourseCompletion(List<FrontLineWorkers> successfulCandidates, String rootPath, String place, Date toDate) {
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
                "Full Name", "Mobile Number", "STATE", "DISTRICT", "BLOCK", "Taluka", "Health Facility", "Health Sub Facility", "First Completion Date", "Role"});
        Integer counter = 2;
        for (FrontLineWorkers frontLineWorker : successfulCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    frontLineWorker.getFullName(), frontLineWorker.getMobileNumber(), frontLineWorker.getState().getStateName(), frontLineWorker.getDistrict().getDistrictName(), frontLineWorker.getBlock().getBlockName(),
                    maCourseAttemptDao.getFirstCompletionDate(frontLineWorker.getFlwId())

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + "/" + "MACourseCompletionreport" + "_" + place + "_" + toDate + ".xlsx"));
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

    public void getCircleWiseAnonymousUsers(List<AnonymousUsers> anonymousUsersList, String rootPath, String place, Date toDate) {

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
                "Circle Name", "Mobile Number", "Last Called Date"});
        Integer counter = 2;
        for (AnonymousUsers anonymousUser : anonymousUsersList) {
            empinfo.put((counter.toString()), new Object[]{
                    place, anonymousUser.getMsisdn(), anonymousUser.getLastCalledDate()


            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + "/" + "MA_AnonymousUsersReport" + "_" + place + "_" + toDate + ".xlsx"));
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

    public void getCumulativeInactiveUsers(List<FrontLineWorkers> inactiveCandidates, String rootPath, String place, Date toDate) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Cumulative Inactive Users Report ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Full Name", "Mobile Number", "STATE", "DISTRICT", "BLOCK", "Taluka", "Health Facility", "Health Sub Facility", "Creation Date", "Job Status"});
        Integer counter = 2;
        for (FrontLineWorkers frontLineWorker : inactiveCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    frontLineWorker.getFullName(), frontLineWorker.getMobileNumber(), frontLineWorker.getState().getStateName(), frontLineWorker.getDistrict().getDistrictName(), frontLineWorker.getBlock().getBlockName(),


            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + "/" + "MAInactiveUsersReport" + "_" + place + "_" + toDate + ".xlsx"));
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

    public void getKilkariSixWeekNoAnswer(List<KilkariSixWeeksNoAnswer> kilkariSixWeeksNoAnswersList, String rootPath, String place, Date toDate){
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
                "State", "District", "Health block", "Taluka", "Health Facility", "Health SubFacility", "Village", "Beneficiary MCTS/RCH Id", "Benificiary Name", "Mobile Number", "Age On Service In Weeks"});
        Integer counter = 2;
        for (KilkariSixWeeksNoAnswer kilkari : kilkariSixWeeksNoAnswersList) {
            empinfo.put((counter.toString()), new Object[]{
                    stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage().getTalukaName(),
                    healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility().getHealthFacilityName(),
                    healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    villageDao.findByVillageId(kilkari.getVillageId()),
                    kilkari.getMctsId(),
                    kilkari.getName(),
                    kilkari.getMsisdn(),
                    kilkari.getAgeOnService()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + "/" + "KilkariNonAnsweringBeneficiariesReport" + "_" + place + "_" + toDate + ".xlsx"));
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

    public void getKilkariSelfDeactivation(List<KilkariSelfDeactivated> kilkariSelfDeactivatedList, String rootPath, String place, Date toDate){
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
                "State", "District", "Health block", "Taluka", "Health Facility", "Health SubFacility", "Village", "Beneficiary MCTS/RCH Id", "Benificiary Name", "Mobile Number", "Age On Service In Weeks", "Date of activation", "Date when beneficiary self-deactivated", "Number of calls answered when subscribed to Kilkari"});
        Integer counter = 2;
        for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
            empinfo.put((counter.toString()), new Object[]{
                    stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage().getTalukaName(),
                    healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility().getHealthFacilityName(),
                    healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    villageDao.findByVillageId(kilkari.getVillageId()),
                    kilkari.getMctsId(),
                    kilkari.getName(),
                    kilkari.getMsisdn(),
                    kilkari.getAgeOnService(),
                    kilkari.getPackActivationDate(),
                    kilkari.getDeactivationDate(),
                    kilkari.getCallsAnswered()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + "/" + "KilkariSelfDeactivatorsReport" + "_" + place + "_" + toDate + ".xlsx"));
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

    public void getKilkariLowUsage(List<KilkariLowUsage> kilkariLowUsageList, String rootPath, String place, Date toDate){
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
                "State", "District", "Health block", "Taluka", "Health Facility", "Health SubFacility", "Village", "Beneficiary MCTS/RCH Id", "Benificiary Name", "Mobile Number", "Age On Service In Weeks"});
        Integer counter = 2;
        for (KilkariLowUsage kilkari : kilkariLowUsageList) {
            empinfo.put((counter.toString()), new Object[]{
                    stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage().getTalukaName(),
                    healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility().getHealthFacilityName(),
                    healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    villageDao.findByVillageId(kilkari.getVillageId()),
                    kilkari.getMctsId(),
                    kilkari.getName(),
                    kilkari.getMsisdn(),
                    kilkari.getAgeOnService()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + "/" + "KilkariLowUsageBeneficiariesReport" + "_" + place + "_" + toDate + ".xlsx"));
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

    @Override
    public void getCumulativeCourseCompletionFiles(Date fromDate, Date toDate) {

        List<State> states = stateDao.getAllStates();
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/Reports/CumulativeCourseCompletion";
        List<FrontLineWorkers> successFullcandidates = maCourseAttemptDao.getSuccessFulFirstCompletion(fromDate, toDate);
        getCumulativeCourseCompletion(successFullcandidates, rootPath, "National", toDate);
        for (State state : states) {
            String stateName = state.getStateName();
            String rootPathState = System.getProperty("user.home") + File.separator + "Documents/Reports/CumulativeCourseCompletion/" + stateName;
            int stateId = state.getStateId();
            List<FrontLineWorkers> candidatesFromThisState = new ArrayList<>();
            for (FrontLineWorkers asha : successFullcandidates) {
                if (asha.getState().getStateId() == stateId) {
                    candidatesFromThisState.add(asha);
                }
            }

            getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = stateDao.getChildLocations(stateId);

            for (District district : districts) {
                String districtName = district.getDistrictName();
                String rootPathDistrict = rootPathState + "/" + districtName;
                int districtId = district.getDistrictId();
                List<FrontLineWorkers> candidatesFromThisDistrict = new ArrayList<>();
                for (FrontLineWorkers asha : candidatesFromThisState) {
                    if (asha.getDistrict().getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(asha);
                    }
                }

                getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = districtDao.getBlocks(districtId);
                for (Block block : Blocks) {
                    String blockName = block.getBlockName();
                    String rootPathblock = rootPathDistrict + "/" + blockName;

                    int blockId = block.getBlockId();
                    List<FrontLineWorkers> candidatesFromThisBlock = new ArrayList<>();
                    for (FrontLineWorkers asha : candidatesFromThisDistrict) {
                        if (asha.getBlock().getBlockId() == blockId) {
                            candidatesFromThisBlock.add(asha);
                        }
                    }
                    getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    @Override
    public void getCircleWiseAnonymousFiles(Date toDate) {
        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/Reports/CumulativeAnonymousUsers";

        for (Circle circle : circleList) {
            String circleName = circle.getCircleName();
            List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsersCircle(toDate, circle.getCircleIdId());
            getCircleWiseAnonymousUsers(anonymousUsersList, rootPath, circleName, toDate);
        }
    }

    @Override
    public void getCumulativeInactiveFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getAllStates();
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/Reports/CumulativeInactiveUsers";
        List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers();
        getCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, "National", toDate);
        for (State state : states) {
            String stateName = state.getStateName();
            String rootPathState = System.getProperty("user.home") + File.separator + "Documents/Reports/CumulativeInactiveUsers/" + stateName;
            int stateId = state.getStateId();
            List<FrontLineWorkers> candidatesFromThisState = new ArrayList<>();
            for (FrontLineWorkers asha : inactiveFrontLineWorkers) {
                if (asha.getState().getStateId() == stateId) {
                    candidatesFromThisState.add(asha);
                }
            }

            getCumulativeInactiveUsers(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = stateDao.getChildLocations(stateId);

            for (District district : districts) {
                String districtName = district.getDistrictName();
                String rootPathDistrict = rootPathState + "/" + districtName;
                int districtId = district.getDistrictId();
                List<FrontLineWorkers> candidatesFromThisDistrict = new ArrayList<>();
                for (FrontLineWorkers asha : candidatesFromThisState) {
                    if (asha.getDistrict().getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(asha);
                    }
                }

                getCumulativeInactiveUsers(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = districtDao.getBlocks(districtId);
                for (Block block : Blocks) {
                    String blockName = block.getBlockName();
                    String rootPathblock = rootPathDistrict + "/" + blockName;

                    int blockId = block.getBlockId();
                    List<FrontLineWorkers> candidatesFromThisBlock = new ArrayList<>();
                    for (FrontLineWorkers asha : candidatesFromThisDistrict) {
                        if (asha.getBlock().getBlockId() == blockId) {
                            candidatesFromThisBlock.add(asha);
                        }
                    }
                    getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    @Override
    public void getKilkariSixWeekNoAnswerFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getAllStates();
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/Reports/KilkariSixWeeksNoAnswer";
        List<KilkariSixWeeksNoAnswer> kilkariSixWeeksNoAnswers = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
        getKilkariSixWeekNoAnswer(kilkariSixWeeksNoAnswers, rootPath, "National", toDate);
        for (State state : states) {
            String stateName = state.getStateName();
            String rootPathState = System.getProperty("user.home") + File.separator + "Documents/Reports/KilkariSixWeeksNoAnswer/" + stateName;
            int stateId = state.getStateId();
            List<KilkariSixWeeksNoAnswer> candidatesFromThisState = new ArrayList<>();
            for (KilkariSixWeeksNoAnswer kilkari : kilkariSixWeeksNoAnswers) {
                if (kilkari.getStateId() == stateId) {
                    candidatesFromThisState.add(kilkari);
                }
            }

            getKilkariSixWeekNoAnswer(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = stateDao.getChildLocations(stateId);

            for (District district : districts) {
                String districtName = district.getDistrictName();
                String rootPathDistrict = rootPathState + "/" + districtName;
                int districtId = district.getDistrictId();
                List<KilkariSixWeeksNoAnswer> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariSixWeeksNoAnswer kilkari : candidatesFromThisState) {
                    if (kilkari.getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }

                getKilkariSixWeekNoAnswer(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = districtDao.getBlocks(districtId);
                for (Block block : Blocks) {
                    String blockName = block.getBlockName();
                    String rootPathblock = rootPathDistrict + "/" + blockName;

                    int blockId = block.getBlockId();
                    List<KilkariSixWeeksNoAnswer> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariSixWeeksNoAnswer kilkari : candidatesFromThisDistrict) {
                        if (kilkari.getBlockId() == blockId) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    getKilkariSixWeekNoAnswer(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    @Override
    public void getKilkariLowUsageFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getAllStates();
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/Reports/KilkariLowUsage";
        List<KilkariLowUsage> kilkariLowUsageList = kilkariLowUsageDao.getKilkariLowUsageUsers(fromDate, toDate);
        getKilkariLowUsage(kilkariLowUsageList, rootPath, "National", toDate);
        for (State state : states) {
            String stateName = state.getStateName();
            String rootPathState = System.getProperty("user.home") + File.separator + "Documents/Reports/KilkariLowUsage/" + stateName;
            int stateId = state.getStateId();
            List<KilkariLowUsage> candidatesFromThisState = new ArrayList<>();
            for (KilkariLowUsage kilkari : kilkariLowUsageList) {
                if (kilkari.getStateId() == stateId) {
                    candidatesFromThisState.add(kilkari);
                }
            }

            getKilkariLowUsage(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = stateDao.getChildLocations(stateId);

            for (District district : districts) {
                String districtName = district.getDistrictName();
                String rootPathDistrict = rootPathState + "/" + districtName;
                int districtId = district.getDistrictId();
                List<KilkariLowUsage> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariLowUsage kilkari : candidatesFromThisState) {
                    if (kilkari.getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }

                getKilkariLowUsage(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = districtDao.getBlocks(districtId);
                for (Block block : Blocks) {
                    String blockName = block.getBlockName();
                    String rootPathblock = rootPathDistrict + "/" + blockName;

                    int blockId = block.getBlockId();
                    List<KilkariLowUsage> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariLowUsage kilkari : candidatesFromThisDistrict) {
                        if (kilkari.getBlockId() == blockId) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    getKilkariLowUsage(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    @Override
    public void getKilkariSelfDeactivationFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getAllStates();
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/Reports/KilkariSelfDeactivated";
        List<KilkariSelfDeactivated> kilkariSelfDeactivatedList = kilkariSelfDeactivatedDao.getSelfDeactivatedUsers(fromDate, toDate);
        getKilkariSelfDeactivation(kilkariSelfDeactivatedList, rootPath, "National", toDate);
        for (State state : states) {
            String stateName = state.getStateName();
            String rootPathState = System.getProperty("user.home") + File.separator + "Documents/Reports/KilkariSelfDeactivated/" + stateName;
            int stateId = state.getStateId();
            List<KilkariSelfDeactivated> candidatesFromThisState = new ArrayList<>();
            for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
                if (kilkari.getStateId() == stateId) {
                    candidatesFromThisState.add(kilkari);
                }
            }

            getKilkariSelfDeactivation(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = stateDao.getChildLocations(stateId);

            for (District district : districts) {
                String districtName = district.getDistrictName();
                String rootPathDistrict = rootPathState + "/" + districtName;
                int districtId = district.getDistrictId();
                List<KilkariSelfDeactivated> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariSelfDeactivated kilkari : candidatesFromThisState) {
                    if (kilkari.getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }

                getKilkariSelfDeactivation(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = districtDao.getBlocks(districtId);
                for (Block block : Blocks) {
                    String blockName = block.getBlockName();
                    String rootPathblock = rootPathDistrict + "/" + blockName;

                    int blockId = block.getBlockId();
                    List<KilkariSelfDeactivated> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariSelfDeactivated kilkari : candidatesFromThisDistrict) {
                        if (kilkari.getBlockId() == blockId) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    getKilkariSelfDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    private String retrievePropertiesFromFileLocationProties() {
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


            prop.setProperty("fileLocation", System.getProperty("user.home")
                    + "/Documents/BulkImportDatacr7ms10.csv");

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


}
