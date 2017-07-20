 /*jslint node: true, browser: true */
"use strict";

get_username()
Get_File()

var myVar = setInterval(Get_Update, 5000);

var file_s = "";
var sequence_ID = 0;
var user_ID;
var username;
var filename = "code.agda";
var Users_Position = "";
// var user_table_update = setInterval(user_update, 10000);
var users = [];
var total_lines = 0;









function user_update() {

    var html_temp = "";
    Get_Users_Position()
    users = [];
        var users_temp = Users_Position.split("///");
        html_temp = "<table><tr><th> User </th><th> Line </th><th> File </th></tr>";
    if (users_temp.length > 0) {

        for (var i = 0; i < users_temp.length; i++) {

            var temp = users_temp[i].split(",");

            users.push([parseInt(temp[0]),temp[1] , parseInt(temp[2]), parseInt(temp[3]), temp[4]])
          console.log("user_update " + parseInt(temp[0]) + temp[1] + parseInt(temp[2]) + parseInt(temp[3]) + temp[4]) + temp[5];
            
            if (!isNaN(temp[5])) {

                html_temp = html_temp + "<tr><th>" + temp[1] + "</th><th>" + temp[5] +"</th><th>" + temp[4] +  "</th></tr>" 
            }

        };

    };


     $('#Users').html(html_temp);

};




function Get_Users_Position() {
 

 var xmlhttp,
        xmlDoc,
        response;
   
        xmlhttp = new XMLHttpRequest();
   

    xmlhttp.open("POST", "http://127.0.0.1:6790/code.agda", true);
    xmlhttp.setRequestHeader("command", "Get_Users_Position")
    xmlhttp.send();


    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


            response = xmlhttp.responseText;
            
            Users_Position = response;
            
            console.log("users_position " + response);

            }
         
        } 

};

function Send_User_Position(person) {
   

 var xmlhttp,
        xmlDoc,
        response;
   
        xmlhttp = new XMLHttpRequest();
   

    xmlhttp.open("POST", "http://127.0.0.1:6790/code.agda", true);
    xmlhttp.setRequestHeader("command", "Set_User_Position")
    xmlhttp.send("///" + parseInt(user_ID) + "///" + person + "///" + filename);


    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


            response = xmlhttp.responseText;

            
            // console.log("user_ID" + parseInt(user_ID));

            }
         
        } 

};











function get_username() {
    var username_t = prompt("Please enter your username", "Harry_Potter");
    
    while (username_t == "") {
        username_t = username_t("Please enter your username", "Harry_Potter");
    }


 var xmlhttp,
        xmlDoc,
        response;




   
        xmlhttp = new XMLHttpRequest();
   

    xmlhttp.open("POST", "http://127.0.0.1:6790/code.agda", true);
    xmlhttp.setRequestHeader("command", "Set_UserName")
    xmlhttp.send("///"+username_t+"///" + "\r\n");


    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


    		user_ID = xmlhttp.responseText;
            username = username_t;
			
			console.log("user_ID" + parseInt(user_ID));


			



            }
          

        } 


};
























function Get_File() {


    var xmlhttp,
        xmlDoc,
        response;




   
        xmlhttp = new XMLHttpRequest();
   

    xmlhttp.open("POST", "http://127.0.0.1:6790/code.agda", true);
    xmlhttp.setRequestHeader("command", "Get_File")
    xmlhttp.send();


    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


    		response = xmlhttp.responseText;

			var temp = response.split("///");
			sequence_ID = parseInt(temp[0]) ;
			console.log("sequence_ID - " + sequence_ID);


			file_s = response.replace((temp[0] + "///"), "");

			




			
			
            $("#test").html(file_s);



            }
          

        } 

    };

String.prototype.insert = function (index, string) {
	
  if (index > 0)
    return this.substring(0, index) + string + this.substring(index, this.length);
  else
    return string + this;
};



