const mongoose = require("mongoose");
const bcrypt = require("bcrypt");


const UserSchema = new mongoose.Schema({

    userName: {
        type: String,
        required: [true, "userName is required"],
        minlength: [3, "userName must be 3 characters or longer"]
    },
    email: {
        type: String,
        required: [true, "Email is required"],
        validate: {
            validator: val => /^([\w-\.]+@([\w-]+\.)+[\w-]+)?$/.test(val),
            message: "Please enter a valid email"
        }
    },
    password: {
        type: String,
        required: [true, "Password is required"],
        minlength: [8, "Password must be 8 characters or longer"]
    }

}, { timestamps: true });

// I like to shorten confirmPassword to just confirm
UserSchema.virtual('confirm')
    .get(() => this._confirm)
    .set(value => this._confirm = value);


// this checks before validations are run
UserSchema.pre('validate', function (next) {
    if (this.password !== this.confirm) {
        this.invalidate('confirm', 'Password must match confirm password');
    }
    next();
});

// this runs before the user is saved to the db
// gotcha this also runs if we update the user
UserSchema.pre('save', function (next) {
    bcrypt.hash(this.password, 10)
        .then(hash => {
            this.password = hash;
            next();
        });
});

module.exports = mongoose.model("User", UserSchema);