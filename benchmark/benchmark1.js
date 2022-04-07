'use strict';
const jwt = require('jsonwebtoken');

module.exports =  function() {
    const key = "LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU="
    const random_value = Math.random()
    let token
    if (random_value < 0.5) {
        token = jwt.sign(
            { vz: "1" },
            "wrong-key",
            { expiresIn: "10h" }
        )
    } else {
        token = jwt.sign(
            { vz: "1", sub: 500 },
            Buffer.from(key, "base64"),
            { header: { typ: undefined }, expiresIn: "1d"}
        )
    }
    return {token, zone: "1"}
}
