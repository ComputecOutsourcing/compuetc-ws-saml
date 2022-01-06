/**
 * Metodo que permite obtener Tokem del Webservice
 * 
 * @param servletName
 * @param servletArguments
 */
var ipAppspot;
function myFunc() {
	alert(ipAppspot);
}
function obtenerToken(servletName, servletArguments) {
	var urlinit = "https://172.24.1.104";
	var servlet = urlinit + servletName; // the name (URI) of your servlet

	var req = servlet + "?" + servletArguments;//+"?callback=?"; // compiling the request
	jQuery.support.cors = true;
	jQuery.getJSON(req, function(data) {
		ipAppspot = data.token ;
		myFunc();
	});

	/*
	 * var res=addRequest(req); // calls the addrequest function
	 * request.onreadystatechange = function() { if (request.readyState==4 &&
	 * request.status==200) { alert(res.responseText); }
	 *  };
	 */
	// delCookie("LtpaToken2", "/", "172.24.1.104");
}
/**
 * Retorna la respuesta del WebService
 * 
 * @param req
 * @returns
 */
function addRequest(req) {

	try { // create a request for netscape, mozilla, opera, etc.
		request = new XMLHttpRequest();
	} catch (e) {

		try { // create a request for internet explorer
			request = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e) { // do some error-handling
			alert("XMLHttpRequest error: " + e);
		}
	}

	request.open("GET", req, true); // prepare the request
	request.send();

	return request; // return the request
}
