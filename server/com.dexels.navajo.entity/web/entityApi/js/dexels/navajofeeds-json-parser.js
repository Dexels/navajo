/* 
 * See Dexels/navajofeeds-json-parser
 */

function defaultFor (argument, defaultValue) {
    return (argument == null) ? defaultValue : argument;
}

var parser = {
    /**
     * @abstract
     * Parses a string to an object.
     *
     * @param {String} string
     * @param {String} separator, default is '&'
     * @param {String} assignment, default is '='
     *
     * @return {Object}
     */
    parse: function (string, separator, assignment) {
        if ($.type(string) !== 'string' || string.length === 0)
            return {};

        separator = defaultFor(separator, '&');
        assignment = defaultFor(assignment, '=');

        var components = string.split(separator),
            result     = {};

        for (var i = 0; i < components.length; i++) {
            var values  = components[i].split(assignment),
                key     = values[0],
                value   = values[1];

            if (key == null)
                continue;

            var decodedKey = decodeURIComponent(key);

            if (value === 'true')
                result[decodedKey] = true;
            else if (value === 'false')
                result[decodedKey] = false;
            else if (value != null)
                result[decodedKey] = decodeURIComponent(value);
            else
                result[decodedKey] = null;
        }

        return result;
    }
};

var params = parser.parse(document.location.search.substr(1));


var token = (function () {
    var hashes = parser.parse(document.location.hash.substr(1));

    
    if (typeof hashes.token !== 'undefined') {
        //We calculate when the token will expire. So we can send a broadcast
        //and let the user do all kind of stuff.
        hashes.expires = new Date().getTime() + hashes.expires_in * 1000;
        sessionStorage.token = hashes.token;
        /*
         * Has to be done because otherwise if the user relogs in a hour
         * then hash will be appended to the current hash. Which cannot be
         * parsed reliable.
         */
        var uri      = window.location.href,
            newState = uri.substring(0, uri.indexOf('#'));

        history.replaceState('', document.title, newState);

        return hashes.token;
    } else {
    	hashes = parser.parse(window.location.search.substring(1));
    	if (typeof hashes.error !== 'undefined') {
        	window.alert('Error in oauth flow: ' + hashes.error +'. \n' + hashes.error_description);
        	 var uri      = window.location.href,
             newState = uri.substring(0, uri.indexOf('?'));
        	 history.replaceState('', document.title, newState);
        	 return;
        }
    }
})();