function Update_file_s(response) {

var keypress = response.split(",,,");
console.log("Update_file_s1");
if (keypress.length > 0) {
console.log("Update_file_s2");
for (var i = 0; i < keypress.length -1; i++) {

	var temp = keypress[i].split("///");
	sequence_ID = parseInt(temp[1]) ;
    var position = temp[2].split(",");

console.log("key " + position[0] + " - " + position[1]);
    if (position[0] == position[1]) {
    	console.log(sequence_ID);
        console.log(position[0]);
        console.log(String.fromCharCode(temp[3]));
    	file_s = file_s.insert(position[0], String.fromCharCode(temp[3]));
    }else{
/*if key is pressed and there are characters selected*/
console.log("key is pressed and there are characters selected");
        var file_start = file_s.slice(0, position[1])
        console.log(file_start);
        var file_end = file_s.slice(position[0])
        file_s = file_start + String.fromCharCode(temp[3]) + file_end;


    }
}

}
$("#test").html(file_s);

}




function Get_Update() {

var el = $("#test").get(0);
    var x = 123;               // Get the Unicode value
    var y = String.fromCharCode(x);      // Convert the value into a character
    var xmlhttp;
    var url = "http://127.0.0.1:6790/";
    var file = "code.agda";
    var keyCode = x;
    var CaretPosition = 321;
    // var keypress = "///12///";
    // $('#caretPos').html(CaretPosition);

    // $('#keyCode').html(x);   			// Display the Unicode value
    // $('#KeyCharacter').html(y);			// Display Key character

    url = url.concat(file);
    //url = url.concat("/");
    //url = url.concat(CaretPosition);
	//url = url.concat("/");
    //url = url.concat(keyCode);

    xmlhttp = new XMLHttpRequest();
    

    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("command", "get_updates")

	
	
    xmlhttp.send("///" + sequence_ID + "///");


    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			//document.getElementById("demo").innerHTML = xhttp.responseText;
			var response;

			response = xmlhttp.responseText;

			Update_file_s(response);
			
			console.log(response);
            // $("#test").html(response);


        // alert("HTML: " + $("#test").html());
    
			// console.log($("#test").html());


			// console.log(xmlhttp.responseText);
    		//text1 = document.getElementById("text1")
			//text1.value = xmlhttp.responseText




            }


	}
};















































// code for cursor location is based on code from http://jsfiddle.net/cpatik/3QAeC/



var ie = (typeof document.selection != "undefined" && document.selection.type != "Control") && true;
var w3 = (typeof window.getSelection != "undefined") && true;



function getCaretPosition(element) {
    var caretOffset;
    
        var range = window.getSelection().getRangeAt(0);
        var preCaretRange = range.cloneRange();
        preCaretRange.selectNodeContents(element);
        preCaretRange.setEnd(range.endContainer, range.endOffset);
        caretOffset = [(preCaretRange.toString().length), ((preCaretRange.toString().length) - range.toString().length) ]
           // console.log(caretOffset);


        // send cursor position to the server
        Send_User_Position(caretOffset);

        
    return caretOffset;
}

function getCaretHTMLBegin(element) {
    var caretOffset = 0;
    if (w3) {
        var range = window.getSelection().getRangeAt(0);
        var preCaretRange = range.cloneRange();
        preCaretRange.selectNodeContents(element);
        preCaretRange.setEnd(range.endContainer, range.beginOffset);
        caretOffset = preCaretRange.toString().length;
    } else if (ie) {
        caretOffset = 'n/a';
    }
    return caretOffset;
}

function getCaretBegin(element) {
    var caretOffset = 0;
    if (w3) {
        var range = window.getSelection().getRangeAt(0);
        var preCaretRange = range.cloneRange();
        preCaretRange.selectNodeContents(element);
        preCaretRange.setEnd(range.endContainer, range.beginOffset);
        caretOffset = preCaretRange.toString().length;
    } else if (ie) {
        var textRange = document.selection.createRange();
        var preCaretTextRange = document.body.createTextRange();
        preCaretTextRange.moveToElementText(element);
        preCaretTextRange.setEndPoint("EndToStart", textRange);
        caretOffset = preCaretTextRange.text.length;
    }
    return caretOffset;
}

