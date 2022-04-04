'use strict';

const jwt = require('jsonwebtoken');

//Command: loadtest.cmd -n numbreq -c concurrency -p .\test.js -T application/json http://localhost:8080/validate

/*
module.exports = function() {
   let token = jwt.sign({vz: "1"}, Buffer.from("LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU=", 'base64'), {
       header: {typ: undefined},
       expiresIn: "1d"
   })
    return {zone: '1',token}
}
*/
/*
module.exports =  function() {

    const v = Math.random();
    let token
    if (v < 0.5) {
        token = jwt.sign({vz: '1', sub:500}, 'LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU', {expiresIn: "10h"});
        return {token,zone: "1"}

    } else {

        token = jwt.sign({vz: "1", sub:500}, Buffer.from("LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU=", 'base64'), {header: { typ: undefined}, expiresIn: "1d"})
        return {token,zone: "1"}
       }
}
*/
//const testValue = 100
//const testValue = 1000
//const testValue = 10000
//const testValue = 100000
//const testValue = 1000000
//const testValue = 10000000
const testValue = 100000000
//const testValue = 4000000000
module.exports =  function() {

    const v = Math.floor(Math.random() * testValue);
    let token
            let threshold
            if(testValue % 2 == 0)
                threshold = testValue/2
            else
                threshold = (testValue-1)/2
    if (v < threshold) {
        token = jwt.sign({vz: '1', sub:v}, 'LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11U', {expiresIn: "10h"});
        return {token,zone: "1"}

    } else {

        token = jwt.sign({vz: "1", sub:v}, Buffer.from("LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU=", 'base64'), {header: { typ: undefined}, expiresIn: "1d"})
        return {token,zone: "1"}
    }
}
