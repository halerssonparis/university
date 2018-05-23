var synaptic = require('synaptic');

var Neuron = synaptic.Neuron,
	Layer = synaptic.Layer,
	Network = synaptic.Network,
	Trainer = synaptic.Trainer,
	Architect = synaptic.Architect;

var inputLayer = new Layer(3);
var hiddenLayer = new Layer(30);
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
	// 0,0 => 0
	myNetwork.activate([0,0,0]);
	myNetwork.propagate(learningRate, [1]);

	// 0,1 => 1
	myNetwork.activate([0,0,5]);
	myNetwork.propagate(learningRate, [0]);

	// 1,0 => 1
	myNetwork.activate([0,5,0]);
	myNetwork.propagate(learningRate, [0]);

	// 1,1 => 0
	myNetwork.activate([0,5,5]);
	myNetwork.propagate(learningRate, [1]);

}


// test the network
console.log(myNetwork.activate([5,5,0])); // [0.015020775950893527]
myNetwork.activate([0,1]); // [0.9815816381088985]
myNetwork.activate([1,0]); // [0.9871822457132193]
myNetwork.activate([1,1]); // [0.012950087641929467]
