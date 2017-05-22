(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'NavBarFactory', [function() {
			var users = []
			
			var states = {
				'userManagement.userTable': {
					'name': 'User Management',
					'sref': 'userManagement.userTable',
				},
				'userManagement.createUser': {
					'name': 'Create User',
					'sref': 'userManagement.createUser',
				},
				'userManagement.bulkUpload': {
					'name': 'Bulk User Upload',
					'sref': ''
				}
			}
		}]);
})();