const sgMail = require('@sendgrid/mail')

sgMail.setApiKey(process.env.SENDGRID_API_KEY)

const sendWelcomeEmail = (email, name) => {
    sgMail.send({
        to: email,
        from: 'joaojesus_94@hotmail.com',
        subject: 'Thanks for joining in!',
        text: `Welcome to the app ${name}. Let me know how you get along with the app.`
    })
}

const sendGoodbyeEmail = (email, name) => {
    sgMail.send({
        to: email,
        from: 'joaojesus_94@hotmail.com',
        subject: 'Good bye ',
        text: `It was nice to have you aboard ${name}. Let me know why are you leaving.`
    })
}
module.exports = {
    sendWelcomeEmail,
    sendGoodbyeEmail,
}