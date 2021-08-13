$(document).ready(function () {
    $("#session").change(function () {
        let sessionId = $("#session").val();

        $('label').each(function () {
            $(this).html('Место свободно').css('color', 'green');
        })

        const radio = document.getElementsByName("place");
        for (let i = 0; i < radio.length; i++) {
            radio[i].removeAttribute('disabled');
        }

        loadTickets(sessionId);
    });
});

function start() {
    window.timerId = window.setInterval(loadTickets, 1000);
    loadFilms();
}

function loadFilms() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/job4j_cinema/films.do",
        dataType: "json",
        success: function (data) {
            const options = $("#session");
            $.each(data, function (index) {
                options.append('<option value=' + data[index].sessionId + '>' + data[index].name + '</option>');
            });
        }
    });
}

function loadTickets(sessionId) {


    $.ajax({
        type: "GET",
        url: "http://localhost:8080/job4j_cinema/tickets.do",
        data: {
            "sessionId": sessionId
        },
        dataType: "json",
        success: function (data) {
            $.each(data, function (index) {
                let row = data[index].row;
                let cell = data[index].cell;
                let seat = row + "_" + cell;
                $('input').each(
                    function () {
                        let val = $(this).attr('value');
                        if (val === seat) {
                            $(this).attr("disabled", true);
                            $(this).siblings('label').html('Место занято').css('color', 'red');
                        }
                    })
            })
        }
    })
}

function redirectPayment() {
    let seat = $('input:checked').val();
    let film = $('#session').val();
    if (seat !== undefined && film !== "null") {
        window.location.href = 'http://localhost:8080/job4j_cinema/payment.jsp?seat=' + seat + '&sessionId=' + film;
    } else {
        alert("Выберите, пожалуйста, место и фильм");
    }
}