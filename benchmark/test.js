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
module.exports =  function() {

    const v = Math.random();
    let token
    if (v < 0.5) {
        token = jwt.sign({vz: '1'}, 'wrong', {expiresIn: "10h"});
        return {token,zone: "1"}

    } else {

        token = jwt.sign({vz: "1"}, Buffer.from("LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU=", 'base64'), {header: { typ: undefined}, expiresIn: "1d"})
        return {token,zone: "1"}
       }
}

