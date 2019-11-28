function sendLike() {
    const req = new XMLHttpRequest();
    const id = document.getElementsByName("user_id")[0].value;
    req.open("post", "/users", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send(`id=${id}&liked=true`);
    req.onload = () => {
        let text = req.responseText;
        console.log(text);
        console.log(typeof text);
        if (text.length > 0) {
            // console.log("redirecting");
            window.location.replace("/users");
        }
    }
}

function sendDisLike() {
    const req = new XMLHttpRequest();
    const id = document.getElementsByName("user_id")[0].value;
    req.open("post", "/users", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send(`id=${id}&liked=true`);
}