const sendMessage = (to) => {
    const req = new XMLHttpRequest();
    const text = document.getElementById("message").value;
    if (text.length < 1) return;

    req.open("post", "/messages", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send(`receiver=${to}&message=${text}`);
    req.onload = () => {
        let text = req.responseText;
        console.log(text);
        console.log(typeof text);
        if (text.length > 0) {
            window.location.replace(window.location.pathname);
        }
    }
};