function getSelectionBegin(element) {
    var caretOffset = 0;
    if (w3) {
    }
    else if (ie) {
        var textRange = document.selection.createRange();
        var preCaretTextRange = document.body.createTextRange();
        preCaretTextRange.moveToElementText(element);
        preCaretTextRange.setEndPoint("EndToStart", textRange);
        caretOffset = preCaretTextRange.text.length;
    }
    return caretOffset;
}

function getSelectedRange(element) {
    var selection = {
        position: 'n/a',
        begin: 'n/a',
        end: 'n/a',
        size: 'n/a',
        htmlBegin: 'n/a',
        htmlEnd: 'n/a',
        wordBegin: 'n/a',
        wordEnd: 'n/a'
    };
    if (ie) {
        var textRange = document.selection.createRange();
        var preCaretTextRange = document.body.createTextRange();
        preCaretTextRange.moveToElementText(element);
        preCaretTextRange.setEndPoint("EndToEnd", textRange);
        selection.end = preCaretTextRange.text.length;
        
        preCaretTextRange.moveToElementText(element);
        preCaretTextRange.setEndPoint("EndToStart", textRange);
        selection.begin = preCaretTextRange.text.length;
        selection.size = selection.end - selection.begin;
    }
    return selection;
}

function keypress_showCaretDiv(event) {

 var el = $("#test").get(0);
    $('#caretPos').html(getCaretPosition(el));
    $('#caretBegin').html("n/a");
    $('#caretEnd').html("n/a");
    $('#htmlBegin').html(getCaretHTMLBegin(el));
    $('#htmlEnd').html(getCaretHTMLBegin(el));
    $('#wordBegin').html(getCaretHTMLBegin(el));


   /* var el = $("#test").get(0);
    var x = event.keyCode;               // Get the Unicode value
    var y = String.fromCharCode(x);      // Convert the value into a character
    var xmlhttp;
    var url = "http://127.0.0.1:6789/";
    var file = "code.agda";
    var keyCode = x;
    var CaretPosition = getCaretPosition(el);
    var keypress = "///" + sequence_ID + "///";
    $('#caretPos').html(CaretPosition);

    $('#keyCode').html(x);              // Display the Unicode value
    $('#KeyCharacter').html(y);         // Display Key character

    url = url.concat(file);
    //url = url.concat("/");
    //url = url.concat(CaretPosition);
    //url = url.concat("/");
    //url = url.concat(keyCode);

    xmlhttp = new XMLHttpRequest();
    

    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("command", "keypress")

    keypress = keypress.concat(CaretPosition);
    keypress = keypress.concat("///");
    keypress = keypress.concat(keyCode);
    keypress = keypress.concat("///");
    
    xmlhttp.send(keypress);


    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

            //document.getElementById("demo").innerHTML = xhttp.responseText;
            
            
            
            console.log("200 console.log(xhttp.responseText);" + sequence_ID);
            //console.log(xmlhttp.responseText);
            //text1 = document.getElementById("text1")
            //text1.value = xmlhttp.responseText

            sequence_ID = sequence_ID + 1;


            }

        

        } 




    // $('#caretBegin').html(getSelectionBegin(el));
    // $('#caretEnd').html(getCaretPosition(el));
    // $('#htmlBegin').html(getCaretHTMLBegin(el));
    // $('#htmlEnd').html(getCaretHTMLBegin(el));
    // $('#wordBegin').html(getCaretHTMLBegin(el));*/




}

function mouseup_showCaretDiv(event) {
  var el = $("#test").get(0);
    $('#caretPos').html(getCaretPosition(el));
    $('#caretBegin').html("n/a");
    $('#caretEnd').html("n/a");
    $('#htmlBegin').html(getCaretHTMLBegin(el));
    $('#htmlEnd').html(getCaretHTMLBegin(el));
    $('#wordBegin').html(getCaretHTMLBegin(el));
}






$('#test').keypress(event, keypress_showCaretDiv);
$('#test').mouseup(event, mouseup_showCaretDiv);

