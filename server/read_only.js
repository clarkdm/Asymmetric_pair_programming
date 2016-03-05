 /*jslint node: true, browser: true */
"use strict";



var file_s = "";
var sequence_ID = 0;
var user_ID















function get_username() {
    var person = prompt("Please enter your username", "Harry_Potter");
    
    while (person == "") {
        person = prompt("Please enter your username", "Harry_Potter");
    }


 var xmlhttp,
        xmlDoc,
        response;




   
        xmlhttp = new XMLHttpRequest();
   

    xmlhttp.open("POST", "http://127.0.0.1:6790/code.agda", true);
    xmlhttp.setRequestHeader("command", "Set_UserName")
    xmlhttp.send("///"+person+"///");


    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


    		user_ID = xmlhttp.responseText;

			
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
			console.log(sequence_ID);


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
if (keypress.length > 0) {

for (var i = 0; i < keypress.length -1; i++) {

	var temp = keypress[i].split("///");
	sequence_ID = parseInt(temp[1]) ;
	console.log(sequence_ID);
	file_s = file_s.insert(temp[2], String.fromCharCode(temp[3]));
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
    $('#caretPos').html(CaretPosition);

    $('#keyCode').html(x);   			// Display the Unicode value
    $('#KeyCharacter').html(y);			// Display Key character

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
			
			
            // $("#test").html(response);


        // alert("HTML: " + $("#test").html());
    
			// console.log($("#test").html());


			console.log(xmlhttp.responseText);
    		//text1 = document.getElementById("text1")
			//text1.value = xmlhttp.responseText




            }


	}
};








get_username()
Get_File()
var myVar = setInterval(Get_Update, 5000);