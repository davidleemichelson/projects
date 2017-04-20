/**
 * 
 */
function errorCheck (servletName, jspName, paramArgs, numArgs, errorDivName) {
	var xhttp = new XMLHttpRequest();
	var path = "/"+window.location.pathname.split("/")[1];
	//create url with first parameter from paramArgs
	var url = path + servletName+"?"+paramArgs[0]+"="+document.getElementById(paramArgs[0]).value;
	//append the rest of the elements in paramArgs
	for (let i = 1; i<numArgs; i++){
		url += "&"+paramArgs[i]+"="+document.getElementById(paramArgs[i]).value;
	}
	//send synchronous ajax call to servelt
	xhttp.open("GET", url, false);
	xhttp.send();
	//if we got a response text, there must have been an error
	if (xhttp.responseText.trim().length > 0) {
		//set the repsonse text as the innerHTML of the error div
		document.getElementById(errorDivName).innerHTML = xhttp.responseText;
	}
	else{
		//otherwise navigate to the destination jsp
		window.location.href = path + "/" +  jspName;
	}
	
	return false;
}