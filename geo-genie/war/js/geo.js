var footers = ["I love web apps", 
           "CS 402 is the best course I've ever taken", 
           "I can't wait to learn more about AJAX",
           "I love the St. Louis Cardinals",
           "Eucalyptus is great (if I could ever log in)",
           "I love servlets",
           "JSON rocks!",
           "I would rather program than eat ice cream.",
           "Can servlets make dinner for me?",
           "I'm going to phone JSON and see if he can help me",
           "When will we talk about JSF?  It's my favorite java thing!",
           "I think I'm missing a semicolon somewhere"
           ];

var alerts = ["Slow down cowboy.  Google doesn't like twitchy trigger fingers.",
              "Hold your horses.  Google prefers a slow trot to a gallop.",
              "Take a pill!  Google has servers that are easily upset by hyper-activity!",
              "I can see that you're a fast-clicker.  Google warned me about people like you."              
              ];


var lastHit;
var appRoot = "/geo-genie";

function setFooter() {
	var i = Math.floor(Math.random() * footers.length);
	$("#footerp").text(footers[i]);
}

function getAlertMessage() {
	var i = Math.floor(Math.random() * alerts.length);
	return alerts[i];
}

function getIconClass(hit) {
	if(isHitComplete(hit)) {
		return "icon-minus-sign icon-white orange";
	} else {
		return "icon-minus-sign icon-white red";
	}
}

function createIcon(parent, hit) {
	var icon_class = getIconClass(hit);
	var icon = document.createElement("i");
	icon.setAttribute("class", icon_class);
	icon.onclick = function() { deleteHit(hit.id); };
	$(icon).css("cursor", "pointer");
	//$(icon).css("cursor:pointer").onclick(deleteHit(hit.id));
//	$(icon).click(deleteHit(hit.id));
	parent.appendChild(icon);
}

function createSpan(parent, text, warn) {
	var labelClass = "";
	if(warn) {
		labelClass = "label label-important";
	}
	var span = document.createElement("span");
	span.setAttribute("class",  labelClass);
	var textNode = document.createTextNode(text);
	span.appendChild(textNode);
	parent.appendChild(span);
	return span;
}

function createDiv(parent, text, warn) {
	var labelClass = "";
	if(warn) {
		labelClass = "label label-important";
	}
	var div = document.createElement("div");
	div.setAttribute("class",  labelClass);
	var textNode = document.createTextNode(text);
	div.appendChild(textNode);
	parent.appendChild(div);
	return div;
}

function isHitComplete(hit) {
	return hit.query &&
	       hit.query.lat &&
	       hit.query.lon &&
		   hit.id &&		   
	       hit.response &&
	       hit.response.address && 
	       hit.response.time &&
	       hit.response.sourceIp;
}

function createWeatherDiv(parent, hit) {
	var div = document.createElement("div");
	div.setAttribute("class", "wdiv");
	var weather = hit.response.weather;
	var weatherSpan = createSpan(div, weather, !weather);	
	$(weatherSpan).addClass('weather');
	
	createSpan(div, " @ ", false);
	
	var temp = hit.response.temp;
	var tempSpan = createSpan(div, temp, !temp);
	$(tempSpan).addClass('temp');
	
	parent.appendChild(div);
}

function createHit(hit) {
	console.log(hit);
	var div = document.createElement("div");
	div.setAttribute("class", "well well-small");
	div.setAttribute("id", hit.id);
	
	createIcon(div, hit);	
	var location = hit.query;
	warn = !location.lon || !location.lat;
	locDiv = createSpan(div, "(" + location.lat + ",  " + location.lon + ")", warn);
	$(locDiv).addClass("location");
	
	
	var time = hit.response.time;
	var d = new Date(time); 
	var timeDiv = createSpan(div, String(d), !time);
	$(timeDiv).addClass("time");
	
	var ip = hit.response.sourceIp;
	var ipSpan = createSpan(div, ip, !ip);
	$(ipSpan).addClass('ip');
	
	var includeWeather = $("#weatherCheck").is(':checked');
	if(includeWeather) {				
		createWeatherDiv(div, hit);
	}
		
	var address = hit.response.address;	
	addressDiv = createDiv(div, address, !address);
	$(addressDiv).addClass("address");	
	
	return div;
}

function createHits(data) {
	console.log(data);
	data = eval(data);
	console.log(data);
	var $list = $("#hitList");
	$list.empty();		
	console.log($list);
	for(var i=0; i<data.length; i+=1) {
		var hit = data[i];
		$list.append(createHit(hit));
	}
}

function tooFast() {
	if(!lastHit) {
		lastHit = new Date();
		return false;
	}
	
	var now = new Date();
	var diff = now - lastHit;
	
	if(diff < 5000) return true; // throttle to 5 seconds
	lastHit = new Date();
	return false;
}


function createAlert(message,alerttype) {
    $('#hitList').append('<div id="alertdiv" class="alert ' 
    		+  alerttype 
    		+ '"><a class="close" data-dismiss="alert">&times;</a><span>'
    		+ message 
    		+'</span></div>');
    $("a.close").click(function() {$(".alert").fadeOut();});
  } 


function deleteHit(id) {
	var endpoint = $("endpointField").val();
	var base = window.location.origin + appRoot;
	if(endpoint) {
		base = "http://" + endpoint;
	}
	var url = getEndpoint(base, "/latlon", {id : id});
	console.log("deleting hit(" + id + ") and endpoint is " + url);
	$.ajax({
		url: url,
		type: 'post',
		async: true,
		xhrFields: {
			crossDomain:true
		},
		success: createHits
	});	
}

function getEndpoint(base, service, queries) {
	var endpoint = base + service;
	var count = 0;
	for(prop in queries) {
		if(queries[prop]) {
		if(count > 0) {
			endpoint = endpoint + "&";
		} else {
			endpoint = endpoint + "?";
		}
		count = count + 1;
		endpoint = endpoint + prop + "=" + queries[prop];
		}
	}
	return endpoint;
}

function makeQuery() {
	if(tooFast()) {
		alert = $(".alert");
		if(!alert.length) {
			createAlert(getAlertMessage(), "alert-error");
		}
		return;
	} else {
		var alert = $(".alert");
		if(alert.length) alert.remove();
	}

	var endpoint = $("endpointField").val();
	var base = window.location.origin + appRoot;
	if(endpoint) {
		base = "http://" + endpoint;
	}
	var url = getEndpoint(base, "/latlon", {lat : $("#latField").val(), lon : $("#lonField").val() });
	console.log(url);
	$.ajax({
		url: url,
		type: 'get',
		async: true,
		xhrFields: {
			crossDomain:true
		},
		success: createHits
	});
	setFooter();
}

