package com.beehyv.nmsreporting.htmlpages;

public class UserManualManagement {
    public static String pageContent ="<div class=\"container-fluid uMManagement\" data-ng-controller=\"userManual_ManagementController\" >" +
            "    <div data-ui-view></div>\n" +
            "    <h2>User Management</h2>\n" +
            "    <br/>\n" +
            "\n" +
            "    <div data-ng-hide=\"selectRole != 5\">\n" +
            "        <div class=\"userManualPic\">\n" +
            "            <p>The User Management tab allows Admin users to view/create new lower/equal access-level users and lower access-level admins, and to edit/delete users created by them.</p>\n" +
            "            <p>1) User Management can be used by admin users only after logging in and then clicking on the <b>'User Management'</b> tab in the navigation bar:</p>\n" +
            "            <img src=\"images/UMclick_NA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>2) You, as a National Admin, will see the list of all users in the system.</p>\n" +
            "            <img src=\"images/UMdisplay_NA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>3) A search field is provided to search for entered keywords from all fields of all the records:</p>\n" +
            "            <img src=\"images/UMtextbox_NA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>4) Filters for 'Access Type', 'Access Level', 'State', 'District' and 'Block' are provided, which can be used to filter the respective columns.</p>\n" +
            "            <p>5) The first five columns can be sorted by clicking on the column headers.</p>\n" +
            "            <img src=\"images/UMfilters_NA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>6) Each page shows a maximum of 10 users. Use the pagination at the bottom-right to navigate to other pages:</p>\n" +
            "            <img src=\"images/UMpages_NA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>7) You, as a National Admin, can create State Admin, District Admin and any User (National, State, District or Block) by clicking on the ‘Create new User’ button as shown below:</p>\n" +
            "            <img src=\"images/UMcreateUser.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>Multiple Admins cannot be created for any State or District</p>\n" +
            "            <p>The following screen is shown with empty fields which are required to be filled.</p>\n" +
            "            <p> All fields in the form are compulsory. Default password will be shared with the user on his registered email id.</p>\n" +
            "            <p>Create user form has two action buttons: 'Create New User' and 'Cancel'.</p>\n" +
            "            <ul>\n" +
            "                <li>Create new User: creates a new user with details as filled in the form.</li>\n" +
            "                <li>Cancel: aborts the action of creating user and navigates back to User Management.</li>\n" +
            "            </ul>\n" +
            "            <img src=\"images/UMuserCreate.png\" alt=\"User Management\"/><br/><br/><br/>\n" +
            "            <p>8) You, as an Admin user, will have access to edit only the users that are created by you, by clicking on the ‘Edit’ option as shown in the below screenshot:</p>\n" +
            "            <img src=\"images/UMedit_NA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>9) Clicking ‘Edit’ will show the following screen, with previous information pre-populated in the form: </p>\n" +
            "            <img src=\"images/UMuserEdit.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "\n" +
            "            <p>10) The form has three options:</p>\n" +
            "            <ul>\n" +
            "                <li>Update user: This option will validate all the values entered and update the same.</li>\n" +
            "                <li>Reset password: Will reset the password of the user to their contact number.</li>\n" +
            "                <li>Cancel: Cancels edit action.</li>\n" +
            "                <img src=\"images/UMEditForm.png\" alt=\"User Management\"/>\n" +
            "\n" +
            "            </ul>\n" +
            "            <br/><br/>\n" +
            "            <p>11) <b>'Upload Bulk Users'</b> on the top right section of the User Management page navigates to the bulk upload page as shown below:</p>\n" +
            "            <img src=\"images/UMbulkUpload.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>12) The admin can download a sample template by clicking on the ‘Download Sample Template’ button:</p>\n" +
            "            <img src=\"images/UMdownloadTemplate.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>13)Using the sample template, the admin can upload multiple users at a time. The first row is the header. From second row onwards, each row represents one user to be created. General guideline is given on the left half of the screen, as shown below:</p>\n" +
            "            <img src=\"images/UMguidelines.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>14) Once the csv file is ready and selected, click on ‘Upload Users’ button to create users.</p>\n" +
            "            <img src=\"images/UMuploadUsers.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>15) In case of all records successfully imported, success message will be shown as below:</p>\n" +
            "            <img src=\"images/UMuploadSuccess.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>16) In case of any failure(s), a summary of row-wise errors will be displayed.</p>\n" +
            "            <img src=\"images/UMuploadFailure.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <div data-ng-hide=\"selectRole != 6\">\n" +
            "        <div class=\"userManualPic\">\n" +
            "            <p>The User Management tab allows Admin users to view/create new lower/equal access-level users and lower access-level admins, and to edit/delete users created by them.</p>\n" +
            "            <p>1) User Management can be used by admin users only after logging in and then clicking on the <b>'User Management'</b> tab in the navigation bar:</p>\n" +
            "            <img src=\"images/UMclick_SA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>2) You, as a State Admin, will see the list of all State and District level users in your state</p>\n" +
            "            <img src=\"images/UMdisplay_SA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>3) A search field is provided to search for entered keywords from all fields of all the records:</p>\n" +
            "            <img src=\"images/UMtextbox_SA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>4) Filters for 'Access Type', 'Access Level', 'State', 'District' and 'Block' are provided, which can be used to filter the respective columns.</p>\n" +
            "            <p>5) The first five columns can be sorted by clicking on the column headers.</p>\n" +
            "            <img src=\"images/UMfilters_SA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>6) Each page shows a maximum of 10 users. Use the pagination at the bottom-right to navigate to other pages:</p>\n" +
            "            <img src=\"images/UMpages_SA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>7) You, as a State Admin, can create District Admin and any User at State, District or Block level by clicking on the ‘Create new User’ button as shown below: </p>\n" +
            "            <img src=\"images/UMcreateUser.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>Multiple Admins cannot be created for any District</p>\n" +
            "            <p>The following screen is shown with empty fields which are required to be filled.</p>\n" +
            "            <p>Create user form has two action buttons: 'Create New user' and 'Cancel'.</p>\n" +
            "            <ul>\n" +
            "                <li>Create new User: creates a new user with details as filled in the form.</li>\n" +
            "                <li>Cancel: aborts the action of creating user and navigates back to User Management.</li>\n" +
            "            </ul>\n" +
            "            <img src=\"images/UMuserCreate.png\" alt=\"User Management\"/><br/><br/><br/>\n" +
            "            <p>8) You, as an Admin user, will have access to edit only the users that are created by you, by clicking on the ‘Edit’ option as shown in the below screenshot:</p>\n" +
            "            <img src=\"images/UMedit_SA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>9) Clicking ‘Edit’ will show the following screen, with previous information pre-populated in the form: </p>\n" +
            "            <img src=\"images/UMuserEdit.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>10) The form has three options:</p>\n" +
            "            <ul>\n" +
            "                <li>Update user: This option will validate all the values entered and update the same.</li>\n" +
            "                <li>Reset password: Will reset the password of the user to their contact number.</li>\n" +
            "                <li>Cancel: Cancels edit action.</li>\n" +
            "            </ul>\n" +
            "            <br/><br/>\n" +
            "            <p>11) <b>'Upload Bulk Users'</b> on the top right section of the User Management page navigates to the bulk upload page as shown below:</p>\n" +
            "            <img src=\"images/UMbulkUpload.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>12) The admin can download a sample template by clicking on the ‘Download Sample Template’ button:</p>\n" +
            "            <img src=\"images/UMdownloadTemplate.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>13)Using the sample template, the admin can upload multiple users at a time. The first row is the header. From second row onwards, each row represents one user to be created. General guideline is given on the left half of the screen, as shown below:</p>\n" +
            "            <img src=\"images/UMguidelines.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>14) Once the csv file is ready and selected, click on ‘Upload Users’ button to create users.</p>\n" +
            "            <img src=\"images/UMuploadUsers.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>15) In case of all records successfully imported, success message will be shown as below:</p>\n" +
            "            <img src=\"images/UMuploadSuccess.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>16) In case of any failure(s), a summary of row-wise errors will be displayed.</p>\n" +
            "            <img src=\"images/UMuploadFailure.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <div data-ng-hide=\"selectRole != 7\">\n" +
            "        <div class=\"userManualPic\">\n" +
            "            <p>The User Management tab allows Admin users to view/create new lower/equal access-level users and lower access-level admins, and to edit/delete users created by them.</p>\n" +
            "            <p>1) User Management can be used by admin users only after logging in and then clicking on the <b>'User Management'</b> tab in the navigation bar:</p>\n" +
            "            <img src=\"images/UMclick_DA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>2) You, as a District Admin, will see the list of all District and Block level users in your district.</p>\n" +
            "            <img src=\"images/UMdisplay_DA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>3) A search field is provided to search for entered keywords from all fields of all the records:</p>\n" +
            "            <img src=\"images/UMtextbox_DA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>4) Filters for 'Access Type', 'Access Level', 'State', 'District' and 'Block' are provided, which can be used to filter the respective columns.</p>\n" +
            "            <p>5) The first five columns can be sorted by clicking on the column headers.</p>\n" +
            "            <img src=\"images/UMfilters_DA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>6) Each page shows a maximum of 10 users. Use the pagination at the bottom-right to navigate to other pages:</p>\n" +
            "            <img src=\"images/UMpages_DA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>7) You, as a District Admin, can create District and Block level users by clicking on the ‘Create new User’ button as shown below:</p>\n" +
            "            <img src=\"images/UMcreateUser.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>The following screen is shown with empty fields which are required to be filled.</p>\n" +
            "            <p>Create user form has two action buttons: 'Create New user' and 'Cancel'.</p>\n" +
            "            <ul>\n" +
            "                <li>Create new User: creates a new user with details as filled in the form.</li>\n" +
            "                <li>Cancel: aborts the action of creating user and navigates back to User Management.</li>\n" +
            "            </ul>\n" +
            "            <img src=\"images/UMuserCreate.png\" alt=\"User Management\"/><br/><br/><br/>\n" +
            "            <p>8) You, as an Admin user, will have access to edit only the users that are created by you, by clicking on the ‘Edit’ option as shown in the below screenshot:</p>\n" +
            "            <img src=\"images/UMedit_DA.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>9) Clicking ‘Edit’ will show the following screen, with previous information pre-populated in the form:</p>\n" +
            "            <img src=\"images/UMuserEdit.png\" alt=\"User Management\"/>\n" +
            "            <br/><br/><br/>\n" +
            "            <p>10) The form has three options:</p>\n" +
            "            <ul>\n" +
            "                <li>Update user: This option will validate all the values entered and update the same.</li>\n" +
            "                <li>Reset password: Will reset the password of the user to their contact number.</li>\n" +
            "                <li>Cancel: Cancels edit action.</li>\n" +
            "            </ul>\n" +
            "            <br/><br/>\n" +
            "            <p>11) <b>'Upload Bulk Users'</b> on the top right section of the User Management page navigates to the bulk upload page as shown below:</p>\n" +
            "            <img src=\"images/UMbulkUpload.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>12) The admin can download a sample template by clicking on the ‘Download Sample Template’ button:</p>\n" +
            "            <img src=\"images/UMdownloadTemplate.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>13)Using the sample template, the admin can upload multiple users at a time. The first row is the header. From second row onwards, each row represents one user to be created. General guideline is given on the left half of the screen, as shown below:</p>\n" +
            "            <img src=\"images/UMguidelines.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>14) Once the csv file is ready and selected, click on ‘Upload Users’ button to create users.</p>\n" +
            "            <img src=\"images/UMuploadUsers.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>15) In case of all records successfully imported, success message will be shown as below:</p>\n" +
            "            <img src=\"images/UMuploadSuccess.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "            <p>16) In case of any failure(s), a summary of row-wise errors will be displayed.</p>\n" +
            "            <img src=\"images/UMuploadFailure.png\" alt=\"User Management\"/><br/><br/>\n" +
            "\n" +
            "        </div>\n" +
            "    </div>" +
            "</div>";
}
