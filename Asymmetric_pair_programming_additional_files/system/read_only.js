 /*jslint node: true, browser: true */
 "use strict";

 var myVar = setInterval(Get_Update, 5000);

 var file_s = "";
 var file_s_h = "";
 var sequence_ID = 0;
 var user_ID;
 var username;
 var filename = "home.html";

 var url_1 = document.URL.split("/");
 var url = url_1[0] + "//" + url_1[2] + "/";

 var Users_Position = "";
 var user_table_update = setInterval(user_update, 1000);
 var File_table_update = setInterval(Get_File_nams, 1000);
 var User_Position_update = setInterval(Send_User_Position, 1000);

 var users = [];
 var total_lines = 0;
 var following_user = "";
 var following_user_select = [];
 var add_index = [];
 var visible_file = [];
 var cursor_position = [0, 0];

 function user_update() {

     var html_temp = "";
     Get_Users_Position()
     users = [];
     var users_temp = Users_Position.split("///");
     html_temp = "<table><tr><th> User </th><th> Line </th><th> File </th><th> follow</th><th> show position </th></tr>";

     if (users_temp.length > 0) {
         users = [];
         for (var i = 0; i < users_temp.length; i++) {

             var temp = users_temp[i].split(",");
             // console.log("i " + i);

             users.push([parseInt(temp[0].trim()), temp[1], parseInt(temp[2]), parseInt(temp[3]), temp[4], parseInt(temp[5]), parseInt(temp[6])])
                 // console.log("user_update " + parseInt(temp[0]) + " /1 " + temp[1] + " /2 " + parseInt(temp[2]) + " /3 " + parseInt(temp[3]) + " /4 " + temp[4] + " /5 " + temp[5] + " /6 " + temp[6]);
                 // console.log("user_update " + " /5 " + temp[5] + " /6 " + temp[6]);

             if (!isNaN(temp[5])) {
                 if (user_ID != parseInt(temp[0]) && temp[4] == filename) {
                     if (temp[4] != "") {
                         html_temp = html_temp + "<tr><th>" + temp[1] + "</th><th>";
                         if (temp[6] >= 0) {
                             html_temp = html_temp + temp[6];
                         } else {
                             html_temp = html_temp + "0";
                         }
                         html_temp = html_temp + "</th><th>" + temp[4];
                         if (following_user == temp[0]) {
                             html_temp = html_temp + "</th><th>" + '<input type="checkbox" name="user" value="' + temp[0] + '" onclick="input_follow(' + temp[0] + ')"checked>';
                         } else {
                             html_temp = html_temp + "</th><th>" + '<input type="checkbox" name="user" value="' + temp[0] + '" onclick="input_follow(' + temp[0] + ')">';
                         }

                         // console.log("found "+ found);
                         var found = false;
                         var i1
                         for (i1 = 0; i1 < following_user_select.length; i1++) {
                             if (following_user_select[i1] == temp[0]) {
                                 found = true;
                             }
                         }
                         if (found == true) {
                             html_temp = html_temp + "</th><th>" + '<input type="checkbox" name="user" value="' + temp[0] + '" onclick="input_select(' + temp[0] + ')"checked>' + "</th></tr>";
                         } else {
                             html_temp = html_temp + "</th><th>" + '<input type="checkbox" name="user" value="' + temp[0] + '" onclick="input_select(' + temp[0] + ')">' + "</th></tr>";
                         }
                     }
                 }
             }
         };
     };

     update_selection();
     update_screen_position();
     // console.log(html_temp);
     $('#Users').html(html_temp);

 };

 function input_select(value) {
     var found = false;
     var i = 0;
     for (i = 0; i < following_user_select.length; i++) {
         if (following_user_select[i] == value) {
             var t1 = following_user_select.slice(0, i);
             var t2 = following_user_select.slice((i + 1), following_user_select.length);
             following_user_select = t1.concat(t2);
             found = true;
         }
     }

     if (found == false) {
         following_user_select.push(value);
     }

 }

 function input_follow(value) {
     if (following_user == value) {
         following_user = 0;
     } else {
         following_user = value;
     }

     // console.log("input_follow "+value);

     // document.getElementById("textdiv").scrollTop

 }

 function Comparator(a, b) {
     if (a[3] < b[3]) return 1;
     if (a[3] > b[3]) return -1;
     return 0;
 }

 function update_selection() {
     file_s_h = file_s;

     var temp_add = [];
     var temp_users = [];

     var temp_index = 0;
     for (var i1 = 0; i1 < following_user_select.length; i1++) {
         for (var x1 = 0; x1 < users.length; x1++) {

             if (users[x1][0] == following_user_select[i1]) {
                 temp_users.push(users[x1])
             }

         }
     }
     // console.log("update_selection 0 "+ temp_users);
     temp_users = temp_users.sort(Comparator);
     // console.log("update_selection 0 "+ temp_users);
     var i = 0;

     // console.log("update_selection 0 "+ following_user_select.length);
     for (var x = 0; x < temp_users.length; x++) {

         // console.log("update_selection 1 "+ users[x][0]);
         // console.log("update_selection 2 "+ following_user_select[i]);
         // console.log("update_selection 2 "+ (users[x][0] == following_user_select[i]));
         // console.log("update_selection username "+ users[x][1]);

         var c1 = temp_users[x][2];
         var c2 = temp_users[x][3];

         var a;

         // console.log("update_selection############## c1 "+c1);
         // console.log("update_selection############## c2 "+c2);
         if (c1 == c2) {

             file_s_h = file_s_h.insert(c1, '<span class="tooltiptext">' + temp_users[x][1] + '</span>');

             temp_add.push([c1, (25 + 7 + temp_users[x][1].length)])
                 // console.log("update_selection############## file_s_h "+file_s_h);

         } else {
             file_s_h = file_s_h.insert(c1, '<mark>')
             temp_add.push([c1, 6])

             file_s_h = file_s_h.insert((c2 + 6), '</mark>')
             temp_add.push([c1, 7])
         }

     }

     add_index = temp_add;
     $("#text").html(file_s_h);
 }

 function update_screen_position() {

     if (following_user > 0) {

         for (var i = 0; i < users.length; i++) {

             if (users[i][0] == following_user) {

                 document.getElementById("textdiv").scrollTop = users[i][5];
             }

         }
     }

 }

 function Get_Users_Position() {

     var xmlhttp,
         xmlDoc,
         response;

     xmlhttp = new XMLHttpRequest();

     xmlhttp.open("POST", (url + filename), true);
     xmlhttp.setRequestHeader("command", "Get_Users_Position")
     xmlhttp.send();

     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

             response = xmlhttp.responseText;
             Users_Position = response;

             // console.log("users_position " + response);

         }

     }

 };

 function Send_User_Position() {

     var xmlhttp,
         xmlDoc,
         response;

     xmlhttp = new XMLHttpRequest();

     // console.log("dsfgsfsdfdfgdg ///" + "///" + parseInt(user_ID) + "///" + person + "///" + filename + "///" + document.getElementById("textdiv").scrollTop );
     // console.log("page_off_set ///" + $("#textdiv").scrollTop );

     xmlhttp.open("POST", (url + filename), true);
     xmlhttp.setRequestHeader("command", "Set_User_Position")
     xmlhttp.send("///" + parseInt(user_ID) + "///" + cursor_position + "///" + filename + "///" + document.getElementById("textdiv").scrollTop);

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
         username_t = prompt("Please enter your username", "Harry_Potter");
     }

     var xmlhttp,
         xmlDoc,
         response;

     xmlhttp = new XMLHttpRequest();

     xmlhttp.open("POST", (url + filename), true);
     xmlhttp.setRequestHeader("command", "Set_UserName")
     xmlhttp.send("///" + username_t + "///");

     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

             user_ID = parseInt(xmlhttp.responseText);
             username = username_t;
              $('#Users_n').html(username);
             // console.log("user_ID" + parseInt(user_ID));

         }

     }

 };

 function Get_File_nams() {

     var xmlhttp,
         xmlDoc,
         response;

     xmlhttp = new XMLHttpRequest();

     xmlhttp.open("POST", (url + filename), true);
     xmlhttp.setRequestHeader("command", "visible_fileS")
     xmlhttp.send("test");
     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
             var html_temp = "<table><tr><th> available files </th></tr>";

             response = xmlhttp.responseText;
             var visible_file = response.split(",");

             if (visible_file.length > 0) {
                 users = [];
                 for (var i = 0; i < visible_file.length; i++) {

                     if (visible_file[i].trim() != filename.trim()) {

                         html_temp = html_temp + "<tr><td><button onclick=\"Get_File('" + visible_file[i].trim() + "')\">" + visible_file[i].trim() + "</button></td></tr>";

                     }

                 };
             };
             // console.log(html_temp);
             $('#file').html(html_temp);

         }

     }

 };

 function Get_File(filename_temp) {

     var xmlhttp,
         xmlDoc,
         response;

     xmlhttp = new XMLHttpRequest();

     xmlhttp.open("POST", (url + filename_temp), true);
     xmlhttp.setRequestHeader("command", "Get_File")
     xmlhttp.send();
     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
             response = xmlhttp.responseText;
             var temp = response.split("///");
             sequence_ID = parseInt(temp[0]);
             // console.log("sequence_ID - " + sequence_ID);

             file_s = response.replace((temp[0] + "///"), "");

             $("#text").html(file_s);
             $("#file_name").html(filename_temp);
             filename = filename_temp;
             following_user = "";
             following_user_select = [];

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

     // console.log("Update_file_s1");
     if (keypress.length > 0) {

         // console.log("Update_file_s2" + response);
         for (var i = 0; i < keypress.length - 1; i++) {

             // console.log(i + " " + (keypress.length - 1));
             var temp = keypress[i].split("///");
             temp[0] = temp[0].trim();

             if (temp[0] == "keypress") {
                 // console.log("keypress");
                 sequence_ID = parseInt(temp[1]);
                 var position = temp[2].split(",");

                 // console.log("key " + position[0] + " - " + position[1]);
                 if (position[0] == position[1]) {
                     // console.log(sequence_ID);
                     //    console.log(position[0]);
                     //    console.log(String.fromCharCode(temp[3]));
                     file_s = file_s.insert(position[0], String.fromCharCode(temp[3]));
                 } else {
                     /*if key is pressed and there are characters selected*/
                     // console.log("key is pressed and there are characters selected");

                     var file_start = file_s.slice(0, position[1])
                         // console.log(file_start);
                     var file_end = file_s.slice(position[0])
                     file_s = file_start + String.fromCharCode(temp[3]) + file_end;

                 }
             } else if (temp[0] == "Delete") {
                 // console.log("Delete");
                 sequence_ID = parseInt(temp[1]);

                 var file_start = file_s.slice(0, parseInt(temp[2]))
                     // console.log(file_start);
                 var file_end = file_s.slice(parseInt(temp[2]) + parseInt(temp[3]))
                 file_s = file_start + file_end;

             } else if (temp[0] == "paste") {
                 // console.log("paste");

                 sequence_ID = parseInt(temp[1]);
                 var position = temp[2].split(",");

                 // console.log("paste " + position[0] + " - " + position[1]);
                 if (position[0] == position[1]) {
                     // console.log(sequence_ID);
                     // console.log(position[0]);
                     // console.log(String.fromCharCode(temp[3]));
                     file_s = file_s.insert(position[0], temp[3]);
                 } else {

                     // console.log("paste position[0] != position[1]");

                     var file_start = file_s.slice(0, position[1])
                         // console.log(file_start);
                     var file_end = file_s.slice(position[0])
                     file_s = file_start + temp[3] + file_end;

                 }
             }

         }

     }

     update_selection();
     $("#text").html(file_s_h);

 }

 function Get_Update() {

     var el = $("#text").get(0);
     var x = 123; // Get the Unicode value
     var y = String.fromCharCode(x); // Convert the value into a character
     var xmlhttp;

     var keyCode = x;
     var CaretPosition = 321;
     // var keypress = "///12///";
     // $('#caretPos').html(CaretPosition);

     // $('#keyCode').html(x);              // Display the Unicode value
     // $('#KeyCharacter').html(y);         // Display Key character

     xmlhttp = new XMLHttpRequest();

     xmlhttp.open("POST", (url + filename), true);
     xmlhttp.setRequestHeader("command", "get_updates")

     xmlhttp.send("///" + sequence_ID + "///");

     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

             //document.getElementById("demo").innerHTML = xhttp.responseText;
             var response;

             response = xmlhttp.responseText;

             Update_file_s(response);

             // console.log(response);
             // $("#text").html(response);

             // alert("HTML: " + $("#text").html());

             // console.log($("#text").html());

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
     caretOffset = [(preCaretRange.toString().length), ((preCaretRange.toString().length) - range.toString().length)]
         // console.log(caretOffset);

     cursor_position = caretOffset;
     // send cursor position to the server
     Send_User_Position();

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
     if (w3) {} else if (ie) {
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

     var el = $("#text").get(0);
     $('#caretPos').html(getCaretPosition(el));
     $('#caretBegin').html("n/a");
     $('#caretEnd').html("n/a");
     $('#htmlBegin').html(getCaretHTMLBegin(el));
     $('#htmlEnd').html(getCaretHTMLBegin(el));
     $('#wordBegin').html(getCaretHTMLBegin(el));

 }

 function mouseup_showCaretDiv(event) {
     var el = $("#text").get(0);
     $('#caretPos').html(getCaretPosition(el));
     $('#caretBegin').html("n/a");
     $('#caretEnd').html("n/a");
     $('#htmlBegin').html(getCaretHTMLBegin(el));
     $('#htmlEnd').html(getCaretHTMLBegin(el));
     $('#wordBegin').html(getCaretHTMLBegin(el));
 }

 $('#text').keypress(event, keypress_showCaretDiv);
 $('#text').mouseup(event, mouseup_showCaretDiv);

 get_username();
 Get_File(filename);
