const request = require('request')

const forecast = (latitude, longitude, callback) => {
    const url = 'http://api.weatherstack.com/current?access_key=5a3521f19ef9755e4081c4cc04d79eea&query=' + latitude + ',' + longitude + '&units=m'

    request(url, {json: true}, (error, {body}) => {
        if (error) {
            callback('Unable to connect to weather service!', undefined)
        } else if (body.error) {
            callback('Unable to find location', undefined)
        } else {
            const {current} = body
            callback(undefined, current)
        }
    })
}

module.exports = forecast