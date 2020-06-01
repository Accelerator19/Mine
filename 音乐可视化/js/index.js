var AudioContext = window.AudioContext || window.webkitAudioContext;

var canvas, ctx;
var audioContext;
var file;
var fileContent;
var audioBufferSourceNode;
var analyser;

function ChooseFile() {
	document.getElementById('choosebutton').click();
}


var loadFile = function() {
	var fileReader = new FileReader();
	fileReader.onload = function(e) {
		fileContent = e.target.result;
		decodecFile();
	}
	fileReader.readAsArrayBuffer(file);
}

var decodecFile = function() {
	audioContext.decodeAudioData(fileContent, function(buffer) {
		start(buffer);
	});
}

var start = function(buffer) {
	if (audioBufferSourceNode) {
		audioBufferSourceNode.stop();
	}

	audioBufferSourceNode = audioContext.createBufferSource();
	audioBufferSourceNode.connect(analyser);
	analyser.connect(audioContext.destination);
	audioBufferSourceNode.buffer = buffer;
	audioBufferSourceNode.start(0);
	hidefile();
	window.requestAnimationFrame(render);
}

var hidefile = function(show) {
	var fileChooser = document.getElementById('fileChooser');
	fileChooser.style.opacity = 0;
	var infotext = document.getElementById("infotext");
	infotext.style.opacity = 0;
	var player = document.getElementById('player-content');
	player.style.opacity = 1;
}

var render = function() {
	ctx = canvas.getContext("2d");
	ctx.strokeStyle = "#00d0ff";
	ctx.lineWidth = 2;
	ctx.clearRect(0, 0, canvas.width, canvas.height);

	var dataArray = new Uint8Array(analyser.frequencyBinCount);
	analyser.getByteFrequencyData(dataArray);
	var step = Math.round(dataArray.length / 60);

	var voicec1 = ["#0f0", "#f00", "#f0f"];
	
	canvas.width = window.innerWidth * .5;
	canvas.height = window.innerHeight * .7;
	
	color = ctx.createLinearGradient(canvas.width * .5, 0, canvas.width * .5, 300);
	color.addColorStop(0, voicec1[0]);
	color.addColorStop(.5, voicec1[1]);
	color.addColorStop(1, voicec1[2]);
	colort = ctx.createLinearGradient(canvas.width * .5, 300, canvas.width * .5, 600);
	colort.addColorStop(0, "rgba(125,225,133,0.7)");
	colort.addColorStop(.5, "rgba(225,225,0,0.1)");
	colort.addColorStop(1, "rgba(125,0,133,0)");
	
	ctx.clearRect(0, 0, canvas.width, canvas.height);

	ctx.beginPath();

	for (var i = 1; i < 60; i++) {
		var value = dataArray[step * i];
		ctx.fillStyle = color;
		ctx.fillRect(i * 10 + canvas.width * .5, 250, 7, -value + 1);
		ctx.fillRect(canvas.width * .5 - (i - 1) * 10, 250, 7, -value + 1);
		ctx.fill();
		ctx.fillStyle = colort;
		ctx.fillRect(i * 10 + canvas.width * .5, 250, 7, value + 1);
		ctx.fillRect(canvas.width * .5 - (i - 1) * 10, 250, 7, value + 1);
	}

	window.requestAnimationFrame(render);
}

window.onload = function() {
	audioContext = new AudioContext();
	analyser = audioContext.createAnalyser();
	analyser.fftSize = 256;

	var choosebutton = document.getElementById('choosebutton');
	choosebutton.onchange = function() {
		if (choosebutton.files[0]) {
			file = choosebutton.files[0];
			loadFile();
		}
	}

	canvas = document.getElementById('visualizer');
}
