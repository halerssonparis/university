//import cases from './dataset.js';
var cases = require('./dataset')

console.log(cases.data[0][0])


function shuffle(array) {
  var currentIndex = array.length, temporaryValue, randomIndex;

  // While there remain elements to shuffle...
  while (0 !== currentIndex) {

    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;

    // And swap it with the current element.
    temporaryValue = array[currentIndex];
    array[currentIndex] = array[randomIndex];
    array[randomIndex] = temporaryValue;
  }

  return array;
}

var synaptic = require('synaptic');

var Neuron = synaptic.Neuron,
	Layer = synaptic.Layer,
	Network = synaptic.Network,
	Trainer = synaptic.Trainer,
	Architect = synaptic.Architect;

var inputLayer = new Layer(17);
var hiddenLayer = new Layer(300);
var outputLayer = new Layer(1);

inputLayer.project(hiddenLayer);
hiddenLayer.project(outputLayer);

var myNetwork = new Network({
	input: inputLayer,
	hidden: [hiddenLayer],
	output: outputLayer
});

var learningRate = .3;
for (var i = 0; i < 5000; i++)
{
    for (var j = 0; j < cases.data.length; j++) {

        myNetwork.activate([cases.data[j][0], cases.data[j][1], cases.data[j][2], cases.data[j][3], cases.data[j][4], cases.data[j][5], cases.data[j][6], cases.data[j][7], cases.data[j][8], cases.data[j][9], cases.data[j][10], cases.data[j][11], cases.data[j][12], cases.data[j][13], cases.data[j][13], cases.data[j][15], cases.data[j][16]]);

        myNetwork.propagate(learningRate, [cases.data[j][17]]);
    }   
    cases.data = shuffle(cases.data)
}

console.log("terminou!")

/*

const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});



rl.question('What do you think of Node.js? ', (answer) => {
    result = answer.split(",");
    if (result[0] == "exit") {
        return;
    }
    
    console.log(myNetwork.activate(result));
  rl.close();
});*/


/*
process.stdin.resume();
process.stdin.setEncoding('utf8');
var util = require('util');

process.stdin.on('data', function (text) {
    console.log('received data:', util.inspect(text));
    if (text === 'quit\n') {
      done();
    }
    text = text.substring(0, text.length-1); 
    text = text.split(",")

    console.log(myNetwork.activate(text))
});

function done() {
    console.log('Now that process.stdin is paused, there is nothing more to do.');
    process.exit();
}*/

results = []
_CORRET = 1;
_INCORRET = 0;
_THINKING = 0.5;
erro1 = 0;
acerto1 = 0;

erro0 = 0;
acerto0 = 0;

for (var j = 0; j < cases.data.length; j++) {

    var resultActual = myNetwork.activate([cases.data[j][0], cases.data[j][1], cases.data[j][2], cases.data[j][3], cases.data[j][4], cases.data[j][5], cases.data[j][6], cases.data[j][7], cases.data[j][8], cases.data[j][9], cases.data[j][10], cases.data[j][11], cases.data[j][12], cases.data[j][13], cases.data[j][13], cases.data[j][15], cases.data[j][16]]);

    if (resultActual <= 0.15 ) {
        if (cases.data[j][17] == 0 ) {
            results.push(_CORRET);
            console.log("Esperado: ",cases.data[j][17],"    ", _CORRET,"       Saida: ",resultActual);
            acerto0 = acerto0 + 1;
        }
        else {
            results.push(_INCORRET);
            console.log("Esperado: ",cases.data[j][17],"    ", _INCORRET,"     Saida: ",resultActual);
            erro0 = erro0 + 1;        
        }
    }
    else if (resultActual > 0.15 && resultActual < 0.75 ) {
        results.push(_THINKING);
        console.log("Esperado: ",cases.data[j][17],"    ", _THINKING,"     Saida: ",resultActual);
    }
    else if (resultActual >= 0.75 ) {
        if (cases.data[j][17] == 1 ) {
            results.push(_CORRET);
            console.log("Esperado: ",cases.data[j][17],"    ", _CORRET,"       Saida: ",resultActual);
            acerto1 = acerto1 + 1;
        }
        else {
            results.push(_INCORRET);
            console.log("Esperado: ",cases.data[j][17],"    ", _INCORRET,"     Saida: ",resultActual);
            erro1 = erro1 + 1;        
        }
    }

    
} 

console.log("acerto 1: ", acerto1, " erro 1: ", erro1, "\nacerto 0: ", acerto0, "  erro 0: ", erro0);


//console.log(myNetwork.activate([ 35, 8, 1, 16, 0, 6, 70, 15, 1, 2, 1850, 21, 23, 2970, 2302, 21, 6]))
//console.log(myNetwork.activate([ 35, 9, 1, 64, 0, 21, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]))
//console.log(myNetwork.activate([ 35, 11, 2, 1, 652, 1, 1, 15, 1, 5, 1635, 21, 17, 2640, 2073, 21, 6]))
//console.log(myNetwork.activate([ 178, 16, 2, 7, 0, 30, 154, 0, 0, 0, 0, 0, 3, 186, 0, 0, 0]))
//console.log(myNetwork.activate([ 194, 17, 2, 7, 0, 30, 154, 0, 0, 0, 0, 0, 3, 186, 0, 0, 0]))
/*


#NEW
[ 161, 23, 1, 47, 417, 1, 1, 7, 7, 2, 582, 7, 11, 752, 582, 7, 0, 0 ],
    [ 170, 17, 2, 3, 0, 30, 154, 0, 0, 0, 0, 0, 2, 124, 0, 0, 0, 1 ],


##OLD
[ 49, 12, 2, 13, 4877, 2, 119, 8, 0, 2, 811, 10, 11, 6062, 961, 10, 2, 1 ],
[ 49, 13, 2, 7, 0, 23, 80, 50, 0, 2, 3571, 52, 55, 66449, 3723, 52, 2, 1 ],
[ 56, 11, 1, 6, 0, 39, 130, 7, 0, 2, 752, 9, 9, 1197, 916, 9, 2, 1 ], // ta treinado
[ 57, 10, 3, 3, 324, 1, 1, 62, 0, 3, 4725, 66, 65, 97272, 5023, 66, 4, 0 ], / ta treinado
[ 58, 13, 2, 13, 5962, 2, 86, 10, 0, 2, 1692, 14, 15, 8467, 2004, 14, 4, 1 ], // ta treinado


[ 54, 10, 1, 16, 18952, 2, 9, 1194, 12, 8, 99843, 1198, 1284, 2060012, 100151, 1198, 4, 0 ],
[ 55, 11, 2, 0, 0, 2, 113, 48, 39, 11, 5137, 55, 61, 67877, 5597, 55, 6, 0 ],
[ 135, 26, 2, 7, 0, 30, 154, 0, 0, 0, 0, 0, 3, 186, 0, 0, 0, 1 ],
[ 136, 43, 2, 4, 649263, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ],

[ 1 ]
[ 0 ]
 1
*/

// test the network
