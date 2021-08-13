let sessionId;

document.addEventListener("DOMContentLoaded", function parseUrl() {

    let temp = window.location.search.split('&');
    let seat = temp[0].split('=');
    let rowCell = seat[1].split('_');
    let row = rowCell[0];
    let cell = rowCell[1];
    let tempSessionId = temp[1].split('=');
    sessionId = tempSessionId[1];
    $("#head3").html(" Вы выбрали ряд " + row + " место " + cell + ", Сумма : 500 рублей.");
    $("#rowId").val(row);
    $("#cellId").val(cell);
});

function validate() {
    let rsl = true;
    if ($('#username').val() === "") {
        alert("Введите ФИО");
        rsl = false;
    }
    if ($('#phone').val() === "") {
        alert("Введите номер телефона");
        rsl = false;
    }
    if ($('#email').val() === "") {
        alert("Введите email");
        rsl = false;
    }
    return rsl;
}

function pay() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_cinema/pay.do',
        data: {
            "sessionId": sessionId,
            "row": $("#rowId").val(),
            "cell": $("#cellId").val(),
            "username": $("#username").val(),
            "email": $("#email").val(),
            "phone": $("#phone").val()
        },
        success: function (response) {
            alert(response);
            window.location.href = 'index.html'
        }
    })
}

function btnClick() {
    if (validate()) {
        pay();
    }
}