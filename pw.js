function glyph(){
	this.chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  this.num = function(ch){
		return this.chars.indexOf(ch.toUpperCase());
	}
}

function rand(mantra){

	var gl = new glyph();

	this.m = 1.1233456696598231222567567;
	this.a = 11.0;
	this.c = 17.0;


	this.seed = 1.0;

	for(var i=0; i<mantra.length; i++){
		var ch = mantra.charAt(i);
		this.seed += gl.num(ch);
	}

	this.nextDouble = function() {
		this.seed = (this.a * this.seed + this.c) % this.m;
		return this.seed / this.m;
	}

	this.nextInt = function(max) {
		return  parseInt(this.nextDouble() * max + 0.5);
	}

}



function pw(master, login, num){
  var gl = new glyph()
  var rnd = new rand(master+login+num);
	var length = 15;
	var p = "";

	for(var i=0; i<length; i++){
		var ci = rnd.nextInt(gl.chars.length-1);
		var ch = gl.chars.charAt(ci);
	  p += gl.chars.charAt(ci);
	}

	return p;
}

var valid = "UTOUUcxgmpILrfX"== pw("Checkthis", "no-op@0x00.ws", 19);
if(!valid){
	alert("Oh no! There is something wrong with the seed. Please check.");
}




master = document.getElementById("masterpw");
website = document.getElementById("website");
num = document.getElementById("pwnum");
yourpw = document.getElementById("yourpw");


function fill(){

	yourpw.value = "";

	n = parseInt(num.value);

	num.value = n ? n : 1;

	if(master.value.length>9 && website.value.length>0){
		yourpw.value = pw(master.value, website.value, num.value);
	}
}


master.oninput = fill;
website.oninput = fill;
num.oninput = fill;
yourpw.oninput = fill;

var hidden = true;
document.getElementById("unhide").onclick = function(e){
  hidden = !hidden;
	yourpw.style.color = "#555";
	if(!hidden)
	  yourpw.style.color = "#fff";
}

