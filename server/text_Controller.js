/*jslint node: true, browser: true */
"use strict";
Get_File()

var file_s = "";
var sequence_ID = 0;



function Get_File() {


    var xmlhttp,
        xmlDoc,
        response;




   
        xmlhttp = new XMLHttpRequest();
   

    xmlhttp.open("POST", "http://127.0.0.1:6789/code.agda", true);
    xmlhttp.setRequestHeader("command", "Get_File")
    xmlhttp.send();


    xmlhttp.onreadystatechange = function () {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


    		response = xmlhttp.responseText;


            var temp = response.split("///");
            sequence_ID = parseInt(temp[0]) ;
            console.log("///hi " + sequence_ID);


            file_s = response.replace((temp[0] + "///"), "");
			
            $("#test").html(file_s);



            }
          

        } 

    };





// code for cursor location is based on code from http://jsfiddle.net/cpatik/3QAeC/



var ie = (typeof document.selection != "undefined" && document.selection.type != "Control") && true;
var w3 = (typeof window.getSelection != "undefined") && true;



function getCaretPosition(element) {
    var caretOffset = 0;
    if (w3) {
        var range = window.getSelection().getRangeAt(0);
        var preCaretRange = range.cloneRange();
        preCaretRange.selectNodeContents(element);
        preCaretRange.setEnd(range.endContainer, range.endOffset);
        caretOffset = preCaretRange.toString().length;
    } else if (ie) {
        var textRange = document.selection.createRange();
        var preCaretTextRange = document.body.createTextRange();
        preCaretTextRange.moveToElementText(element);
        preCaretTextRange.setEndPoint("EndToEnd", textRange);
        caretOffset = preCaretTextRange.text.length;
    }
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
    var x = event.keyCode;               // Get the Unicode value
    var y = String.fromCharCode(x);      // Convert the value into a character
    var xmlhttp;
    var url = "http://127.0.0.1:6789/";
    var file = "code.agda";
    var keyCode = x;
    var CaretPosition = getCaretPosition(el);
    var keypress = "///" + sequence_ID + "///";
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
    // $('#wordBegin').html(getCaretHTMLBegin(el));
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