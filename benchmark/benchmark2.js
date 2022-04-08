'use strict';
const jwt = require('jsonwebtoken');

// Changes max range for tickets ids
const testValue=2000

module.exports =  function() {
    const key = "LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU="
    const random_value = Math.floor(Math.random() * testValue)
    let threshold = (testValue % 2 === 0) ? testValue/2 : (testValue-1)/2
    let token
    if (random_value < threshold) {
        token = jwt.sign(
            { vz: "1", sub: random_value },
            "wrong-key",
            { expiresIn: "10h" }
        )
    } else {
        token = jwt.sign(
            { vz: "1", sub: random_value },
            Buffer.from(key, "base64"),
            { header: { typ: undefined }, expiresIn: "1d" }
        )
    }
    return { token, zone: "1" }
}
