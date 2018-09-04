
(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'SaltHashFactory', function() {
		var crypto = CryptoJS;
            /**
             * generates random string of characters i.e salt
             * @functions
             * @param {number} length - Length of the random string.
             */
            var genRandomString = function(length){
                return crypto.randomBytes(Math.ceil(length/2))
                        .toString('hex') /** convert to hexadecimal format */
                        .slice(0,length);   /** return required number of characters */
            };
            /**
             * hash password with sha512.
             * @function
             * @param {string} password - List of required fields.
             * @param {string} salt - Data to be validated.
             */
            var sha512 = function(password, salt){
                var hash = crypto.createHmac('sha512', salt); /** Hashing algorithm sha512 */
                hash.update(password);
                var value = hash.digest('hex');
                return {
                    salt:salt,
                    passwordHash:value
                };
            };
			return {
				saltHashPassword: function(userpassword) {
//                    var salt = genRandomString(16); /** Gives us salt of length 16 */
                    var passwordData = crypto.HmacSHA256(userpassword, 'Key');
                    console.log('UserPassword = '+userpassword);
                    console.log('Passwordhash = '+passwordData);
//                    console.log('nSalt = '+passwordData.salt);
//                    return passwordData;
                }
			};
		});
})();