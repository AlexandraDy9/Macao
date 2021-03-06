<%@ page
        contentType="text/html; charset=windows-1256"
        pageEncoding="windows-1256"
        import="model.User" %>
<%@ page import="model.Room" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="rooms_view.css">
    <title>Macao</title>
</head>

<% User user = (User) session.getAttribute("currentSessionUser"); %>
<% List<Room> rooms = (List<Room>) session.getAttribute("rooms"); %>

<body>
<header class="logout">
    <a style="margin-right: 100px">Welcome   <%=user.getUsername() %>
    </a>
    <a href="index.jsp" class="logout-button" type="submit" name="join">Logout</a>
</header>
<main class="main">
    <h1 class="room-title">Rooms</h1>
    <div class="card-container">
        <%for (int i = 0; i < rooms.size(); i++) {%>
        <%Room room = rooms.get(i);%>
        <button type="submit" value="<%=room.getId()%>" players="<%=room.getJoinedUsers()%>" class="join game-card">
            <span class="number-players"> <%=room.getJoinedUsers()%> / 6 </span>
            <span class="join-button">Join</span>
        </button>

        <% } %>
    </div>
</main>
<footer class="footer">
    <div id="alert" class="alert">
        <span id="close-alert" class="close-error">&times;</span>
        Camera este plina, te rog selecteaza alta camera.
    </div>
    <form class="new-game-form" action="rooms" method="post">
        <button class="new-game-button" type="submit" name="newGame">New Game</button>
    </form>
</footer>
<script>
    var closeAlert = document.getElementById("close-alert");
    var alert = document.getElementById("alert");
    var joinButtons = document.getElementsByClassName("join");

    Array.from(joinButtons).forEach(function (button) {
        button.addEventListener('click', function () {
            var players = button.getAttribute("players");
            if (players >= 6) {
                alert.style.opacity = "1";
            } else {
                var value = button.value;
                fetch('rooms?' + 'roomId=' + value, {
                    method: "POST"
                })
                    .then(function (data) {
                        window.location.href = data.url;
                    })
            }
        })
    });


    function getRooms() {
        fetch('rooms', {
            method: "GET"
        })
            .then(function (data) {
                var room = data;

                var contentElement = document.getElementById("card-container");
                for (var i = 0; i < room.length; i ++) {
                    var a = "<button type=\"submit\" value=\" "  + room[i].id + "\" players=\" " + room[i].joinedUsers + "\" class=\"join game-card\">" +
                        "<span class=\"number-players\">" + room[i].joinedUsers / 6 + "</span>" +
                        "<span class=\"join-button\"> Join </span>" + "</button>";

                    contentElement.innerHTML = a + contentElement.innerHTML;
                }

            })
    }

    setInterval(getRooms, 1000);


    closeAlert.addEventListener('click', function () {
        alert.style.opacity = "0";
    });

</script>
</body>

</html>