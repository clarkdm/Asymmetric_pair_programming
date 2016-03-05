<!DOCTYPE html>
<html>
<body>

<p>Press a key on the keyboard in the input field to get the number and character of the pressed key.</p>

<input type="text" size="40" onkeypress="myFunction(event)">

<p id="demo"></p>

<script>
function myFunction(event) {
    var x = event.keyCode;               // Get the Unicode value
    var y = String.fromCharCode(x);      // Convert the value into a character
    document.getElementById("demo").innerHTML = "Number: " + x + " = Character: " + y;
}
</script>

</body>
</html>
