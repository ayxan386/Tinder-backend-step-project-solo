const sendLike = (source) =>
{
    var req = new XMLHttpRequest();
    req.open("post", "/users",true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send({
        id: document.getElementsByName("user_id")[0].value,
        liked: true
    });
    req.onreadystatechange = () =>{

        }
}
const sendDisLike = (source) =>
{
    var req = new XMLHttpRequest();
    req.open("post", "/users",true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send({
        id: document.getElementsByName("user_id")[0].value,
        liked: false
    });
}