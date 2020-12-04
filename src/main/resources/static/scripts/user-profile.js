$('document').ready(function(){
    var password = document.getElementById("password-update")
    var confirmPassword = document.getElementById("password-update-confirm");

    function validatePassword(){
        if(password.value !== confirmPassword.value) {
            confirmPassword.setCustomValidity("Passwords Don't Match");
        } else {
            confirmPassword.setCustomValidity('');
        }
    }
    password.onchange = validatePassword;
    confirmPassword.onkeyup = validatePassword;
});
